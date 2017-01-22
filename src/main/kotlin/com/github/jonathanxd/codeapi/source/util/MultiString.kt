package com.github.jonathanxd.codeapi.source.util

/**
 * Internal undocumented.
 */
class MultiString(private val delimiter: String, private val preparer: (String) -> String) {

    private val strings = mutableListOf<String>()
    private var line = 0

    fun add(str: String) {
        this.add(str, true)
    }

    private fun add(str: String, appendDelimiter: Boolean) {
        var str = str
        if (strings.size <= line) {
            this.strings.add("")
            str = preparer(str)
        }

        val get = strings[line]
        strings[line] = get + str + if (appendDelimiter) delimiter else ""
    }

    fun newLine() {
        this.add("\n", false)
        line += 1
    }

    override fun toString(): String {
        return strings.joinToString(separator = "")
    }
}