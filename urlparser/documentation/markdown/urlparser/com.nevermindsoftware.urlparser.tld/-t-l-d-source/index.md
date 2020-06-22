[urlparser](../../index.md) / [com.nevermindsoftware.urlparser.tld](../index.md) / [TLDSource](./index.md)

# TLDSource

`interface TLDSource`

Interface specifying a provider of TLD SuffixRules. It should return a list of SuffixRules. Caching is down to the
implementation and is not required, but the TLDSource will be queried on each usage of the parse methods of a UrlParser

### Functions

| [get](get.md) | Get a list of TLD suffix rules as per the Mozilla Suffix List specifications`abstract fun get(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`SuffixRule`](../../com.nevermindsoftware.urlparser/-suffix-rule/index.md)`>` |

### Inheritors

| [CachedTLDSource](../-cached-t-l-d-source/index.md) | Wrapper for a TLD source that intercepts and caches the results of the first invocation`class CachedTLDSource : `[`TLDSource`](./index.md) |
| [ClasspathTLDSource](../-classpath-t-l-d-source/index.md) | `class ClasspathTLDSource : `[`TLDSource`](./index.md) |

