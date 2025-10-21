// Conjunctions.kt

package org.operatorfoundation.iota

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.Word
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.iota.nouns.Noun

object Conjunction {
    fun make(i: Int): Storage {
        return Word.make(i, NounType.CONJUNCTION.value)
    }
}

object Conjunctions {
    const val THEN = 0

    fun initialize() {
        // FIXME - implement Noun.registerConjunction
        // Noun.registerConjunction(THEN, ::thenImpl)
    }

    // then ignores i and returns x
    // This is also known as KI, Kite, false, zero, or second (snd) in functional combinator languages.
    fun thenImpl(i: Storage, x: Storage): Storage {
        return x
    }
}

// Conjunctions - can be imported directly
val then = Conjunction.make(Conjunctions.THEN)
