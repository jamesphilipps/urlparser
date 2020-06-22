[urlparser](../index.md) / [com.nevermindsoftware.urlparser.tld](./index.md)

## Package com.nevermindsoftware.urlparser.tld

### Types

| [CachedTLDSource](-cached-t-l-d-source/index.md) | Wrapper for a TLD source that intercepts and caches the results of the first invocation`class CachedTLDSource : `[`TLDSource`](-t-l-d-source/index.md) |
| [ClasspathTLDSource](-classpath-t-l-d-source/index.md) | `class ClasspathTLDSource : `[`TLDSource`](-t-l-d-source/index.md) |
| [TLDSource](-t-l-d-source/index.md) | Interface specifying a provider of TLD SuffixRules. It should return a list of SuffixRules. Caching is down to the implementation and is not required, but the TLDSource will be queried on each usage of the parse methods of a UrlParser`interface TLDSource` |

