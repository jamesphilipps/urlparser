package com.nevermindsoftware.urlparser

/**
 * Data class representing a parsed rule from the TLD Suffix List
 */
class SuffixRule(
    /** Raw rule string used to generate this rule */
    val raw: String
) {
    /** Whether this rule is an exception rule, as defined by the Suffix List */
    val isException: Boolean = raw.startsWith("!")

    /** Labels specified by this rule, in reverse order and including the exception label (e.g. !a.b.c = [c,b,a,!] */
    val labels: List<String>

    /** Labels specified by this rule, in reverse order and excluding the exception label (e.g. !a.b.c = [c,b,a] */
    val matchableLabels: List<String>

    init {
        if (isException) {
            matchableLabels = raw.substring(1).split(".").reversed()
            labels = matchableLabels + listOf("!")
        } else {
            matchableLabels = raw.split(".").reversed()
            labels = matchableLabels
        }
    }

    /**
     * Matches a given list of host groups, in reverse order against this rule's matchable groups
     * (e.g. matching example.co.uk would expect an input of [uk,co,example])
     */
    fun match(reversedHostGroups: List<String>) = reversedHostGroups
        .subList(0, if (isException) matchableLabels.size - 1 else matchableLabels.size)

    override fun toString(): String {
        return "SuffixRule(raw='$raw', isException=$isException, labels=$labels, matchableLabels=$matchableLabels)"
    }
}