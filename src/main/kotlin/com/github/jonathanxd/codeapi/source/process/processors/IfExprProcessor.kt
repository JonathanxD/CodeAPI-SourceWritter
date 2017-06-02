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
import com.github.jonathanxd.codeapi.base.IfExpr
import com.github.jonathanxd.codeapi.base.InstanceOfCheck
import com.github.jonathanxd.codeapi.literal.Literals
import com.github.jonathanxd.codeapi.processor.CodeProcessor
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.codeapi.util.safeForComparison
import com.github.jonathanxd.iutils.data.TypedData

object IfExprProcessor : AppendingProcessor<IfExpr> {

    override fun process(part: IfExpr, data: TypedData, codeProcessor: CodeProcessor<*>, appender: JavaSourceAppender) {
        val expr1 = part.expr1
        val expr2 = part.expr2
        val safeExpr1 = expr1.safeForComparison
        val safeExpr2 = expr2.safeForComparison
        val operation = part.operation

        fun CodeInstruction.isBoolean() = this is Literals.BoolLiteral
        fun CodeInstruction.toBoolStr() = (this as Literals.BoolLiteral).name

        fun processNegate(codePart: CodeInstruction) {
            appender += "!"
            if (codePart.safeForComparison is InstanceOfCheck)
                appender += "("

            codeProcessor.processAs(codePart, data)

            if (codePart.safeForComparison is InstanceOfCheck)
                appender += ")"
        }

        // expr1 == true
        // true == expr2
        // expr1 != true
        // true != expr1
        val boolValue1 = if (safeExpr1.isBoolean()) safeExpr1.toBoolStr() else null
        val boolValue2 = if (safeExpr2.isBoolean()) safeExpr2.toBoolStr() else null

        if ((boolValue1 == null && boolValue2 == null)
                || (boolValue1 != null && boolValue2 != null)) {
            codeProcessor.processAs(expr1, data)
            codeProcessor.process(operation, data)
            codeProcessor.processAs(expr2, data)
        } else if (boolValue1 == "true" && boolValue2 == null) {
            codeProcessor.processAs(expr2, data)
        } else if (boolValue1 == "false" && boolValue2 == null) {
            processNegate(expr2)
        } else if (boolValue1 == null && boolValue2 == "true") {
            codeProcessor.processAs(expr1, data)
        } else if (boolValue1 == null && boolValue2 == "false") {
            processNegate(expr1)
        }


    }
}
