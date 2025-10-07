// QuotedSymbol.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.WordArray

object QuotedSymbol {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Int>): Storage {
        return WordArray.make(i, NounType.QUOTED_SYMBOL)
    }

    fun undefined(): Storage {
        val name = Symbol.integerToString[SymbolType.UNDEFINED]
            ?: throw IllegalStateException("undefined symbol not found")
        return make(name)
    }
}
