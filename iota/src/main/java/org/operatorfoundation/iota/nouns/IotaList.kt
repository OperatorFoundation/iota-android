// List.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.FloatArray
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.ion.Connection
import org.operatorfoundation.ion.squeeze_ints
import org.operatorfoundation.ion.squeeze_floats
import org.operatorfoundation.ion.storage.I

object IotaList {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    // Serialization - from bytes
    fun from_bytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY.value -> WordArray.from_bytes(bs, NounType.LIST.value)
            StorageType.FLOAT_ARRAY.value -> FloatArray.from_bytes(bs, NounType.LIST.value)
            StorageType.MIXED_ARRAY.value -> MixedArray.from_bytes(bs, NounType.LIST.value)
            else -> null
        }
    }

    // Serialization - to bytes
    fun to_bytes(i: Storage): ByteArray? {
        // Don't include type, that is handled by Noun::to_bytes
        if (i.o != NounType.LIST.value) {
            return null
        }

        when (i.t) {
            StorageType.WORD_ARRAY.value -> {
                when(val ii = i.i) {
                    is I.WordArray -> return squeeze_ints(ii.value)
                    else -> return null
                }
            }
            
            StorageType.FLOAT_ARRAY.value -> {
                when(val ii = i.i) {
                    is I.FloatArray -> return squeeze_floats(ii.value)
                    else -> return null
                }
            }
            
            StorageType.MIXED_ARRAY.value -> {
                return MixedArray.to_bytes(i)
            }
            
            else -> return null
        }
    }

    // Serialization - from connection
    fun from_conn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY.value -> WordArray.from_conn(conn, NounType.LIST.value)
            StorageType.FLOAT_ARRAY.value -> FloatArray.from_conn(conn, NounType.LIST.value)
            StorageType.MIXED_ARRAY.value -> MixedArray.from_conn(conn, NounType.LIST.value)
            else -> null
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, i: Storage) {
        if (i.o != NounType.LIST.value) {
            return
        }

        when (i.t) {
            StorageType.WORD_ARRAY.value -> {
                when (val ii = i.i) {
                    is I.WordArray -> {
                        // Always include type in to_conn implementation
                        val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
                        conn.write(typeBytes)

                        val bs = squeeze_ints(ii.value)
                        conn.write(bs)
                    }
                    else -> return
                }
            }
            
            StorageType.FLOAT_ARRAY.value -> {
                when (val ii = i.i) {
                    is I.FloatArray -> {
                        // Always include type in to_conn implementation
                        val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
                        conn.write(typeBytes)

                        val bs = squeeze_floats(ii.value)
                        conn.write(bs)
                    }
                    else -> return
                }
            }
            
            StorageType.MIXED_ARRAY.value -> {
                // No need to include type here, because it is provided by MixedArray::to_conn
                MixedArray.to_conn(conn, i)
            }
            
            else -> return
        }
    }
}