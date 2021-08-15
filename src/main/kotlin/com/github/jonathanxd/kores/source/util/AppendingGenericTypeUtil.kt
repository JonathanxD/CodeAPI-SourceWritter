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
package com.github.jonathanxd.kores.source.util

import com.github.jonathanxd.kores.generic.GenericSignature
import com.github.jonathanxd.kores.type.GenericType
import com.github.jonathanxd.kores.type.concreteType

/**
 * Convert generic signature to string.
 *
 * @param genericSignature Generic signature.
 * @return Generic signature string.
 */
fun toSourceString(
    genericSignature: GenericSignature,
    appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
) {

    val types = genericSignature.types

    for (i in types.indices) {
        val hasNext = i + 1 < types.size

        val genericType = types[i]

        toSourceString(genericType, appender)

        if (hasNext)
            appender += ","
    }
}

/**
 * Convert generic type to string.
 *
 * @param genericType Generic type.
 * @return Generic type string.
 */
fun toSourceString(
    genericType: GenericType,
    appender: com.github.jonathanxd.kores.source.process.JavaSourceAppender
) {

    if (genericType.isType) {
        appender.appendImport(genericType.concreteType)
        com.github.jonathanxd.kores.source.process.KoresTypeHelper.appendName(
            genericType.concreteType,
            appender
        )
    } else {
        if (!genericType.isWildcard) {
            appender += genericType.name
        } else {
            appender += "?"
        }
    }


    val bounds = genericType.bounds

    if (bounds.isNotEmpty()) {

        for (i in bounds.indices) {

            val hasNext = i + 1 < bounds.size

            val bound = bounds[i]

            val extendsOrSuper = bound.sign == "+" || bound.sign == "-"

            if (bound.sign == "+") {
                appender += " extends "
            } else if (bound.sign == "-") {
                appender += " super "
            } else {
                if (i == 0) {
                    appender += "<"
                }
            }

            val type = bound.type

            if (type is GenericType) {
                toSourceString(type, appender)
            } else {
                appender.appendImport(type.concreteType)
                com.github.jonathanxd.kores.source.process.KoresTypeHelper.appendName(
                    type.concreteType,
                    appender
                )
            }

            if (!extendsOrSuper && !hasNext) {
                appender += ">"
            }

            if (hasNext && extendsOrSuper) {
                appender += " & "
            } else if (hasNext) {
                appender += ", "
            }
        }

    }
}
