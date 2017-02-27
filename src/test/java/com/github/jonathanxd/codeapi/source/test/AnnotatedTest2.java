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
package com.github.jonathanxd.codeapi.source.test;

import com.github.jonathanxd.codeapi.CodeSource;
import com.github.jonathanxd.codeapi.base.TypeDeclaration;
import com.github.jonathanxd.codeapi.builder.ClassDeclarationBuilder;
import com.github.jonathanxd.codeapi.generic.GenericSignature;
import com.github.jonathanxd.codeapi.test.AnnotatedTest_;
import com.github.jonathanxd.codeapi.type.PlainCodeType;
import com.github.jonathanxd.codeapi.util.Modifiers;
import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.object.Pair;

import org.junit.Test;

import java.lang.reflect.Modifier;

import kotlin.collections.CollectionsKt;

import static com.github.jonathanxd.codeapi.CodeAPI.enumValue;
import static com.github.jonathanxd.codeapi.CodeAPI.sourceOfParts;
import static com.github.jonathanxd.codeapi.CodeAPI.values;
import static com.github.jonathanxd.codeapi.CodeAPI.visibleAnnotation;

public class AnnotatedTest2 {

    public static Pair<@Named("Main class") TypeDeclaration, @Named("Source") CodeSource> $() {

        PlainCodeType plainCodeType = new PlainCodeType("java.lang.invoke.MethodHandle.PolymorphicSignature", true);

        TypeDeclaration typeDeclaration = new ClassDeclarationBuilder()
                .withModifiers(Modifiers.fromJavaModifiers(Modifier.PUBLIC))
                .withGenericSignature(GenericSignature.empty())
                .withAnnotations(CollectionsKt.listOf(
                        visibleAnnotation(AnnotatedTest_.Simple.class,
                                values("value", new Object[]{
                                        enumValue(AnnotatedTest_.MyEnum.class, "A"), enumValue(AnnotatedTest_.MyEnum.class, "B"), enumValue(AnnotatedTest_.MyEnum.class, "C")
                                }, "myEnum", enumValue(AnnotatedTest_.MyEnum.class, "B"))
                        ))
                )
                .withQualifiedName("test.AnnotatedTestClass")
                .withBody(CodeSource.empty())
                .build();

        return Pair.of(typeDeclaration, sourceOfParts(typeDeclaration));
    }

    @Test
    public void annotatedTest() {
        Pair<@Named("Main class") TypeDeclaration, @Named("Source") CodeSource> $ = AnnotatedTest2.$();
        SourceTest test = CommonSourceTest.test(this.getClass(), $._1(), $._2());
        System.out.println(test.result());
        test.expect("package test;\n" +
                "\n" +
                "import com.github.jonathanxd.codeapi.test.AnnotatedTest_.Simple;\n" +
                "import com.github.jonathanxd.codeapi.test.AnnotatedTest_.MyEnum;\n" +
                "\n" +
                "@ Simple ( myEnum = MyEnum . B , value = { MyEnum . A , MyEnum . B , MyEnum . C  } ) \n" +
                "public class AnnotatedTestClass { \n" +
                "     \n" +
                "} \n" +
                "\n");
    }
}
