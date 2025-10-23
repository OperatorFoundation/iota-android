// Real.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.transmission.Connection
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.ion.storage.IonFloat

object Real {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: Float): Storage {
        return IonFloat.make(i, NounType.REAL.value)
    }

    fun zero(): Storage {
        return make(0.0f)
    }

    fun one(): Storage {
        return make(1.0f)
    }

    // Serialization - from bytes
    fun from_bytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.FLOAT.value -> IonFloat.from_bytes(bs, NounType.REAL.value)
            else -> null
        }
    }

    // Serialization - to bytes
    fun to_bytes(i: Storage): ByteArray? {
        if (i.o != NounType.REAL.value) {
            return null
        }

        return when (i.t) {
            StorageType.FLOAT.value -> IonFloat.to_bytes(i)
            else -> null
        }
    }

    // Serialization - from connection
    fun from_conn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.FLOAT.value -> IonFloat.from_conn(conn, NounType.REAL.value)
            else -> null
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, i: Storage) {
        if (i.o != NounType.REAL.value) {
            return
        }

        when (i.t) {
            StorageType.FLOAT.value -> {
                // No need to include type here because it is provided by Float::to_conn
                IonFloat.to_conn(conn, i)
            }
            else -> return
        }
    }
}
