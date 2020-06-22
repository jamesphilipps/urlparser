package com.nevermindsoftware.urlparser

data class Url(
    val raw: String?,
    val scheme: String? = null,
    val host: String? = null,
    val tld: String? = null,
    val path: String? = null,
    val queryStr: String? = null,
    val subDomains: List<String> = emptyList()
) {
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