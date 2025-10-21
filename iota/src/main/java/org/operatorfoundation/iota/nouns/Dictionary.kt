// Dictionary.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.ion.Connection

object Dictionary {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Storage>): Storage {
        return MixedArray.make(i, NounType.DICTIONARY.value)
    }

    fun makeEmpty(): Storage {
        return MixedArray.make(emptyList(), NounType.DICTIONARY.value)
    }

    // Serialization - from bytes
    fun from_bytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.MIXED_ARRAY.value -> MixedArray.from_bytes(bs, NounType.DICTIONARY.value)
            else -> null
        }
    }

    // Serialization - to bytes
    fun to_bytes(i: Storage): ByteArray? {
        // Don't include type, that is handled by Noun::to_bytes

        if (i.o != NounType.LIST.value) {
            return null
        }

        return when (i.t) {
            StorageType.MIXED_ARRAY.value -> MixedArray.to_bytes(i)
            else -> null
        }
    }

    // Serialization - from connection
    fun from_conn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.MIXED_ARRAY.value -> MixedArray.from_conn(conn, NounType.DICTIONARY.value)
            else -> null
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, i: Storage) {
        if (i.o != NounType.LIST.value) {
            return
        }

        when (i.t) {
            StorageType.MIXED_ARRAY.value -> {
                // No need to include type here, because it is provided by MixedArray::to_conn
                MixedArray.to_conn(conn, i)
            }
            else -> return
        }
    }
}
