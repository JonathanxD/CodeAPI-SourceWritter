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

import com.github.jonathanxd.kores.source.process.PlainSourceGenerator;
import com.github.jonathanxd.kores.test.CommonGen;import com.github.jonathanxd.kores.source.process.PlainSourceGenerator;

import org.junit.Test;

public class ArrayTest {
    final String name = getClass().getCanonicalName() + "_Generated";

    @Test
    public void arrayTest() {


        PlainSourceGenerator plainSourceGenerator = new PlainSourceGenerator();
        SourceTest test = CommonSourceTest.test(this.getClass(), CommonGen.gen());
        test.expect("package com.github.jonathanxd.kores.test;\n" +
                "\n" +
                "public class CommonGen_Generated {\n" +
                "\n" +
                "    public CommonGen_Generated() {\n" +
                "        String[][] array = new String[][]{new String[]{\"A\", \"B\", \"C\", \"D\", \"E\"}, new String[]{\"F\", \"G\", \"H\", \"I\", \"J\"}};\n" +
                "        String[] array2 = new String[0];\n" +
                "        Object[] array3 = new Object[]{1};\n" +
                "        System.out.println(array[0][0]);\n" +
                "    }\n" +
                "}\n");

    }

}