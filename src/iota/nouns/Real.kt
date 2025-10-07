// Real.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.Float
import ion.storage.StorageType
import ion.Connection

object Real {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: Float): Storage {
        return Float.make(i, NounType.REAL)
    }

    fun zero(): Storage {
        return make(0.0f)
    }

    fun one(): Storage {
        return make(1.0f)
    }

    // Serialization - from bytes
    fun fromBytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.FLOAT -> Float.fromBytes(bs, NounType.REAL)
            else -> null
        }
    }

    // Serialization - to bytes
    fun toBytes(i: Storage): ByteArray? {
        if (i.o != NounType.REAL) {
            return null
        }

        return when (i.t) {
            StorageType.FLOAT -> Float.toBytes(i)
            else -> null
        }
    }

    // Serialization - from connection
    fun fromConn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.FLOAT -> Float.fromConn(conn, NounType.REAL)
            else -> null
        }
    }

    // Serialization - to connection
    fun toConn(conn: Connection, i: Storage) {
        if (i.o != NounType.REAL) {
            return
        }

        when (i.t) {
            StorageType.FLOAT -> {
                // No need to include type here because it is provided by Float::toConn
                Float.toConn(conn, i)
            }
            else -> return
        }
    }
}
