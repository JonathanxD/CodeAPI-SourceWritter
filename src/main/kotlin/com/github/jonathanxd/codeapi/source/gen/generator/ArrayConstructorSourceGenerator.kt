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
package com.github.jonathanxd.codeapi.source.gen.generator

import com.github.jonathanxd.codeapi.base.ArgumentHolder
import com.github.jonathanxd.codeapi.base.ArrayConstructor
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.keyword.Keyword
import com.github.jonathanxd.codeapi.keyword.Keywords
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import com.github.jonathanxd.codeapi.source.gen2.generator.Util
import com.github.jonathanxd.codeapi.type.CodeType

object ArrayConstructorSourceGenerator : ValueGenerator<ArrayConstructor, String, PlainSourceGenerator> {

    override fun gen(inp: ArrayConstructor, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>()

        values.add(TargetValue.create(Keyword::class.java, Keywords.NEW, parents))
        values.add(TargetValue.create(CodeType::class.java, inp.arrayType.arrayBaseComponent, parents))

        val generateSizes = inp.arguments.isEmpty()

        if (!generateSizes) { // Arguments is Not EMPTY

            val collect = inp.dimensions.map { "[]" }.joinToString(separator = "")

            values.add(PlainValue.create(collect))

            values.add(TargetValue.create(ArgumentHolder::class.java, inp, parents))
        } else {
            for (i in inp.dimensions) {
                values.add(PlainValue.create("["))
                values.add(TargetValue.create(i, parents))
                values.add(PlainValue.create("]"))
            }

            // Arguments is empty, don't process
        }

        if (Util.isBody(parents)) {
            values.add(PlainValue.create(";"))
        }


        return values

    }

}
