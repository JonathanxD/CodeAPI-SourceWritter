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

import com.github.jonathanxd.codeapi.CodeSource
import com.github.jonathanxd.codeapi.MutableCodeSource
import com.github.jonathanxd.codeapi.base.CatchStatement
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import java.util.*

object CatchBlockSourceGenerator : ValueGenerator<CatchStatement, String, PlainSourceGenerator> {

    private var CATCH_VAR_COUNT = 0

    private val andIncrementCatchVar: Int
        get() {
            val i = CATCH_VAR_COUNT

            ++CATCH_VAR_COUNT

            return i
        }

    override fun gen(catchBlock: CatchStatement, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>()

        values.add(PlainValue.create("catch"))

        val parameters = catchBlock.exceptionTypes

        val sj = StringJoiner(" | ")

        if (!parameters.isEmpty()) {

            for (parameter in parameters) {
                val append = parameter.canonicalName

                sj.add(append)
            }
        }

        val catchName = catchBlock.variable.name

        values.add(PlainValue.create("(" + sj.toString() + " " + catchName + ")"))

        val codeSource = catchBlock.body

        val source2 = MutableCodeSource()

        //var field = catchBlock.variable

        /*if (field.value == null) {
            field = field.builder().withValue(CodeAPI)
            field = CodeField(field.name, field.variableType, Helper.accessLocalVariable(catchName, Throwable::class.java), emptyList(), emptyList())
        }

        source2.add(field)*/

        source2.addAll(codeSource)

        values.add(TargetValue.create(CodeSource::class.java, source2, parents))

        return values
    }

}
