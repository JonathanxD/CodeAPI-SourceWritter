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
package com.github.jonathanxd.kores.source.process.processors

import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.kores.base.BodyHolder
import com.github.jonathanxd.kores.base.ForStatement
import com.github.jonathanxd.kores.base.IfExpressionHolder
import com.github.jonathanxd.kores.common.KoresNothing
import com.github.jonathanxd.kores.processor.ProcessorManager
import com.github.jonathanxd.kores.processor.processAs
import com.github.jonathanxd.kores.safeForComparison
import com.github.jonathanxd.kores.source.process.*

object ForStatementProcessor :
    AppendingProcessor<ForStatement> {

    override fun process(
        part: ForStatement,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        appender += "for "
        appender += "("

        val inits = part.forInit.filter { it.safeForComparison != KoresNothing }

        inits.forEachIndexed { index, it ->
            if (index > 0) {
                appender += ", "
                FOR_INIT.set(data, Unit)
            }
            processorManager.processAs(it, data)
            if (index > 0) {
                FOR_INIT.remove(data)
            }
        }

        appender += ";"

        val forExpressionOpt = part.forExpression

        if (!forExpressionOpt.isEmpty()) {
            appender += " "
            processorManager.processAs<IfExpressionHolder>(part, data)
        }

        appender += ";"

        val iter2 = part.forUpdate.filter { it.safeForComparison != KoresNothing }.iterator()
        if (iter2.hasNext())
            appender += " "

        while (iter2.hasNext()) {
            val elem = iter2.next()
            processorManager.processAs(elem, data)
            if (iter2.hasNext())
                appender += ", "
        }

        appender += ")"
        appender += " "

        VARIABLE_INDEXER.requireIndexer(data).tempFrame {
            processorManager.processAs<BodyHolder>(part, data)
        }

        appender += "\n"
    }

}
