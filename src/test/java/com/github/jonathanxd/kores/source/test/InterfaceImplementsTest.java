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
import com.github.jonathanxd.kores.Types;
import com.github.jonathanxd.kores.base.*;
import com.github.jonathanxd.kores.factory.InvocationFactory;
import com.github.jonathanxd.kores.generic.GenericSignature;
import com.github.jonathanxd.kores.literal.Literals;
import com.github.jonathanxd.kores.type.Generic;
import kotlin.collections.CollectionsKt;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.function.Function;

import static com.github.jonathanxd.kores.factory.Factories.returnValue;

public class InterfaceImplementsTest {

    @Test
    public void interfaceTest() {
        TypeDeclaration $ = InterfaceDeclaration.Builder.builder()
                .modifiers(KoresModifier.fromJavaModifiers(Modifier.PUBLIC))
                .genericSignature(GenericSignature.empty())
                .qualifiedName("test.InterfaceClass")
                .implementations(Generic.type(Function.class).of(String.class, Integer.class))
                .build();


        SourceTest test = CommonSourceTest.test(this.getClass(), $);

        test.expect("package test;\n" +
                "\n" +
                "import java.util.function.Function;\n" +
                "\n" +
                "public interface InterfaceClass extends Function<String, Integer> {\n" +
                "}\n");
    }

}
