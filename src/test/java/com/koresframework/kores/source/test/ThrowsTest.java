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
package com.koresframework.kores.source.test;

import com.koresframework.kores.Instructions;
import com.koresframework.kores.Types;
import com.koresframework.kores.base.ClassDeclaration;
import com.koresframework.kores.base.KoresModifier;
import com.koresframework.kores.base.MethodDeclaration;
import com.koresframework.kores.base.TypeDeclaration;
import com.koresframework.kores.generic.GenericSignature;
import com.koresframework.kores.literal.Literals;
import kotlin.collections.CollectionsKt;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static com.koresframework.kores.factory.Factories.returnValue;

public class ThrowsTest {

    @Test
    public void throwsTest() {
        TypeDeclaration $ = ClassDeclaration.Builder.builder()
                .modifiers(KoresModifier.fromJavaModifiers(Modifier.PUBLIC))
                .genericSignature(GenericSignature.empty())
                .qualifiedName("test.StringClass")
                .methods(MethodDeclaration.Builder.builder()
                        .publicModifier()
                        .name("aaa")
                        .returnType(Types.STRING)
                        .throwsClause(Exception.class, Throwable.class)
                        .body(Instructions.fromIterable(CollectionsKt.listOf(
                                returnValue(Literals.STRING("\"hello\\\" \\"))
                        )))
                        .build())
                .build();


        SourceTest test = CommonSourceTest.test(this.getClass(), $);

        test.expect("package test;\n" +
                "\n" +
                "public class StringClass {\n" +
                "\n" +
                "    public String aaa() throws Exception, Throwable {\n" +
                "        return \"\\\"hello\\\\\\\" \\\\\";\n" +
                "    }\n" +
                "}\n");
    }

}
