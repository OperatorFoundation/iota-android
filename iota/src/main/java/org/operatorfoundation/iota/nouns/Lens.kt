// Lens.kt

package ion.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.iota.nouns.Noun

object Lens {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: Int): Storage {
        val results = Noun.mix(WordArray.make(listOf(i), NounType.LIST.value))
        return results.copy(o = NounType.LENS.value)
    }

    fun make(i: List<Int>): Storage {
        val results = Noun.mix(WordArray.make(i, NounType.LIST.value))
        return results.copy(o = NounType.LENS.value)
    }

    fun makeMixed(i: List<Storage>): Storage {
        return MixedArray.make(i, NounType.LENS.value)
    }
}
