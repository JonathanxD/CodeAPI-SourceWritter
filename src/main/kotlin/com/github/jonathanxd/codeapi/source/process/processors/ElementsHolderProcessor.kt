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
package com.github.jonathanxd.codeapi.source.process.processors

import com.github.jonathanxd.codeapi.base.AnnotationDeclaration
import com.github.jonathanxd.codeapi.base.ElementsHolder
import com.github.jonathanxd.codeapi.base.EnumDeclaration
import com.github.jonathanxd.codeapi.base.InnerTypesHolder
import com.github.jonathanxd.codeapi.processor.CodeProcessor
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.iutils.data.TypedData

object ElementsHolderProcessor : AppendingProcessor<ElementsHolder> {

    override fun process(part: ElementsHolder, data: TypedData, codeProcessor: CodeProcessor<*>, appender: JavaSourceAppender) {

        appender += "{"
        appender += "\n"
        appender.addIndent()

        if (part is AnnotationDeclaration) {
            part.properties.forEach {
                codeProcessor.processAs(it, data)
            }
        }

        if (part is EnumDeclaration) {
            appender += "\n"
            val iter = part.entries.iterator()

            while(iter.hasNext()) {
                val entry = iter.next()

                codeProcessor.processAs(entry, data)

                if(iter.hasNext()) {
                    appender += ","
                } else {
                    appender += ";"
                }

                appender += "\n"
            }

        }


        if(part.staticBlock.body.isNotEmpty) {
            appender += "\n"
            codeProcessor.processAs(part.staticBlock, data)
        }

        if(part.fields.isNotEmpty()) {
            appender += "\n"
            part.fields.forEach {
                codeProcessor.processAs(it, data)
                appender += "\n"
            }
        }

        if(part.constructors.isNotEmpty()) {
            part.constructors.forEach {
                appender += "\n"
                codeProcessor.processAs(it, data)
            }
        }

        if(part.methods.isNotEmpty()) {
            part.methods.forEach {
                appender += "\n"
                codeProcessor.processAs(it, data)
            }
        }

        codeProcessor.processAs<InnerTypesHolder>(part, data)

        appender.removeIndent()
        appender += "}"
    }

}