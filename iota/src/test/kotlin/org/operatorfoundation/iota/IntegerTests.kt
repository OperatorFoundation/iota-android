package org.operatorfoundation.iota

import org.junit.Test
import org.junit.Assert.*
import java.math.BigInteger
import kotlin.random.Random

import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.iota.nouns.Noun
import org.operatorfoundation.iota.nouns.Integer
import org.operatorfoundation.ion.Pipe

class IntegerTests
{

    // ============= Byte Tests =============

    @Test
    fun testByteMinValue()
    {
        val storage = Integer.make(Byte.MIN_VALUE)
        val result = Integer.toByte(storage)
        assertEquals(Byte.MIN_VALUE, result)
    }

    @Test
    fun testByteMaxValue()
    {
        val storage = Integer.make(Byte.MAX_VALUE)
        val result = Integer.toByte(storage)
        assertEquals(Byte.MAX_VALUE, result)
    }

    @Test
    fun testByteZero()
    {
        val storage = Integer.make(0.toByte())
        val result = Integer.toByte(storage)
        assertEquals(0.toByte(), result)
    }

    @Test
    fun testByteNegative()
    {
        val storage = Integer.make((-42).toByte())
        val result = Integer.toByte(storage)
        assertEquals((-42).toByte(), result)
    }

    @Test(expected = ArithmeticException::class)
    fun testByteOverflow()
    {
        val storage = Integer.make(200.toShort()) // Too large for Byte
        Integer.toByte(storage)
    }

    @Test(expected = ArithmeticException::class)
    fun testByteUnderflow()
    {
        val storage = Integer.make((-200).toShort()) // Too small for Byte
        Integer.toByte(storage)
    }

    // ============= UByte Tests =============

    @Test
    fun testUByteMinValue()
    {
        val storage = Integer.make(UByte.MIN_VALUE)
        val result = Integer.toUByte(storage)
        assertEquals(UByte.MIN_VALUE, result)
    }

    @Test
    fun testUByteMaxValue()
    {
        val storage = Integer.make(UByte.MAX_VALUE)
        val result = Integer.toUByte(storage)
        assertEquals(UByte.MAX_VALUE, result)
    }

    @Test
    fun testUByteZero()
    {
        val storage = Integer.make(0u.toUByte())
        val result = Integer.toUByte(storage)
        assertEquals(0u.toUByte(), result)
    }

    @Test
    fun testUByteMidRange()
    {
        val storage = Integer.make(128u.toUByte())
        val result = Integer.toUByte(storage)
        assertEquals(128u.toUByte(), result)
    }

    @Test(expected = ArithmeticException::class)
    fun testUByteOverflow()
    {
        val storage = Integer.make(256.toShort()) // Too large for UByte
        Integer.toUByte(storage)
    }

    @Test(expected = ArithmeticException::class)
    fun testUByteNegative()
    {
        val storage = Integer.make((-1).toByte())
        Integer.toUByte(storage)
    }

    // ============= Short Tests =============

    @Test
    fun testShortMinValue()
    {
        val storage = Integer.make(Short.MIN_VALUE)
        val result = Integer.toShort(storage)
        assertEquals(Short.MIN_VALUE, result)
    }

    @Test
    fun testShortMaxValue()
    {
        val storage = Integer.make(Short.MAX_VALUE)
        val result = Integer.toShort(storage)
        assertEquals(Short.MAX_VALUE, result)
    }

    @Test
    fun testShortZero()
    {
        val storage = Integer.make(0.toShort())
        val result = Integer.toShort(storage)
        assertEquals(0.toShort(), result)
    }

    @Test
    fun testShortNegative()
    {
        val storage = Integer.make((-1000).toShort())
        val result = Integer.toShort(storage)
        assertEquals((-1000).toShort(), result)
    }

    @Test(expected = ArithmeticException::class)
    fun testShortOverflow()
    {
        val storage = Integer.make(40000) // Too large for Short
        Integer.toShort(storage)
    }

    @Test(expected = ArithmeticException::class)
    fun testShortUnderflow()
    {
        val storage = Integer.make(-40000) // Too small for Short
        Integer.toShort(storage)
    }

    // ============= UShort Tests =============

    @Test
    fun testUShortMinValue()
    {
        val storage = Integer.make(UShort.MIN_VALUE)
        val result = Integer.toUShort(storage)
        assertEquals(UShort.MIN_VALUE, result)
    }

    @Test
    fun testUShortMaxValue()
    {
        val storage = Integer.make(UShort.MAX_VALUE)
        val result = Integer.toUShort(storage)
        assertEquals(UShort.MAX_VALUE, result)
    }

    @Test
    fun testUShortZero()
    {
        val storage = Integer.make(0u.toUShort())
        val result = Integer.toUShort(storage)
        assertEquals(0u.toUShort(), result)
    }

    @Test
    fun testUShortMidRange()
    {
        val storage = Integer.make(40000u.toUShort())
        val result = Integer.toUShort(storage)
        assertEquals(40000u.toUShort(), result)
    }

    @Test(expected = ArithmeticException::class)
    fun testUShortOverflow()
    {
        val storage = Integer.make(70000) // Too large for UShort
        Integer.toUShort(storage)
    }

    @Test(expected = ArithmeticException::class)
    fun testUShortNegative()
    {
        val storage = Integer.make(-1)
        Integer.toUShort(storage)
    }

    // ============= Int Tests =============

    @Test
    fun testIntMinValue()
    {
        val storage = Integer.make(Int.MIN_VALUE)
        val result = Integer.toInt(storage)
        assertEquals(Int.MIN_VALUE, result)
    }

    @Test
    fun testIntMaxValue()
    {
        val storage = Integer.make(Int.MAX_VALUE)
        val result = Integer.toInt(storage)
        assertEquals(Int.MAX_VALUE, result)
    }

    @Test
    fun testIntZero()
    {
        val storage = Integer.make(0)
        val result = Integer.toInt(storage)
        assertEquals(0, result)
    }

    @Test
    fun testIntNegative()
    {
        val storage = Integer.make(-123456)
        val result = Integer.toInt(storage)
        assertEquals(-123456, result)
    }

    @Test
    fun testIntPositive()
    {
        val storage = Integer.make(123456)
        val result = Integer.toInt(storage)
        assertEquals(123456, result)
    }

    // ============= UInt Tests =============

    @Test
    fun testUIntMinValue()
    {
        val storage = Integer.make(UInt.MIN_VALUE)
        val result = Integer.toUInt(storage)
        assertEquals(UInt.MIN_VALUE, result)
    }

    @Test
    fun testUIntMaxValue()
    {
        val storage = Integer.make(UInt.MAX_VALUE)
        val result = Integer.toUInt(storage)
        assertEquals(UInt.MAX_VALUE, result)
    }

    @Test
    fun testUIntZero()
    {
        val storage = Integer.make(0u)
        val result = Integer.toUInt(storage)
        assertEquals(0u, result)
    }

    @Test
    fun testUIntLargeValue()
    {
        val storage = Integer.make(3000000000u)
        val result = Integer.toUInt(storage)
        assertEquals(3000000000u, result)
    }

    @Test(expected = ArithmeticException::class)
    fun testUIntNegative()
    {
        val storage = Integer.make(-1)
        Integer.toUInt(storage)
    }

    // ============= Long Tests =============

    @Test
    fun testLongMinValue()
    {
        val storage = Integer.make(Long.MIN_VALUE)
        val result = Integer.toLong(storage)
        assertEquals(Long.MIN_VALUE, result)
    }

    @Test
    fun testLongMaxValue()
    {
        val storage = Integer.make(Long.MAX_VALUE)
        val result = Integer.toLong(storage)
        assertEquals(Long.MAX_VALUE, result)
    }

    @Test
    fun testLongZero()
    {
        val storage = Integer.make(0L)
        val result = Integer.toLong(storage)
        assertEquals(0L, result)
    }

    @Test
    fun testLongFromInt()
    {
        val storage = Integer.make(123456)
        val result = Integer.toLong(storage)
        assertEquals(123456L, result)
    }

    @Test
    fun testLongNegative()
    {
        val storage = Integer.make(-9000000000000000000L)
        val result = Integer.toLong(storage)
        assertEquals(-9000000000000000000L, result)
    }

    @Test
    fun testLongPositiveLarge()
    {
        val storage = Integer.make(9000000000000000000L)
        val result = Integer.toLong(storage)
        assertEquals(9000000000000000000L, result)
    }

    @Test(expected = ArithmeticException::class)
    fun testLongOverflow()
    {
        // Create a BigInteger larger than Long.MAX_VALUE
        val bigInt = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)
        val storage = Integer.make(bigInt)
        Integer.toLong(storage)
    }

    // ============= ULong Tests =============

    @Test
    fun testULongMinValue()
    {
        val storage = Integer.make(ULong.MIN_VALUE)
        val result = Integer.toULong(storage)
        assertEquals(ULong.MIN_VALUE, result)
    }

    @Test
    fun testULongMaxValue()
    {
        val storage = Integer.make(ULong.MAX_VALUE)
        val result = Integer.toULong(storage)
        assertEquals(ULong.MAX_VALUE, result)
    }

    @Test
    fun testULongZero()
    {
        val storage = Integer.make(0uL)
        val result = Integer.toULong(storage)
        assertEquals(0uL, result)
    }

    @Test
    fun testULongFromInt()
    {
        val storage = Integer.make(123456)
        val result = Integer.toULong(storage)
        assertEquals(123456uL, result)
    }

    @Test
    fun testULongLargeValue()
    {
        val storage = Integer.make(18000000000000000000uL)
        val result = Integer.toULong(storage)
        assertEquals(18000000000000000000uL, result)
    }

    @Test(expected = ArithmeticException::class)
    fun testULongNegative()
    {
        val storage = Integer.make(-1L)
        Integer.toULong(storage)
    }

    @Test(expected = ArithmeticException::class)
    fun testULongOverflow()
    {
        // Create a BigInteger larger than ULong.MAX_VALUE
        val bigInt = BigInteger("18446744073709551616") // ULong.MAX_VALUE + 1
        val storage = Integer.make(bigInt)
        Integer.toULong(storage)
    }

    // ============= BigInteger Tests =============

    @Test
    fun testBigIntegerZero()
    {
        val storage = Integer.make(BigInteger.ZERO)
        val result = Integer.toBigInteger(storage)
        assertEquals(BigInteger.ZERO, result)
    }

    @Test
    fun testBigIntegerOne()
    {
        val storage = Integer.make(BigInteger.ONE)
        val result = Integer.toBigInteger(storage)
        assertEquals(BigInteger.ONE, result)
    }

    @Test
    fun testBigIntegerFromInt()
    {
        val bigInt = BigInteger.valueOf(123456)
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerFromLong()
    {
        val bigInt = BigInteger.valueOf(Long.MAX_VALUE)
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerNegative()
    {
        val bigInt = BigInteger.valueOf(-123456789)
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerVeryLarge()
    {
        val bigInt = BigInteger("123456789012345678901234567890")
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerVeryLargeNegative()
    {
        val bigInt = BigInteger("-987654321098765432109876543210")
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerPowerOf2()
    {
        val bigInt = BigInteger.valueOf(2).pow(100)
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerEdgeCase31Bits()
    {
        // Test values around 31-bit boundaries (important for your representation)
        val bigInt = BigInteger.valueOf((1L shl 31) - 1) // Max 31-bit value
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    @Test
    fun testBigIntegerEdgeCase62Bits()
    {
        // Test values around 62-bit boundaries (two 31-bit chunks)
        val bigInt = BigInteger.valueOf((1L shl 62) - 1)
        val storage = Integer.make(bigInt)
        val result = Integer.toBigInteger(storage)
        assertEquals(bigInt, result)
    }

    // ============= Round-trip Tests =============

    @Test
    fun testRoundTripByte()
    {
        val original: Byte = 42
        val storage = Integer.make(original)
        val result = Integer.toByte(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripUByte()
    {
        val original: UByte = 200u
        val storage = Integer.make(original)
        val result = Integer.toUByte(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripShort()
    {
        val original: Short = -12345
        val storage = Integer.make(original)
        val result = Integer.toShort(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripUShort()
    {
        val original: UShort = 50000u
        val storage = Integer.make(original)
        val result = Integer.toUShort(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripInt()
    {
        val original: Int = -987654321
        val storage = Integer.make(original)
        val result = Integer.toInt(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripUInt()
    {
        val original = 3000000000u
        val storage = Integer.make(original)
        val result = Integer.toUInt(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripLong()
    {
        val original: Long = -9223372036854775807L
        val storage = Integer.make(original)
        val result = Integer.toLong(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripULong()
    {
        val original = 18446744073709551615u
        val storage = Integer.make(original)
        val result = Integer.toULong(storage)
        assertEquals(original, result)
    }

    @Test
    fun testRoundTripBigInteger()
    {
        val original = BigInteger("999999999999999999999999999999")
        val storage = Integer.make(original)
        val result = Integer.toBigInteger(storage)
        assertEquals(original, result)
    }

    @Test
    fun testULongFullRangeSystematic()
    {
        // Test specific important boundary values
        val testValues = listOf(
            0uL,                                    // Minimum
            1uL,
            255uL,                                  // UByte.MAX
            256uL,
            65535uL,                                // UShort.MAX
            65536uL,
            (1uL shl 31) - 1uL,                    // 31-bit boundary
            (1uL shl 31),
            (1uL shl 31) + 1uL,
            (1uL shl 32) - 1uL,                    // 32-bit boundary (UInt.MAX)
            (1uL shl 32),
            (1uL shl 32) + 1uL,
            (1uL shl 62) - 1uL,                    // 62-bit boundary
            (1uL shl 62),
            (1uL shl 62) + 1uL,
            (1uL shl 63) - 1uL,                    // 63-bit boundary
            (1uL shl 63),
            (1uL shl 63) + 1uL,
            ULong.MAX_VALUE - 1uL,
            ULong.MAX_VALUE                         // Maximum
        )

        for(value in testValues)
        {
            val storage = Integer.make(value)
            val result = Integer.toULong(storage)
            assertEquals("Failed for value: $value", value, result)
        }
    }

    @Test
    fun testLongFullRangeSystematic()
    {
        // Test specific important boundary values
        val testValues = listOf(
            Long.MIN_VALUE,                         // Minimum
            Long.MIN_VALUE + 1,
            -(1L shl 62),                          // -2^62
            -(1L shl 62) + 1,
            -(1L shl 31),                          // -2^31
            -(1L shl 31) + 1,
            -65536L,
            -256L,
            -1L,
            0L,
            1L,
            255L,
            256L,
            65535L,
            65536L,
            (1L shl 31) - 1,                       // 2^31 - 1
            (1L shl 31),                           // 2^31
            (1L shl 31) + 1,
            (1L shl 62) - 1,                       // 2^62 - 1
            (1L shl 62),                           // 2^62
            (1L shl 62) + 1,
            Long.MAX_VALUE - 1,
            Long.MAX_VALUE                          // Maximum
        )

        for(value in testValues)
        {
            val storage = Integer.make(value)
            val result = Integer.toLong(storage)
            assertEquals("Failed for value: $value", value, result)
        }
    }

    @Test
    fun testULongRandomValues()
    {
        val random = Random(12345) // Fixed seed for reproducibility

        // Test 1000 random values across the full range
        repeat(1000) {
            // Generate random ULong by combining two random Ints
            val high = random.nextInt().toUInt().toULong()
            val low = random.nextInt().toUInt().toULong()
            val value = (high shl 32) or low

            val storage = Integer.make(value)
            val result = Integer.toULong(storage)
            assertEquals("Failed for random value: $value", value, result)
        }
    }

    @Test
    fun testLongRandomValues()
    {
        val random = Random(12345) // Fixed seed for reproducibility

        // Test 1000 random values across the full range
        repeat(1000) {
            val value = random.nextLong()

            val storage = Integer.make(value)
            val result = Integer.toLong(storage)
            assertEquals("Failed for random value: $value", value, result)
        }
    }

    @Test
    fun testRadioFrequencyUseCase()
    {
        // Test radio frequencies in centihertz
        // Shortwave range: ~3 MHz to ~30 MHz = 300,000,000 to 3,000,000,000 centihertz
        // With sub-hertz offsets: 0.61 Hz = 61 centihertz

        val baseFrequencies = listOf(
            300_000_000uL,      // 3 MHz in centihertz
            700_000_000uL,      // 7 MHz
            1_400_000_000uL,    // 14 MHz
            2_100_000_000uL,    // 21 MHz
            2_800_000_000uL,    // 28 MHz
            3_000_000_000uL     // 30 MHz
        )

        val offsets = listOf(
            0uL,                // No offset
            61uL,               // 0.61 Hz
            122uL,              // 1.22 Hz
            183uL,              // 1.83 Hz
            244uL,              // 2.44 Hz
            305uL,              // 3.05 Hz
            1000uL,             // 10 Hz
            10000uL             // 100 Hz
        )

        // Test all combinations
        for(base in baseFrequencies)
        {
            for(offset in offsets)
            {
                val frequency = base + offset

                val storage = Integer.make(frequency)
                val result = Integer.toULong(storage)
                assertEquals(
                    "Failed for frequency: $frequency (base: $base, offset: $offset)",
                    frequency,
                    result
                )
            }
        }
    }

    @Test
    fun testULongNearPowerOfTwo()
    {
        // Test values near powers of 2, which can reveal issues with bit boundaries
        for(power in 1..63)
        {
            val base = 1uL shl power
            val testValues = listOf(
                base - 2uL,
                base - 1uL,
                base,
                base + 1uL,
                base + 2uL
            )

            for(value in testValues)
            {
                val storage = Integer.make(value)
                val result = Integer.toULong(storage)
                assertEquals("Failed near 2^$power for value: $value", value, result)
            }
        }
    }

    @Test
    fun testLongNearPowerOfTwo()
    {
        // Test values near powers of 2 (both positive and negative)
        for(power in 1..62)
        {
            val base = 1L shl power
            val testValues = listOf(
                -base - 2L,
                -base - 1L,
                -base,
                -base + 1L,
                -base + 2L,
                base - 2L,
                base - 1L,
                base,
                base + 1L,
                base + 2L
            )

            for(value in testValues)
            {
                val storage = Integer.make(value)
                val result = Integer.toLong(storage)
                assertEquals("Failed near 2^$power for value: $value", value, result)
            }
        }
    }

    @Test
    fun testULongStride()
    {
        // Test values with regular strides across the entire range
        // This catches issues that might occur at specific bit patterns
        val stride = ULong.MAX_VALUE / 10000uL
        var value = 0uL

        for(i in 0 until 10000)
        {
            val storage = Integer.make(value)
            val result = Integer.toULong(storage)
            assertEquals("Failed for stride value: $value", value, result)

            val nextValue = value + stride
            if(nextValue < value) break // Handle overflow
            value = nextValue
        }

        // Make sure we test MAX_VALUE
        val storage = Integer.make(ULong.MAX_VALUE)
        val result = Integer.toULong(storage)
        assertEquals(ULong.MAX_VALUE, result)
    }

    @Test
    fun testLongStride()
    {
        // Test values with regular strides across the entire range
        val stride = Long.MAX_VALUE / 5000L

        // Test positive range
        var value = 0L
        repeat(5000) {
            val storage = Integer.make(value)
            val result = Integer.toLong(storage)
            assertEquals("Failed for positive stride value: $value", value, result)
            value += stride
        }

        // Test negative range
        value = Long.MIN_VALUE
        repeat(5000) {
            val storage = Integer.make(value)
            val result = Integer.toLong(storage)
            assertEquals("Failed for negative stride value: $value", value, result)
            value += stride
        }
    }

// ============= Serialization Tests =============

    @Test
    fun testIntSerializationRoundTrip() {
        val pipe = Pipe()
        val value = 42

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toInt(result!!))
    }

    @Test
    fun testIntNegativeSerializationRoundTrip() {
        val pipe = Pipe()
        val value = -12345

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toInt(result!!))
    }

    @Test
    fun testIntMinMaxSerializationRoundTrip() {
        val pipe = Pipe()

        // Test MIN
        val minStorage = Integer.make(Int.MIN_VALUE)
        Noun.to_conn(pipe.endA, minStorage)
        val minResult = Noun.from_conn(pipe.endB)
        assertNotNull(minResult)
        assertEquals(Int.MIN_VALUE, Integer.toInt(minResult!!))

        // Test MAX
        val maxStorage = Integer.make(Int.MAX_VALUE)
        Noun.to_conn(pipe.endA, maxStorage)
        val maxResult = Noun.from_conn(pipe.endB)
        assertNotNull(maxResult)
        assertEquals(Int.MAX_VALUE, Integer.toInt(maxResult!!))
    }

    @Test
    fun testLongSerializationRoundTrip() {
        val pipe = Pipe()
        val value = 123456789012345L

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toLong(result!!))
    }

    @Test
    fun testLongMinMaxSerializationRoundTrip() {
        val pipe = Pipe()

        // Test MIN
        val minStorage = Integer.make(Long.MIN_VALUE)
        Noun.to_conn(pipe.endA, minStorage)
        val minResult = Noun.from_conn(pipe.endB)
        assertNotNull(minResult)
        assertEquals(Long.MIN_VALUE, Integer.toLong(minResult!!))

        // Test MAX
        val maxStorage = Integer.make(Long.MAX_VALUE)
        Noun.to_conn(pipe.endA, maxStorage)
        val maxResult = Noun.from_conn(pipe.endB)
        assertNotNull(maxResult)
        assertEquals(Long.MAX_VALUE, Integer.toLong(maxResult!!))
    }

    @Test
    fun testULongSerializationRoundTrip() {
        val pipe = Pipe()
        val value = 18000000000000000000uL

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toULong(result!!))
    }

    @Test
    fun testULongMaxSerializationRoundTrip() {
        val pipe = Pipe()
        val value = ULong.MAX_VALUE

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toULong(result!!))
    }

    @Test
    fun testBigIntegerSerializationRoundTrip() {
        val pipe = Pipe()
        val value = BigInteger("123456789012345678901234567890")

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toBigInteger(result!!))
    }

    @Test
    fun testBigIntegerNegativeSerializationRoundTrip() {
        val pipe = Pipe()
        val value = BigInteger("-987654321098765432109876543210")

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toBigInteger(result!!))
    }

    @Test
    fun testBigIntegerVeryLargeSerializationRoundTrip() {
        val pipe = Pipe()
        val value = BigInteger.TWO.pow(200)

        val original = Integer.make(value)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(value, Integer.toBigInteger(result!!))
    }

    @Test
    fun testRadioFrequencySerializationRoundTrip() {
        val pipe = Pipe()
        // 14 MHz + 0.61 Hz offset in centihertz
        val frequency = 1400000061uL

        val original = Integer.make(frequency)
        Noun.to_conn(pipe.endA, original)

        val result = Noun.from_conn(pipe.endB)
        assertNotNull(result)
        assertEquals(frequency, Integer.toULong(result!!))
    }

    @Test
    fun testMultipleIntegersSerialization() {
        val pipe = Pipe()
        val values = listOf(1, 42, -100, Int.MAX_VALUE, Int.MIN_VALUE)

        // Write all values
        for (value in values) {
            val storage = Integer.make(value)
            Noun.to_conn(pipe.endA, storage)
        }

        // Read all values back
        for (expected in values) {
            val result = Noun.from_conn(pipe.endB)
            assertNotNull(result)
            assertEquals(expected, Integer.toInt(result!!))
        }
    }

    // ============= Golden Value Tests with Connection =============

    @Test
    fun testIntZeroGoldenValueConn() {
        val pipe = Pipe()
        val value = 0

        // Expected bytes: type bytes (WORD=0, INTEGER=0) + data (single 0 byte)
        val expected = byteArrayOf(0, 0, 0)
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toInt(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testIntPositiveSmallGoldenValueConn() {
        val pipe = Pipe()
        val value = 42

        // Expected bytes: type bytes (WORD=0, INTEGER=0) + length (1) + value (42)
        val expected = byteArrayOf(0, 0, 1, 42)
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toInt(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testIntNegativeSmallGoldenValueConn() {
        val pipe = Pipe()
        val value = -42

        // Expected bytes: type bytes (WORD=0, INTEGER=0) + length with sign (0x81) + value (42)
        val expected = byteArrayOf(0, 0, 0x81.toByte(), 42)
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toInt(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testIntMaxValueGoldenValueConn() {
        val pipe = Pipe()
        val value = Int.MAX_VALUE // 0x7FFFFFFF

        // Expected bytes: type bytes + length (4) + 4 bytes
        val expected = byteArrayOf(0, 0, 4, 0x7F, 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toInt(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testIntMinValueGoldenValueConn() {
        val pipe = Pipe()
        val value = Int.MIN_VALUE // -0x80000000

        // Expected bytes: type bytes + length with sign (0x84) + 4 bytes
        val expected = byteArrayOf(0, 0, 0x84.toByte(), 0x80.toByte(), 0x00, 0x00, 0x00)
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toInt(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testLongSmallGoldenValueConn() {
        val pipe = Pipe()
        val value = 123456789012345L // 0x70488600DF79 in your encoding
        // Expected bytes: type bytes (0x00, 0x00) + length (8) + 8 bytes
        val expected = byteArrayOf(0x00, 0x00, 0x08, 0x00, 0x00, 0x70, 0x48, 0x86.toByte(), 0x0D, 0xDF.toByte(), 0x79)
        pipe.endA.write(expected)
        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toLong(decoded))
        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testLongMaxValueGoldenValueConn() {
        val pipe = Pipe()
        val value = Long.MAX_VALUE // 0x7FFFFFFFFFFFFFFF

        // Expected bytes: type bytes + length (8) + 8 bytes
        val expected = byteArrayOf(
            0, 0, 8,
            0x7F, 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(),
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()
        )
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toLong(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testLongMinValueGoldenValueConn() {
        val pipe = Pipe()
        val value = Long.MIN_VALUE // -0x8000000000000000

        // Expected bytes: type bytes + length with sign (0x88) + 8 bytes
        val expected = byteArrayOf(
            0, 0, 0x88.toByte(),
            0x80.toByte(), 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00
        )
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toLong(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testULongMaxValueGoldenValueConn() {
        val pipe = Pipe()
        val value = ULong.MAX_VALUE // 0xFFFFFFFFFFFFFFFF

        // Expected bytes: type bytes + length (8) + 8 bytes
        val expected = byteArrayOf(
            0, 0, 8,
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(),
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()
        )
        pipe.endA.write(expected)

        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toULong(decoded))

        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testRadioFrequencyGoldenValueConn() {
        val pipe = Pipe()
        val frequency = 1400000061uL // Encodes as 0x53724E3D
        // Expected bytes: type bytes + length (4) + 4 bytes
        val expected = byteArrayOf(0x00, 0x00, 0x04, 0x53, 0x72, 0x4E, 0x3D)
        pipe.endA.write(expected)
        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(frequency, Integer.toULong(decoded))
        // Test reverse direction
        val storage = Integer.make(frequency)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testBigIntegerSmallGoldenValueConn() {
        val pipe = Pipe()
        val value = BigInteger("123456789012345") // Encodes as 0x000070488600DF79
        // Expected bytes: type bytes + length (8) + 8 bytes
        val expected = byteArrayOf(0x00, 0x00, 0x08, 0x00, 0x00, 0x70, 0x48, 0x86.toByte(), 0x0D, 0xDF.toByte(), 0x79)
        pipe.endA.write(expected)
        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toBigInteger(decoded))
        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testBigIntegerLargeGoldenValueConn() {
        val pipe = Pipe()
        val value = BigInteger("123456789012345678901234567890") // 0x18EE90FF6C373E0EE4E3F0AD2
        // Expected bytes: type bytes + length (16) + 16 bytes
        val expected = byteArrayOf(
            0x00, 0x00, 0x10,
            0x00, 0x00, 0x00, 0x01, 0x8E.toByte(), 0xE9.toByte(), 0x0F, 0xF6.toByte(),
            0xC3.toByte(), 0x73, 0xE0.toByte(), 0xEE.toByte(), 0x4E,
            0x3F, 0x0A, 0xD2.toByte()
        )
        pipe.endA.write(expected)
        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toBigInteger(decoded))
        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testBigIntegerNegativeGoldenValueConn() {
        val pipe = Pipe()
        val value = BigInteger("-987654321098765432109876543210")
        // Expected bytes: type bytes + length with sign (0x90) + 16 bytes
        val expected = byteArrayOf(
            0x00, 0x00, 0x90.toByte(),
            0x00, 0x00, 0x00, 0x0C, 0x77,
            0x48, 0x81.toByte(), 0x9D.toByte(), 0xFF.toByte(), 0xB6.toByte(), 0x24, 0x38, 0xD1.toByte(),
            0xC6.toByte(), 0x7E, 0xEA.toByte()
        )
        pipe.endA.write(expected)
        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toBigInteger(decoded))
        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }

    @Test
    fun testBigIntegerPowerOf2GoldenValueConn() {
        val pipe = Pipe()
        val value = BigInteger.TWO.pow(100)
        // Expected bytes: type bytes + length (16) + 16 bytes
        val expected = byteArrayOf(
            0x00, 0x00, 0x10,
            0x00, 0x00, 0x00, 0x10,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00
        )
        pipe.endA.write(expected)
        val decoded = Noun.from_conn(pipe.endB)
        if (decoded == null) {
            fail("from_conn returned null")
            return
        }
        assertEquals(value, Integer.toBigInteger(decoded))
        // Test reverse direction
        val storage = Integer.make(value)
        Noun.to_conn(pipe.endA, storage)
        val bytes = pipe.endB.read(expected.size)
        assertArrayEquals(expected, bytes)
    }
}