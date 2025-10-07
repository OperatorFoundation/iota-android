// Dictionary.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.MixedArray
import ion.storage.StorageType
import ion.Connection

object Dictionary {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: List<Storage>): Storage {
        return MixedArray.make(i, NounType.DICTIONARY)
    }

    fun makeEmpty(): Storage {
        return MixedArray.make(emptyList(), NounType.DICTIONARY)
    }

    // Serialization - from bytes
    fun fromBytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.MIXED_ARRAY -> MixedArray.fromBytes(bs, NounType.DICTIONARY)
            else -> null
        }
    }

    // Serialization - to bytes
    fun toBytes(i: Storage): ByteArray? {
        // Don't include type, that is handled by Noun::toBytes

        if (i.o != NounType.LIST) {
            return null
        }

        return when (i.t) {
            StorageType.MIXED_ARRAY -> MixedArray.toBytes(i)
            else -> null
        }
    }

    // Serialization - from connection
    fun fromConn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.MIXED_ARRAY -> MixedArray.fromConn(conn, NounType.DICTIONARY)
            else -> null
        }
    }

    // Serialization - to connection
    fun toConn(conn: Connection, i: Storage) {
        if (i.o != NounType.LIST) {
            return
        }

        when (i.t) {
            StorageType.MIXED_ARRAY -> {
                // No need to include type here, because it is provided by MixedArray::toConn
                MixedArray.toConn(conn, i)
            }
            else -> return
        }
    }
}
