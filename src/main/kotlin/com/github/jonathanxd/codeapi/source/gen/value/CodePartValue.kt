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
import com.github.jonathanxd.codeapi.gen.value.*
import com.github.jonathanxd.codeapi.type.CodeType

/**
 * A [Value] that holds a [CodePart] and generate [TARGET] from the part.
 * This value call another generators that supports the provided [CodePart].
 *
 * @param TARGET   Output object type.
 * @param C        Generator type.
 */
class CodePartValue<TARGET, C : AbstractGenerator<TARGET, C>>(override val value: CodePart, val parents: Parent<ValueGenerator<*, TARGET, C>>) : Value<CodePart, TARGET, C> {

    override fun apply(value: CodePart, generator: C, appender: Appender<TARGET>, codeSourceData: CodeSourceData, data: Data) {
        try {

            val call: List<Value<*, TARGET, C>>?

            if (this.value is CodeType) {
                call = generator.generateTo(CodeType::class.java, this.value, parents, codeSourceData, data)
            } else {
                call = generator.generateTo(this.value.javaClass, this.value, parents, codeSourceData, data)
            }

            if (!call.isEmpty()) {
                for (genValue in call) {
                    AbstractGenerator.helpApply(genValue, this.value, generator, appender, codeSourceData, data)
                }
            } else {
                throw IllegalStateException("Cannot find generator for '" + this.value.javaClass.canonicalName + "'")
            }
        } catch (t: Throwable) {
            throw RuntimeException("Parents: " + parents, t)
        }

    }

    companion object {

        /**
         * Create value from part and parent generators.
         *
         * @param part     Part
         * @param current  Parent generators
         * @param TARGET   Output object type.
         * @param C        Generator type.
         * @return [CodePartValue] instance.
         */
        fun <TARGET, C : AbstractGenerator<TARGET, C>> create(part: CodePart, current: Parent<ValueGenerator<*, TARGET, C>>): Value<CodePart, TARGET, C> {
            return CodePartValue(part, current)
        }
    }

}
