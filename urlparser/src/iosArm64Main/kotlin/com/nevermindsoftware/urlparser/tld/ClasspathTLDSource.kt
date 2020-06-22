package com.nevermindsoftware.urlparser.tld

import com.nevermindsoftware.urlparser.SuffixRule

actual class ClasspathTLDSource : TLDSource {

    override fun get(): List<SuffixRule> {
        throw NotImplementedError("Only applicable to JVM applications. Cannot be used on OSX")
    }
}