// Adverbs.kt

package ion.adverbs

import ion.storage.Storage
import ion.storage.Word
import ion.nouns.NounType
import ion.symbols.MonadicAdverbs
import ion.symbols.DyadicAdverbs

object MonadicAdverb {
    fun make(i: Int): Storage {
        return Word.make(i, NounType.MONADIC_ADVERB)
    }
}

object DyadicAdverb {
    fun make(i: Int): Storage {
        return Word.make(i, NounType.DYADIC_ADVERB)
    }
}

// Monadic Adverbs - can be imported directly
val converge = MonadicAdverb.make(MonadicAdverbs.CONVERGE)
val each = MonadicAdverb.make(MonadicAdverbs.EACH)
val eachPair = MonadicAdverb.make(MonadicAdverbs.EACH_PAIR)
val over = MonadicAdverb.make(MonadicAdverbs.OVER)
val scanConverging = MonadicAdverb.make(MonadicAdverbs.SCAN_CONVERGING)
val scanOver = MonadicAdverb.make(MonadicAdverbs.SCAN_OVER)

// Dyadic Adverbs - can be imported directly
val each2 = DyadicAdverb.make(DyadicAdverbs.EACH2)
val eachLeft = DyadicAdverb.make(DyadicAdverbs.EACH_LEFT)
val eachRight = DyadicAdverb.make(DyadicAdverbs.EACH_RIGHT)
val overNeutral = DyadicAdverb.make(DyadicAdverbs.OVER_NEUTRAL)
val iterate = DyadicAdverb.make(DyadicAdverbs.ITERATE)
val scanIterating = DyadicAdverb.make(DyadicAdverbs.SCAN_ITERATING)
val scanOverNeutral = DyadicAdverb.make(DyadicAdverbs.SCAN_OVER_NEUTRAL)
val scanWhileOne = DyadicAdverb.make(DyadicAdverbs.SCAN_WHILE_ONE)
val whileOne = DyadicAdverb.make(DyadicAdverbs.WHILE_ONE)
