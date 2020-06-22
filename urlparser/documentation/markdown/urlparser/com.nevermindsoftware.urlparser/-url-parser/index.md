[urlparser](../../index.md) / [com.nevermindsoftware.urlparser](../index.md) / [UrlParser](./index.md)

# UrlParser

`open class UrlParser`

Core Url Parser instance. Uses a TLD source to load TLD suffixes to parse raw string domains

### Constructors

| [&lt;init&gt;](-init-.md) | Core Url Parser instance. Uses a TLD source to load TLD suffixes to parse raw string domains`UrlParser(tldSource: `[`TLDSource`](../../com.nevermindsoftware.urlparser.tld/-t-l-d-source/index.md)`)` |

### Functions

| [getTLD](get-t-l-d.md) | Get the tld from a url host. Note that this method does not expect a full url, only the host part (e.g. example.co.uk)`fun getTLD(host: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [parse](parse.md) | Parses a raw url string into a Url object`fun parse(url: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Url`](../-url/index.md) |

