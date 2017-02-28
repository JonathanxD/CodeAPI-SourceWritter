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

import com.github.jonathanxd.codeapi.base.comment.*
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.*
import com.github.jonathanxd.codeapi.type.PlainCodeType

object CommentHolderSourceGenerator : ValueGenerator<CommentHolder, String, PlainSourceGenerator> {

    override fun gen(inp: CommentHolder, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        return listOf(TargetValue.create(Comments::class.java, inp.comments, parents))
    }

}

object CommentsSourceGenerator : ValueGenerator<Comments, String, PlainSourceGenerator> {

    override fun gen(inp: Comments, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {

        if (inp.isAbsent())
            return emptyList()

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>()

        val commentList = inp.comments

        val prefix = when (inp.type) {
            Comments.Type.COMMENT -> ""
            Comments.Type.DOCUMENTATION -> " *"
        }


        when (inp.type) {
            Comments.Type.COMMENT -> {
                if (commentList.isEmpty())
                    values.add(SimplePlainValue.create("// "))
                else {
                    values.add(SimplePlainValue.create("/*"))
                    values.add(SimplePlainValue.create("\n"))
                }
            }
            Comments.Type.DOCUMENTATION -> {
                values.add(SimplePlainValue.create("/**"))
                values.add(SimplePlainValue.create("\n"))
            }
        }

        values.add(PrefixPlainValue.create("$prefix "))

        commentList.forEach {
            values.add(TargetValue.create(it, parents))
        }

        values.add(PrefixPlainValue.create(""))

        when (inp.type) {
            Comments.Type.COMMENT -> {
                if (commentList.isNotEmpty()) {
                    values.add(SimplePlainValue.create("\n"))
                    values.add(SimplePlainValue.create("*/"))
                    values.add(SimplePlainValue.create("\n"))
                }
            }
            Comments.Type.DOCUMENTATION -> {
                values.add(SimplePlainValue.create("\n"))
                values.add(SimplePlainValue.create(" */"))
                values.add(SimplePlainValue.create("\n"))
            }
        }


        return values
    }

}

object PlainCommentSourceGenerator : ValueGenerator<Plain, String, PlainSourceGenerator> {

    override fun gen(inp: Plain, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        return listOf(SimplePlainValue.create(inp.text))
    }

}

object LinkCommentSourceGenerator : ValueGenerator<Link, String, PlainSourceGenerator> {

    override fun gen(inp: Link, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {

        val name = inp.name

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>()


        val target = inp.target

        when (target) {
            is Link.LinkTarget.URL -> values.add(SimplePlainValue.create("""<a href="${target.url}">${name ?: target.url}</a>"""))
            is Link.LinkTarget.Element -> {

                values.add(PlainValue.create("{@link "))


                when (target) {
                    is Link.LinkTarget.Element.Class -> {
                        target.let {
                            val it = PlainCodeType(it.canonicalName)
                            values.add(ImportValue.create(it))
                            values.add(CodeTypeValue.create(it))
                        }
                    }
                    is Link.LinkTarget.Element.Field -> {

                        target.let {
                            val declaring = PlainCodeType(it.declaringClass)
                            values.add(ImportValue.create(declaring))
                            values.add(CodeTypeValue.create(declaring))
                        }

                        values.add(PlainValue.create("#"))
                        values.add(PlainValue.create(target.name))

                    }
                    is Link.LinkTarget.Element.Method -> {

                        target.spec.localization.let {
                            values.add(ImportValue.create(it))
                            values.add(CodeTypeValue.create(it))
                        }

                        values.add(PlainValue.create("#"))
                        values.add(PlainValue.create(target.spec.methodName))
                        values.add(PlainValue.create("("))

                        target.spec.typeSpec.parameterTypes.let { parameterTypes ->
                            parameterTypes.forEachIndexed { i, it ->
                                values.add(ImportValue.create(it))
                                values.add(CodeTypeValue.create(it))

                                if (i + 1 < parameterTypes.size)
                                    values.add(PlainValue.create(", "))
                            }
                        }

                        values.add(PlainValue.create(")"))

                    }
                }

                if(name != null) {
                    values.add(PlainValue.create(" "))
                    values.add(PlainValue.create(name))
                }

                values.add(PlainValue.create("}"))

            }
        }

        return values
    }

}

object CodeCommentSourceGenerator : ValueGenerator<Code, String, PlainSourceGenerator> {

    override fun gen(inp: Code, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        val codeNode = inp.code

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>(
                SimplePlainValue.create("<pre>"), SimplePlainValue.create("\n"),
                SimplePlainValue.create("@{code"), SimplePlainValue.create("\n")
        )

        values.add(TargetValue.create(codeNode, parents))

        values.add(SimplePlainValue.create("\n"))
        values.add(SimplePlainValue.create("}"))
        values.add(SimplePlainValue.create("\n"))
        values.add(SimplePlainValue.create("</pre>"))

        return values
    }

}

object PlainCodeNodeSourceGenerator : ValueGenerator<Code.CodeNode.Plain, String, PlainSourceGenerator> {

    override fun gen(inp: Code.CodeNode.Plain, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        return listOf(SimplePlainValue.create(inp.plain))
    }

}

object RepCodeNodeSourceGenerator : ValueGenerator<Code.CodeNode.CodeRepresentation, String, PlainSourceGenerator> {

    override fun gen(inp: Code.CodeNode.CodeRepresentation, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {
        return listOf(TargetValue.create(inp.representation, parents))
    }

}