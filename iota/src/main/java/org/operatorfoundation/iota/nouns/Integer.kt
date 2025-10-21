// Integer.kt

package org.operatorfoundation.iota.nouns

import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.I
import org.operatorfoundation.ion.storage.Word
import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.ion.Connection
import org.operatorfoundation.ion.squeeze_bigint
import java.math.BigInteger

object Integer {
    fun make(i: Byte): Storage {
        return make(i.toInt())
    }

    fun make(i: UByte): Storage {
        return make(i.toInt())
    }

    fun make(i: Short): Storage {
        return make(i.toInt())
    }

    fun make(i: UShort): Storage {
        return make(i.toInt())
    }

    fun make(i: Int): Storage {
        return Word.make(i, NounType.INTEGER.value)
    }

    fun make(i: UInt): Storage {
        if(i > Int.MAX_VALUE.toUInt())
        {
            return make(i.toLong())
        }
        else
        {
            return make(i.toInt())
        }
    }

    fun make(i: Long): Storage {
        return make(BigInteger.valueOf(i))
    }

    fun make(i: ULong): Storage {
        val longValue = i.toLong()
        if (i <= Long.MAX_VALUE.toULong()) {
            return make(BigInteger.valueOf(longValue))
        } else {
            // For large values, add 2^64 to the negative wrapped value
            return make(BigInteger.valueOf(longValue).add(BigInteger.ONE.shiftLeft(64)))
        }
    }

    fun make(i: BigInteger): Storage {
        // Check if it fits in Int range
        if (i >= BigInteger.valueOf(Int.MIN_VALUE.toLong()) &&
            i <= BigInteger.valueOf(Int.MAX_VALUE.toLong())) {
            return Storage(0, 0, I.Word(i.toInt()))
        }

        // Convert to WordArray representation
        val isNegative = i.signum() < 0
        val absValue = i.abs()

        val chunks = mutableListOf<Int>()
        var remaining = absValue
        val mask = BigInteger.valueOf(0x7FFFFFFFL)  // 31-bit mask

        // Extract 31-bit chunks (process from least significant to most significant)
        while (remaining > BigInteger.ZERO) {
            chunks.add(remaining.and(mask).toInt())
            remaining = remaining.shiftRight(31)
        }

        // Reverse to get big-endian order
        chunks.reverse()

        // Prepend sign bit
        val wordArray = mutableListOf(if (isNegative) 1 else 0)
        wordArray.addAll(chunks)

        return Storage(0, 0, I.WordArray(wordArray))
    }

    fun toByte(i: Storage): Byte {
        when (val ii = i.i)
        {
            is I.Word ->
            {
                if(ii.value in Byte.MIN_VALUE..Byte.MAX_VALUE)
                {
                    return ii.value.toByte()
                }
                else
                {
                    throw ArithmeticException("Word value $ii.value out of Byte range")
                }
            }
            is I.WordArray -> {
                // Convert through Long, then check range
                val longValue = toLong(i)
                if (longValue < Byte.MIN_VALUE || longValue > Byte.MAX_VALUE) {
                    throw ArithmeticException("WordArray value out of Byte range")
                }
                return longValue.toByte()
            }

            else -> throw IllegalArgumentException("Cannot convert ${this::class.simpleName} to Byte")
        }
    }

    fun toUByte(i: Storage): UByte {
        when (val ii = i.i) {
            is I.Word -> {
                if(ii.value in UByte.MIN_VALUE.toInt()..UByte.MAX_VALUE.toInt()) {
                    return ii.value.toUByte()
                } else {
                    throw ArithmeticException("Word value ${ii.value} out of UByte range")
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to UByte")
        }
    }

    fun toShort(i: Storage): Short {
        when (val ii = i.i) {
            is I.Word -> {
                if(ii.value in Short.MIN_VALUE..Short.MAX_VALUE) {
                    return ii.value.toShort()
                } else {
                    throw ArithmeticException("Word value ${ii.value} out of Short range")
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to Short")
        }
    }

    fun toUShort(i: Storage): UShort {
        when (val ii = i.i) {
            is I.Word -> {
                if(ii.value in UShort.MIN_VALUE.toInt()..UShort.MAX_VALUE.toInt()) {
                    return ii.value.toUShort()
                } else {
                    throw ArithmeticException("Word value ${ii.value} out of UShort range")
                }
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to UShort")
        }
    }

    fun toInt(i: Storage): Int {
        when (val ii = i.i) {
            is I.Word -> {
                return ii.value  // Already an Int, no conversion or range check needed!
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to Int")
        }
    }

    fun toUInt(i: Storage): UInt {
        when (val ii = i.i) {
            is I.Word -> {
                if(ii.value >= 0) {
                    return ii.value.toUInt()
                } else {
                    throw ArithmeticException("Word value ${ii.value} out of UInt range")
                }
            }
            is I.WordArray -> {
                // Convert through Long, then check range
                val longValue = toLong(i)
                if (longValue < 0 || longValue > UInt.MAX_VALUE.toLong()) {
                    throw ArithmeticException("WordArray value out of UInt range")
                }
                return longValue.toUInt()
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to UInt")
        }
    }

    fun toLong(i: Storage): Long {
        when (val ii = i.i) {
            is I.Word -> {
                return ii.value.toLong()
            }
            is I.WordArray -> {
                val list = ii.value
                if (list.isEmpty()) throw IllegalArgumentException("Empty WordArray")
                val isNegative = list[0] == 1

                var result = 0L
                // Process subsequent items as 31-bit chunks in big-endian order
                for (idx in 1 until list.size) {
                    val chunk = list[idx].toUInt().toLong() and 0x7FFFFFFFL

                    // Check if shift would overflow
                    // For positive: max is Long.MAX_VALUE (result can be up to 4294967295 before shift)
                    // For negative: max is Long.MAX_VALUE + 1 (result can be up to 4294967296 before shift)
                    val maxBeforeShift = if (isNegative) 4294967296L else 4294967295L
                    if (result > maxBeforeShift) {
                        throw ArithmeticException("WordArray value too large for Long")
                    }

                    result = (result shl 31) or chunk
                }

                // Final validation: check if positive value wrapped to negative
                if (!isNegative && result < 0) {
                    throw ArithmeticException("WordArray value too large for Long")
                }

                return if (isNegative) -result else result
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to Long")
        }
    }

    fun toULong(i: Storage): ULong {
        when (val ii = i.i) {
            is I.Word -> {
                if (ii.value >= 0) {
                    return ii.value.toULong()
                } else {
                    throw ArithmeticException("Word value ${ii.value} out of ULong range")
                }
            }
            is I.WordArray -> {
                val list = ii.value
                if (list.isEmpty()) throw IllegalArgumentException("Empty WordArray")

                val isNegative = list[0] == 1
                if (isNegative) {
                    throw ArithmeticException("Cannot convert negative WordArray to ULong")
                }

                var result = 0UL
                // Process subsequent items as 31-bit chunks in big-endian order
                for (idx in 1 until list.size) {
                    val chunk = list[idx].toUInt().toULong() and 0x7FFFFFFFUL

                    // Check if shift would overflow
                    // ULong.MAX_VALUE >> 31 = 8589934591 (max safe value before shift)
                    if (result > 8589934591UL) {
                        throw ArithmeticException("WordArray value too large for ULong")
                    }

                    result = (result shl 31) or chunk
                }

                return result
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to ULong")
        }
    }

    fun toBigInteger(i: Storage): BigInteger {
        when (val ii = i.i) {
            is I.Word -> {
                return BigInteger.valueOf(ii.value.toLong())
            }
            is I.WordArray -> {
                val list = ii.value
                if (list.isEmpty()) throw IllegalArgumentException("Empty WordArray")

                val isNegative = list[0] == 1
                var result = BigInteger.ZERO

                // Process subsequent items as 31-bit chunks in big-endian order
                for (idx in 1 until list.size) {
                    val chunk = list[idx].toUInt().toLong() and 0x7FFFFFFFL  // Mask to 31 bits
                    result = result.shiftLeft(31).or(BigInteger.valueOf(chunk))
                }

                return if (isNegative) result.negate() else result
            }
            else -> throw IllegalArgumentException("Cannot convert ${ii::class.simpleName} to BigInteger")
        }
    }

    fun zero(): Storage {
        return make(0)
    }

    fun one(): Storage {
        return make(1)
    }

    // Serialization - from bytes
    fun from_bytes(bs: ByteArray, t: Int): Storage? {
        return when (t) {
            StorageType.WORD.value -> Word.from_bytes(bs, NounType.INTEGER.value)
            else -> null
        }
    }

    // Serialization - to bytes
    fun to_bytes(i: Storage): ByteArray? {
        if (i.o != NounType.INTEGER.value) {
            return null
        }

        when (i.t) {
            StorageType.WORD.value -> {
                return Word.to_bytes(i)
            }

            StorageType.WORD_ARRAY.value -> {
                when(val ii = i.i)
                {
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
            NounType.INTEGER.value -> Word.from_conn(conn, NounType.INTEGER.value)
            else -> null
        }
    }

    // Serialization - to connection
    fun to_conn(conn: Connection, i: Storage) {
        if (i.o != NounType.INTEGER.value) {
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
            
            StorageType.WORD_ARRAY.value ->
            {
                when(val ii = i.i)
                {
                    is I.WordArray ->
                    {
                        val intBytes = squeeze_bigint(ii.value)

                        // Note that we always send NounType::INTEGER and StorageType::WORD,
                        // even if we internally represent it as a StorageType::WORD_ARRAY.
                        val typeBytes = byteArrayOf(
                            StorageType.WORD.value.toByte(),
                            i.o.toByte()
                        )
                        conn.write(typeBytes)
                        conn.write(intBytes)
                    }
                    else -> return
                }
            }

            else -> return
        }
    }
}
