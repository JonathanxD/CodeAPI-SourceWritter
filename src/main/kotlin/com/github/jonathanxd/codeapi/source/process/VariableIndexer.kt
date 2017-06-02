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
package com.github.jonathanxd.codeapi.source.process

class VariableIndexer {

    private val mainFrame: Frame = Frame(null)
    var currentFrame: Frame = mainFrame

    fun enterFrame() {
        this.currentFrame = Frame(currentFrame)
    }

    fun addVariable(name: String) {
        this.currentFrame.variables += name
    }

    fun containsVariable(name: String): Boolean {
        var frame = currentFrame

        while(true) {

            if(frame.variables.contains(name))
                return true

            if(frame.parent == null)
                return false
            else
                frame = frame.parent!!
        }

    }

    fun createUniqueName(base: String): String {
        if(!this.containsVariable(base))
            return base

        var x = 0

        while(true) {
            x+=1
            val name = "$base$x"

            if(!this.containsVariable(name))
                return name
        }
    }

    fun exitFrame() {
        val parent = currentFrame.parent ?: throw IllegalStateException("Attempted to exit main frame!")
        currentFrame = parent
    }

}

inline fun VariableIndexer.tempFrame(block: () -> Unit) {
    this.enterFrame()
    block()
    this.exitFrame()
}

data class Frame(val parent: Frame?, val variables: MutableList<String> = mutableListOf())