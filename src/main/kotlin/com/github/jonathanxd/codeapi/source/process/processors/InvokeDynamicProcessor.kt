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

import com.github.jonathanxd.codeapi.CodeInstruction
import com.github.jonathanxd.codeapi.Types
import com.github.jonathanxd.codeapi.base.Access
import com.github.jonathanxd.codeapi.base.InvokeDynamicBase
import com.github.jonathanxd.codeapi.base.Return
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.codeapi.util.`is`
import com.github.jonathanxd.codeapi.util.simpleName
import com.github.jonathanxd.iutils.data.TypedData

object InvokeDynamicProcessor : AppendingProcessor<InvokeDynamicBase> {
    override fun process(part: InvokeDynamicBase, data: TypedData, processorManager: ProcessorManager<*>, appender: JavaSourceAppender) {

        when (part) {
            is InvokeDynamicBase.LambdaLocalCodeBase -> {
                val size = part.baseSam.typeSpec.parameterTypes.size
                val args = part.localCode.parameters.subList(0, size).joinToString(separator = ", ", prefix = "(", postfix = ")") { it.name }

                appender += args
                appender += " -> "


                val single = part.localCode.body.singleOrNull()

                if (single != null) {

                    val shouldSingle = (part.baseSam.typeSpec.returnType.`is`(Types.VOID) && single !is Return)
                            || (!part.baseSam.typeSpec.returnType.`is`(Types.VOID) && single is Return)

                    if (shouldSingle) {
                        if (single is Return) {
                            processorManager.processAs(single.value, data)
                        } else {
                            processorManager.processAs(single, data)
                        }
                    } else {
                        processorManager.processAs(part.localCode.body, data)
                    }
                } else
                    processorManager.processAs(part.localCode.body, data)
            }
            is InvokeDynamicBase.LambdaMethodRefBase -> {
                val name = part.methodRef.methodTypeSpec.methodName

                if (part.methodRef.invokeType.isStatic()) {
                    processorManager.processAs(Util.localizationResolve(part.methodRef.methodTypeSpec.localization, data), data)
                } else {
                    processorManager.processAs(part.arguments.first(), data)
                }
                appender += "::"
                appender += name
            }
            else -> {
                appender += "// Unsupported: $part"
            }
        }

    }
}