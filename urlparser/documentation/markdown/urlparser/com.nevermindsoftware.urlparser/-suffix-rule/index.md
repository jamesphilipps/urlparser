[urlparser](../../index.md) / [com.nevermindsoftware.urlparser](../index.md) / [SuffixRule](./index.md)

# SuffixRule

`class SuffixRule`

Data class representing a parsed rule from the TLD Suffix List

### Constructors

| [&lt;init&gt;](-init-.md) | Data class representing a parsed rule from the TLD Suffix List`SuffixRule(raw: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)` |

### Properties

| [isException](is-exception.md) | Whether this rule is an exception rule, as defined by the Suffix List`val isException: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [labels](labels.md) | Labels specified by this rule, in reverse order and including the exception label (e.g. !a.b.c = [c,b,a,](#)`val labels: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [matchableLabels](matchable-labels.md) | Labels specified by this rule, in reverse order and excluding the exception label (e.g. !a.b.c = [c,b,a](#)`val matchableLabels: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [raw](raw.md) | Raw rule string used to generate this rule`val raw: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| [match](match.md) | Matches a given list of host groups, in reverse order against this rule's matchable groups (e.g. matching example.co.uk would expect an input of [uk,co,example](#))`fun match(reversedHostGroups: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

