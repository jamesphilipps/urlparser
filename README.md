# Url Parser
![badge](https://img.shields.io/badge/license-MIT-green)
![badge](https://img.shields.io/badge/platform-jvm-red)
![badge](https://img.shields.io/badge/platform-ios-d6d6d6)
![badge](https://img.shields.io/badge/platform-android-brightgreen) 
![badge](https://img.shields.io/badge/platform-kotlin--multiplatform-lightgrey)

Url Parser is a no-dependency, cross-platform utility for parsing raw URL strings, using up-to-date TLD definitions 
provided by the Mozilla Public Suffix initiative (https://publicsuffix.org/). It is distributed as a Kotlin Multiplatform library.

Url parser supports:
* Scheme
* Subdomains
* Host
* TLD
* Path
* Query Parameters

# Building
From the root of the project, run `./gradlew build`. The project jars will be available at urlparser/build/lib

You can also publish directly to your local maven repository using `./gradlew publishToMavenLocal` (be sure to add `mavenLocal()` to your project's repositories configuration)

# Usage
The project 
Create a `UrlParser`, passing an object that implements the `TLDSource` interface. A `TLDSource` should return the TLD
rules as provided by the Mozilla Public Suffix list.

On JVM implementations, a `ClasspathTLDLoader` class is provided which will read the TLD definitions from the 
bundled suffix list

The `UrlParser` exposes two methods: `parse` and `getTLD` which will return all url properties, or just the TLD part of a host string respectively

The Suffix list is very large, so it is advised to cache instances of the `UrlParser`

### Example

```kotlin
    val parser = UrlParser(ClasspathTLDParser())
    parser.parse("http://www.example.co.uk") // Url(scheme=http, host=example, tld=co.uk, path=null, queryStr=null, subDomains=[www])
    parser.getTLD("example.co.uk") // "co.uk"
```
