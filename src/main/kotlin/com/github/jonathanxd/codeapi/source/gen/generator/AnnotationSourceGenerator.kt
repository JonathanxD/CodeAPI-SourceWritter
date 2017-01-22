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

import com.github.jonathanxd.codeapi.base.Annotation
import com.github.jonathanxd.codeapi.base.EnumValue
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import com.github.jonathanxd.codeapi.type.CodeType
import com.github.jonathanxd.iutils.array.ArrayUtils

object AnnotationSourceGenerator : ValueGenerator<Annotation, String, PlainSourceGenerator> {

    override fun gen(inp: Annotation, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>()

        values.add(PlainValue.create("@"))
        values.add(TargetValue.create(CodeType::class.java, inp.type, parents))

        val valuesMap = inp.values

        values.add(PlainValue.create<String, PlainSourceGenerator>("("))

        if (valuesMap.size == 1 && valuesMap.containsKey("value")) {
            val value = valuesMap["value"]!!

            AnnotationSourceGenerator.addType(value, values, parents)
        } else {

            valuesMap.forEach { key, value ->
                values.add(PlainValue.create<String, PlainSourceGenerator>(key))
                values.add(PlainValue.create<String, PlainSourceGenerator>("="))

                addType(value, values, parents)
            }
        }

        values.add(PlainValue.create<String, PlainSourceGenerator>(")"))
        values.add(PlainValue.create<String, PlainSourceGenerator>("\n"))

        return values
    }

    fun addType(value: Any, values: MutableList<Value<*, String, PlainSourceGenerator>>, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>) {
        if (value is CodeType) {
            values.add(TargetValue.create(CodeType::class.java, value, parents))
        } else if (value is EnumValue) {
            values.add(TargetValue.create(EnumValue::class.java, value, parents))
        } else if (value is Annotation) {
            values.add(TargetValue.create(Annotation::class.java, value, parents))
        } else if (value.javaClass.isArray) {
            val valuesObj = ArrayUtils.toObjectArray(value)

            values.add(PlainValue.create("{"))

            for (i in valuesObj.indices) {
                val o = valuesObj[i]

                AnnotationSourceGenerator.addType(o, values, parents)

                if (i + 1 < valuesObj.size) {
                    values.add(PlainValue.create(","))
                }
            }

            values.add(PlainValue.create("}"))
        } else if (value is String) {
            values.add(PlainValue.create("\"" + value.toString() + "\""))
        } else {
            values.add(PlainValue.create(value.toString()))
        }
    }
}
