/*
 *      CodeAPI-SourceWriter - Framework to generate Java code and Bytecode code. <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/ & https://github.com/TheRealBuggy/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.codeapi.source.gen.generator

import com.github.jonathanxd.codeapi.common.InvokeDynamic.LambdaFragment
import com.github.jonathanxd.codeapi.common.InvokeDynamic.isInvokeDynamicLambda
import com.github.jonathanxd.codeapi.common.MethodType
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.interfaces.*
import com.github.jonathanxd.codeapi.keywords.Keywords
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue
import com.github.jonathanxd.codeapi.types.CodeType
import com.github.jonathanxd.codeapi.util.Parent
import com.github.jonathanxd.iutils.data.MapData
import java.util.*

object MethodInvocationSourceGenerator : ValueGenerator<MethodInvocation, String, PlainSourceGenerator> {

    override fun gen(methodInvocationImpl: MethodInvocation, plainSourceGenerator: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: MapData): List<Value<*, String, PlainSourceGenerator>> {

        val values = ArrayList<Value<*, String, PlainSourceGenerator>>()

        val invokeDynamicOpt = methodInvocationImpl.invokeDynamic

        val spec = methodInvocationImpl.spec

        val METHOD_SEPARATOR: String

        if (invokeDynamicOpt.isPresent) {

            val invokeDynamic = invokeDynamicOpt.get()

            if (invokeDynamic is LambdaFragment) {

                val methodFragment = invokeDynamic.methodFragment

                val method = methodFragment.method

                val bodyOpt = method.body

                values.add(TargetValue.create(Parameterizable::class.java, method, parents))

                if (bodyOpt.isPresent) {
                    values.add(PlainValue.create("->"))

                    values.add(TargetValue.create(Bodied::class.java, method, parents))
                } else {
                    values.add(PlainValue.create("-> {};"))
                }

                return values

            } else if (isInvokeDynamicLambda(invokeDynamic)) {
                if (spec.arguments.isEmpty()) {
                    METHOD_SEPARATOR = "::"
                } else {
                    values.add(PlainValue.create("() ->"))
                    METHOD_SEPARATOR = "."
                }
            } else {
                return listOf(PlainValue.create("// Dynamic::[" + methodInvocationImpl.toString() + "];"))
            }
        } else {
            METHOD_SEPARATOR = "."
        }

        // Is method reference
        val isRef = METHOD_SEPARATOR == "::"
        val isCtr = spec.methodName == "<init>"
        val isSuper = spec.methodType == MethodType.SUPER_CONSTRUCTOR

        var mi = methodInvocationImpl

        if (isSuper) {
            val localization = mi.localization.orElse(null)
            mi = mi.setTarget(null)

            val type = parents.find(TypeDeclaration::class.java)
                    .orElseThrow { IllegalArgumentException("Cannot determine current class.") }
                    .target as CodeType

            if (localization == null || localization.`is`(type)) {
                values.add(PlainValue.create("this"))
            } else {
                values.add(PlainValue.create("super"))
            }
        }

        if (isCtr && !isRef && !isSuper) {
            values.add(TargetValue.create(Keywords.NEW, parents))
            mi = mi.setTarget(null)
        }

        if (!isSuper) {
            values.addAll(AccessorSourceGenerator.gen(mi, !isRef && !isCtr, parents))
        }

        if (isRef) {
            values.add(PlainValue.create(METHOD_SEPARATOR))
        }

        if (isCtr && isRef && !isSuper) {
            values.add(TargetValue.create(Keywords.NEW, parents))
        }

        values.add(TargetValue.create(MethodSpecification::class.java, spec, parents))


        if (Util.isBody(parents)) {
            values.add(PlainValue.create(";"))
        }

        return values
    }

}
