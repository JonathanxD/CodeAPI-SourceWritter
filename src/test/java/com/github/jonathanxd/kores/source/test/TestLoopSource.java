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
package com.github.jonathanxd.kores.source.test;

import com.github.jonathanxd.kores.base.TypeDeclaration;
import com.github.jonathanxd.kores.test.LoopTest_;

import org.junit.Test;

public class TestLoopSource {
    @Test
    public void testSource() {
        TypeDeclaration $ = LoopTest_.$();
        SourceTest test = CommonSourceTest.test(this.getClass(), $);
        test.expect("package fullName;\n" +
                "\n" +
                "public class LoopTest_ {\n" +
                "\n" +
                "    public LoopTest_() {\n" +
                "        int x = 0;\n" +
                "        while (x < 17) {\n" +
                "            System.out.println(x);\n" +
                "            x = x + 1;\n" +
                "        }\n" +
                "        for (int i = 0; (i < 100); i = i + 1) {\n" +
                "            if (i == 5) {\n" +
                "                continue;\n" +
                "            }\n" +
                "            System.out.println(i);\n" +
                "        }\n" +
                "        int u = 0;\n" +
                "        do {\n" +
                "            System.out.println(u);\n" +
                "            u = u + 1;\n" +
                "            if (u == 2) {\n" +
                "                break;\n" +
                "            }\n" +
                "        } while (u < 5);\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}\n");
    }

}
