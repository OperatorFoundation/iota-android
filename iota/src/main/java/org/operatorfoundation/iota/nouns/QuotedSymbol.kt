// QuotedSymbol.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.NounType

object QuotedSymbol {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Int>): Storage {
        return WordArray.make(i, NounType.QUOTED_SYMBOL.value)
    }

    fun undefined(): Storage {
        val name = Symbol.integerToString[SymbolType.UNDEFINED]
            ?: throw IllegalStateException("undefined symbol not found")
        return make(name)
    }
}
