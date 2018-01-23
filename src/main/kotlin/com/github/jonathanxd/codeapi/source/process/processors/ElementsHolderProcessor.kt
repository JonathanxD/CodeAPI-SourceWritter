/*
 *      CodeAPI-SourceWriter - Translates CodeAPI Structure to Java Source <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.codeapi.base.*
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.iutils.data.TypedData

object ElementsHolderProcessor : AppendingProcessor<ElementsHolder> {

    override fun process(
        part: ElementsHolder,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: JavaSourceAppender
    ) {

        appender += "{"
        appender += "\n"
        appender.addIndent()

        if (part is AnnotationDeclaration) {
            part.properties.forEach {
                processorManager.processAs(it, data)
            }
        }

        if (part is EnumDeclaration) {
            appender += "\n"
            val iter = part.entries.iterator()

            while (iter.hasNext()) {
                val entry = iter.next()

                processorManager.processAs(entry, data)

                if (iter.hasNext()) {
                    appender += ","
                } else {
                    appender += ";"
                }

                appender += "\n"
            }

        }


        if (part.staticBlock.body.isNotEmpty) {
            appender += "\n"
            processorManager.processAs(part.staticBlock, data)
        }

        if (part.fields.isNotEmpty()) {
            appender += "\n"
            part.fields.forEach {
                processorManager.processAs(it, data)
                appender += "\n"
            }
        }

        if (part is ConstructorsHolder) {
            part.constructors.forEach {
                appender += "\n"
                processorManager.processAs(it, data)
            }
        }

        if (part.methods.isNotEmpty()) {
            part.methods.forEach {
                appender += "\n"
                processorManager.processAs(it, data)
            }
        }

        processorManager.processAs<InnerTypesHolder>(part, data)

        appender.removeIndent()
        appender += "}"
    }

}

object ConstructorsHolderProcessor : AppendingProcessor<ConstructorsHolder> {

    override fun process(
        part: ConstructorsHolder,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: JavaSourceAppender
    ) {
        part.constructors.forEach {
            appender += "\n"
            processorManager.processAs(it, data)
        }
    }

}