[urlparser](../../index.md) / [com.nevermindsoftware.urlparser](../index.md) / [Url](./index.md)

# Url

`data class Url`

Data class containing a parsed url. The queryParams property is lazily evaluated from the queryStr

### Constructors

| [&lt;init&gt;](-init-.md) | Data class containing a parsed url. The queryParams property is lazily evaluated from the queryStr`Url(raw: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, scheme: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, host: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, tld: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, path: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, queryStr: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, subDomains: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`> = emptyList())` |

### Properties

| [host](host.md) | The url host, ie the last group before the TLD`val host: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [path](path.md) | The path following the TLD. Prefixed with "/"`val path: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [queryParams](query-params.md) | Lazily evaluated map of query parameter names to a list of parameter values, to account for empty parameters and parameters specified multiple times`val queryParams: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>` |
| [queryStr](query-str.md) | The raw query string. Prefixed with "?"`val queryStr: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [raw](raw.md) | The raw string used to parse this Url`val raw: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [scheme](scheme.md) | The url scheme (e.g. http, https, ftp)`val scheme: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [subDomains](sub-domains.md) | A list of sub domain groups, not including the host (e.g. a.b.c.example.com = [a,b,c](#))`val subDomains: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [tld](tld.md) | The TLD for this Url, as specified by the Suffix List`val tld: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |

