// IotaString.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.WordArray
import ion.storage.StorageType
import ion.Connection

object IotaString {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Int>): Storage {
        return WordArray.make(i, NounType.STRING)
    }

    fun makeEmpty(): Storage {
        return WordArray.make(emptyList(), NounType.STRING)
    }

    // Serialization - from bytes
    fun fromBytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY -> WordArray.fromBytes(bs, NounType.STRING)
            else -> null
        }
    }

    // Serialization - to bytes
    fun toBytes(i: Storage): ByteArray? {
        if (i.o != NounType.STRING) {
            return null
        }

        return when (i.t) {
            StorageType.WORD_ARRAY -> WordArray.toBytes(i)
            else -> null
        }
    }

    // Serialization - from connection
    fun fromConn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY -> WordArray.fromConn(conn, NounType.STRING)
            else -> null
        }
    }

    // Serialization - to connection
    fun toConn(conn: Connection, i: Storage) {
        if (i.o != NounType.STRING) {
            return
        }

        when (i.t) {
            StorageType.WORD_ARRAY -> {
                // No need to include type here because it is provided by WordArray::toConn
                WordArray.toConn(conn, i)
            }
            else -> return
        }
    }
}
