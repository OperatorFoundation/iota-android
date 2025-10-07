// Symbol.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.Word
import ion.storage.StorageType

object Symbol {
    
    val integerToString = mutableMapOf<Int, List<Int>>()
    val stringToInteger = mutableMapOf<List<Int>, Int>()
    val values = mutableMapOf<Int, Storage>()

    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
        
        // Symbol tables
        integerToString[SymbolType.X] = asciiToUTF32("x")
        stringToInteger[asciiToUTF32("x")] = SymbolType.X

        integerToString[SymbolType.Y] = asciiToUTF32("y")
        stringToInteger[asciiToUTF32("y")] = SymbolType.Y

        integerToString[SymbolType.Z] = asciiToUTF32("z")
        stringToInteger[asciiToUTF32("z")] = SymbolType.Z

        integerToString[SymbolType.F] = asciiToUTF32("f")
        stringToInteger[asciiToUTF32("f")] = SymbolType.F

        integerToString[SymbolType.UNDEFINED] = asciiToUTF32("undefined")
        stringToInteger[asciiToUTF32("undefined")] = SymbolType.UNDEFINED
    }

    fun make(i: Int): Storage {
        return Word.make(i, NounType.USER_SYMBOL)
    }

    fun asciiToUTF32(ascii: String): List<Int> {
        return ascii.map { it.code }
    }
}

object SymbolType {
    const val X = 0
    const val Y = 1
    const val Z = 2
    const val F = 3
    const val I = 4
    const val CAUSING = 5
    const val UNDEFINED = 6
}
