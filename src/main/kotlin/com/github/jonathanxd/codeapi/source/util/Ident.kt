package com.github.jonathanxd.codeapi.source.util

/**
 * Internal class undocumented.
 */
class Ident(private val indentationSize: Int) {
    private var ident = 0

    fun addIdent() {
        this.addIdent(1)
    }

    fun addIdent(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Negative value!")
        }
        this.ident += indentationSize * amount
    }

    fun removeIdent() {
        this.removeIdent(1)
    }

    fun removeIdent(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Negative value!")
        }
        this.ident -= indentationSize * amount
    }

    val identString: String
        get() {
            val sb = StringBuilder()

            for (i in 0..this.ident - 1) {
                sb.append(' ')
            }

            return sb.toString()
        }
}