// Noun.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.*
import org.operatorfoundation.ion.storage.FloatArray
import org.operatorfoundation.transmission.Connection

object Noun {
    fun mix(i: Storage): Storage {
        when(val ii = i.i) {
            is I.WordArray -> {
                val results: List<Storage> = ii.value.map {
                    Word.make(it, NounType.INTEGER.value)
                }
                return MixedArray.make(results,NounType.LIST.value)
            }
            is I.FloatArray -> {
                val results: List<Storage> = ii.value.map {
                    Real.make(it)
                }
                return MixedArray.make(results,NounType.LIST.value)
            }
            is I.MixedArray -> {
                return i
            }
            else -> return i
        }
    }

    fun initialize() {

    }

    // Serialization - from bytes
    // Decodes a byte array into a Storage object by delegating to each Storage subclass's decoder
    fun from_bytes(x: ByteArray): Storage? {
        if (x.size < 2) return null
        
        val t = x[0].toInt() and 0xFF
        val o = x[1].toInt() and 0xFF
        val untypedData = x.sliceArray(2 until x.size)

        return when (o) {
            NounType.INTEGER.value -> Integer.from_bytes(untypedData, t)
            NounType.REAL.value -> Real.from_bytes(untypedData, t)
            NounType.LIST.value -> IotaList.from_bytes(untypedData, t)
            NounType.CHARACTER.value -> Character.from_bytes(untypedData, t)
            NounType.STRING.value -> IotaString.from_bytes(untypedData, t)
            
            else -> when (t) {
                StorageType.WORD.value -> Word.from_bytes(untypedData, o)
                StorageType.FLOAT.value -> IonFloat.from_bytes(untypedData, o)
                StorageType.WORD_ARRAY.value -> WordArray.from_bytes(untypedData, o)
                StorageType.FLOAT_ARRAY.value -> FloatArray.from_bytes(untypedData, o)
                StorageType.MIXED_ARRAY.value -> MixedArray.from_bytes(untypedData, o)
                else -> null
            }
        }
    }

    // Serialization - to bytes
    // Encodes a Storage into a byte array by delegating to each subclass
    // Format: byte:t byte:o [byte]:subclass.to_bytes(i)
    fun to_bytes(x: Storage): ByteArray {
        // Noun::to_bytes includes type, never include type in any other to_bytes
        val typeBytes = byteArrayOf(x.t.toByte(), x.o.toByte())

        val valueBytes = when (x.o) {
            NounType.INTEGER.value -> Integer.to_bytes(x) ?: return byteArrayOf()
            NounType.REAL.value -> Real.to_bytes(x) ?: return byteArrayOf()
            NounType.LIST.value -> IotaList.to_bytes(x) ?: return byteArrayOf()
            NounType.CHARACTER.value -> Character.to_bytes(x) ?: return byteArrayOf()
            NounType.STRING.value -> IotaString.to_bytes(x) ?: return byteArrayOf()
            else -> return byteArrayOf()
        }

        return typeBytes + valueBytes
    }

    // Serialization - from connection
    fun from_conn(conn: Connection): Storage?
    {
        val storageBytes = conn.read(1)
        if(storageBytes == null)
        {
            return null
        }

        val objectBytes = conn.read(1)
        if(objectBytes == null)
        {
            return null
        }

        val storageType = storageBytes[0].toInt() and 0xFF
        val objectType = objectBytes[0].toInt() and 0xFF

        return when (objectType) {
            NounType.INTEGER.value -> Integer.from_conn(conn, storageType)
            NounType.REAL.value -> Real.from_conn(conn, storageType)
            NounType.LIST.value -> IotaList.from_conn(conn, storageType)
            NounType.CHARACTER.value -> Character.from_conn(conn, storageType)
            NounType.STRING.value -> IotaString.from_conn(conn, storageType)
            
            else -> when (storageType) {
                StorageType.WORD.value -> Word.from_conn(conn, objectType)
                StorageType.FLOAT.value -> IonFloat.from_conn(conn, objectType)
                StorageType.WORD_ARRAY.value -> WordArray.from_conn(conn, objectType)
                StorageType.FLOAT_ARRAY.value -> FloatArray.from_conn(conn, objectType)
                StorageType.MIXED_ARRAY.value -> MixedArray.from_conn(conn, objectType)
                else -> null
            }
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, x: Storage) {
        // Storage.to_conn does not include type information, always include it in the specific to_conn implementation
        when (x.o) {
            NounType.INTEGER.value -> Integer.to_conn(conn, x)
            NounType.REAL.value -> Real.to_conn(conn, x)
            NounType.LIST.value -> IotaList.to_conn(conn, x)
            NounType.CHARACTER.value -> Character.to_conn(conn, x)
            NounType.STRING.value -> IotaString.to_conn(conn, x)
            
            else -> when (x.t) {
                StorageType.WORD.value -> Word.to_conn(conn, x)
                StorageType.FLOAT.value -> IonFloat.to_conn(conn, x)
                StorageType.WORD_ARRAY.value -> WordArray.to_conn(conn, x)
                StorageType.FLOAT_ARRAY.value -> FloatArray.to_conn(conn, x)
                StorageType.MIXED_ARRAY.value -> MixedArray.to_conn(conn, x)
                else -> Word.to_conn(conn, Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR.value))
            }
        }
    }
}
