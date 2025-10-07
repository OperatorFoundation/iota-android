// List.kt

package ion.nouns

import ion.storage.Storage
import ion.storage.WordArray
import ion.storage.FloatArray
import ion.storage.MixedArray
import ion.storage.StorageType
import ion.Connection
import ion.squeeze.squeezeInts
import ion.squeeze.squeezeFloats

object List {
    
    fun initialize() {
        // Registration of dispatch table - not needed for Kotlin version
        // as evaluation happens on Arduino
    }

    // Serialization - from bytes
    fun fromBytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY -> WordArray.fromBytes(bs, NounType.LIST)
            StorageType.FLOAT_ARRAY -> FloatArray.fromBytes(bs, NounType.LIST)
            StorageType.MIXED_ARRAY -> MixedArray.fromBytes(bs, NounType.LIST)
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
            StorageType.WORD_ARRAY -> {
                i.i.asIntList()?.let { integers ->
                    squeezeInts(integers)
                }
            }
            
            StorageType.FLOAT_ARRAY -> {
                i.i.asFloatList()?.let { floats ->
                    squeezeFloats(floats)
                }
            }
            
            StorageType.MIXED_ARRAY -> {
                MixedArray.toBytes(i)
            }
            
            else -> null
        }
    }

    // Serialization - from connection
    fun fromConn(conn: Connection, t: Int): Storage? {
        return when (t) {
            StorageType.WORD_ARRAY -> WordArray.fromConn(conn, NounType.LIST)
            StorageType.FLOAT_ARRAY -> FloatArray.fromConn(conn, NounType.LIST)
            StorageType.MIXED_ARRAY -> MixedArray.fromConn(conn, NounType.LIST)
            else -> null
        }
    }

    // Serialization - to connection
    fun toConn(conn: Connection, i: Storage) {
        if (i.o != NounType.LIST) {
            return
        }

        when (i.t) {
            StorageType.WORD_ARRAY -> {
                i.i.asIntList()?.let { integers ->
                    // Always include type in toConn implementation
                    val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
                    conn.write(typeBytes)
                    
                    val bs = squeezeInts(integers)
                    conn.write(bs)
                }
            }
            
            StorageType.FLOAT_ARRAY -> {
                i.i.asFloatList()?.let { floats ->
                    // Always include type in toConn implementation
                    val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
                    conn.write(typeBytes)
                    
                    val bs = squeezeFloats(floats)
                    conn.write(bs)
                }
            }
            
            StorageType.MIXED_ARRAY -> {
                // No need to include type here, because it is provided by MixedArray::toConn
                MixedArray.toConn(conn, i)
            }
            
            else -> return
        }
    }
}

// Helper extension functions (if not already defined elsewhere)
private fun Any.asIntList(): List<Int>? = this as? List<Int>
private fun Any.asFloatList(): List<Float>? = this as? List<Float>
