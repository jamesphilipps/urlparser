package com.nevermindsoftware.urlparser


import com.nevermindsoftware.urlparser.tld.TLDSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UrlParserTest {
    class TestTLDSource(private val rawRules: List<String>) :
        TLDSource {
        override fun get(): List<SuffixRule> = rawRules.map { SuffixRule(it) }
    }

    private fun createParser(tldSource: TLDSource = TestTLDSource(emptyList())) = UrlParser(tldSource)

    @Test
    fun `parse - returns scheme as null if no scheme`() {
        val parser = createParser()
        assertNull(parser.parse("www.example.com").scheme)
    }

    @Test
    fun `parse - returns scheme`() {
        val parser = createParser()
        assertEquals("http", parser.parse("http://www.example.com").scheme)
        assertEquals("https", parser.parse("https://www.example.com").scheme)
        assertEquals("ftp", parser.parse("ftp://www.example.com").scheme)
        assertEquals("ftps", parser.parse("ftps://www.example.com").scheme)
        assertEquals("foo", parser.parse("foo://www.example.com").scheme)
    }

    @Test
    fun `parse - returns queryStr as null if no query params`() {
        val parser = createParser()
        assertNull(parser.parse("www.example.com").queryStr)
    }

    @Test
    fun `parse - returns queryStr as null if empty query params`() {
        val parser = createParser()
        assertNull(parser.parse("www.example.com?").queryStr)
    }

    @Test
    fun `parse - returns queryStr`() {
        val parser = createParser()
        assertEquals("?x=1", parser.parse("https://www.example.com?x=1").queryStr)
        assertEquals("?x=1&y=2&z=avalue", parser.parse("ftp://www.example.com?x=1&y=2&z=avalue").queryStr)
    }

    @Test
    fun `parse - returns path as null if no path`() {
        val parser = createParser()
        assertNull(parser.parse("www.example.com").path)
    }

    @Test
    fun `parse - returns path as null if empty path`() {
        val parser = createParser()
        assertNull(parser.parse("www.example.com/").path)
    }

    @Test
    fun `parse - returns path`() {
        val parser = createParser()
        assertEquals("/A", parser.parse("http://www.example.com/A").path)
        assertEquals("/A/bDir", parser.parse("http://www.example.com/A/bDir").path)
        assertEquals("/A/bDir/", parser.parse("http://www.example.com/A/bDir/").path)
    }

    @Test
    fun `parse - returns TLD`() {
        val parser = createParser(TestTLDSource(listOf("com", "net", "co.uk")))
        assertEquals("com", parser.parse("http://www.example.com").tld)
        assertEquals("co.uk", parser.parse("https://www.example.co.uk").tld)
        assertEquals("net", parser.parse("ftp://www.example.net").tld)
    }

    @Test
    fun `parse - returns host`() {
        val parser = createParser(TestTLDSource(listOf("com")))
        assertEquals("example", parser.parse("example.com").host)
        assertEquals("example", parser.parse("www.example.com").host)
        assertEquals("example", parser.parse("http://example.com").host)
        assertEquals("example", parser.parse("http://www.example.com").host)
    }

    // TODO
//    @Test
//    fun `parse - matches complex urls`() {
//        val inputs = mapOf(
//                "example.com" to Url(null, "example", "com"),
//                "http://example.com" to Url("http", "example", "com"),
//                "http://www.example.com" to Url("http", "example", "com", subDomains = listOf("www")),
//                "http://a.b.c.example.com" to Url("http", "example", "com", subDomains = listOf("a", "b", "c")),
//                "http://example.com/a/b/c" to Url("http", "example", "com", path = "/a/b/c"),
//                "http://example.com/a/b/c?x=1" to Url("http", "example", "com", path = "/a/b/c", queryStr = "?x=1"),
//                "http://example.com/a/b/c?x=1&y=2" to Url("http", "example", "com", path = "/a/b/c", queryStr = "?x=1&y=2"),
//                "https://a.b.c.example.city.kobe.jp/a/b/c?x=1&y=2" to Url("https", "city", "kobe.jp", subDomains = listOf("a", "b", "c", "example"), path = "/a/b/c", queryStr = "?x=1&y=2")
//        )
//        val parser = createParser(ClasspathTldSource())
//
//        inputs.forEach {
//            assertEquals(it.value, parser.parse(it.key))
//        }
//
//    }

    @Test
    fun `getTld - matches single part TLD`() {
        val parser = createParser(TestTLDSource(listOf("com")))

        assertEquals("com", parser.getTLD("com"))
        assertEquals("com", parser.getTLD("example.com"))
        assertEquals("com", parser.getTLD("www.example.com"))
        assertEquals("com", parser.getTLD("a.b.www.example.com"))
    }

    @Test
    fun `getTld - matches two part TLD`() {
        val parser = createParser(TestTLDSource(listOf("co.uk")))

        assertEquals("co.uk", parser.getTLD("co.uk"))
        assertEquals("co.uk", parser.getTLD("example.co.uk"))
        assertEquals("co.uk", parser.getTLD("www.example.co.uk"))
        assertEquals("co.uk", parser.getTLD("a.b.www.example.co.uk"))
    }

    @Test
    fun `getTld - matches three part TLD`() {
        val parser = createParser(TestTLDSource(listOf("u2.xnbay.com")))

        assertEquals("u2.xnbay.com", parser.getTLD("u2.xnbay.com"))
        assertEquals("u2.xnbay.com", parser.getTLD("example.u2.xnbay.com"))
        assertEquals("u2.xnbay.com", parser.getTLD("www.example.u2.xnbay.com"))
        assertEquals("u2.xnbay.com", parser.getTLD("a.b.www.example.u2.xnbay.com"))
    }

    @Test
    fun `getTld - matches leading two part wildcard TLD`() {
        val parser = createParser(TestTLDSource(listOf("*.bd")))

        assertEquals("example.bd", parser.getTLD("example.bd"))
        assertEquals("example.bd", parser.getTLD("www.example.bd"))
        assertEquals("example.bd", parser.getTLD("a.b.www.example.bd"))
    }

    @Test
    fun `getTld - matches leading three part wildcard TLD`() {
        val parser = createParser(TestTLDSource(listOf("*.nom.br")))

        assertEquals("example.nom.br", parser.getTLD("example.nom.br"))
        assertEquals("example.nom.br", parser.getTLD("www.example.nom.br"))
        assertEquals("example.nom.br", parser.getTLD("a.b.www.example.nom.br"))
    }

    @Test
    fun `getTld - matches non-leading three part wildcard TLD`() {
        val parser = createParser(TestTLDSource(listOf("nom.*.br")))

        assertEquals("nom.example.br", parser.getTLD("nom.example.br"))
        assertEquals("nom.example.br", parser.getTLD("www.nom.example.br"))
        assertEquals("nom.example.br", parser.getTLD("a.b.www.nom.example.br"))
    }

    @Test
    fun `getTld - matches multiple wildcard TLD`() {
        val parser = createParser(TestTLDSource(listOf("*.nom.*.br")))

        assertEquals("e1.nom.example.br", parser.getTLD("e1.nom.example.br"))
        assertEquals("e1.nom.example.br", parser.getTLD("www.e1.nom.example.br"))
        assertEquals("e1.nom.example.br", parser.getTLD("a.b.www.e1.nom.example.br"))
    }

    @Test
    fun `getTld - matches leading period`() {
        val parser = createParser(TestTLDSource(listOf("com")))
        assertEquals("com", parser.getTLD(".com"))
    }

    @Test
    fun `getTld - matches exception rule`() {
        val parser = createParser(TestTLDSource(listOf("!city.kobe.jp")))

        assertEquals("kobe.jp", parser.getTLD("city.kobe.jp"))
        assertEquals("kobe.jp", parser.getTLD("example.city.kobe.jp"))
        assertEquals("kobe.jp", parser.getTLD("www.example.city.kobe.jp"))
        assertEquals("kobe.jp", parser.getTLD("a.b.www.example.city.kobe.jp"))
    }

//    @Test
//    fun `getTld matches suffix list rules correctly`() {
//        val parser = createParser(ClasspathTldSource())
//
//        // Mixed case.
//        assertEquals("com", parser.getTLD("COM"))
//        assertEquals("com", parser.getTLD("example.COM"))
//        assertEquals("com", parser.getTLD("WwW.example.COM"))
//
//        // Leading dot.
//        assertEquals("com", parser.getTLD(".com"))
//        assertEquals("com", parser.getTLD(".example.com"))
//
//        // Unlisted TLD.
//        assertEquals("example", parser.getTLD("example"))
//        assertEquals("example", parser.getTLD("foo.example"))
//        assertEquals("example", parser.getTLD("example.example"))
//        assertEquals("example", parser.getTLD("b.foo.example"))
//        assertEquals("example", parser.getTLD("a.b.foo.example"))
//
//        // TLD with 1 rule.
//        assertEquals("biz", parser.getTLD("biz"))
//        assertEquals("biz", parser.getTLD("domain.biz"))
//        assertEquals("biz", parser.getTLD("b.domain.biz"))
//        assertEquals("biz", parser.getTLD("a.b.domain.biz"))
//
//        // TLD with 2-level rules.
//        assertEquals("uk.com", parser.getTLD("uk.com"))
//        assertEquals("uk.com", parser.getTLD("example.uk.com"))
//        assertEquals("uk.com", parser.getTLD("b.example.uk.com"))
//        assertEquals("uk.com", parser.getTLD("a.b.example.uk.com"))
//
//        // TLD with wildcard rule.
//        assertEquals("example.awdev.ca", parser.getTLD("example.awdev.ca"))
//        assertEquals("example.awdev.ca", parser.getTLD("www.example.awdev.ca"))
//        assertEquals("example.awdev.ca", parser.getTLD("a.b.www.example.awdev.ca"))
//
//        // More complex TLD.
//        assertEquals("jp", parser.getTLD("jp"))
//        assertEquals("jp", parser.getTLD("test.jp"))
//        assertEquals("jp", parser.getTLD("www.test.jp"))
//        assertEquals("ac.jp", parser.getTLD("ac.jp"))
//        assertEquals("ac.jp", parser.getTLD("test.ac.jp"))
//        assertEquals("ac.jp", parser.getTLD("www.test.ac.jp"))
//        assertEquals("kyoto.jp", parser.getTLD("kyoto.jp"))
//        assertEquals("kyoto.jp", parser.getTLD("test.kyoto.jp"))
//        assertEquals("ide.kyoto.jp", parser.getTLD("ide.kyoto.jp"))
//        assertEquals("ide.kyoto.jp", parser.getTLD("b.ide.kyoto.jp"))
//        assertEquals("ide.kyoto.jp", parser.getTLD("a.b.ide.kyoto.jp"))
//        assertEquals("c.kobe.jp", parser.getTLD("c.kobe.jp"))
//        assertEquals("c.kobe.jp", parser.getTLD("b.c.kobe.jp"))
//        assertEquals("c.kobe.jp", parser.getTLD("a.b.c.kobe.jp"))
//
//        // TLD with exceptions.
//        assertEquals("kobe.jp", parser.getTLD("city.kobe.jp"))
//        assertEquals("kobe.jp", parser.getTLD("www.city.kobe.jp"))
//        assertEquals("kobe.jp", parser.getTLD("a.b.www.city.kobe.jp"))
//        assertEquals("kawasaki.jp", parser.getTLD("city.kawasaki.jp"))
//        assertEquals("kawasaki.jp", parser.getTLD("www.city.kawasaki.jp"))
//        assertEquals("kawasaki.jp", parser.getTLD("a.b.www.city.kawasaki.jp"))
//
//        // US K12.
//        assertEquals("us", parser.getTLD("us"))
//        assertEquals("us", parser.getTLD("test.us"))
//        assertEquals("us", parser.getTLD("www.test.us"))
//        assertEquals("ak.us", parser.getTLD("ak.us"))
//        assertEquals("ak.us", parser.getTLD("test.ak.us"))
//        assertEquals("ak.us", parser.getTLD("www.test.ak.us"))
//        assertEquals("k12.ak.us", parser.getTLD("k12.ak.us"))
//        assertEquals("k12.ak.us", parser.getTLD("test.k12.ak.us"))
//        assertEquals("k12.ak.us", parser.getTLD("www.test.k12.ak.us"))
//    }
}
 