/*
 *      CodeAPI-SourceWriter - Framework to generate Java code and Bytecode code. <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/ & https://github.com/TheRealBuggy/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.codeapi.gen.Appender
import com.github.jonathanxd.codeapi.gen.value.AbstractGenerator
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.iutils.data.MapData

/**
 * Value of plain [TARGET].
 *
 * This [Value] append the provided [value] in [Appender].
 *
 * @param TARGET   Output object type.
 * @param C        Generator type.
 */
class PlainValue<TARGET, C : AbstractGenerator<TARGET, C>>(private val value: TARGET) : Value<TARGET, TARGET, C> {

    override fun apply(value: TARGET, generator: C, appender: Appender<TARGET>, codeSourceData: CodeSourceData, data: MapData) {
        appender.add(this.getValue())
    }

    override fun getValue(): TARGET {
        return value
    }

    companion object {

        /**
         * Create [PlainValue].
         *
         * @param value    Plain value.
         * @param TARGET   Output object type.
         * @param C        Generator type.
         * @return [PlainValue]
         */
        fun <TARGET, C : AbstractGenerator<TARGET, C>> create(value: TARGET): Value<TARGET, TARGET, C> {
            return PlainValue<TARGET, C>(value)
        }
    }
}
