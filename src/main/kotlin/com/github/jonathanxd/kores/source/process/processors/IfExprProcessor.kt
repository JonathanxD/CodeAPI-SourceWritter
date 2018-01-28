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
package com.github.jonathanxd.kores.source.process.processors

import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.kores.Instruction
import com.github.jonathanxd.kores.base.IfExpr
import com.github.jonathanxd.kores.base.InstanceOfCheck
import com.github.jonathanxd.kores.literal.Literals
import com.github.jonathanxd.kores.operator.Operators
import com.github.jonathanxd.kores.processor.ProcessorManager
import com.github.jonathanxd.kores.processor.processAs
import com.github.jonathanxd.kores.safeForComparison
import com.github.jonathanxd.kores.source.process.AppendingProcessor

object IfExprProcessor : AppendingProcessor<IfExpr> {

    override fun process(
        part: IfExpr,
        data: TypedData,
        processorManager: ProcessorManager<*>,
        appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
    ) {
        val expr1 = part.expr1
        val expr2 = part.expr2
        val safeExpr1 = expr1.safeForComparison
        val safeExpr2 = expr2.safeForComparison
        val operation = part.operation

        fun Instruction.isBoolean() = this is Literals.BoolLiteral
        fun Instruction.toBoolStr() = (this as Literals.BoolLiteral).name

        fun processNegate(codePart: Instruction) {
            appender += "!"
            if (codePart.safeForComparison is InstanceOfCheck)
                appender += "("

            processorManager.processAs(codePart, data)

            if (codePart.safeForComparison is InstanceOfCheck)
                appender += ")"
        }

        // expr1 == true
        // true == expr2
        // expr1 != true
        // true != expr1
        val isEq = operation == Operators.EQUAL_TO
        val boolValue1 =
            if (safeExpr1.isBoolean()) isEq == safeExpr1.toBoolStr().toBoolean() else null
        val boolValue2 =
            if (safeExpr2.isBoolean()) isEq == safeExpr2.toBoolStr().toBoolean() else null

        if ((boolValue1 == null && boolValue2 == null)
                || (boolValue1 != null && boolValue2 != null)
        ) {
            processorManager.processAs(expr1, data)
            processorManager.process(operation, data)
            processorManager.processAs(expr2, data)
        } else if (boolValue1 == true && boolValue2 == null) {
            processorManager.processAs(expr2, data)
        } else if (boolValue1 == false && boolValue2 == null) {
            processNegate(expr2)
        } else if (boolValue1 == null && boolValue2 == true) {
            processorManager.processAs(expr1, data)
        } else if (boolValue1 == null && boolValue2 == false) {
            processNegate(expr1)
        }

    }
}
