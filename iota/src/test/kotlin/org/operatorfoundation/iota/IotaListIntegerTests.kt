package org.operatorfoundation.iota

import org.junit.Test
import org.junit.Assert.*
import org.operatorfoundation.ion.storage.I
import org.operatorfoundation.ion.storage.StorageType
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

  @Test
  fun testULongListRoundTrip() {
    val original = listOf(
      0uL,
      1uL,
      100uL,
      ULong.MAX_VALUE,
      0x123456789ABCDEFuL,
      300_000_000uL,      // 3 MHz in centihertz (radio frequency)
      18000000000000000000uL
    )

    // Convert to IotaValue
    val iotaValue = original.toIotaValue()
    assertTrue(iotaValue is IotaValue.ListValue)

    // Convert to Storage
    val storage = IotaObject.fromKotlin(iotaValue)

    // Convert back to IotaValue
    val result = IotaObject.toKotlin(storage)

    // Verify it's a list
    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values

    // All elements should be BigIntValue (since ULongs can exceed Int range)
    assertEquals(original.size, resultList.size)

    // Convert back to ULongs for comparison
    val resultULongs = resultList.map { value ->
      when (value) {
        is IotaValue.IntValue -> value.value.toULong()
        is IotaValue.BigIntValue -> {
          // Convert BigInteger back to ULong
          val bi = value.value
          if (bi < BigInteger.ZERO || bi > BigInteger(ULong.MAX_VALUE.toString())) {
            throw IllegalArgumentException("BigInteger out of ULong range")
          }
          bi.toString().toULong()
        }
        else -> throw IllegalArgumentException("Expected IntValue or BigIntValue")
      }
    }

    assertEquals(original, resultULongs)
  }

  @Test
  fun testULongListAllSmallValues() {
    // All values fit in Int - should be more efficient storage
    val original = listOf(0uL, 1uL, 100uL, 1000uL, Int.MAX_VALUE.toULong())

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values

    // All should be IntValues since they fit
    assertTrue(resultList.all { it is IotaValue.IntValue })

    val resultULongs = resultList.map { (it as IotaValue.IntValue).value.toULong() }
    assertEquals(original, resultULongs)
  }

  @Test
  fun testULongListMixedSizesIotaValue() {
    // Mix of small and large values
    val original = listOf(
      1uL,                    // fits in Int
      UInt.MAX_VALUE.toULong(),  // fits in Long but not Int
      ULong.MAX_VALUE,        // requires BigInteger
      100uL                   // fits in Int
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values

    assertEquals(original.size, resultList.size)

    // First should be IntValue, last two should be BigIntValue
    assertTrue(resultList[0] is IotaValue.IntValue)
    assertTrue(resultList[1] is IotaValue.BigIntValue)
    assertTrue(resultList[2] is IotaValue.BigIntValue)
    assertTrue(resultList[3] is IotaValue.IntValue)
  }

  @Test
  fun testSingleULongRoundTrip() {
    val original: ULong = 0x123456789ABCDEFuL

    // Convert to IotaValue (should become BigIntValue)
    val iotaValue = original.toIotaValue()
    assertTrue(iotaValue is IotaValue.BigIntValue)

    // Convert to Storage (should become WordArray)
    val storage = IotaObject.fromKotlin(iotaValue)
    assertEquals(StorageType.WORD_ARRAY.value, storage.t)
    assertTrue(storage.i is I.WordArray)

    // Convert back to IotaValue
    val result = IotaObject.toKotlin(storage)

    // Verify
    assertTrue(result is IotaValue.BigIntValue)
    assertEquals(BigInteger(original.toString()), (result as IotaValue.BigIntValue).value)
  }

  @Test
  fun testNestedULongListRoundTrip() {
    val original = listOf(
      listOf(1uL, 2uL, 3uL),
      listOf(ULong.MAX_VALUE, 100uL),
      listOf(0x123456789ABCDEFuL)
    )

    // Convert to IotaValue
    val iotaValue = original.toIotaValue()
    assertTrue(iotaValue is IotaValue.ListValue)

    // Convert to Storage (should be MixedArray of lists)
    val storage = IotaObject.fromKotlin(iotaValue)
    assertEquals(StorageType.MIXED_ARRAY.value, storage.t)
    assertTrue(storage.i is I.MixedArray)

    // Convert back to IotaValue
    val result = IotaObject.toKotlin(storage)

    // Verify structure
    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values
    assertEquals(3, resultList.size)

    // Each element should be a ListValue
    assertTrue(resultList.all { it is IotaValue.ListValue })

    // Convert back to List<List<ULong>> for comparison
    val resultNested = resultList.map { outerValue ->
      val innerList = (outerValue as IotaValue.ListValue).values
      innerList.map { innerValue ->
        when (innerValue) {
          is IotaValue.IntValue -> innerValue.value.toULong()
          is IotaValue.BigIntValue -> innerValue.value.toString().toULong()
          else -> throw IllegalArgumentException("Expected IntValue or BigIntValue")
        }
      }
    }

    assertEquals(original, resultNested)
  }

  @Test
  fun testNestedULongListAllSmall() {
    // All values fit in Int - inner lists should be WordArrays
    val original = listOf(
      listOf(1uL, 2uL, 3uL),
      listOf(10uL, 20uL),
      listOf(100uL)
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values

    // Verify each inner list contains only IntValues
    resultList.forEach { outerValue ->
      assertTrue(outerValue is IotaValue.ListValue)
      val innerList = (outerValue as IotaValue.ListValue).values
      assertTrue(innerList.all { it is IotaValue.IntValue })
    }
  }

  @Test
  fun testNestedULongListMixedSizes() {
    // Some inner lists have large values, some don't
    val original = listOf(
      listOf(1uL, 2uL, 3uL),                      // all small
      listOf(ULong.MAX_VALUE, 100uL),             // mixed
      listOf(1uL, 2uL),                           // all small
      listOf(0x123456789ABCDEFuL, 0xFEDCBA9876543210uL)  // all large
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values
    assertEquals(4, resultList.size)

    // First inner list: all IntValues
    val inner0 = ((resultList[0] as IotaValue.ListValue).values)
    assertTrue(inner0.all { it is IotaValue.IntValue })

    // Second inner list: mixed
    val inner1 = ((resultList[1] as IotaValue.ListValue).values)
    assertTrue(inner1[0] is IotaValue.BigIntValue)
    assertTrue(inner1[1] is IotaValue.IntValue)

    // Third inner list: all IntValues
    val inner2 = ((resultList[2] as IotaValue.ListValue).values)
    assertTrue(inner2.all { it is IotaValue.IntValue })

    // Fourth inner list: all BigIntValues
    val inner3 = ((resultList[3] as IotaValue.ListValue).values)
    assertTrue(inner3.all { it is IotaValue.BigIntValue })
  }

  @Test
  fun testEmptyNestedULongList() {
    val original = listOf<List<ULong>>(
      emptyList(),
      listOf(1uL, 2uL),
      emptyList()
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values
    assertEquals(3, resultList.size)

    // First and last should be empty lists
    assertTrue((resultList[0] as IotaValue.ListValue).values.isEmpty())
    assertTrue((resultList[2] as IotaValue.ListValue).values.isEmpty())

    // Middle should have values
    val middle = (resultList[1] as IotaValue.ListValue).values
    assertEquals(2, middle.size)
  }

  @Test
  fun testDeeplyNestedULongs() {
    // Test List<List<List<ULong>>>
    val original = listOf(
      listOf(
        listOf(1uL, 2uL),
        listOf(ULong.MAX_VALUE)
      ),
      listOf(
        listOf(100uL, 200uL, 300uL)
      )
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val level1 = (result as IotaValue.ListValue).values
    assertEquals(2, level1.size)

    // Verify structure is preserved
    assertTrue(level1.all { it is IotaValue.ListValue })
    val level2_0 = (level1[0] as IotaValue.ListValue).values
    assertTrue(level2_0.all { it is IotaValue.ListValue })
  }

  @Test
  fun testMixedArrayIntAndNestedULongList() {
    val original: Any = listOf(
      42,  // Int
      listOf(
        listOf(1uL, 2uL, 3uL),
        listOf(ULong.MAX_VALUE, 100uL)
      )
    )

    // Convert to IotaValue
    val iotaValue = original.toIotaValue()
    assertTrue(iotaValue is IotaValue.ListValue)

    // Convert to Storage (should be MixedArray)
    val storage = IotaObject.fromKotlin(iotaValue)
    assertEquals(StorageType.MIXED_ARRAY.value, storage.t)
    assertTrue(storage.i is I.MixedArray)

    // Convert back to IotaValue
    val result = IotaObject.toKotlin(storage)

    // Verify structure
    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values
    assertEquals(2, resultList.size)

    // First element should be IntValue
    assertTrue(resultList[0] is IotaValue.IntValue)
    assertEquals(42, (resultList[0] as IotaValue.IntValue).value)

    // Second element should be ListValue containing ListValues
    assertTrue(resultList[1] is IotaValue.ListValue)
    val nestedList = (resultList[1] as IotaValue.ListValue).values
    assertEquals(2, nestedList.size)
    assertTrue(nestedList.all { it is IotaValue.ListValue })

    // Verify the nested ULong values
    val innerList0 = (nestedList[0] as IotaValue.ListValue).values
    assertEquals(3, innerList0.size)
    assertEquals(1, (innerList0[0] as IotaValue.IntValue).value)
    assertEquals(2, (innerList0[1] as IotaValue.IntValue).value)
    assertEquals(3, (innerList0[2] as IotaValue.IntValue).value)

    val innerList1 = (nestedList[1] as IotaValue.ListValue).values
    assertEquals(2, innerList1.size)
    assertTrue(innerList1[0] is IotaValue.BigIntValue)
    assertEquals(BigInteger(ULong.MAX_VALUE.toString()), (innerList1[0] as IotaValue.BigIntValue).value)
    assertEquals(100, (innerList1[1] as IotaValue.IntValue).value)
  }

  @Test
  fun testMixedArrayWithMultipleTypes() {
    // More complex: Int, String, List<ULong>, List<List<ULong>>
    val original = listOf(
      42,
      "hello",
      listOf(1uL, 2uL, ULong.MAX_VALUE),
      listOf(
        listOf(100uL, 200uL),
        listOf(0x123456789ABCDEFuL)
      )
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values
    assertEquals(4, resultList.size)

    // Verify types
    assertTrue(resultList[0] is IotaValue.IntValue)
    assertTrue(resultList[1] is IotaValue.StringValue)
    assertTrue(resultList[2] is IotaValue.ListValue)
    assertTrue(resultList[3] is IotaValue.ListValue)

    // Verify values
    assertEquals(42, (resultList[0] as IotaValue.IntValue).value)
    assertEquals("hello", (resultList[1] as IotaValue.StringValue).value)
  }

  @Test
  fun testMixedArrayIntAndEmptyNestedList() {
    val original = listOf(
      42,
      listOf<List<ULong>>(
        emptyList(),
        listOf(1uL)
      )
    )

    val iotaValue = original.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)
    val result = IotaObject.toKotlin(storage)

    assertTrue(result is IotaValue.ListValue)
    val resultList = (result as IotaValue.ListValue).values
    assertEquals(2, resultList.size)

    assertTrue(resultList[0] is IotaValue.IntValue)
    assertEquals(42, (resultList[0] as IotaValue.IntValue).value)

    val nestedList = (resultList[1] as IotaValue.ListValue).values
    assertEquals(2, nestedList.size)  // Two inner lists

    // First inner list is empty
    assertTrue((nestedList[0] as IotaValue.ListValue).values.isEmpty())

    // Second inner list has one element
    val innerList1 = (nestedList[1] as IotaValue.ListValue).values
    assertEquals(1, innerList1.size)
    assertEquals(1, (innerList1[0] as IotaValue.IntValue).value)
  }

  @Test
  fun testMixedArrayUsingConvenienceFunction() {
    // Using the 'a' convenience function from your API
    val mixed = listOf(
      42,
      listOf(
        listOf(1uL, 2uL),
        listOf(ULong.MAX_VALUE)
      )
    )

    val iotaValue = mixed.toIotaValue()
    val storage = IotaObject.fromKotlin(iotaValue)

    // Should be a MixedArray
    assertEquals(StorageType.MIXED_ARRAY.value, storage.t)

    val result = IotaObject.toKotlin(storage)
    assertTrue(result is IotaValue.ListValue)

    val resultList = (result as IotaValue.ListValue).values
    assertEquals(42, (resultList[0] as IotaValue.IntValue).value)
  }
}