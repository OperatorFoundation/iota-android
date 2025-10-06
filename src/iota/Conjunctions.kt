// Conjunctions.kt

package ion.conjunctions

import ion.storage.Storage
import ion.storage.Word
import ion.nouns.NounType
import ion.nouns.Noun

object Conjunction {
    fun make(i: Int): Storage {
        return Word.make(i, NounType.CONJUNCTION)
    }
}

object Conjunctions {
    const val THEN = 0

    fun initialize() {
        Noun.registerConjunction(THEN, ::thenImpl)
    }

    // then ignores i and returns x
    // This is also known as KI, Kite, false, zero, or second (snd) in functional combinator languages.
    fun thenImpl(i: Storage, x: Storage): Storage {
        return x
    }
}

// Conjunctions - can be imported directly
val then = Conjunction.make(Conjunctions.THEN)
