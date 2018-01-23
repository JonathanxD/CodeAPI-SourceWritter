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

import com.github.jonathanxd.codeapi.base.Access
import com.github.jonathanxd.codeapi.base.Accessor
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.safeForComparison
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.iutils.data.TypedData

object AccessorProcessor : AppendingProcessor<Accessor> {

    override fun process(
        part: Accessor,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: JavaSourceAppender
    ) {
        process(part, true, data, processorManager, appender)
    }

    fun process(
        accessor: Accessor,
        separator: Boolean,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: JavaSourceAppender
    ) {
        var anySeparator = false

        val target = accessor.target
        val safeTarget = target.safeForComparison
        val localization = Util.localizationResolve(accessor.localization, data)
        val targetIsAccess = target is Access

        if (!targetIsAccess && safeTarget !== localization) {
            processorManager.processAs(target, data)

            if (separator) {
                appender.append(".")
                anySeparator = true
            }
        } else if (!targetIsAccess && safeTarget === localization) {
            processorManager.processAs(localization, data)

            if (separator) {
                appender.append(".")
                anySeparator = true
            }
        }

        if (targetIsAccess && safeTarget !== localization) {
            safeTarget as Access

            if (safeTarget == Access.STATIC) {
                processorManager.processAs(localization, data)
                processorManager.processAs(target, data)
                if (!anySeparator && separator) appender.append(".")
            } else if (safeTarget == Access.THIS || safeTarget != Access.LOCAL) {
                processorManager.processAs(target, data)
                if (!anySeparator && separator) appender.append(".")
            }
        }


    }

}