/*
 *      Kores-SourceWriter - Translates Kores Structure to Java Source <https://github.com/JonathanxD/Kores-SourceWriter>
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
package com.github.jonathanxd.kores.source.process.processors

import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.iutils.kt.require
import com.github.jonathanxd.kores.Types
import com.github.jonathanxd.kores.base.*
import com.github.jonathanxd.kores.base.comment.CommentHolder
import com.github.jonathanxd.kores.processor.Processor
import com.github.jonathanxd.kores.processor.ProcessorManager
import com.github.jonathanxd.kores.processor.processAs
import com.github.jonathanxd.kores.source.process.APPENDER
import com.github.jonathanxd.kores.source.process.DECLARATION
import com.github.jonathanxd.kores.type.`is`

object TypeDeclarationProcessor : Processor<TypeDeclaration> {

    override fun process(
        part: TypeDeclaration,
        data: TypedData,
        processorManager: ProcessorManager<*>
    ) {
        val appender = APPENDER.require(data)
        @Suppress("NAME_SHADOWING")
        val data = TypedData(data)

        DECLARATION.set(data, part)
        APPENDER.set(data, appender)

        appender.addDeclaration(part)

        appender.addDeclaration(part)

        processorManager.processAs<CommentHolder>(part, data)
        processorManager.processAs<Annotable>(part, data)

        processorManager.processAs<ModifiersHolder>(part, data)

        appender += when (part) {
            is InterfaceDeclaration -> "interface "
            is AnnotationDeclaration -> "@interface "
            is ClassDeclaration -> "class "
            is EnumDeclaration -> "enum "
            else -> ""
        }

        processorManager.processAs<Named>(part, data)

        processorManager.processAs<GenericSignatureHolder>(part, data)

        appender += " "

        val packageName = part.packageName

        appender.setPackageIfNotDefined(packageName)

        var superOrItfs = false

        if (part is SuperClassHolder) {
            if (!part.superClass.`is`(Types.OBJECT)) {
                processorManager.processAs<SuperClassHolder>(part, data)
                superOrItfs = true
            }
        }

        if (part is ImplementationHolder) {
            if (part.implementations.isNotEmpty()) {
                processorManager.processAs<ImplementationHolder>(part, data)
                superOrItfs = true
            }
        }

        if (superOrItfs)
            appender += " "

        processorManager.processAs<ElementsHolder>(part, data)

        appender += "\n"

        if (part.outerType != null)
            appender += "\n"

    }

}
