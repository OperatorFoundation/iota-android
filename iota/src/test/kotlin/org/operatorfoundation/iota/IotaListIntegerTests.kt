package org.operatorfoundation.iota

import org.junit.Test
import org.junit.Assert.*
import org.operatorfoundation.iota.nouns.IotaList
import java.math.BigInteger
import kotlin.random.Random

class IotaListIntegerTests {

  // ============= Int List Tests =============

  @Test
  fun testIntListEmpty() {
    val list = emptyList<Int>()
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testIntListSingle() {
    val list = listOf(42)
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testIntListMultiple() {
    val list = listOf(1, 2, 3, 4, 5)
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testIntListMinMax() {
    val list = listOf(Int.MIN_VALUE, 0, Int.MAX_VALUE)
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testIntListNegative() {
    val list = listOf(-100, -50, -1, 0, 1, 50, 100)
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testIntListRandom() {
    val random = Random(12345)
    val list = List(100) { random.nextInt() }
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  // ============= Byte List Tests =============

  @Test
  fun testByteListEmpty() {
    val list = emptyList<Byte>()
    val storage = IotaList.makeBytes(list)
    val result = IotaList.toByteList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testByteListMinMax() {
    val list = listOf(Byte.MIN_VALUE, 0.toByte(), Byte.MAX_VALUE)
    val storage = IotaList.makeBytes(list)
    val result = IotaList.toByteList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testByteListMultiple() {
    val list = listOf<Byte>(1, 2, 3, 4, 5, -1, -2, -3)
    val storage = IotaList.makeBytes(list)
    val result = IotaList.toByteList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testByteListRandom() {
    val random = Random(12345)
    val list = List(100) { random.nextInt(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt() + 1).toByte() }
    val storage = IotaList.makeBytes(list)
    val result = IotaList.toByteList(storage)
    assertEquals(list, result)
  }

  // ============= UByte List Tests =============

  @Test
  fun testUByteListEmpty() {
    val list = emptyList<UByte>()
    val storage = IotaList.makeUBytes(list)
    val result = IotaList.toUByteList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUByteListMinMax() {
    val list = listOf(UByte.MIN_VALUE, 128u.toUByte(), UByte.MAX_VALUE)
    val storage = IotaList.makeUBytes(list)
    val result = IotaList.toUByteList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUByteListMultiple() {
    val list = listOf<UByte>(0u, 1u, 127u, 128u, 255u)
    val storage = IotaList.makeUBytes(list)
    val result = IotaList.toUByteList(storage)
    assertEquals(list, result)
  }

  // ============= Short List Tests =============

  @Test
  fun testShortListEmpty() {
    val list = emptyList<Short>()
    val storage = IotaList.makeShorts(list)
    val result = IotaList.toShortList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testShortListMinMax() {
    val list = listOf(Short.MIN_VALUE, 0.toShort(), Short.MAX_VALUE)
    val storage = IotaList.makeShorts(list)
    val result = IotaList.toShortList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testShortListMultiple() {
    val list = listOf<Short>(1, 100, 1000, 10000, -1, -100, -1000, -10000)
    val storage = IotaList.makeShorts(list)
    val result = IotaList.toShortList(storage)
    assertEquals(list, result)
  }

  // ============= UShort List Tests =============

  @Test
  fun testUShortListEmpty() {
    val list = emptyList<UShort>()
    val storage = IotaList.makeUShorts(list)
    val result = IotaList.toUShortList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUShortListMinMax() {
    val list = listOf(UShort.MIN_VALUE, 32768u.toUShort(), UShort.MAX_VALUE)
    val storage = IotaList.makeUShorts(list)
    val result = IotaList.toUShortList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUShortListMultiple() {
    val list = listOf<UShort>(0u, 100u, 1000u, 10000u, 50000u, 65535u)
    val storage = IotaList.makeUShorts(list)
    val result = IotaList.toUShortList(storage)
    assertEquals(list, result)
  }

  // ============= Long List Tests =============

  @Test
  fun testLongListEmpty() {
    val list = emptyList<Long>()
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testLongListAllInIntRange() {
    // All values fit in Int - should use WordArray
    val list = listOf(1L, 2L, 3L, 100L, -50L)
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testLongListSomeLarge() {
    // Some values don't fit in Int - should use MixedArray
    val list = listOf(1L, Long.MAX_VALUE, 3L, Long.MIN_VALUE, 100L)
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testLongListMinMax() {
    val list = listOf(Long.MIN_VALUE, 0L, Long.MAX_VALUE)
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testLongListRandom() {
    val random = Random(12345)
    val list = List(100) { random.nextLong() }
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testLongListNear64BitBoundaries() {
    val list = listOf(
      (1L shl 31) - 1,
      (1L shl 31),
      (1L shl 31) + 1,
      (1L shl 62) - 1,
      (1L shl 62),
      (1L shl 62) + 1,
      -(1L shl 31),
      -(1L shl 62)
    )
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }

  // ============= UInt List Tests =============

  @Test
  fun testUIntListEmpty() {
    val list = emptyList<UInt>()
    val storage = IotaList.makeUInts(list)
    val result = IotaList.toUIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUIntListAllInIntRange() {
    // All values fit in Int range
    val list = listOf(0u, 1u, 100u, 1000u, Int.MAX_VALUE.toUInt())
    val storage = IotaList.makeUInts(list)
    val result = IotaList.toUIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUIntListSomeLarge() {
    // Some values exceed Int.MAX_VALUE
    val list = listOf(1u, UInt.MAX_VALUE, 100u, 3000000000u)
    val storage = IotaList.makeUInts(list)
    val result = IotaList.toUIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testUIntListMinMax() {
    val list = listOf(UInt.MIN_VALUE, UInt.MAX_VALUE)
    val storage = IotaList.makeUInts(list)
    val result = IotaList.toUIntList(storage)
    assertEquals(list, result)
  }

  // ============= ULong List Tests =============

  @Test
  fun testULongListEmpty() {
    val list = emptyList<ULong>()
    val storage = IotaList.makeULongs(list)
    val result = IotaList.toULongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testULongListAllInIntRange() {
    val list = listOf(0uL, 1uL, 100uL, 1000uL)
    val storage = IotaList.makeULongs(list)
    val result = IotaList.toULongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testULongListSomeLarge() {
    val list = listOf(1uL, ULong.MAX_VALUE, 100uL, 18000000000000000000uL)
    val storage = IotaList.makeULongs(list)
    val result = IotaList.toULongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testULongListMinMax() {
    val list = listOf(ULong.MIN_VALUE, ULong.MAX_VALUE)
    val storage = IotaList.makeULongs(list)
    val result = IotaList.toULongList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testULongListRadioFrequencies() {
    // Test radio frequency use case
    val baseFrequencies = listOf(
      300_000_000uL,      // 3 MHz in centihertz
      1_400_000_000uL,    // 14 MHz
      2_800_000_000uL     // 28 MHz
    )
    val storage = IotaList.makeULongs(baseFrequencies)
    val result = IotaList.toULongList(storage)
    assertEquals(baseFrequencies, result)
  }

  @Test
  fun testULongListRandom() {
    val random = Random(12345)
    val list = List(100) {
      // Generate random ULong
      val high = random.nextInt().toUInt().toULong()
      val low = random.nextInt().toUInt().toULong()
      (high shl 32) or low
    }
    val storage = IotaList.makeULongs(list)
    val result = IotaList.toULongList(storage)
    assertEquals(list, result)
  }

  // ============= BigInteger List Tests =============

  @Test
  fun testBigIntegerListEmpty() {
    val list = emptyList<BigInteger>()
    val storage = IotaList.makeBigIntegers(list)
    val result = IotaList.toBigIntegerList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testBigIntegerListAllInIntRange() {
    val list = listOf(
      BigInteger.ZERO,
      BigInteger.ONE,
      BigInteger.valueOf(100),
      BigInteger.valueOf(-50)
    )
    val storage = IotaList.makeBigIntegers(list)
    val result = IotaList.toBigIntegerList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testBigIntegerListSomeLarge() {
    val list = listOf(
      BigInteger.ONE,
      BigInteger("123456789012345678901234567890"),
      BigInteger.valueOf(100),
      BigInteger("-987654321098765432109876543210")
    )
    val storage = IotaList.makeBigIntegers(list)
    val result = IotaList.toBigIntegerList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testBigIntegerListVeryLarge() {
    val list = listOf(
      BigInteger.TWO.pow(100),
      BigInteger.TWO.pow(200),
      BigInteger.TWO.pow(500)
    )
    val storage = IotaList.makeBigIntegers(list)
    val result = IotaList.toBigIntegerList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testBigIntegerListNegative() {
    val list = listOf(
      BigInteger("-123456789012345678901234567890"),
      BigInteger.ZERO,
      BigInteger("123456789012345678901234567890")
    )
    val storage = IotaList.makeBigIntegers(list)
    val result = IotaList.toBigIntegerList(storage)
    assertEquals(list, result)
  }

  // ============= Mixed Size Tests =============

  @Test
  fun testLongListMixedSizes() {
    // Test that we correctly handle transition between WordArray and MixedArray
    val smallList = listOf(1L, 2L, 3L)
    val largeList = listOf(1L, Long.MAX_VALUE, 3L)

    val smallStorage = IotaList.makeLongs(smallList)
    val largeStorage = IotaList.makeLongs(largeList)

    val smallResult = IotaList.toLongList(smallStorage)
    val largeResult = IotaList.toLongList(largeStorage)

    assertEquals(smallList, smallResult)
    assertEquals(largeList, largeResult)
  }

  @Test
  fun testULongListMixedSizes() {
    val smallList = listOf(1uL, 2uL, 3uL)
    val largeList = listOf(1uL, ULong.MAX_VALUE, 3uL)

    val smallStorage = IotaList.makeULongs(smallList)
    val largeStorage = IotaList.makeULongs(largeList)

    val smallResult = IotaList.toULongList(smallStorage)
    val largeResult = IotaList.toULongList(largeStorage)

    assertEquals(smallList, smallResult)
    assertEquals(largeList, largeResult)
  }

  // ============= Large List Tests =============

  @Test
  fun testLargeIntList() {
    val list = List(10000) { it }
    val storage = IotaList.makeInts(list)
    val result = IotaList.toIntList(storage)
    assertEquals(list, result)
  }

  @Test
  fun testLargeLongList() {
    val list = List(1000) { it.toLong() * 1000000000L }
    val storage = IotaList.makeLongs(list)
    val result = IotaList.toLongList(storage)
    assertEquals(list, result)
  }
}