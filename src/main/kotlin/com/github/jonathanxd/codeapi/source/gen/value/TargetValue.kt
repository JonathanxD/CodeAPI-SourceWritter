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
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.types.CodeType
import com.github.jonathanxd.codeapi.util.Parent
import com.github.jonathanxd.iutils.data.MapData

/**
 * [Value] that holds object of type [V].
 *
 * This [Value] process the provided [.val] using [ ][AbstractGenerator.generateTo] and call applier
 * methods of the resulted [Value]s.
 *
 * @param V        Value type.
 * @param TARGET   Output object type.
 * @param C        Generator type.
 */
class TargetValue<V, TARGET, C : AbstractGenerator<TARGET, C>> internal constructor(private val value: Class<*>, val `val`: V, private val parents: Parent<ValueGenerator<*, TARGET, C>>) : Value<Class<*>, TARGET, C> {

    override fun apply(value: TARGET, abstractGenerator: C, appender: Appender<TARGET>, codeSourceData: CodeSourceData, data: MapData) {

        val to = abstractGenerator.generateTo(this.value, this.`val`, this.parents, codeSourceData, data)
        to.forEach { d -> d.apply(value, abstractGenerator, appender, codeSourceData, data) }
    }

    override fun getValue(): Class<*> = this.value


    companion object {

        /**
         * Create [TargetValue].
         *
         * @param targetClass Value type used to determine which [ValueGenerator] to be used to process `val`.
         * @param val         Value.
         * @param parents     Parent Generators (bug tracing).
         * @param V           Value type.
         * @param TARGET      Output object type.
         * @param C           Generator type.
         * @return [TargetValue].
         */
        fun <V, TARGET, C : AbstractGenerator<TARGET, C>> create(targetClass: Class<*>, `val`: V, parents: Parent<ValueGenerator<*, TARGET, C>>): Value<Class<*>, TARGET, C> {
            return TargetValue(targetClass, `val`, parents)
        }

        /**
         * Create [TargetValue].
         *
         * Value type is inferred automatically by the [AbstractGenerator].
         *
         * Not recommended for
         * complex [com.github.jonathanxd.codeapi.CodePart]s
         *
         * @param val      Value.
         * @param parents  Parent Generators (bug tracing).
         * @param V        Value type.
         * @param TARGET   Output object type.
         * @param C        Generator type.
         * @return [TargetValue].
         */
        fun <V: Any, TARGET, C : AbstractGenerator<TARGET, C>> create(`val`: V, parents: Parent<ValueGenerator<*, TARGET, C>>): Value<Class<*>, TARGET, C> {
            if (`val` is CodeType) {
                return create(CodeType::class.java, `val`, parents)
            }

            return TargetValue(`val`.javaClass, `val`, parents)
        }
    }
}
