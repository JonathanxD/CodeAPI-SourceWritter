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

import com.github.jonathanxd.iutils.array.ArrayUtils
import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.iutils.kt.require
import com.koresframework.kores.base.Annotation
import com.koresframework.kores.base.EnumValue
import com.koresframework.kores.processor.ProcessorManager
import com.koresframework.kores.processor.processAs
import com.koresframework.kores.source.process.APPENDER
import com.koresframework.kores.source.process.AppendingProcessor
import com.koresframework.kores.type.KoresType

object AnnotationProcessor :
    com.koresframework.kores.source.process.AppendingProcessor<Annotation> {

    override fun process(
        part: Annotation,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.koresframework.kores.source.process.JavaSourceAppender
    ) {
        appender += "@"
        processorManager.processAs(part.type, data)

        val valuesMap = part.values

        if (valuesMap.isNotEmpty()) {
            appender += "("

            if (valuesMap.size == 1 && valuesMap.containsKey("value")) {
                val value = valuesMap["value"]!!

                AnnotationProcessor.addType(value, data, processorManager)
            } else {
                val entries = valuesMap.entries

                entries.forEachIndexed { index, (key, value) ->
                    appender += key
                    appender += " = "

                    AnnotationProcessor.addType(value, data, processorManager)

                    if (index + 1 < entries.size)
                        appender += ", "
                }
            }

            appender += ")"
        }
    }

    fun addType(value: Any, data: TypedData, processorManager: ProcessorManager<*>) {
        val appender = APPENDER.require(data)
        when {
            value is KoresType -> {
                processorManager.processAs(value, data)
                appender += ".class"
            }
            value is EnumValue -> processorManager.processAs(value, data)
            value is Annotation -> processorManager.processAs(value, data)
            value is List<*> -> {
                appender += "{"

                value.filterNotNull().iterator().let {
                    while (it.hasNext()) {
                        AnnotationProcessor.addType(it.next(), data, processorManager)

                        if (it.hasNext())
                            appender += ", "
                    }
                }

                appender += "}"
            }
            value::class.java.isArray -> { // compatibility, keep until CodeAPI-JavaValidator is not ready
                val valuesObj = ArrayUtils.toObjectArray(value)

                appender += "{"

                for (i in valuesObj.indices) {
                    val o = valuesObj[i]

                    AnnotationProcessor.addType(o, data, processorManager)

                    if (i + 1 < valuesObj.size) {
                        appender += ", "
                    }
                }

                appender += "}"
            }
            value is String -> appender += "\"$value\""
            else -> appender += value.toString()
        }
    }
}
