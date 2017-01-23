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

import com.github.jonathanxd.codeapi.type.CodeType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import kotlin.text.Charsets;

/**
 * Tests history saver
 */
public class HistorySaver {

    private static final String PATH = "src/test/resources/";
    private static final boolean IS_GRADLE_ENVIRONMENT;

    static {
        String prop = System.getProperty("env");

        IS_GRADLE_ENVIRONMENT = prop != null && prop.equals("gradle");

        if (IS_GRADLE_ENVIRONMENT) {
            System.out.println("Gradle environment property defined!");
        }
    }


    public static void save(Class<?> ofClass, CodeType theClass, String output) {
        if (IS_GRADLE_ENVIRONMENT)
            return;

        File root = new File(PATH, "history/");

        if (!root.exists())
            root.mkdirs();

        File toSave = new File(root, ofClass.getSimpleName() + "_" + theClass.getSimpleName() + ".history");

        if (toSave.exists())
            toSave.delete();

        try {
            Files.write(toSave.toPath(), output.getBytes(Charsets.UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
