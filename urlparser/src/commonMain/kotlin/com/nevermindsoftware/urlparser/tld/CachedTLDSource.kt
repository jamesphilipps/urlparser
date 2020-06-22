package com.nevermindsoftware.urlparser.tld

import com.nevermindsoftware.urlparser.SuffixRule

/**
 * Wrapper for a TLD source that intercepts and caches the results of the first invocation
 */
class CachedTLDSource(
    /** The source to retrieve and cache data from */
    private val source: TLDSource
) : TLDSource {
    private var cache: List<SuffixRule>? = null

    /**
     * Returns true if the cache is currently initialized and holding data from a previous invocation
     */
    val isCached: Boolean get() = cache != null

    override fun get(): List<SuffixRule> = cache ?: source.get().also { cache = it }

    /**
     * Removes any cached data. The next invocation will retrieve and cache from the provided source
     */
    fun flush() {
        cache = null
    }
}

