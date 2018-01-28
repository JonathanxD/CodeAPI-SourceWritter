/*
 *      Kores-SourceWriter - Translates Kores Structure to Java Source <https://github.com/JonathanxD/Kores-SourceWriter>
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
package com.github.jonathanxd.kores.source.test;

import com.github.jonathanxd.kores.base.ClassDeclaration;
import com.github.jonathanxd.kores.base.KoresModifier;
import com.github.jonathanxd.kores.base.TypeDeclaration;
import com.github.jonathanxd.kores.generic.GenericSignature;
import com.github.jonathanxd.kores.test.AnnotatedTest_;
import com.github.jonathanxd.iutils.map.MapUtils;

import org.junit.Test;

import java.lang.reflect.Modifier;

import kotlin.collections.CollectionsKt;

import static com.github.jonathanxd.kores.factory.Factories.enumValue;
import static com.github.jonathanxd.kores.factory.Factories.runtimeAnnotation;

public class AnnotatedTest2 {

    public static TypeDeclaration $() {

        TypeDeclaration typeDeclaration = ClassDeclaration.Builder.builder()
                .modifiers(KoresModifier.fromJavaModifiers(Modifier.PUBLIC))
                .genericSignature(GenericSignature.empty())
                .annotations(CollectionsKt.listOf(
                        runtimeAnnotation(AnnotatedTest_.Simple.class,
                                MapUtils.mapOf("value", new Object[]{
                                        enumValue(AnnotatedTest_.MyEnum.class, "A"), enumValue(AnnotatedTest_.MyEnum.class, "B"), enumValue(AnnotatedTest_.MyEnum.class, "C")
                                }, "myEnum", enumValue(AnnotatedTest_.MyEnum.class, "B"))
                        ),
                        runtimeAnnotation(Override.class))
                )
                .qualifiedName("test.AnnotatedTestClass")
                .build();

        return typeDeclaration;
    }

    @Test
    public void annotatedTest() {
        TypeDeclaration $ = AnnotatedTest2.$();
        SourceTest test = CommonSourceTest.test(this.getClass(), $);
        test.expect("package test;\n" +
                "\n" +
                "import com.github.jonathanxd.kores.test.AnnotatedTest_.Simple;\n" +
                "import com.github.jonathanxd.kores.test.AnnotatedTest_.MyEnum;\n" +
                "\n" +
                "@Simple(myEnum = MyEnum.B, value = {MyEnum.A, MyEnum.B, MyEnum.C})\n" +
                "@Override\n" +
                "public class AnnotatedTestClass {\n" +
                "}\n");
    }
}
