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
package com.github.jonathanxd.codeapi.source.gen.generator

import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.interfaces.Argumenterizable
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import com.github.jonathanxd.codeapi.util.Parent
import com.github.jonathanxd.iutils.data.MapData
import com.github.jonathanxd.iutils.optional.Require
import java.util.*

object ArgumenterizableSourceGenerator : ValueGenerator<Argumenterizable, String, PlainSourceGenerator> {

    private const val NORMAL_OPEN_TAG = "("
    private const val NORMAL_CLOSE_TAG = ")"

    private const val ARRAY_OPEN_TAG = "{"
    private const val ARRAY_CLOSE_TAG = "}"

    override fun gen(argumenterizable: Argumenterizable, plainSourceGenerator: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: MapData): List<Value<*, String, PlainSourceGenerator>> {

        val values = ArrayList<Value<*, String, PlainSourceGenerator>>()

        val OPEN_TOKEN = if (argumenterizable.isArray) ARRAY_OPEN_TAG else NORMAL_OPEN_TAG
        val CLOSE_TOKEN = if (argumenterizable.isArray) ARRAY_CLOSE_TAG else NORMAL_CLOSE_TAG

        values.add(PlainValue.create(OPEN_TOKEN))

        val arguments = argumenterizable.arguments

        val iterator = arguments.iterator()

        while (iterator.hasNext()) {
            val argument = iterator.next()

            if (argument.isCasted) {
                values.add(PlainValue.create(OPEN_TOKEN + Require.require(argument.type).canonicalName + CLOSE_TOKEN))
            }

            values.add(TargetValue.create(Require.require(argument.value).javaClass, Require.require(argument.value), parents))

            if (iterator.hasNext())
                values.add(PlainValue.create(", "))

        }

        values.add(PlainValue.create(CLOSE_TOKEN))

        return values
    }

}
