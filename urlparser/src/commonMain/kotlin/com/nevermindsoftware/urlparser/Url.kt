package com.nevermindsoftware.urlparser

/**
 * Data class containing a parsed url. The queryParams property is lazily evaluated from the queryStr
 */
data class Url(
    /** The raw string used to parse this Url */
    val raw: String?,
    /** The url scheme (e.g. http, https, ftp) */
    val scheme: String? = null,
    /** The url host, ie the last group before the TLD */
    val host: String? = null,
    /** The TLD for this Url, as specified by the Suffix List */
    val tld: String? = null,
    /** The path following the TLD. Prefixed with "/" */
    val path: String? = null,
    /** The raw query string. Prefixed with "?" */
    val queryStr: String? = null,
    /** A list of sub domain groups, not including the host (e.g. a.b.c.example.com = [a,b,c]) */
    val subDomains: List<String> = emptyList()
) {
    /**
     * Lazily evaluated map of query parameter names to a list of parameter values, to account for empty parameters
     * and parameters specified multiple times
     * */
    val queryParams: Map<String, List<String>> by lazy { initQueryParams() }

    private fun initQueryParams(): Map<String, List<String>> {
        val paramStr = sanitisedQueryParamStr() ?: return emptyMap()
        val params = mutableMapOf<String, List<String>>()

        paramStr.split("&")
            .forEach {
                val s = it.split("=")
                if (params.containsKey(s[0])) {
                    (params[s[0]] as MutableList).add(s[1])
                } else {
                    params[s[0]] = mutableListOf(s[1])
                }
            }

        return params
    }

    private fun sanitisedQueryParamStr(): String? {
        if (queryStr.isNullOrBlank()) return null
        var v = queryStr.trim()
        v = if (v.startsWith("?")) v.substring(1) else v
        if (v.isBlank()) return null
        return v
    }

}