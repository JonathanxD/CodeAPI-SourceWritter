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

import com.github.jonathanxd.codeapi.Types
import com.github.jonathanxd.codeapi.base.*
import com.github.jonathanxd.codeapi.base.comment.CommentHolder
import com.github.jonathanxd.codeapi.processor.CodeProcessor
import com.github.jonathanxd.codeapi.processor.Processor
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.APPENDER
import com.github.jonathanxd.codeapi.source.process.DECLARATION
import com.github.jonathanxd.codeapi.util.`is`
import com.github.jonathanxd.codeapi.util.require
import com.github.jonathanxd.iutils.data.TypedData

object TypeDeclarationProcessor : Processor<TypeDeclaration> {

    override fun process(part: TypeDeclaration, data: TypedData, codeProcessor: CodeProcessor<*>) {
        val appender = APPENDER.require(data)
        @Suppress("NAME_SHADOWING")
        val data = TypedData(data)

        DECLARATION.set(data, part)
        APPENDER.set(data, appender)

        appender.addDeclaration(part)

        appender.addDeclaration(part)

        codeProcessor.processAs<CommentHolder>(part, data)
        codeProcessor.processAs<Annotable>(part, data)

        codeProcessor.processAs<ModifiersHolder>(part, data)

        appender += when (part) {
            is InterfaceDeclaration -> "interface "
            is AnnotationDeclaration -> "@interface "
            is ClassDeclaration -> "class "
            is EnumDeclaration -> "enum "
            else -> ""
        }

        codeProcessor.processAs<Named>(part, data)

        codeProcessor.processAs<GenericSignatureHolder>(part, data)

        appender += " "

        val packageName = part.packageName

        appender.setPackageIfNotDefined(packageName)

        var superOrItfs = false

        if (part is SuperClassHolder) {
            if (!part.superClass.`is`(Types.OBJECT)) {
                codeProcessor.processAs<SuperClassHolder>(part, data)
                superOrItfs = true
            }
        }

        if (part is ImplementationHolder) {
            if (part.implementations.isNotEmpty()) {
                codeProcessor.processAs<ImplementationHolder>(part, data)
                superOrItfs = true
            }
        }

        if (superOrItfs)
            appender += " "

        codeProcessor.processAs<ElementsHolder>(part, data)

        appender += "\n"

        if (part.outerClass != null)
            appender += "\n"

    }

}
