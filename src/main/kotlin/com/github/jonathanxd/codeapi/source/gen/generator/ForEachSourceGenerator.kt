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

import com.github.jonathanxd.codeapi.base.BodyHolder
import com.github.jonathanxd.codeapi.base.ForEachStatement
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.common.IterationTypes
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.SourceSugarEnvironment
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import java.util.*

object ForEachSourceGenerator : ValueGenerator<ForEachStatement, String, PlainSourceGenerator> {

    override fun gen(inp: ForEachStatement, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        val values = ArrayList<Value<*, String, PlainSourceGenerator>>()

        val variableDeclaration = inp.variable
        val iterableElement = inp.iterableElement
        val iterationType = inp.iterationType

        if (iterationType === IterationTypes.ITERABLE_ELEMENT || iterationType === IterationTypes.ARRAY) {
            values.add(PlainValue.create("for "))
            values.add(PlainValue.create("("))
            values.add(TargetValue.create(variableDeclaration::class.java, variableDeclaration, parents))
            values.add(PlainValue.create(" : "))
            values.add(TargetValue.create(iterableElement::class.java, iterableElement, parents))
            values.add(PlainValue.create(")"))
            values.add(TargetValue.create(BodyHolder::class.java, inp, parents))
            values.add(PlainValue.create("\n"))
        } else {
            val start = iterationType.createGenerator(SourceSugarEnvironment)

            val generated = start.generate(inp, this)

            generated.mapTo(values) { TargetValue.create(it::class.java, it, parents) }

        }


        return values
    }

}
