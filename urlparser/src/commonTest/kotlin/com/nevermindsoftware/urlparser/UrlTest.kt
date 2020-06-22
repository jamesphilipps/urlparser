package com.nevermindsoftware.urlparser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UrlTest {

    @Test
    fun `urlParams - returns empty map when no params`() {
        assertTrue(Url(raw = "", queryStr = null).queryParams.isEmpty())
    }

    @Test
    fun `urlParams - returns empty map when empty queryStr`() {
        assertTrue(Url(raw = "", queryStr = "").queryParams.isEmpty())
    }

    @Test
    fun `urlParams - returns empty map when no params after marker`() {
        assertTrue(Url(raw = "", queryStr = "?").queryParams.isEmpty())
    }

    @Test
    fun `urlParams - returns empty map when whitespace after marker`() {
        assertTrue(Url(raw = "", queryStr = "?  ").queryParams.isEmpty())
    }

    @Test
    fun `urlParams - returns map of empty parameter`() {
        val expected = mapOf(
            "x" to listOf("")
        )
        assertEquals(expected, Url(raw = "", queryStr = "?x=").queryParams)
    }

    @Test
    fun `urlParams - returns map of empty and assigned parameters`() {
        val expected = mapOf(
            "x" to listOf("1"),
            "y" to listOf(""),
            "z" to listOf("2")
        )
        assertEquals(expected, Url(raw = "", queryStr = "?x=1&y=&z=2").queryParams)
    }

    @Test
    fun `urlParams - returns map of single parameter`() {
        val expected = mapOf(
            "x" to listOf("hello")
        )
        assertEquals(expected, Url(raw = "", queryStr = "?x=hello").queryParams)
    }

    @Test
    fun `urlParams - returns map of multiple unique parameter`() {
        val expected = mapOf(
            "x" to listOf("hello"),
            "y" to listOf("wonderful"),
            "z" to listOf("world")
        )
        assertEquals(expected, Url(raw = "", queryStr = "?x=hello&y=wonderful&z=world").queryParams)
    }

    @Test
    fun `urlParams - returns map of single repeated parameter`() {
        val expected = mapOf(
            "x" to listOf("hello", "wonderful", "world")
        )
        assertEquals(expected, Url(raw = "", queryStr = "?x=hello&x=wonderful&x=world").queryParams)
    }

    @Test
    fun `urlParams - returns map of mixed parameters`() {
        val expected = mapOf(
            "x" to listOf("hello", "wonderful"),
            "y" to listOf(""),
            "z" to listOf("world")
        )
        assertEquals(expected, Url(raw = "", queryStr = "?x=hello&y=&x=wonderful&z=world").queryParams)
    }
}
 