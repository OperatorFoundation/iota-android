// List.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.FloatArray
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.transmission.Connection
import org.operatorfoundation.ion.squeeze_ints
import org.operatorfoundation.ion.squeeze_floats
import org.operatorfoundation.ion.storage.I
import java.math.BigInteger

object IotaList {
    fun makeBytes(i: List<Byte>): Storage {
        // Convert Bytes to Ints for WordArray storage
        val intList = i.map { it.toInt() }
        return WordArray.make(intList, NounType.LIST.value)
    }

    fun makeUBytes(i: List<UByte>): Storage {
        // Convert UBytes to Ints for WordArray storage
        val intList = i.map { it.toInt() }
        return WordArray.make(intList, NounType.LIST.value)
    }

    fun makeShorts(i: List<Short>): Storage {
        // Convert Shorts to Ints for WordArray storage
        val intList = i.map { it.toInt() }
        return WordArray.make(intList, NounType.LIST.value)
    }

    fun makeUShorts(i: List<UShort>): Storage {
        // Convert UShorts to Ints for WordArray storage
        val intList = i.map { it.toInt() }
        return WordArray.make(intList, NounType.LIST.value)
    }

    fun makeInts(i: List<Int>): Storage {
        return WordArray.make(i, NounType.LIST.value)
    }

    fun makeUInts(i: List<UInt>): Storage {
        // Convert each UInt to Storage
        val storageList = i.map { Integer.make(it) }

        // Check if all are Words (can use WordArray) or if any are WordArrays (need MixedArray)
        val allWords = storageList.all { it.i is I.Word }

        return if (allWords) {
            // All are Words, extract the Int values and use WordArray
            val intList = storageList.map { storage ->
                when (val ii = storage.i) {
                    is I.Word -> ii.value
                    else -> throw IllegalStateException("Expected Word but got ${ii::class.simpleName}")
                }
            }
            WordArray.make(intList, NounType.LIST.value)
        } else {
            // At least one is a WordArray, use MixedArray
            MixedArray.make(storageList, NounType.LIST.value)
        }
    }

    fun makeLongs(i: List<Long>): Storage {
        // Convert each Long to Storage
        val storageList = i.map { Integer.make(it) }

        // Check if all are Words (can use WordArray) or if any are WordArrays (need MixedArray)
        val allWords = storageList.all { it.i is I.Word }

        return if (allWords) {
            // All are Words, extract the Int values and use WordArray
            val intList = storageList.map { storage ->
                when (val ii = storage.i) {
                    is I.Word -> ii.value
                    else -> throw IllegalStateException("Expected Word but got ${ii::class.simpleName}")
                }
            }
            WordArray.make(intList, NounType.LIST.value)
        } else {
            // At least one is a WordArray, use MixedArray
            MixedArray.make(storageList, NounType.LIST.value)
        }
    }

    fun makeULongs(i: List<ULong>): Storage {
        // Convert each ULong to Storage
        val storageList = i.map { Integer.make(it) }

        // Check if all are Words (can use WordArray) or if any are WordArrays (need MixedArray)
        val allWords = storageList.all { it.i is I.Word }

        return if (allWords) {
            // All are Words, extract the Int values and use WordArray
            val intList = storageList.map { storage ->
                when (val ii = storage.i) {
                    is I.Word -> ii.value
                    else -> throw IllegalStateException("Expected Word but got ${ii::class.simpleName}")
                }
            }
            WordArray.make(intList, NounType.LIST.value)
        } else {
            // At least one is a WordArray, use MixedArray
            MixedArray.make(storageList, NounType.LIST.value)
        }
    }

    fun makeBigIntegers(i: List<BigInteger>): Storage {
        // Convert each BigInteger to Storage
        val storageList = i.map { Integer.make(it) }

        // Check if all are Words (can use WordArray) or if any are WordArrays (need MixedArray)
        val allWords = storageList.all { it.i is I.Word }

        return if (allWords) {
            // All are Words, extract the Int values and use WordArray
            val intList = storageList.map { storage ->
                when (val ii = storage.i) {
                    is I.Word -> ii.value
                    else -> throw IllegalStateException("Expected Word but got ${ii::class.simpleName}")
                }
            }
            WordArray.make(intList, NounType.LIST.value)
        } else {
            // At least one is a WordArray, use MixedArray
            MixedArray.make(storageList, NounType.LIST.value)
        }
    }

    fun toByteList(i: Storage): List<Byte> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int back to Byte with range checking
                return ii.value.map { intValue ->
                    if (intValue in Byte.MIN_VALUE..Byte.MAX_VALUE) {
                        intValue.toByte()
                    } else {
                        throw ArithmeticException("Value $intValue out of Byte range")
                    }
                }
            }
            is I.MixedArray -> {
                // Convert each Storage element to Byte
                return ii.value.map { storage ->
                    Integer.toByte(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<Byte>")
        }
    }

    fun toUByteList(i: Storage): List<UByte> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int back to UByte with range checking
                return ii.value.map { intValue ->
                    if (intValue in UByte.MIN_VALUE.toInt()..UByte.MAX_VALUE.toInt()) {
                        intValue.toUByte()
                    } else {
                        throw ArithmeticException("Value $intValue out of UByte range")
                    }
                }
            }
            is I.MixedArray -> {
                // Convert each Storage element to UByte
                return ii.value.map { storage ->
                    Integer.toUByte(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<UByte>")
        }
    }

    fun toShortList(i: Storage): List<Short> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int back to Short with range checking
                return ii.value.map { intValue ->
                    if (intValue in Short.MIN_VALUE..Short.MAX_VALUE) {
                        intValue.toShort()
                    } else {
                        throw ArithmeticException("Value $intValue out of Short range")
                    }
                }
            }
            is I.MixedArray -> {
                // Convert each Storage element to Short
                return ii.value.map { storage ->
                    Integer.toShort(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<Short>")
        }
    }

    fun toUShortList(i: Storage): List<UShort> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int back to UShort with range checking
                return ii.value.map { intValue ->
                    if (intValue in UShort.MIN_VALUE.toInt()..UShort.MAX_VALUE.toInt()) {
                        intValue.toUShort()
                    } else {
                        throw ArithmeticException("Value $intValue out of UShort range")
                    }
                }
            }
            is I.MixedArray -> {
                // Convert each Storage element to UShort
                return ii.value.map { storage ->
                    Integer.toUShort(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<UShort>")
        }
    }

    fun toIntList(i: Storage): List<Int> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                return ii.value
            }
            is I.MixedArray -> {
                // Convert each Storage element to Int
                return ii.value.map { storage ->
                    Integer.toInt(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<Int>")
        }
    }

    fun toUIntList(i: Storage): List<UInt> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int to UInt
                return ii.value.map { it.toUInt() }
            }
            is I.MixedArray -> {
                // Convert each Storage element to UInt
                return ii.value.map { storage ->
                    Integer.toUInt(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<UInt>")
        }
    }

    fun toLongList(i: Storage): List<Long> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int to Long
                return ii.value.map { it.toLong() }
            }
            is I.MixedArray -> {
                // Convert each Storage element to Long
                return ii.value.map { storage ->
                    Integer.toLong(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<Long>")
        }
    }

    fun toULongList(i: Storage): List<ULong> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int to ULong
                return ii.value.map { it.toULong() }
            }
            is I.MixedArray -> {
                // Convert each Storage element to ULong
                return ii.value.map { storage ->
                    Integer.toULong(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<ULong>")
        }
    }

    fun toBigIntegerList(i: Storage): List<BigInteger> {
        // Verify it's a LIST type
        if (i.o != NounType.LIST.value) {
            throw IllegalArgumentException("Storage is not a LIST type")
        }

        when (val ii = i.i) {
            is I.WordArray -> {
                // Convert each Int to BigInteger
                return ii.value.map { BigInteger.valueOf(it.toLong()) }
            }
            is I.MixedArray -> {
                // Convert each Storage element to BigInteger
                return ii.value.map { storage ->
                    Integer.toBigInteger(storage)
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to List<BigInteger>")
        }
    }

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