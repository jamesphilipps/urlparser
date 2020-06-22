package com.nevermindsoftware.urlparser


class SuffixRule(val raw: String) {
    val isException: Boolean = raw.startsWith("!")
    val labels: List<String>
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

    fun match(reversedHostParts: List<String>) = reversedHostParts
        .subList(0, if (isException) matchableLabels.size - 1 else matchableLabels.size)

    override fun toString(): String {
        return "SuffixRule(raw='$raw', isException=$isException, labels=$labels, matchableLabels=$matchableLabels)"
    }
}