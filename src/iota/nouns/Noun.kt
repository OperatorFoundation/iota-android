// Noun.kt

package ion.nouns

import ion.storage.*
import ion.Connection

object Noun {
    // Serialization - from bytes
    // Decodes a byte array into a Storage object by delegating to each Storage subclass's decoder
    fun fromBytes(x: ByteArray): Storage? {
        if (x.size < 2) return null
        
        val t = x[0].toInt() and 0xFF
        val o = x[1].toInt() and 0xFF
        val untypedData = x.sliceArray(2 until x.size)

        return when (o) {
            NounType.INTEGER -> Integer.fromBytes(untypedData, t)
            NounType.REAL -> Real.fromBytes(untypedData, t)
            NounType.LIST -> List.fromBytes(untypedData, t)
            NounType.CHARACTER -> Character.fromBytes(untypedData, t)
            NounType.STRING -> IotaString.fromBytes(untypedData, t)
            
            else -> when (t) {
                StorageType.WORD -> Word.fromBytes(untypedData, o)
                StorageType.FLOAT -> Float.fromBytes(untypedData, o)
                StorageType.WORD_ARRAY -> WordArray.fromBytes(untypedData, o)
                StorageType.FLOAT_ARRAY -> FloatArray.fromBytes(untypedData, o)
                StorageType.MIXED_ARRAY -> MixedArray.fromBytes(untypedData, o)
                else -> null
            }
        }
    }

    // Serialization - to bytes
    // Encodes a Storage into a byte array by delegating to each subclass
    // Format: byte:t byte:o [byte]:subclass.toBytes(i)
    fun toBytes(x: Storage): ByteArray {
        // Noun::toBytes includes type, never include type in any other toBytes
        val typeBytes = byteArrayOf(x.t.toByte(), x.o.toByte())

        val valueBytes = when (x.o) {
            NounType.INTEGER -> Integer.toBytes(x) ?: return byteArrayOf()
            NounType.REAL -> Real.toBytes(x) ?: return byteArrayOf()
            NounType.LIST -> List.toBytes(x) ?: return byteArrayOf()
            NounType.CHARACTER -> Character.toBytes(x) ?: return byteArrayOf()
            NounType.STRING -> IotaString.toBytes(x) ?: return byteArrayOf()
            else -> return byteArrayOf()
        }

        return typeBytes + valueBytes
    }

    // Serialization - from connection
    fun fromConn(conn: Connection): Storage? {
        val storageType = conn.readOne().toInt() and 0xFF
        val objectType = conn.readOne().toInt() and 0xFF

        return when (objectType) {
            NounType.INTEGER -> Integer.fromConn(conn, storageType)
            NounType.REAL -> Real.fromConn(conn, storageType)
            NounType.LIST -> List.fromConn(conn, storageType)
            NounType.CHARACTER -> Character.fromConn(conn, storageType)
            NounType.STRING -> IotaString.fromConn(conn, storageType)
            
            else -> when (storageType) {
                StorageType.WORD -> Word.fromConn(conn, objectType)
                StorageType.FLOAT -> Float.fromConn(conn, objectType)
                StorageType.WORD_ARRAY -> WordArray.fromConn(conn, objectType)
                StorageType.FLOAT_ARRAY -> FloatArray.fromConn(conn, objectType)
                StorageType.MIXED_ARRAY -> MixedArray.fromConn(conn, objectType)
                else -> null
            }
        }
    }

    // Serialization - to connection
    fun toConn(conn: Connection, x: Storage) {
        // Storage.toConn does not include type information, always include it in the specific toConn implementation
        when (x.o) {
            NounType.INTEGER -> Integer.toConn(conn, x)
            NounType.REAL -> Real.toConn(conn, x)
            NounType.LIST -> List.toConn(conn, x)
            NounType.CHARACTER -> Character.toConn(conn, x)
            NounType.STRING -> IotaString.toConn(conn, x)
            
            else -> when (x.t) {
                StorageType.WORD -> Word.toConn(conn, x)
                StorageType.FLOAT -> Float.toConn(conn, x)
                StorageType.WORD_ARRAY -> WordArray.toConn(conn, x)
                StorageType.FLOAT_ARRAY -> FloatArray.toConn(conn, x)
                StorageType.MIXED_ARRAY -> MixedArray.toConn(conn, x)
                else -> Word.toConn(conn, Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR))
            }
        }
    }
}
