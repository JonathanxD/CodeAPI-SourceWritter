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
import com.koresframework.kores.base.ValueHolder
import com.koresframework.kores.base.VariableDeclaration
import com.koresframework.kores.common.KoresNothing
import com.koresframework.kores.processor.ProcessorManager
import com.koresframework.kores.processor.processAs
import com.koresframework.kores.safeForComparison
import com.koresframework.kores.source.process.AppendingProcessor
import com.koresframework.kores.source.process.FOR_INIT
import com.koresframework.kores.source.process.VARIABLE_INDEXER
import com.koresframework.kores.source.process.requireIndexer

object VariableDeclarationProcessor :
    com.koresframework.kores.source.process.AppendingProcessor<VariableDeclaration> {

    override fun process(
        part: VariableDeclaration,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.koresframework.kores.source.process.JavaSourceAppender
    ) {
        if (!FOR_INIT.contains(data)) {
            processorManager.processAs(part.type, data)
        }

        VARIABLE_INDEXER.requireIndexer(data).addVariable(part.name)

        if (!FOR_INIT.contains(data)) {
            appender += " "
        }

        appender += part.name

        if (part.value.safeForComparison != KoresNothing) {
            appender += " = "
            processorManager.processAs<ValueHolder>(part, data)
        }

        /*if (Util.isBody(parents)) {
            Util.close(values)
        }*/
    }


}
