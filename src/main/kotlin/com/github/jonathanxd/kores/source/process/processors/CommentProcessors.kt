/*
 *      Kores-SourceWriter - Translates Kores Structure to Java Source <https://github.com/JonathanxD/Kores-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.kores.source.process.processors

import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.kores.base.comment.*
import com.github.jonathanxd.kores.processor.Processor
import com.github.jonathanxd.kores.processor.ProcessorManager
import com.github.jonathanxd.kores.processor.processAs
import com.github.jonathanxd.kores.source.process.AppendingProcessor
import com.github.jonathanxd.kores.type.PlainKoresType
import com.github.jonathanxd.kores.type.canonicalName
import com.github.jonathanxd.kores.type.concreteType

object CommentHolderProcessor : Processor<CommentHolder> {

    override fun process(
        part: CommentHolder,
        data: TypedData,
        processorManager: ProcessorManager<*>
    ) {
        processorManager.processAs(part.comments, data)
    }

}

object CommentsProcessor : AppendingProcessor<Comments> {

    override fun process(
        part: Comments,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        if (part.isAbsent())
            return

        val commentList = part.comments

        val prefix = when (part.type) {
            Comments.Type.COMMENT -> ""
            Comments.Type.DOCUMENTATION -> " *"
        }


        when (part.type) {
            Comments.Type.COMMENT -> {
                if (commentList.isEmpty())
                    appender.simpleAppend("// ")
                else {
                    appender.simpleAppend("/*")
                    appender.simpleAppend("\n")
                }
            }
            Comments.Type.DOCUMENTATION -> {
                appender.simpleAppend("/**")
                appender.simpleAppend("\n")
            }
        }

        appender.setPrefix("$prefix ")

        commentList.forEach {
            processorManager.process(it, data)
        }

        appender.setPrefix("")

        when (part.type) {
            Comments.Type.COMMENT -> {
                if (commentList.isNotEmpty()) {
                    appender.simpleAppend("\n")
                    appender.simpleAppend("*/")
                    appender.simpleAppend("\n")
                }
            }
            Comments.Type.DOCUMENTATION -> {
                appender.simpleAppend("\n")
                appender.simpleAppend(" */")
                appender.simpleAppend("\n")
            }
        }
    }

}

object PlainCommentProcessor :
    AppendingProcessor<Plain> {

    override fun process(
        part: Plain,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        appender.simpleAppend(part.text)
    }

}

object LinkCommentProcessor : AppendingProcessor<Link> {

    override fun process(
        part: Link,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        val name = part.name

        val target = part.target

        when (target) {
            is Link.LinkTarget.URL -> appender.simpleAppend(
                """<a href="${target.url}">${name ?: target.url}</a>"""
            )
            is Link.LinkTarget.Element -> {

                appender += "{@link "


                when (target) {
                    is Link.LinkTarget.Element.Class -> {
                        target.let {
                            @Suppress("NAME_SHADOWING")
                            val it = PlainKoresType(it.type.canonicalName)
                            appender.appendImport(it)
                            com.github.jonathanxd.kores.source.process.KoresTypeHelper.appendName(
                                it,
                                appender
                            )
                        }
                    }
                    is Link.LinkTarget.Element.Field -> {

                        target.let {
                            val declaring = PlainKoresType(it.declaringClass.canonicalName)
                            appender.appendImport(declaring)
                            com.github.jonathanxd.kores.source.process.KoresTypeHelper.appendName(
                                declaring,
                                appender
                            )
                        }

                        appender += "#"
                        appender += target.name

                    }
                    is Link.LinkTarget.Element.Method -> {

                        target.spec.localization.let {
                            appender.appendImport(it)
                            com.github.jonathanxd.kores.source.process.KoresTypeHelper.appendName(
                                it,
                                appender
                            )
                        }

                        appender += "#"
                        appender += target.spec.methodName
                        appender += "("

                        target.spec.typeSpec.parameterTypes.let { parameterTypes ->
                            parameterTypes.forEachIndexed { i, it ->
                                appender.appendImport(it.concreteType)
                                com.github.jonathanxd.kores.source.process.KoresTypeHelper.appendName(
                                    it.concreteType,
                                    appender
                                )

                                if (i + 1 < parameterTypes.size)
                                    appender += ", "
                            }
                        }

                        appender += ")"

                    }
                }

                if (name != null) {
                    appender += " "
                    appender += name
                }

                appender += "}"

            }
        }
    }

}

object CodeCommentProcessor : AppendingProcessor<Code> {

    override fun process(
        part: Code,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        val codeNode = part.code

        appender.simpleAppend("<pre>"); appender.simpleAppend("\n")
        appender.simpleAppend("{@code"); appender.simpleAppend("\n")

        processorManager.process(codeNode, data)

        appender.simpleAppend("\n")
        appender.simpleAppend("}")
        appender.simpleAppend("\n")
        appender.simpleAppend("</pre>")
    }

}

object PlainCodeNodeProcessor :
    AppendingProcessor<Code.CodeNode.Plain> {

    override fun process(
        part: Code.CodeNode.Plain,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        appender.simpleAppend(part.plain)
    }


}

object RepCodeNodeProcessor : Processor<Code.CodeNode.CodeRepresentation> {

    override fun process(
        part: Code.CodeNode.CodeRepresentation,
        data: TypedData,
        processorManager: ProcessorManager<*>
    ) {
        processorManager.process(part.representation, data)
    }

}