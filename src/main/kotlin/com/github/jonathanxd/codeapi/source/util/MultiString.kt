/*
 *      CodeAPI-SourceWriter - Framework to generate Java code and Bytecode code. <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/ & https://github.com/TheRealBuggy/) <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.jonathanxd.codeapi.source.util

/**
 * Internal undocumented.
 */
class MultiString(val delimiter: String, private val preparer: (String) -> String) {

    private val strings = mutableListOf<String>()
    private var line = 0

    fun getStrings() = strings.toMutableList()

    fun add(str: String) {
        this.add(str, true)
    }

    fun addND(str: String) {
        this.add(str, false)
    }

    fun appendBefore(strs: List<String>) {
        if (strings.size == 0) {
            strings.addAll(strs)
        } else {
            val index = strings.lastIndex
            strings.addAll(index, strs)
        }
        line += strs.size
    }

    private fun add(str: String, appendDelimiter: Boolean) {
        @Suppress("NAME_SHADOWING")
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

    fun isCurrentLineEmpty() = line == strings.size

    override fun toString(): String {
        return strings.joinToString(separator = "")
    }
}
