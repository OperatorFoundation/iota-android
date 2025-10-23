// Character.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.Word
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.transmission.Connection
import org.operatorfoundation.ion.squeeze_bigint
import org.operatorfoundation.ion.storage.I

object Character {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    fun make(i: Char): Storage {
        return make(i.code)
    }

    fun make(i: Int): Storage {
        return Word.make(i, NounType.CHARACTER.value)
    }

    fun toInt(i: Storage): Int {
        when(i.o) {
            NounType.CHARACTER.value -> {
                when(val ii = i.i) {
                    is I.Word -> return ii.value
                    else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to Char")
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${i.t} to Char")
        }
    }

    fun toChar(i: Storage): Char {
        when(i.o) {
            NounType.CHARACTER.value -> {
                when(val ii = i.i) {
                    is I.Word -> return ii.value.toChar()
                    else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to Char")
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${i.t} to Char")
        }
    }

    // Serialization - from bytes
    fun from_bytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD.value -> Word.from_bytes(bs, NounType.CHARACTER.value)
            else -> null
        }
    }

    // Serialization - to bytes
    fun to_bytes(i: Storage): ByteArray? {
        if (i.o != NounType.CHARACTER.value) {
            return null
        }

        when (i.t) {
            StorageType.WORD.value -> return Word.to_bytes(i)
            StorageType.WORD_ARRAY.value -> {
                when(val ii = i.i) {
                    is I.WordArray -> return squeeze_bigint(ii.value)
                    else -> return null
                }
            }
            else -> return null
        }
    }

    // Serialization - from connection
    fun from_conn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.WORD.value -> {
                Word.from_conn(conn, NounType.CHARACTER.value)
                // FIXME - add support for StorageType.WORD_ARRAY to represent grapheme clusters
            }
            else -> null
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, i: Storage) {
        if (i.o != NounType.CHARACTER.value) {
            return
        }

        when (i.t) {
            StorageType.WORD.value -> {
                when(i.i)
                {
                    is I.Word -> {
                        // No need to include type here because it is provided by Word::to_conn
                        Word.to_conn(conn, i)
                    }
                    else -> return
                }
            }
            
            // FIXME - WordArray support for grapheme clusters

            else -> return
        }
    }
}