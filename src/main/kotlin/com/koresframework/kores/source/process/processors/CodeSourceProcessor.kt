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
import com.koresframework.kores.Instruction
import com.koresframework.kores.Instructions
import com.koresframework.kores.base.BodyHolder
import com.koresframework.kores.base.SwitchStatement
import com.koresframework.kores.base.comment.Comments
import com.koresframework.kores.processor.ProcessorManager
import com.koresframework.kores.safeForComparison
import com.koresframework.kores.source.process.AppendingProcessor

object InstructionsProcessor :
    com.koresframework.kores.source.process.AppendingProcessor<Instructions> {

    override fun process(
        part: Instructions,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.koresframework.kores.source.process.JavaSourceAppender
    ) {
        appender += "{"
        appender += "\n"
        appender.addIndent()

        part.forEachIndexed { index, it ->
            try {
                processorManager.process(it::class.java, it, data)
            } catch (t: Throwable) {
                t.addSuppressed(IllegalStateException("Failed to process part '$it' inside Instructions[index=$index]!"))
                throw t
            }

            if (this.genSemiColon(it)) {
                appender += ";"
                appender += "\n"
            }
        }

        appender.removeIndent()
        appender += "}"

    }

    private fun genSemiColon(insn: Instruction) = insn.safeForComparison.let {
        it !is BodyHolder && it !is SwitchStatement && it !is Comments
    }

}
