package com.nevermindsoftware.urlparser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class SuffixRuleTest {

    @Test
    fun `sets properties for non-exception rule`() {
        val rule = SuffixRule("co.uk")

        assertFalse(rule.isException)
        assertEquals("co.uk", rule.raw)
        assertEquals(listOf("uk", "co"), rule.labels)
        assertEquals(listOf("uk", "co"), rule.matchableLabels)
    }

    @Test
    fun `sets properties for exception rule`() {
        val rule = SuffixRule("!city.kawasaki.jp")

        assertTrue(rule.isException)
        assertEquals("!city.kawasaki.jp", rule.raw)
        assertEquals(listOf("jp", "kawasaki", "city", "!"), rule.labels)
        assertEquals(listOf("jp", "kawasaki", "city"), rule.matchableLabels)
    }

    @Test
    fun `apply - returns matched parts for host`() {
        val rule = SuffixRule("co.uk")
        val hostParts = "www.example.co.uk".split(".").reversed()
        val expected = "co.uk".split(".").reversed()
        assertEquals(expected, rule.match(hostParts))
    }

    @Test
    fun `apply - returns matched parts for host for exception rule`() {
        val rule = SuffixRule("!example.co.uk")
        val hostParts = "www.example.co.uk".split(".").reversed()
        val expected = "co.uk".split(".").reversed()
        assertEquals(expected, rule.match(hostParts))
    }

}