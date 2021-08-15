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
import com.koresframework.kores.test.BitwiseIfTest_;

import org.junit.Test;

public class BitwiseIfTest {

    @Test
    public void bitwiseIfTest() {
        TypeDeclaration test = BitwiseIfTest_.$();
        SourceTest sourceTest = CommonSourceTest.test(BitwiseIfTest.class, test);

        sourceTest.expect("package test;\n" +
                "\n" +
                "public class BitwiseIf {\n" +
                "\n" +
                "    public BitwiseIf(boolean h, boolean x) {\n" +
                "        if (h & x) {\n" +
                "            System.out.println(\"BITWISE_AND: true\");\n" +
                "        } else {\n" +
                "            System.out.println(\"BITWISE_AND: false\");\n" +
                "        }\n" +
                "        if (h | x) {\n" +
                "            System.out.println(\"BITWISE_INCLUSIVE_OR: true\");\n" +
                "        } else {\n" +
                "            System.out.println(\"BITWISE_INCLUSIVE_OR: false\");\n" +
                "        }\n" +
                "        if (h ^ x) {\n" +
                "            System.out.println(\"BITWISE_EXCLUSIVE_OR: true\");\n" +
                "        } else {\n" +
                "            System.out.println(\"BITWISE_EXCLUSIVE_OR: false\");\n" +
                "        }\n" +
                "        if (!h ^ x) {\n" +
                "            System.out.println(\"NEGATE_FIRST BITWISE_EXCLUSIVE_OR: true\");\n" +
                "        } else {\n" +
                "            System.out.println(\"NEGATE_FIRST BITWISE_EXCLUSIVE_OR: false\");\n" +
                "        }\n" +
                "        if (!h ^ !x) {\n" +
                "            System.out.println(\"NEGATE_ALL BITWISE_EXCLUSIVE_OR: true\");\n" +
                "        } else {\n" +
                "            System.out.println(\"NEGATE_ALL BITWISE_EXCLUSIVE_OR: false\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public void test(int a, int b) {\n" +
                "        if (a < 100 & b > 100) {\n" +
                "            System.out.println(\"a < 100 & b > 100\");\n" +
                "        } else {\n" +
                "            System.out.println(\"false\");\n" +
                "        }\n" +
                "    }\n" +
                "}\n");
    }

}
