/*
 *      CodeAPI-SourceWriter - Translates CodeAPI Structure to Java Source <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
 * Internal class undocumented.
 */
class Indent(private val indentationSize: Int) {
    private var indent = 0

    val indentCount get() = indent / indentationSize

    fun addIdent() {
        this.addIdent(1)
    }

    fun addIdent(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Negative value!")
        }
        this.indent += indentationSize * amount
    }

    fun removeIdent() {
        this.removeIdent(1)
    }

    fun removeIdent(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Negative value!")
        }
        this.indent -= indentationSize * amount
    }

    val identString: String
        get() {
            val sb = StringBuilder()

            for (i in 0 until this.indent) {
                sb.append(' ')
            }

            return sb.toString()
        }
}