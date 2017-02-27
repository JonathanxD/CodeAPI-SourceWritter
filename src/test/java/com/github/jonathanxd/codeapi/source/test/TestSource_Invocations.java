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
import com.github.jonathanxd.codeapi.test.InvocationsTest_;
import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.object.Pair;

import org.junit.Test;

/**
 * Created by jonathan on 03/06/16.
 */
public class TestSource_Invocations {
    @Test
    public void testSource() {
        Pair<@Named("Main class") TypeDeclaration, @Named("Source") CodeSource> $ = InvocationsTest_.$();
        SourceTest test = CommonSourceTest.test(this.getClass(), $._1(), $._2());
        test.expect("package fullName;\n" +
                "\n" +
                "import com.github.jonathanxd.codeapi.test.InvocationsTest_;\n" +
                "import com.github.jonathanxd.codeapi.test.Greeter;\n" +
                "import com.github.jonathanxd.codeapi.test.WorldGreeter;\n" +
                "import java.util.function.Supplier;\n" +
                "\n" +
                "public class InvocationsTest__Generated { \n" +
                "    public final String FIELD = \"AVD\" ; \n" +
                "    public final int n = 15 ; \n" +
                "    \n" +
                "    public InvocationsTest__Generated ( ) { \n" +
                "        System . out . println ( \"Hello\" ) ; \n" +
                "        this . printIt ( \"Oi\" ) ; \n" +
                "         \n" +
                "    } \n" +
                "    \n" +
                "    \n" +
                "    public void printIt ( Object n ) { \n" +
                "        if ( n != null ) { \n" +
                "            System . out . println ( \"Hello :D\" ) ; \n" +
                "             \n" +
                "        } \n" +
                "        \n" +
                "        String dingdong = \"DingDong\" ; \n" +
                "        System . out . println ( dingdong ) ; \n" +
                "        System . out . println ( n ) ; \n" +
                "         \n" +
                "    } \n" +
                "    \n" +
                "    \n" +
                "    public boolean check ( int x ) { \n" +
                "        InvocationsTest_ . bmp ( \"xy\" ,  \"yz\" ) ; \n" +
                "        System . out . println ( \"Invoke Interface ->\" ) ; \n" +
                "        Greeter greeter = new WorldGreeter ( ) ; \n" +
                "        String greetingVar = greeter . hello ( ) ; \n" +
                "        System . out . println ( greetingVar ) ; \n" +
                "        System . out . println ( \"Invoke Interface <-\" ) ; \n" +
                "        System . out . println ( \"Invoke Dynamic ->\" ) ; \n" +
                "        Supplier supplier2 = ( ) -> { \n" +
                "            return \"BRB\" ; \n" +
                "             \n" +
                "        } \n" +
                "        \n" +
                "        ; \n" +
                "        System . out . println ( ( String ) supplier2 . get ( ) ) ; \n" +
                "        Supplier supplier = greeter :: hello ; \n" +
                "        String str = ( String ) supplier . get ( ) ; \n" +
                "        System . out . println ( str ) ; \n" +
                "        System . out . println ( \"Invoke Dynamic <-\" ) ; \n" +
                "        System . out . println ( \"Invoke Dynamic Bootstrap ->\" ) ; \n" +
                "        // Dynamic::[MethodInvocationImpl(localization=JavaType[Lcom/github/jonathanxd/codeapi/test/InvocationsTest_;], arguments=[QuotedStringLiteral[name=\"World\", type=JavaType[Ljava/lang/String;]]], spec=MethodSpecificationImpl(methodType=DYNAMIC_METHOD, methodName=helloWorld, description=TypeSpec(returnType=PredefinedType[V], parameterTypes=[PredefinedType[Ljava/lang/String;]])), invokeType=INVOKE_VIRTUAL, invokeDynamic=Bootstrap[methodTypeSpec = MethodTypeSpec(localization=JavaType[Lcom/github/jonathanxd/codeapi/test/InvocationsTest_;], methodName=myBootstrap, typeSpec=TypeSpec(returnType=JavaType[Ljava/lang/invoke/CallSite;], parameterTypes=[JavaType[Ljava/lang/invoke/MethodHandles$Lookup;], JavaType[Ljava/lang/String;], JavaType[Ljava/lang/invoke/MethodType;], LoadedArrayCodeType[[Ljava/lang/Object;]])), invokeType = INVOKE_STATIC, arguments = []], target=AccessImpl(type=STATIC, localization=null))]; \n" +
                "        System . out . println ( \"Invoke Dynamic Bootstrap <-\" ) ; \n" +
                "        if ( x == 9 || ( x == 7 ) ) { \n" +
                "            return 0 ; \n" +
                "             \n" +
                "        } \n" +
                "        \n" +
                "        System . out . println ( x ) ; \n" +
                "        return 1 ; \n" +
                "         \n" +
                "    } \n" +
                "    \n" +
                "     \n" +
                "} \n" +
                "\n");
        test.consume(System.out::println);

    }


}