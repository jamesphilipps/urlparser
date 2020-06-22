[urlparser](../../index.md) / [com.nevermindsoftware.urlparser](../index.md) / [SuffixRule](index.md) / [match](./match.md)

# match

`fun match(reversedHostGroups: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`

Matches a given list of host groups, in reverse order against this rule's matchable groups
(e.g. matching example.co.uk would expect an input of [uk,co,example](#))

