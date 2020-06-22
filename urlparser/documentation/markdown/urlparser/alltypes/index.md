

### All Types

|

##### [com.nevermindsoftware.urlparser.tld.CachedTLDSource](../com.nevermindsoftware.urlparser.tld/-cached-t-l-d-source/index.md)

Wrapper for a TLD source that intercepts and caches the results of the first invocation


|

##### [com.nevermindsoftware.urlparser.tld.ClasspathTLDSource](../com.nevermindsoftware.urlparser.tld/-classpath-t-l-d-source/index.md)


|

##### [com.nevermindsoftware.urlparser.SuffixRule](../com.nevermindsoftware.urlparser/-suffix-rule/index.md)

Data class representing a parsed rule from the TLD Suffix List


|

##### [com.nevermindsoftware.urlparser.tld.TLDSource](../com.nevermindsoftware.urlparser.tld/-t-l-d-source/index.md)

Interface specifying a provider of TLD SuffixRules. It should return a list of SuffixRules. Caching is down to the
implementation and is not required, but the TLDSource will be queried on each usage of the parse methods of a UrlParser


|

##### [com.nevermindsoftware.urlparser.Url](../com.nevermindsoftware.urlparser/-url/index.md)

Data class containing a parsed url. The queryParams property is lazily evaluated from the queryStr


|

##### [com.nevermindsoftware.urlparser.UrlParser](../com.nevermindsoftware.urlparser/-url-parser/index.md)

Core Url Parser instance. Uses a TLD source to load TLD suffixes to parse raw string domains


