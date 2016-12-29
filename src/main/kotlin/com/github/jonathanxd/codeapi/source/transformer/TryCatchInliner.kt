/*
 *      CodeAPI-SourceWriter - Framework to generate Java code and Bytecode code. <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/ & https://github.com/TheRealBuggy/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.codeapi.source.transformer

import com.github.jonathanxd.codeapi.CodeSource
import com.github.jonathanxd.codeapi.MutableCodeSource
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.impl.TagLineImpl
import com.github.jonathanxd.codeapi.interfaces.ThrowException
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import com.github.jonathanxd.codeapi.util.Parent
import com.github.jonathanxd.codeapi.util.source.CodeSourceUtil
import com.github.jonathanxd.iutils.container.primitivecontainers.BooleanContainer
import java.util.*

/**
 * Internal class. Inline finally statement.
 */
object TryCatchInliner {

    fun inlineSource(bodyOpt: Optional<CodeSource>,
                     finallyBlockOpt: Optional<CodeSource>,
                     values: MutableList<Value<*, String, PlainSourceGenerator>>,
                     parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>): Boolean {

        if (bodyOpt.isPresent && finallyBlockOpt.isPresent) {
            val codeSource = bodyOpt.get()
            val finallyBlockSource = finallyBlockOpt.get()

            val modified = TryCatchInliner.insertInlineSecure(codeSource, finallyBlockSource)

            values.add(TargetValue.create(CodeSource::class.java, modified, parents))
            return true
        }

        return false
    }

    fun insertInlineSecure(originalSource: CodeSource, toAdd0: CodeSource): CodeSource {

        val booleanContainer = BooleanContainer(false)

        val toAdd = MutableCodeSource()

        toAdd.add(TagLineImpl("Inlined finally", toAdd0))

        val codeSource = CodeSourceUtil.insertBefore({ codePart ->
            if (codePart is ThrowException) {
                booleanContainer.set(true)
                return@insertBefore true
            }

            false
        }, toAdd, originalSource)

        if (!booleanContainer.get()) {
            codeSource.add(toAdd)
        }

        return codeSource
    }

}
