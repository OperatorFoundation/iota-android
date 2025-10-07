// Lens.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.WordArray
import ion.storage.MixedArray

object Lens {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: Int): Storage {
        val results = Noun.mix(WordArray.make(listOf(i)))
        return results.copy(o = NounType.LENS)
    }

    fun make(i: List<Int>): Storage {
        val results = Noun.mix(WordArray.make(i))
        return results.copy(o = NounType.LENS)
    }

    fun make(i: List<Storage>): Storage {
        return MixedArray.make(i, NounType.LENS)
    }
}
