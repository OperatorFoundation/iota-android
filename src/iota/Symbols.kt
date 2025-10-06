// Symbols.kt

package ion.symbols

object Monads {
    const val ATOM = 0
    const val ICHAR = 1
    const val ENCLOSE = 3
    const val ENUMERATE = 4
    const val EXPAND = 27
    const val FIRST = 5
    const val FLOOR = 6
    const val FORMAT = 7
    const val GRADE_DOWN = 8
    const val GRADE_UP = 9
    const val GROUP = 10
    const val NEGATE = 11
    const val INOT = 2
    const val RECIPROCAL = 12
    const val REVERSE = 13
    const val SHAPE = 14
    const val SIZE = 15
    const val TRANSPOSE = 16
    const val UNIQUE = 17
    const val COUNT = 18
    const val UNDEFINED = 67

    const val EVALUATE = 19
    const val ERASE = 20
    const val TRUTH = 21
    const val LIFT = 68
    const val OCCURS = 75
}

object Dyads {
    const val AMEND = 22
    const val CUT = 23
    const val DIVIDE = 24
    const val DROP = 25
    const val EQUAL = 26
    const val FIND = 28
    const val FORM = 29
    const val FORMAT2 = 30
    const val INDEX = 31
    const val INDEX_IN_DEPTH = 32
    const val INTEGER_DIVIDE = 33
    const val JOIN = 34
    const val LESS = 35
    const val MATCH = 36
    const val MAX = 37
    const val MIN = 38
    const val MINUS = 39
    const val MORE = 40
    const val PLUS = 41
    const val POWER = 42
    const val REMAINDER = 43
    const val RESHAPE = 44
    const val ROTATE = 45
    const val SPLIT = 46
    const val TAKE = 47
    const val TIMES = 48

    const val APPLY_MONAD = 49
    const val RETYPE = 50
    const val CAUSES = 69
    const val BECAUSE = 72
    const val THEN = 70
    const val BIND = 71
    const val RETRIEVE = 73
    const val MUTATE = 74
}

object Triads {
    const val APPLY_DYAD = 51
}

object MonadicAdverbs {
    const val CONVERGE = 52
    const val EACH = 53
    const val EACH_PAIR = 54
    const val OVER = 55
    const val SCAN_CONVERGING = 56
    const val SCAN_OVER = 57
}

object DyadicAdverbs {
    const val EACH2 = 58
    const val EACH_LEFT = 59
    const val EACH_RIGHT = 60
    const val OVER_NEUTRAL = 61
    const val WHILE_ONE = 62
    const val ITERATE = 63
    const val SCAN_OVER_NEUTRAL = 64
    const val SCAN_WHILE_ONE = 65
    const val SCAN_ITERATING = 66
}
