[urlparser](../../index.md) / [com.nevermindsoftware.urlparser.tld](../index.md) / [CachedTLDSource](./index.md)

# CachedTLDSource

`class CachedTLDSource : `[`TLDSource`](../-t-l-d-source/index.md)

Wrapper for a TLD source that intercepts and caches the results of the first invocation

### Constructors

| [&lt;init&gt;](-init-.md) | Wrapper for a TLD source that intercepts and caches the results of the first invocation`CachedTLDSource(source: `[`TLDSource`](../-t-l-d-source/index.md)`)` |

### Properties

| [isCached](is-cached.md) | Returns true if the cache is currently initialized and holding data from a previous invocation`val isCached: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| [flush](flush.md) | Removes any cached data. The next invocation will retrieve and cache from the provided source`fun flush(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [get](get.md) | Get a list of TLD suffix rules as per the Mozilla Suffix List specifications`fun get(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`SuffixRule`](../../com.nevermindsoftware.urlparser/-suffix-rule/index.md)`>` |

