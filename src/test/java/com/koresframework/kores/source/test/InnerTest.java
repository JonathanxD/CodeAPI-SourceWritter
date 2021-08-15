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

import com.koresframework.kores.base.TypeDeclaration;
import com.koresframework.kores.test.InnerClassTest_;

import org.junit.Test;

public class InnerTest {

    @Test
    public void innerTest() {
        TypeDeclaration $ = InnerClassTest_.$();

        SourceTest test = CommonSourceTest.test(this.getClass(), $);
        test.expect("package test;\n" +
                "\n" +
                "public class InnerClass {\n" +
                "\n" +
                "    protected String field = \"XSD\";\n" +
                "\n" +
                "    public InnerClass() {\n" +
                "        new Inner().call();\n" +
                "    }\n" +
                "\n" +
                "    protected InnerClass(String str) {\n" +
                "        System.out.println(str);\n" +
                "    }\n" +
                "\n" +
                "    public void mm() {\n" +
                "        System.out.println(\"A\");\n" +
                "    }\n" +
                "\n" +
                "    public class Inner {\n" +
                "\n" +
                "        public InnerClass a = new InnerClass(\"Hello\");\n" +
                "\n" +
                "        protected String call() {\n" +
                "            System.out.println(InnerClass.this.field);\n" +
                "            InnerClass.this.mm();\n" +
                "            return \"A\";\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}\n");
    }
}
