// Expression.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.ion.storage.NounType

object Expression {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(e: List<Storage>): Storage {
        return MixedArray.make(e, NounType.EXPRESSION.value)
    }
}
