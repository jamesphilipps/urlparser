package com.nevermindsoftware.urlparser

import com.nevermindsoftware.urlparser.tld.TLDSource


open class UrlParser(private val tldSource: TLDSource) {
    private class MutableUrl(
        val raw: String,
        var scheme: String? = null,
        var host: String? = null,
        var tld: String? = null,
        var path: String? = null,
        var queryStr: String? = null,
        var subDomains: List<String> = emptyList()
    ) {
        var current: String = raw

        fun toUrl() =
            Url(raw=raw,scheme = scheme, host = host, tld = tld, path = path, queryStr = queryStr, subDomains = subDomains)
    }

    fun parse(url: String): Url {
        val mUrl = MutableUrl(raw = url)
        consumeScheme(mUrl)
        consumeQueryStr(mUrl)
        consumePath(mUrl)
        consumeTLD(mUrl)
        consumeHost(mUrl)
        consumeSubDomains(mUrl)

        return mUrl.toUrl()
    }

    private fun consumeScheme(mUrl: MutableUrl) {
        val url = mUrl.current
        val boundaryMarker = "://"
        val boundary = url.indexOf(boundaryMarker)
        if (boundary >= 0) {
            mUrl.scheme = url.substring(0, boundary)
            mUrl.current = url.substring(boundary + boundaryMarker.length, url.length)
        }
    }

    private fun consumeQueryStr(mUrl: MutableUrl) {
        val url = mUrl.current
        val boundaryMarker = "?"
        val boundary = url.lastIndexOf(boundaryMarker)
        if (boundary >= 0) {
            mUrl.queryStr = nullIfEmpty(url.substring(boundary + 1), prefix = "?")
            mUrl.current = url.substring(0, boundary)
        }
    }

    private fun consumePath(mUrl: MutableUrl) {
        val url = mUrl.current
        val boundaryMarker = "/"
        val boundary = url.indexOf(boundaryMarker)
        if (boundary >= 0) {
            mUrl.path = nullIfEmpty(url.substring(boundary + 1), prefix = "/")
            mUrl.current = url.substring(0, boundary)
        }
    }

    private fun consumeTLD(mUrl: MutableUrl) {
        val url = mUrl.current
        val tld = getTLD(url)
        val boundary = url.lastIndexOf(tld)
        mUrl.tld = tld
        mUrl.current = url.substring(0, boundary - 1)
    }

    private fun consumeHost(mUrl: MutableUrl) {
        val url = mUrl.current
        val boundaryMarker = "."
        val boundary = url.lastIndexOf(boundaryMarker)
        if (boundary >= 0) {
            mUrl.host = nullIfEmpty(url.substring(boundary + 1))
            mUrl.current = url.substring(0, boundary)
        } else {
            mUrl.host = url
            mUrl.current = ""
        }
    }

    private fun consumeSubDomains(mUrl: MutableUrl) {
        val url = mUrl.current
        if (!url.isBlank()) {
            mUrl.subDomains = url.split(".")
            mUrl.current = ""
        }
    }


    fun getTLD(host: String): String {
        val hostParts = host
            .split(".") // Get individual parts
            .map { it.toLowerCase() } // Standardise to lowercase for rule matching
            .reversed() // Rules are matched from the end of the string backwards

        return getBestTldRule(hostParts)
            .match(hostParts)
            .reversed()
            .joinToString(separator = ".")
    }

    private fun getBestTldRule(hostParts: List<String>): SuffixRule {
        val rules = tldSource.get()

        val matches = rules
            .filter { it.matchableLabels.size <= hostParts.size } // Host must have at least as many parts as the rule
            .filter { rule ->
                rule.matchableLabels.withIndex()
                    .map { it.value == hostParts[it.index] || it.value == "*" } // Part must either match or be a wildcard
                    .reduce { acc, v -> acc && v } // Rule matches if each part matches
            }


        if (matches.isEmpty()) {
            return SuffixRule("*")
        }

        if (matches.size == 1) {
            return matches.first()
        }

        // If there is an exception rule, it wins
        val exception = getExceptionRule(matches)
        if (exception != null) {
            return exception
        }

        // Longest match wins
        return matches.sortedBy { it.labels.size }.last()
    }

    private fun getExceptionRule(rules: List<SuffixRule>): SuffixRule? {
        val exceptions = rules.filter { it.isException }
        return when (exceptions.size) {
            0 -> null
            1 -> exceptions.first()
            else ->
                throw IllegalArgumentException("Rules list contained multiple exception rules for pattern: ${exceptions.map { it.raw }}")
        }
    }

    private fun nullIfEmpty(v: String?, prefix: String? = null): String? {
        return if (v != null && !v.trim().isEmpty()) {
            val r = v.trim()
            if (prefix != null) "$prefix$r" else r
        } else null
    }
}