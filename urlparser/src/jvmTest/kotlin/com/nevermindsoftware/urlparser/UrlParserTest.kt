package com.nevermindsoftware.urlparser


import com.nevermindsoftware.urlparser.tld.ClasspathTLDSource
import com.nevermindsoftware.urlparser.tld.TLDSource
import kotlin.test.Test
import kotlin.test.assertEquals


class UrlParserJvmTest {
    class TestTLDSource(private val rawRules: List<String>) :
        TLDSource {
        override fun get(): List<SuffixRule> = rawRules.map { SuffixRule(it) }
    }

    private fun createParser(tldSource: TLDSource = TestTLDSource(emptyList())) = UrlParser(tldSource)


    @Test
    fun `parse - matches complex urls`() {
        fun input(
            raw: String, scheme: String? = null, host: String? = null, tld: String? = null, path: String? = null,
            queryStr: String? = null, subDomains: List<String> = emptyList()
        ): Pair<String, Url> = Pair(
            raw,
            Url(
                raw = raw, scheme = scheme, host = host, tld = tld, path = path, queryStr = queryStr,
                subDomains = subDomains
            )
        )

        val inputs = mapOf(
            input("example.com", null, "example", "com"),
            input("http://example.com", "http", "example", "com"),
            input("http://www.example.com", "http", "example", "com", subDomains = listOf("www")),
            input("http://a.b.c.example.com", "http", "example", "com", subDomains = listOf("a", "b", "c")),
            input("http://example.com/a/b/c", "http", "example", "com", path = "/a/b/c"),
            input("http://example.com/a/b/c?x=1&y=2", "http", "example", "com", path = "/a/b/c", queryStr = "?x=1&y=2"),
            input(
                "https://a.b.c.example.city.kobe.jp/a/b/c?x=1&y=2",
                "https",
                "city",
                "kobe.jp",
                subDomains = listOf("a", "b", "c", "example"),
                path = "/a/b/c",
                queryStr = "?x=1&y=2"
            )
        )
        val parser = createParser(ClasspathTLDSource())

        inputs.forEach {
            assertEquals(it.value, parser.parse(it.key))
        }

    }

    @Test
    fun `getTld matches suffix list rules correctly`() {
        val parser = createParser(ClasspathTLDSource())

        // Mixed case.
        assertEquals("com", parser.getTLD("COM"))
        assertEquals("com", parser.getTLD("example.COM"))
        assertEquals("com", parser.getTLD("WwW.example.COM"))

        // Leading dot.
        assertEquals("com", parser.getTLD(".com"))
        assertEquals("com", parser.getTLD(".example.com"))

        // Unlisted TLD.
        assertEquals("example", parser.getTLD("example"))
        assertEquals("example", parser.getTLD("foo.example"))
        assertEquals("example", parser.getTLD("example.example"))
        assertEquals("example", parser.getTLD("b.foo.example"))
        assertEquals("example", parser.getTLD("a.b.foo.example"))

        // TLD with 1 rule.
        assertEquals("biz", parser.getTLD("biz"))
        assertEquals("biz", parser.getTLD("domain.biz"))
        assertEquals("biz", parser.getTLD("b.domain.biz"))
        assertEquals("biz", parser.getTLD("a.b.domain.biz"))

        // TLD with 2-level rules.
        assertEquals("uk.com", parser.getTLD("uk.com"))
        assertEquals("uk.com", parser.getTLD("example.uk.com"))
        assertEquals("uk.com", parser.getTLD("b.example.uk.com"))
        assertEquals("uk.com", parser.getTLD("a.b.example.uk.com"))

        // TLD with wildcard rule.
        assertEquals("example.awdev.ca", parser.getTLD("example.awdev.ca"))
        assertEquals("example.awdev.ca", parser.getTLD("www.example.awdev.ca"))
        assertEquals("example.awdev.ca", parser.getTLD("a.b.www.example.awdev.ca"))

        // More complex TLD.
        assertEquals("jp", parser.getTLD("jp"))
        assertEquals("jp", parser.getTLD("test.jp"))
        assertEquals("jp", parser.getTLD("www.test.jp"))
        assertEquals("ac.jp", parser.getTLD("ac.jp"))
        assertEquals("ac.jp", parser.getTLD("test.ac.jp"))
        assertEquals("ac.jp", parser.getTLD("www.test.ac.jp"))
        assertEquals("kyoto.jp", parser.getTLD("kyoto.jp"))
        assertEquals("kyoto.jp", parser.getTLD("test.kyoto.jp"))
        assertEquals("ide.kyoto.jp", parser.getTLD("ide.kyoto.jp"))
        assertEquals("ide.kyoto.jp", parser.getTLD("b.ide.kyoto.jp"))
        assertEquals("ide.kyoto.jp", parser.getTLD("a.b.ide.kyoto.jp"))
        assertEquals("c.kobe.jp", parser.getTLD("c.kobe.jp"))
        assertEquals("c.kobe.jp", parser.getTLD("b.c.kobe.jp"))
        assertEquals("c.kobe.jp", parser.getTLD("a.b.c.kobe.jp"))

        // TLD with exceptions.
        assertEquals("kobe.jp", parser.getTLD("city.kobe.jp"))
        assertEquals("kobe.jp", parser.getTLD("www.city.kobe.jp"))
        assertEquals("kobe.jp", parser.getTLD("a.b.www.city.kobe.jp"))
        assertEquals("kawasaki.jp", parser.getTLD("city.kawasaki.jp"))
        assertEquals("kawasaki.jp", parser.getTLD("www.city.kawasaki.jp"))
        assertEquals("kawasaki.jp", parser.getTLD("a.b.www.city.kawasaki.jp"))

        // US K12.
        assertEquals("us", parser.getTLD("us"))
        assertEquals("us", parser.getTLD("test.us"))
        assertEquals("us", parser.getTLD("www.test.us"))
        assertEquals("ak.us", parser.getTLD("ak.us"))
        assertEquals("ak.us", parser.getTLD("test.ak.us"))
        assertEquals("ak.us", parser.getTLD("www.test.ak.us"))
        assertEquals("k12.ak.us", parser.getTLD("k12.ak.us"))
        assertEquals("k12.ak.us", parser.getTLD("test.k12.ak.us"))
        assertEquals("k12.ak.us", parser.getTLD("www.test.k12.ak.us"))
    }
}
 