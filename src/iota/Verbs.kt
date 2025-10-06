// Verbs.kt

package ion.verbs

import ion.storage.Storage
import ion.storage.Word
import ion.nouns.NounType
import ion.symbols.Monads
import ion.symbols.Dyads

object Monad {
    fun make(i: Int): Storage {
        return Word.make(i, NounType.BUILTIN_MONAD)
    }
}

object Dyad {
    fun make(i: Int): Storage {
        return Word.make(i, NounType.BUILTIN_DYAD)
    }
}

// Monads - can be imported directly
val atom = Monad.make(Monads.ATOM)
val ichar = Monad.make(Monads.ICHAR)
val enclose = Monad.make(Monads.ENCLOSE)
val enumerate = Monad.make(Monads.ENUMERATE)
val expand = Monad.make(Monads.EXPAND)
val first = Monad.make(Monads.FIRST)
val floor = Monad.make(Monads.FLOOR)
val format = Monad.make(Monads.FORMAT)
val gradeDown = Monad.make(Monads.GRADE_DOWN)
val gradeUp = Monad.make(Monads.GRADE_UP)
val group = Monad.make(Monads.GROUP)
val negate = Monad.make(Monads.NEGATE)
val inot = Monad.make(Monads.INOT)
val reciprocal = Monad.make(Monads.RECIPROCAL)
val reverse = Monad.make(Monads.REVERSE)
val shape = Monad.make(Monads.SHAPE)
val size = Monad.make(Monads.SIZE)
val transpose = Monad.make(Monads.TRANSPOSE)
val unique = Monad.make(Monads.UNIQUE)
val undefined = Monad.make(Monads.UNDEFINED)

// Extension Monads
val evaluate = Monad.make(Monads.EVALUATE)
val erase = Monad.make(Monads.ERASE)
val truth = Monad.make(Monads.TRUTH)
val lift = Monad.make(Monads.LIFT)
val occurs = Monad.make(Monads.OCCURS)

// Dyads
val amend = Dyad.make(Dyads.AMEND)
val cut = Dyad.make(Dyads.CUT)
val divide = Dyad.make(Dyads.DIVIDE)
val drop = Dyad.make(Dyads.DROP)
val equal = Dyad.make(Dyads.EQUAL)
val find = Dyad.make(Dyads.FIND)
val form = Dyad.make(Dyads.FORM)
val format2 = Dyad.make(Dyads.FORMAT2)
val index = Dyad.make(Dyads.INDEX)
val integerDivide = Dyad.make(Dyads.INTEGER_DIVIDE)
val join = Dyad.make(Dyads.JOIN)
val less = Dyad.make(Dyads.LESS)
val match = Dyad.make(Dyads.MATCH)
val max = Dyad.make(Dyads.MAX)
val min = Dyad.make(Dyads.MIN)
val minus = Dyad.make(Dyads.MINUS)
val more = Dyad.make(Dyads.MORE)
val plus = Dyad.make(Dyads.PLUS)
val power = Dyad.make(Dyads.POWER)
val remainder = Dyad.make(Dyads.REMAINDER)
val reshape = Dyad.make(Dyads.RESHAPE)
val rotate = Dyad.make(Dyads.ROTATE)
val split = Dyad.make(Dyads.SPLIT)
val take = Dyad.make(Dyads.TAKE)
val times = Dyad.make(Dyads.TIMES)
