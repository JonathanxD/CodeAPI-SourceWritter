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

import com.github.jonathanxd.codeapi.base.*
import com.github.jonathanxd.codeapi.base.comment.CommentHolder
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.inspect.SourceInspect
import com.github.jonathanxd.codeapi.inspect.SourceInspectBuilder
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import com.github.jonathanxd.codeapi.type.CodeType

object FieldDeclarationSourceGenerator : ValueGenerator<FieldDeclaration, String, PlainSourceGenerator> {

    override fun gen(inp: FieldDeclaration, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>()

        values.add(TargetValue.create(CommentHolder::class.java, inp, parents))
        values.add(TargetValue.create(Annotable::class.java, inp, parents))
        values.add(TargetValue.create(ModifiersHolder::class.java, inp, parents))


        values.add(TargetValue.create(CodeType::class.java, inp.type, parents))
        values.add(PlainValue.create(" "))
        values.add(PlainValue.create(inp.name))

        inp.value?.let { value ->
            values.add(PlainValue.create(" = "))
            values.add(TargetValue.create(value::class.java, value, parents))
        }

        val generatorParent1 = parents.find { generatorParent ->
            if (MethodInvocation::class.java.isAssignableFrom(generatorParent.current::class.java)) {
                return@find true
            }

            TypeDeclaration::class.java.isAssignableFrom(generatorParent.current::class.java)

        }.orElse(null)

        if (generatorParent1 != null && TypeDeclaration::class.java.isAssignableFrom(generatorParent1.current::class.java)) {
            values.add(PlainValue.create(";"))
        } else {

            if (Util.isBody(parents)) {
                Util.close(values)

                val source = Util.getBody(parents)

                var cancel = false
                var found = false
                var foundAt = -1

                source.forEachIndexed { i, it ->

                    if(found)
                        return@forEachIndexed

                    if(it === inp) {
                        cancel = true
                        foundAt = i
                    } else if(cancel) {
                        if(it is FieldDeclaration) {
                            if (it.annotations.isNotEmpty() || it.comments.isNotAbsent()) {
                                values.add(PlainValue.create("\n"))
                            }
                        } else {
                            values.add(PlainValue.create("\n"))
                        }

                        found = true
                    }

                }

                if(!found && foundAt + 1 == source.size)
                    values.add(PlainValue.create("\n"))

            }
        }


        return values
    }

}
