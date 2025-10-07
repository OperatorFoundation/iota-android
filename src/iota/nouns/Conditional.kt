// Conditional.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.MixedArray

object Conditional {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Storage>): Storage {
        return MixedArray.make(i, NounType.CONDITIONAL)
    }
}
