package com.nevermindsoftware.urlparser.tld

import com.nevermindsoftware.urlparser.SuffixRule

/**
 * Interface specifying a provider of TLD SuffixRules. It should return a list of SuffixRules. Caching is down to the
 * implementation and is not required, but the TLDSource will be queried on each usage of the parse methods of a UrlParser
 */
interface TLDSource {
    /**
     * Get a list of TLD suffix rules as per the Mozilla Suffix List specifications
     */
    fun get(): List<SuffixRule>
}

expect class ClasspathTLDSource : TLDSource {

}
