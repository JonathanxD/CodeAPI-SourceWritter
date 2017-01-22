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
package com.github.jonathanxd.codeapi.source.gen.generator

import com.github.jonathanxd.codeapi.base.*
import com.github.jonathanxd.codeapi.common.Data
import com.github.jonathanxd.codeapi.gen.value.CodeSourceData
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.generic.GenericSignature
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.source.gen.value.DeclarationValue
import com.github.jonathanxd.codeapi.source.gen.value.PlainValue
import com.github.jonathanxd.codeapi.source.gen.value.TargetValue

object TypeDeclarationSourceGenerator : ValueGenerator<TypeDeclaration, String, PlainSourceGenerator> {

    override fun gen(inp: TypeDeclaration, c: PlainSourceGenerator, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>, codeSourceData: CodeSourceData, data: Data): List<Value<*, String, PlainSourceGenerator>> {

        val values = mutableListOf<Value<*, String, PlainSourceGenerator>>(
                DeclarationValue.create(inp),
                TargetValue.create(Annotable::class.java, inp, parents),

                TargetValue.create(ModifiersHolder::class.java, inp, parents),

                PlainValue.create(
                        when (inp) {
                            is InterfaceDeclaration -> "interface"
                            is AnnotationDeclaration -> "@interface"
                            is ClassDeclaration -> "class"
                            is EnumDeclaration -> "enum"
                            else -> ""
                        }
                ),

                TargetValue.create(Named::class.java, inp, parents),

                TargetValue.create(GenericSignatureHolder::class.java, inp, parents)
        )

        val packageName = inp.packageName

        if (!packageName.isEmpty() && !Util.hasTypeParent(parents)) {
            values.add(0, PlainValue.create("package $packageName;"))
        }

        if (inp is SuperClassHolder) {
            values.add(TargetValue.create(SuperClassHolder::class.java, inp, parents))
        }

        if (inp is ImplementationHolder) {
            values.add(TargetValue.create(ImplementationHolder::class.java, inp, parents))
        }

        values.add(TargetValue.create(BodyHolder::class.java, inp, parents))

        return values
    }

}
