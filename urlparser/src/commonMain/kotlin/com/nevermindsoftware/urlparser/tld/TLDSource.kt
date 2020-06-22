package com.nevermindsoftware.urlparser.tld

import com.nevermindsoftware.urlparser.SuffixRule


interface TLDSource {
    fun get(): List<SuffixRule>
}

expect class ClasspathTLDSource : TLDSource{

}
