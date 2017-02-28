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

import com.github.jonathanxd.codeapi.Types
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.literal.Literal
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.CodeTypeValue
import com.github.jonathanxd.codeapi.source.gen.value.ImportValue
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.type.CodeType
import com.github.jonathanxd.codeapi.type.PlainCodeType
import com.github.jonathanxd.codeapi.util.codeType
import java.util.*

object LiteralSourceGenerator : ValueGenerator<Literal, String, PlainSourceGenerator> {

    override fun gen(inp: Literal, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        val values = ArrayList<Value<*, String, PlainSourceGenerator>>()

        if (inp.type.`is`(Types.LONG)) {
            values.add(PlainValue.create(inp.name + "L"))
        } else if (inp.type.`is`(Types.DOUBLE)) {
            values.add(PlainValue.create(inp.name + "D"))
        } else if (inp.type.`is`(Types.FLOAT)) {
            values.add(PlainValue.create(inp.name + "F"))
        } else if (inp.type.`is`(CodeType::class.codeType)) {
            val type = inp.value as CodeType
            values.add(ImportValue.create(type))
            values.add(CodeTypeValue.create(type))
            values.add(PlainValue.create(".class"))
        } else {
            values.add(PlainValue.create(inp.name))
        }

        if (Util.isBody(parents)) {
            Util.close(values)
        }

        return values

    }

}
