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
package com.github.jonathanxd.codeapi.source.util

import com.github.jonathanxd.codeapi.gen.value.Value
import com.github.jonathanxd.codeapi.generic.GenericSignature
import com.github.jonathanxd.codeapi.type.CodeType
import com.github.jonathanxd.codeapi.type.GenericType

/**
 * Convert generic signature to string.
 *
 * @param genericSignature Generic signature.
 * @return Generic signature string.
 */
fun <U, O> toSourceString(genericSignature: GenericSignature, typeToValue: (CodeType) -> List<Value<*, U, O>>, stringToValue: (String) -> Value<*, U, O>): List<Value<*, U, O>> {
    val list = mutableListOf<Value<*, U, O>>()

    val types = genericSignature.types

    for (i in types.indices) {
        val hasNext = i + 1 < types.size

        val genericType = types[i]

        list += toSourceString(genericType, typeToValue, stringToValue)

        if (hasNext)
            list += stringToValue(",")
    }

    return list
}

/**
 * Convert generic type to string.
 *
 * @param genericType Generic type.
 * @return Generic type string.
 */
fun <U, O> toSourceString(genericType: GenericType, typeToValue: (CodeType) -> List<Value<*, U, O>>, stringToValue: (String) -> Value<*, U, O>): List<Value<*, U, O>> {

    val list = mutableListOf<Value<*, U, O>>()

    if (genericType.isType) {
        list += typeToValue(genericType)
    } else {
        if (!genericType.isWildcard) {
            list += stringToValue(genericType.name)
        } else {
            list += stringToValue("?")
        }
    }


    val bounds = genericType.bounds

    if (bounds.isNotEmpty()) {

        for (i in bounds.indices) {

            val hasNext = i + 1 < bounds.size

            val bound = bounds[i]

            val extendsOrSuper = bound.sign == "+" || bound.sign == "-"

            if (bound.sign == "+") {
                list += stringToValue(" extends ")
            } else if (bound.sign == "-") {
                list += stringToValue(" super ")
            } else {
                if (i == 0) {
                    list += stringToValue("<")
                }
            }

            val type = bound.type

            if (type is GenericType) {
                list += toSourceString(type, typeToValue, stringToValue)
            } else {
                list += typeToValue(type)
            }

            if (!extendsOrSuper && !hasNext) {
                list += stringToValue(">")
            }

            if (hasNext && extendsOrSuper) {
                list += stringToValue(" & ")
            } else if (hasNext) {
                list += stringToValue(", ")
            }
        }

    }

    return list
}
