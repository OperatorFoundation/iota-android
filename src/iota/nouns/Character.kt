// Character.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.Word
import ion.storage.WordArray
import ion.storage.StorageType
import ion.Connection
import ion.squeeze.squeezeBigint

object Character {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: Int): Storage {
        return Word.make(i, NounType.CHARACTER)
    }

    // Serialization - from bytes
    fun fromBytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD -> Word.fromBytes(bs, NounType.CHARACTER)
            else -> null
        }
    }

    // Serialization - to bytes
    fun toBytes(i: Storage): ByteArray? {
        if (i.o != NounType.CHARACTER) {
            return null
        }

        return when (i.t) {
            StorageType.WORD -> Word.toBytes(i)
            
            StorageType.WORD_ARRAY -> {
                i.i.asIntList()?.let { integers ->
                    squeezeBigint(integers)
                }
            }
            
            else -> null
        }
    }

    // Serialization - from connection
    fun fromConn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.WORD -> {
                Word.fromConn(conn, NounType.CHARACTER)
                // FIXME - add support for StorageType.WORD_ARRAY to represent grapheme clusters
            }
            else -> null
        }
    }

    // Serialization - to connection
    fun toConn(conn: Connection, i: Storage) {
        if (i.o != NounType.CHARACTER) {
            return
        }

        when (i.t) {
            StorageType.WORD -> {
                // No need to include type here because it is provided by Word::toConn
                Word.toConn(conn, i)
            }
            
            StorageType.WORD_ARRAY -> {
                i.i.asIntList()?.let { integers ->
                    val intBytes = squeezeBigint(integers)
                    
                    // Note that we always send NounType.CHARACTER and StorageType.WORD,
                    // even if we internally represent them as StorageType.WORD_ARRAYs.
                    val typeBytes = byteArrayOf(
                        StorageType.WORD.toByte(),
                        i.o.toByte()
                    )
                    conn.write(typeBytes)
                    conn.write(intBytes)
                }
            }
            
            else -> return
        }
    }
}

// Helper extension function (if not already defined elsewhere)
private fun Any.asIntList(): List<Int>? = this as? List<Int>
