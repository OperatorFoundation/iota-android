// IotaString.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.ion.Connection

object IotaString {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Int>): Storage {
        return WordArray.make(i, NounType.STRING.value)
    }

    fun makeEmpty(): Storage {
        return WordArray.make(emptyList(), NounType.STRING.value)
    }

    // Serialization - from bytes
    fun from_bytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY.value -> WordArray.from_bytes(bs, NounType.STRING.value)
            else -> null
        }
    }

    // Serialization - to bytes
    fun to_bytes(i: Storage): ByteArray? {
        if (i.o != NounType.STRING.value) {
            return null
        }

        return when (i.t) {
            StorageType.WORD_ARRAY.value -> WordArray.to_bytes(i)
            else -> null
        }
    }

    // Serialization - from connection
    fun from_conn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY.value -> WordArray.from_conn(conn, NounType.STRING.value)
            else -> null
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, i: Storage) {
        if (i.o != NounType.STRING.value) {
            return
        }

        when (i.t) {
            StorageType.WORD_ARRAY.value -> {
                // No need to include type here because it is provided by WordArray::to_conn
                WordArray.to_conn(conn, i)
            }
            else -> return
        }
    }
}
