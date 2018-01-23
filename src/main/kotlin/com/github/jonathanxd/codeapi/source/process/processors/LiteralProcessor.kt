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

import com.github.jonathanxd.codeapi.Types
import com.github.jonathanxd.codeapi.literal.Literal
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.CodeTypeHelper
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.codeapi.type.CodeType
import com.github.jonathanxd.codeapi.type.codeType
import com.github.jonathanxd.iutils.data.TypedData
import java.lang.reflect.Type

object LiteralProcessor : AppendingProcessor<Literal> {

    override fun process(
        part: Literal,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: JavaSourceAppender
    ) {
        if (part.type.`is`(Types.LONG)) {
            appender += "${part.name}L"
        } else if (part.type.`is`(Types.DOUBLE)) {
            appender += "${part.name}D"
        } else if (part.type.`is`(Types.FLOAT)) {
            appender += "${part.name}F"
        } else if (part.type.`is`(CodeType::class.codeType)) {
            val type = part.value as Type
            appender.appendImport(type)
            CodeTypeHelper.appendName(type, appender)
            appender += ".class"
        } else {
            appender += part.name
        }

        /*if (Util.isBody(parents)) {
            Util.close(values)
        }*/
    }

}
