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
package com.koresframework.kores.source.process.processors

import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.iutils.kt.require
import com.koresframework.kores.base.*
import com.koresframework.kores.base.comment.CommentHolder
import com.koresframework.kores.processor.ProcessorManager
import com.koresframework.kores.processor.processAs
import com.koresframework.kores.source.process.AppendingProcessor
import com.koresframework.kores.source.process.DECLARATION

object MethodDeclarationProcessor :
    com.koresframework.kores.source.process.AppendingProcessor<MethodDeclarationBase> {

    override fun process(
        part: MethodDeclarationBase,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.koresframework.kores.source.process.JavaSourceAppender
    ) {
        processorManager.processAs<CommentHolder>(part, data)
        processorManager.processAs<Annotable>(part, data)
        processorManager.processAs<ModifiersHolder>(part, data)

        if (part.genericSignature.isNotEmpty) {
            processorManager.processAs<GenericSignatureHolder>(part, data)
            appender += " "
        }

        if (part !is ConstructorDeclaration) {
            processorManager.processAs<ReturnTypeHolder>(part, data)
            appender += " "
        }

        if (part is ConstructorDeclaration) {
            appender += DECLARATION.require(data).simpleName
        } else {
            processorManager.processAs<Named>(part, data)
        }

        processorManager.processAs<ParametersHolder>(part, data)
        processorManager.processAs<ThrowsHolder>(part, data)

        if (part.body.isEmpty &&
                (part.modifiers.contains(KoresModifier.ABSTRACT)
                        || (DECLARATION.require(data).isInterface && !part.modifiers.contains(
                    KoresModifier.DEFAULT
                )))
        ) {
            appender += ";"
            appender += "\n"
        } else {
            appender += " "
            processorManager.processAs<BodyHolder>(part, data)
            appender += "\n"
        }
    }
}
