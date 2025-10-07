// Error.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.Word

// Error codes as constants
object ErrorCode {
    const val BAD_INDEX_TYPE = 0
    const val BAD_INITIALIZATION = 1
    const val BAD_STORAGE = 2
    const val BAD_OPERATION = 3
    const val EMPTY = 4
    const val INVALID_ARGUMENT = 5
    const val INVALID_ADVERB_ARGUMENT = 6
    const val OUT_OF_BOUNDS = 7
    const val SHAPE_MISMATCH = 8
    const val TEST_ERROR = 9
    const val UNSUPPORTED_OBJECT = 10
    const val UNSUPPORTED_SUBJECT = 11
    const val UNKNOWN_KEY = 12
    const val UNEQUAL_ARRAY_LENGTHS = 13
    const val DIVISION_BY_ZERO = 14
    const val MAXIMUM_ITERATIONS = 15
}

data class Error(val code: Int) {
    companion object {
        fun make(i: Int): Storage {
            return Word.make(i, NounType.ERROR)
        }
    }

    fun string(): String {
        return codeToString(code)
    }

    private fun codeToString(code: Int): String = when (code) {
        ErrorCode.BAD_INDEX_TYPE -> "unsupported index type"
        ErrorCode.BAD_INITIALIZATION -> "bad initialization value"
        ErrorCode.BAD_STORAGE -> "this object type does not support this storage type"
        ErrorCode.BAD_OPERATION -> "this operation is not supported by this object type with this storage type"
        ErrorCode.EMPTY -> "empty"
        ErrorCode.INVALID_ARGUMENT -> "invalid argument type"
        ErrorCode.INVALID_ADVERB_ARGUMENT -> "invalid adverb argument"
        ErrorCode.OUT_OF_BOUNDS -> "out of bounds"
        ErrorCode.SHAPE_MISMATCH -> "mismatched shapes"
        ErrorCode.TEST_ERROR -> "test error"
        ErrorCode.UNSUPPORTED_OBJECT -> "operation is not supported by this object type"
        ErrorCode.UNSUPPORTED_SUBJECT -> "unsupported subject type"
        ErrorCode.UNKNOWN_KEY -> "unknown key"
        ErrorCode.UNEQUAL_ARRAY_LENGTHS -> "unequal array lengths"
        ErrorCode.DIVISION_BY_ZERO -> "division by zero"
        ErrorCode.MAXIMUM_ITERATIONS -> "maximum iterations"
        else -> "unknown error"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Error) return false
        return code == other.code
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }
}
