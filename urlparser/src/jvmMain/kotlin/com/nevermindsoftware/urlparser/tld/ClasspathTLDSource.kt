package com.nevermindsoftware.urlparser.tld

import com.nevermindsoftware.urlparser.SuffixRule
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.streams.toList

/**
 * An implementation of a TLDSource that will attempt to load the bundled SuffixList resource from the classpath
 *
 * This implementation is available to JVM applications only and will throw an exception on any other platform
 */
actual class ClasspathTLDSource : TLDSource {

    override fun get(): List<SuffixRule> {
        val res: InputStream = this.javaClass::class.java.getResourceAsStream("/public_suffix_list.dat")

        BufferedReader(InputStreamReader(res)).use { reader ->
            return reader.lines()
                .map { it.trim() }
                .filter { it.isNotEmpty() && !it.startsWith("//") }
                .map { SuffixRule(it) }
                .toList()
        }
    }
}