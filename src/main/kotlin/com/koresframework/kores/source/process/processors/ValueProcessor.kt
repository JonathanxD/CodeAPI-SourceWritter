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
import com.koresframework.kores.*
import com.koresframework.kores.base.IfStatement
import com.koresframework.kores.base.Line
import com.koresframework.kores.base.VariableAccess
import com.koresframework.kores.base.VariableDeclaration
import com.koresframework.kores.common.KoresNothing
import com.koresframework.kores.factory.accessVariable
import com.koresframework.kores.factory.setVariableValue
import com.koresframework.kores.literal.Literals
import com.koresframework.kores.processor.Processor
import com.koresframework.kores.processor.ProcessorManager
import com.koresframework.kores.source.process.*
import com.koresframework.kores.type.`is`
import java.lang.reflect.Type

object ValueProcessor : Processor<Instruction> {

    override fun process(
        part: Instruction,
        data: TypedData,
        processorManager: ProcessorManager<*>
    ) {
        val safePart = part.safeForComparison

        if (safePart != KoresNothing) {
            if (safePart is IfStatement) {
                if (processorManager.options[EXPAND_ELVIS] && !IfStatementProcessor.isValidElvis(
                            safePart
                        )
                ) {
                    val origin = APPENDER.require(data)

                    val newAppender = origin.createNew()

                    APPENDER.set(data, newAppender)

                    if (part is Line)
                        processorManager.process(
                            Line::class.java,
                            part.builder().value(KoresNothing).build(),
                            data
                        )

                    // Expand

                    val stm = safePart

                    val indexer = VARIABLE_INDEXER.requireIndexer(data)
                    val name = indexer
                        .createUniqueName("stack_var$")

                    var inferredType: Type = Types.OBJECT

                    val declaration = VariableDeclaration.Builder.builder()
                        .name(name)
                        .type(Types.OBJECT)
                        .value(Literals.NULL)
                        .build()

                    fun expand(source: Instructions): Instructions {

                        if (source.isNotEmpty) {
                            val newBody = MutableInstructions.create()
                            val bodyLast = source.last()

                            if (!bodyLast.isExitOrFlow()) {
                                val sub = source.subSource(0, source.size - 1)

                                newBody += sub

                                val type = bodyLast.safeForComparison.typeOrNull

                                val frag: Instruction = bodyLast

                                if (type != null && !inferredType.`is`(type)) {
                                    inferredType = type
                                }

                                newBody += setVariableValue(Types.OBJECT, name, frag)
                            } else {
                                newBody += source
                            }

                            return newBody
                        }

                        return source
                    }

                    val newStm = stm.builder()
                        .body(expand(stm.body))
                        .elseStatement(expand(stm.elseStatement))
                        .build()

                    processorManager.process(
                        VariableDeclaration::class.java,
                        declaration.builder().type(inferredType).build(),
                        data
                    )

                    newAppender.append(";")
                    newAppender.append("\n")

                    // /Expand

                    processorManager.process(newStm::class.java, newStm, data)

                    origin.appendBefore(newAppender.getStrings())

                    APPENDER.set(data, origin)

                    processorManager.process(
                        VariableAccess::class.java,
                        accessVariable(inferredType, name),
                        data
                    )

                    return
                } else {
                    ELVIS.set(data, Unit, true)
                }
            }

            processorManager.process(part::class.java, part, data)
        }
    }
}