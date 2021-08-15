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
import com.github.jonathanxd.kores.Instruction
import com.github.jonathanxd.kores.base.BodyHolder
import com.github.jonathanxd.kores.base.IfExpressionHolder
import com.github.jonathanxd.kores.base.IfStatement
import com.github.jonathanxd.kores.processor.ProcessorManager
import com.github.jonathanxd.kores.processor.processAs
import com.github.jonathanxd.kores.safeForComparison
import com.github.jonathanxd.kores.source.process.*

object IfStatementProcessor :
    AppendingProcessor<IfStatement> {

    override fun process(
        part: IfStatement,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        val isElvis = ELVIS.getOrNull(data) != null

        if (!isElvis) {
            appender += "if"
            appender += " "
        }

        processorManager.processAs<IfExpressionHolder>(part, data)

        val elseStatement = part.elseStatement

        if (elseStatement.isEmpty) {
            if (!isElvis) {
                // Clean body
                appender += " "
                VARIABLE_INDEXER.requireIndexer(data).tempFrame {
                    processorManager.processAs<BodyHolder>(part, data)
                }
                appender += "\n"
            } else {
                val body = part.body

                if (body.size != 1)
                    throw IllegalStateException("Elvis if expression must have only one element in the body!");
                VARIABLE_INDEXER.requireIndexer(data).tempFrame {
                    processorManager.processAs(body.single(), data)
                }
            }
        } else {

            if (!isElvis) {
                appender += " "
                VARIABLE_INDEXER.requireIndexer(data).tempFrame {
                    processorManager.processAs<BodyHolder>(part, data)
                }
                appender += " else "
                VARIABLE_INDEXER.requireIndexer(data).tempFrame {
                    processorManager.processAs(elseStatement, data)
                }
                appender += "\n"
            } else {

                if (!isValidElvis(part))
                    throw IllegalStateException("Invalid elvis if expression.")

                val body = part.body

                appender += " ? "

                VARIABLE_INDEXER.requireIndexer(data).tempFrame {
                    processorManager.processAs(body.single(), data)
                }

                appender += " : "

                VARIABLE_INDEXER.requireIndexer(data).tempFrame {
                    processorManager.processAs(elseStatement.single(), data)
                }

            }

        }
    }

    fun isValidElvis(stm: IfStatement): Boolean {
        val body = stm.body
        val elseStatement = stm.elseStatement

        if (body.size != 1 || elseStatement.size != 1)
            return false

        val f = { it: Instruction ->
            it.isExitOrFlow()
                    || it.safeForComparison is BodyHolder
            // Anything else? I bet I'm missing some expressions
        }

        return body.none(f) && elseStatement.none(f)
    }
}
