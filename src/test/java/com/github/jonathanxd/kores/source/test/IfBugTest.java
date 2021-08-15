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
package com.github.jonathanxd.kores.source.test;

import com.github.jonathanxd.kores.Instructions;
import com.github.jonathanxd.kores.base.IfExpr;
import com.github.jonathanxd.kores.base.IfStatement;
import com.github.jonathanxd.kores.factory.Factories;
import com.github.jonathanxd.kores.literal.Literals;
import com.github.jonathanxd.kores.operator.Operators;
import com.github.jonathanxd.kores.source.process.PlainSourceGenerator;import com.github.jonathanxd.kores.source.process.PlainSourceGenerator;

import org.junit.Test;

public class IfBugTest {

    @Test
    public void ifBugTest() {
        IfStatement x = IfStatement.Builder.builder()
                .expressions(new IfExpr(Factories.accessVariable(Boolean.TYPE, "b"), Operators.NOT_EQUAL_TO, Literals.FALSE))
                .body(Instructions.empty())
                .elseStatement(Instructions.empty())
                .build();

        PlainSourceGenerator generator = new PlainSourceGenerator();

        new SourceTest(generator, localGenerator -> localGenerator.process(x))
                .expect("if (b) {\n" +
                        "}\n");
    }

}
