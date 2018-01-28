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

import com.github.jonathanxd.kores.base.TypeDeclaration;
import com.github.jonathanxd.kores.test.SwitchTest_;

import org.junit.Test;

public class SwitchSourceTest {

    @Test
    public void switchTest() {
        TypeDeclaration $ = SwitchTest_.$();

        SourceTest test = CommonSourceTest.test(this.getClass(), $);

        test.expect("package com;\n" +
                "\n" +
                "import com.github.jonathanxd.kores.test.SwitchTest_.TestEnum;\n" +
                "\n" +
                "public class SwitchTestClass {\n" +
                "\n" +
                "    public SwitchTestClass(int number, int number2, TestEnum testEnum, String str, Object o) {\n" +
                "        switch (number) {\n" +
                "            case 1: {\n" +
                "                System.out.println(\"1\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case 3: {\n" +
                "                System.out.println(\"3\");\n" +
                "            }\n" +
                "            case 5: {\n" +
                "                System.out.println(\"5\");\n" +
                "                break;\n" +
                "            }\n" +
                "            default: {\n" +
                "                System.out.println(\"default\");\n" +
                "            }\n" +
                "        }\n" +
                "        switch (number2) {\n" +
                "            case 1: {\n" +
                "                System.out.println(\"1\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case 10: {\n" +
                "                System.out.println(\"10\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case 100: {\n" +
                "                System.out.println(\"100\");\n" +
                "                break;\n" +
                "            }\n" +
                "            default: {\n" +
                "                System.out.println(\"default\");\n" +
                "            }\n" +
                "        }\n" +
                "        switch (testEnum) {\n" +
                "            case TestEnum.A: {\n" +
                "                System.out.println(\"A\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case TestEnum.B: {\n" +
                "                System.out.println(\"B\");\n" +
                "            }\n" +
                "            default: {\n" +
                "                System.out.println(\"B or default\");\n" +
                "            }\n" +
                "        }\n" +
                "        switch (str) {\n" +
                "            case \"AHEAD\": {\n" +
                "                System.out.println(\"AHEAD\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case \"XM\": {\n" +
                "                System.out.println(\"XM\");\n" +
                "                break;\n" +
                "            }\n" +
                "            case \"AH\": {\n" +
                "                System.out.println(\"AH\");\n" +
                "                break;\n" +
                "            }\n" +
                "            default: {\n" +
                "                System.out.println(\"default\");\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n");
    }

}
