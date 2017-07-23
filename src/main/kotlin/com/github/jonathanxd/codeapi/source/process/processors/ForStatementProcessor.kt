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

import com.github.jonathanxd.codeapi.base.BodyHolder
import com.github.jonathanxd.codeapi.base.ForStatement
import com.github.jonathanxd.codeapi.base.IfExpressionHolder
import com.github.jonathanxd.codeapi.common.CodeNothing
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.*
import com.github.jonathanxd.codeapi.util.safeForComparison
import com.github.jonathanxd.iutils.data.TypedData

object ForStatementProcessor : AppendingProcessor<ForStatement> {

    override fun process(part: ForStatement, data: TypedData, processorManager: ProcessorManager<*>, appender: JavaSourceAppender) {
        appender += "for "
        appender += "("

        if (part.forInit.safeForComparison != CodeNothing) processorManager.processAs(part.forInit, data)

        appender += ";"

        val forExpressionOpt = part.forExpression

        if (!forExpressionOpt.isEmpty()) {
            appender += " "
            processorManager.processAs<IfExpressionHolder>(part, data)
        }

        appender += ";"

        if (part.forUpdate.safeForComparison != CodeNothing) {
            appender += " "
            processorManager.processAs(part.forUpdate, data)
        }

        appender += ")"
        appender += " "

        VARIABLE_INDEXER.requireIndexer(data).tempFrame {
            processorManager.processAs<BodyHolder>(part, data)
        }

        appender += "\n"
    }

}
