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
import com.github.jonathanxd.kores.test.InvocationsTest_;

import org.junit.Test;

public class TestSource_Invocations {
    @Test
    public void testSource() {
        TypeDeclaration $ = InvocationsTest_.$();
        SourceTest test = CommonSourceTest.test(this.getClass(), $);
        test.expect("package fullName;\n" +
                "\n" +
                "import com.github.jonathanxd.kores.test.InvocationsTest_;\n" +
                "import com.github.jonathanxd.kores.test.Greeter;\n" +
                "import com.github.jonathanxd.kores.test.WorldGreeter;\n" +
                "import java.util.function.Supplier;\n" +
                "\n" +
                "public class InvocationsTest__Generated {\n" +
                "\n" +
                "    public final String FIELD = \"AVD\";\n" +
                "    public final int n = 15;\n" +
                "\n" +
                "    public InvocationsTest__Generated() {\n" +
                "        System.out.println(\"Hello\");\n" +
                "        this.printIt(\"Oi\");\n" +
                "    }\n" +
                "\n" +
                "    public void printIt(Object n) {\n" +
                "        if (n != null) {\n" +
                "            System.out.println(\"Hello :D\");\n" +
                "        }\n" +
                "        String dingdong = \"DingDong\";\n" +
                "        System.out.println(dingdong);\n" +
                "        System.out.println(n);\n" +
                "    }\n" +
                "\n" +
                "    public boolean check(int x) {\n" +
                "        InvocationsTest_.bmp(\"xy\", \"yz\");\n" +
                "        System.out.println(\"Invoke Interface ->\");\n" +
                "        Greeter greeter = new WorldGreeter();\n" +
                "        String greetingVar = greeter.hello();\n" +
                "        System.out.println(greetingVar);\n" +
                "        System.out.println(\"Invoke Interface <-\");\n" +
                "        System.out.println(\"Invoke Dynamic ->\");\n" +
                "        Supplier supplier2 = () -> \"BRB\";\n" +
                "        System.out.println((String)supplier2.get());\n" +
                "        Supplier supplier = greeter::hello;\n" +
                "        String str = (String)supplier.get();\n" +
                "        System.out.println(str);\n" +
                "        System.out.println(\"Invoke Dynamic <-\");\n" +
                "        System.out.println(\"Invoke Dynamic Bootstrap ->\");\n" +
                "        // Unsupported: InvokeDynamic(bootstrap=MethodInvokeSpec(invokeType=INVOKE_STATIC, methodTypeSpec=MethodTypeSpec(localization=JavaType[Lcom/github/jonathanxd/kores/test/InvocationsTest_;], methodName=myBootstrap, typeSpec=TypeSpec(returnType=class java.lang.invoke.CallSite, parameterTypes=[class java.lang.invoke.MethodHandles$Lookup, class java.lang.String, class java.lang.invoke.MethodType, class [Ljava.lang.Object;]))), dynamicMethod=DynamicMethodSpec(name=helloWorld, typeSpec=TypeSpec(returnType=PredefinedType[V], parameterTypes=[PredefinedType[Ljava/lang/String;]]), arguments=[StringLiteral[name=\"World\", type=JavaType[Ljava/lang/String;]]]), bootstrapArgs=[]);\n" +
                "        System.out.println(\"Invoke Dynamic Bootstrap <-\");\n" +
                "        if (x == 9 || x == 7) {\n" +
                "            return 0;\n" +
                "        }\n" +
                "        System.out.println(x);\n" +
                "        return 1;\n" +
                "    }\n" +
                "}\n");

    }


}