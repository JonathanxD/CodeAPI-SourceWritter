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
package com.github.jonathanxd.codeapi.source.gen.value

import com.github.jonathanxd.codeapi.CodePart
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.Appender
import com.github.jonathanxd.codeapi.gen.value.AbstractGenerator
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.source.gen.SourceAppender
import com.github.jonathanxd.codeapi.type.CodeType

/**
 * Value of plain [TARGET].
 *
 * This [Value] append the provided [value] in [Appender].
 *
 * @param C        Generator type.
 */
class CodeTypeValue<C : AbstractGenerator<String, C>>(override val value: CodeType) : Value<CodeType, String, C> {

    override fun apply(value: CodePart, generator: C, appender: Appender<String>, codeSourceData: CodeSourceData, data: Data) {
        if (this.value is CodeType && appender is SourceAppender<*>) {

            val type = if(this.value.isArray) this.value.arrayBaseComponent else this.value

            if(appender.imports.any { type.`is`(it) }) {
                appender.add(this.value.simpleName)
                return
            }

        }

        appender.add(this.value.canonicalName)

    }

    companion object {

        /**
         * Create [PlainValue].
         *
         * @param value    Plain value.
         * @param C        Generator type.
         * @return [PlainValue]
         */
        fun <C : AbstractGenerator<String, C>> create(value: CodeType): Value<CodeType, String, C> {
            return CodeTypeValue(value)
        }
    }
}
