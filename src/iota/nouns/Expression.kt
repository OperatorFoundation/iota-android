// Expression.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.MixedArray

object Expression {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(e: List<Storage>): Storage {
        return MixedArray.make(e, NounType.EXPRESSION)
    }
}
