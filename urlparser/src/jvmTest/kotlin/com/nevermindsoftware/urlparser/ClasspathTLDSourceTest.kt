package com.nevermindsoftware.urlparser

import com.nevermindsoftware.urlparser.tld.ClasspathTLDSource
import kotlin.test.Test
import kotlin.test.assertTrue

class ClasspathTLDSourceTest {

    @Test
    fun `can read rules from resources`() {
        val rules = ClasspathTLDSource().get()
        assertTrue(rules.isNotEmpty())
    }

}