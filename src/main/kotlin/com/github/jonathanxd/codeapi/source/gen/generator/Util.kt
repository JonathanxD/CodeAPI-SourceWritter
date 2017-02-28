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

import com.github.jonathanxd.codeapi.CodeSource
import com.github.jonathanxd.codeapi.base.BodyHolder
import com.github.jonathanxd.codeapi.base.ImplementationHolder
import com.github.jonathanxd.codeapi.base.SuperClassHolder
import com.github.jonathanxd.codeapi.base.TypeDeclaration
import com.github.jonathanxd.codeapi.gen.value.Parent
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.source.gen.PlainSourceGenerator
import com.github.jonathanxd.codeapi.type.CodeType
import com.github.jonathanxd.codeapi.util.Alias

object Util {

    fun localizationResolve(type: CodeType, parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>): CodeType {
        if (type is Alias) {
            val decl = parents.find(TypeDeclaration::class.java).orElseThrow { RuntimeException("Cannot determine the localization type for alias '$type'") }.target as TypeDeclaration

            return when (type) {
                is Alias.THIS -> decl
                is Alias.SUPER -> (decl as SuperClassHolder).superClass
                is Alias.INTERFACE -> (decl as ImplementationHolder).implementations[type.n]
            }

        } else {
            return type
        }
    }


    fun hasTypeParent(parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>) = parents.parent?.find(TypeDeclaration::class.java)?.isPresent ?: false

    fun isBody(parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>): Boolean {
        val parent = parents.parent

        return parent != null && (BodyHolderSourceGenerator::class.java.isAssignableFrom(parent.current::class.java) || CodeSourceSourceGenerator::class.java.isAssignableFrom(parent.current::class.java))
    }

    fun getBody(parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>): CodeSource {
        val parent = parents.parent

        if (parent != null) {

            if (BodyHolderSourceGenerator::class.java.isAssignableFrom(parent.current::class.java))
                return (parent.target as BodyHolder).body
            else if (CodeSourceSourceGenerator::class.java.isAssignableFrom(parent.current::class.java))
                return parent.target as CodeSource

        }

        throw IllegalStateException("Cannot find source, parents: '$parents'")
    }

    fun isType(parents: Parent<ValueGenerator<*, String, PlainSourceGenerator>>): Boolean {
        val parent = parents.parent

        return parent != null && TypeDeclarationSourceGenerator::class.java.isAssignableFrom(parent.current::class.java)
    }

}
