package com.nevermindsoftware.urlparser

import com.nevermindsoftware.urlparser.tld.CachedTLDSource
import com.nevermindsoftware.urlparser.tld.TLDSource
import kotlin.test.Test
import kotlin.test.assertEquals


class CachedTLDSourceTest {
    class TestSource(var returnValue: List<SuffixRule>) : TLDSource {
        override fun get(): List<SuffixRule> = returnValue
    }

    val sr1 = SuffixRule("co.uk")
    val sr2 = SuffixRule("com")

    @Test
    fun `returns correct result after first invocation`() {
        val testSource = TestSource(listOf(sr1))
        val source = CachedTLDSource(testSource)
        val result = source.get()

        assertEquals(listOf(sr1), result)
    }

    @Test
    fun `caches result after first invocation`() {
        val testSource = TestSource(listOf(sr1))
        val source = CachedTLDSource(testSource)
        val result = source.get()
        testSource.returnValue = listOf(sr2)

        assertEquals(listOf(sr1), result)
    }

    @Test
    fun `returns cached result after subsequent invocation`() {
        val testSource = TestSource(listOf(sr1))
        val source = CachedTLDSource(testSource)

        source.get()
        val result = source.get()

        assertEquals(listOf(sr1), result)
    }

    @Test
    fun `returns cached result after subsequent invocation when source has changed`() {
        val testSource = TestSource(listOf(sr1))
        val source = CachedTLDSource(testSource)

        source.get()
        testSource.returnValue = listOf(sr2)
        val result = source.get()

        assertEquals(listOf(sr1), result)
    }

    @Test
    fun `returns fresh result after cache is flushed`() {
        val testSource = TestSource(listOf(sr1))
        val source = CachedTLDSource(testSource)

        var result = source.get()
        assertEquals(listOf(sr1), result)

        source.flush()
        testSource.returnValue = listOf(sr2)
        result = source.get()
        assertEquals(listOf(sr2), result)
    }


}