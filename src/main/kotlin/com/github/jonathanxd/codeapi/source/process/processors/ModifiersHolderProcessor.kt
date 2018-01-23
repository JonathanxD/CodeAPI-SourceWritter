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

import com.github.jonathanxd.codeapi.base.CodeModifier
import com.github.jonathanxd.codeapi.base.CodeParameter
import com.github.jonathanxd.codeapi.base.ModifierType
import com.github.jonathanxd.codeapi.base.ModifiersHolder
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.iutils.data.TypedData

object ModifiersHolderProcessor : AppendingProcessor<ModifiersHolder> {

    override fun process(
        part: ModifiersHolder,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: JavaSourceAppender
    ) {

        val mutableModifiers = part.modifiers.sorted().toMutableSet()

        if (!part.modifiers.any { it.modifierType == ModifierType.VISIBILITY }
                && part !is CodeParameter)
            mutableModifiers += CodeModifier.PUBLIC

        if (mutableModifiers.isEmpty())
            return

        val modifierStr = CodeModifier.toString(mutableModifiers)

        if (modifierStr.isNotEmpty()) {
            appender += "$modifierStr "
        }
    }

}
