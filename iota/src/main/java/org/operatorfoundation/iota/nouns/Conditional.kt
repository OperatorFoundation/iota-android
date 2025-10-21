// Conditional.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.ion.storage.NounType

object Conditional {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Storage>): Storage {
        return MixedArray.make(i, NounType.CONDITIONAL.value)
    }
}
