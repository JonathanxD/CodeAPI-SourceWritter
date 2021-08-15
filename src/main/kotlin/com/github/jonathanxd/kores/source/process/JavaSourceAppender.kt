/*
 *      Kores-SourceWriter - Translates Kores Structure to Java Source <https://github.com/JonathanxD/Kores-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.kores.source.process

import com.github.jonathanxd.kores.base.TypeDeclaration
import com.github.jonathanxd.kores.source.util.Indent
import com.github.jonathanxd.kores.source.util.MultiString
import com.github.jonathanxd.kores.type.KoresType
import com.github.jonathanxd.kores.type.`is`
import com.github.jonathanxd.kores.type.koresType
import java.lang.reflect.Type

class JavaSourceAppender internal constructor(delimiter: String) {
    val imports = mutableListOf<KoresType>()
    private var packageName: String? = null
    private val declarations = mutableListOf<TypeDeclaration>()
    private val indentation = Indent(4)
    private val multiString: MultiString
    private var isAnnotation = false
    private var brackets = 0
    private val isInBrackets get() = brackets > 0
    private var prefix = ""

    init {
        this.multiString =
                MultiString(delimiter) { s -> if (s == "\n") s else indentation.identString + s }
    }

    fun setPackageIfNotDefined(packageName: String) {
        if (this.packageName != null)
            return

        this.packageName = packageName
    }

    fun addDeclaration(typeDeclaration: TypeDeclaration) {
        declarations += typeDeclaration
    }

    private fun TypeDeclaration.outerIs(type: KoresType): Boolean {
        var outer = this.outerType

        while (outer != null && outer is TypeDeclaration && outer.outerType != null)
            outer = outer.outerType

        return outer?.`is`(type) ?: false
    }

    fun addIndent() {
        this.indentation.addIdent()
    }

    fun removeIndent() {
        this.indentation.removeIdent()
    }

    fun appendImport(type: Type) {
        val koresType = type.koresType
        if (koresType.isPrimitive)
            return

        if (this.declarations.isEmpty())
            return

        @Suppress("NAME_SHADOWING")
        val type = koresType.run {
            if (isArray)
                arrayBaseComponent
            else
                this
        }

        if (type.isPrimitive)
            return

        if (imports.contains(type))
            return

        val simple = koresType.simpleName

        val found = imports.any { it != type && it.simpleName == simple }

        if (!found)
            imports += type
    }

    fun setPrefix(str: String) {
        this.prefix = str
    }

    /**
     * Returns an appender which appends to string before the last new line.
     */
    fun getLastLineAppender(): Nothing = TODO()

    fun simpleAppend(str: String) {

        if (str == "\n")
            this.multiString.newLine()
        else {
            if (this.multiString.isCurrentLineEmpty())
                this.multiString.addND("$prefix$str")
            else this.multiString.addND(str)
        }
    }

    operator fun plusAssign(str: String) = append(str)

    fun createNew(): JavaSourceAppender {
        val appender = JavaSourceAppender(this.multiString.delimiter)
        appender.indentation.addIdent(this.indentation.indentCount)
        appender.imports += this.imports
        return appender
    }

    fun getStrings() = this.multiString.getStrings()

    fun appendBefore(strs: List<String>) = this.multiString.appendBefore(strs)

    fun append(str: String) {

        @Suppress("NAME_SHADOWING")
        var elem = str

        if (this.multiString.isCurrentLineEmpty())
            elem = "$prefix$elem"

        if (elem.isEmpty())
            return

        val endsWithCloseBr = elem.endsWith("}")

        if (endsWithCloseBr) {
            elem = elem.substring(0, elem.length - 1)
        }

        if (elem == "(")
            ++brackets

        if (elem == ")")
            --brackets

        if (elem != "\n" && !elem.isEmpty())
            this.multiString.add(elem)

        if (elem == "@")
            this.isAnnotation = true

        if (elem == "\n" && this.isAnnotation) {
            this.isAnnotation = false
        }

        if (elem == "\n") {
            if (!this.isAnnotation && !isInBrackets) {
                // Don't strip lines if element is in brackets
                this.multiString.newLine()
            }
        }


        if (endsWithCloseBr) {
            if (!this.isAnnotation) {
                this.multiString.add("}")
            } else {
                this.multiString.add("}")
            }
        }
    }

    val importsStr
        get() = imports.filter {
            if (it.canonicalName.startsWith("java.lang")
                    && !it.canonicalName.startsWith("java.lang.annotation")
                    && !it.canonicalName.startsWith("java.lang.instrument")
                    && !it.canonicalName.startsWith("java.lang.invoke")
                    && !it.canonicalName.startsWith("java.lang.management")
                    && !it.canonicalName.startsWith("java.lang.ref")
                    && !it.canonicalName.startsWith("java.lang.reflect")
            )
                return@filter false
            if (this.declarations.isEmpty())
                return@filter true
            if (this.declarations.any { i -> it.`is`(i) })
                return@filter false
            return@filter true

        }.map { "import ${it.canonicalName};" }.let {
            return@let if (it.isNotEmpty()) "${it.joinToString("\n")}\n\n" else ""
        }

    val packageStr
        get() =
            packageName.let {
                if (it == null || it.isEmpty() || it.isBlank()) ""
                else "package $it;\n\n"
            }

    override fun toString(): String = "$packageStr$importsStr$multiString"
}