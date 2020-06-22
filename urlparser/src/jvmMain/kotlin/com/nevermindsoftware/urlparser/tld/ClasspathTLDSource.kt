package com.nevermindsoftware.urlparser.tld

import com.nevermindsoftware.urlparser.SuffixRule
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.streams.toList

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