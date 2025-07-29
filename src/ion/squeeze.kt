package ion

import ion.Connection
import java.nio.ByteBuffer

fun squeeze_int(v: Int): ByteArray
{
  if (v == 0) return byteArrayOf(0)
  val neg = v < 0
  var pos = if (neg) -v else v
  val b = mutableListOf<Byte>()
  while (pos != 0)
  {
    b.add(0, (pos and 0xFF).toByte())
    pos = pos ushr 8
  }
  var len = b.size.toByte()
  if (neg) len = (len.toInt() or 0x80).toByte()
  return byteArrayOf(len) + b
}

fun squeeze_bigint(v: List<Int>): ByteArray
{
  val neg = v.first() != 0
  val remainingValues = v.drop(1)
  val bytes = mutableListOf<Byte>()

  for (signedInteger in remainingValues)
  {
    var unsignedInteger = signedInteger.toUInt()
    for (j in 0 until Int.SIZE_BYTES)
    {
      val b = (unsignedInteger and 0xFFu).toByte()
      bytes.add(0, b)
      unsignedInteger = unsignedInteger shr 8
    }
  }

  var len = bytes.size.toByte()
  if (neg) len = (len.toInt() or 0x80).toByte()
  return byteArrayOf(len) + bytes
}

fun expand_int(v: ByteArray): Pair<Varint, ByteArray>
{
  if (v.isEmpty()) return Pair(Varint.IonInt(0), byteArrayOf())
  var len = v[0].toUByte().toInt()
  if (len == 0) return Pair(Varint.IonInt(0), v.sliceArray(1 until v.size))
  val neg = (len and 0x80) != 0
  if (neg) len = len and 0x7F
  if (v.size < 1 + len) return Pair(Varint.IonInt(0), byteArrayOf())
  val data = v.sliceArray(1 until 1 + len)
  val rest = v.sliceArray(1 + len until v.size)
  var i = expand_int_from_bytes(data)
  if (neg) i = when (i)
  {
    is Varint.IonInt -> Varint.IonInt(-i.value)
    is Varint.IonInts -> Varint.IonInts(listOf(-i.value.first()) + i.value.drop(1))
  }
  return Pair(i, rest)
}

fun expand_conn(c: Connection): Varint
{
  var len = c.readOne().toUByte().toInt()
  if (len == 0) return Varint.IonInt(0)
  val neg = (len and 0x80) != 0
  if (neg) len = len and 0x7F
  var i = expand_int_from_bytes(c.read(len))
  return when (i)
  {
    is Varint.IonInt -> if (neg) Varint.IonInt(-i.value) else i
    is Varint.IonInts -> Varint.IonInts(listOf(if (neg) 1 else 0) + i.value)
  }
}

fun expand_int_from_bytes(b: ByteArray): Varint
{
  val ints = mutableListOf<Int>()
  for (c in 1..b.size)
  {
    if (c % Int.SIZE_BYTES == 1) ints.add(0, 0)
    for (i in ints.indices)
    {
      ints[i] = if (i == ints.size - 1)
        ((ints[i].toUInt() shl 8) or b[c - 1].toUByte().toUInt()).toInt()
      else
      {
        val cur = ints[i].toUInt()
        val next = ints[i + 1].toUInt()
        ((cur shl 8) or ((next shr 24) and 0xFFu)).toInt()
      }
    }
  }
  return if (ints.size == 1 && ints[0].toUInt() <= Int.MAX_VALUE.toUInt())
    Varint.IonInt(ints[0]) else Varint.IonInts(ints)
}

fun squeeze_floating(v: Floating): ByteArray = when (v)
{
  is Floating.IonFloat -> if (v.value == 0.0f) byteArrayOf(0) else
    byteArrayOf(4) + ByteBuffer.allocate(4).putFloat(v.value).array()
  is Floating.IonDouble -> if (v.value == 0.0) byteArrayOf(0) else
    byteArrayOf(8) + ByteBuffer.allocate(8).putDouble(v.value).array()
}

fun expand_floating(v: ByteArray): Floating?
{
  if (v.isEmpty()) return null
  val len = v[0].toUByte().toInt()
  if (len == 0) return Floating.IonFloat(0.0f)
  if (v.size < 1 + len) return null
  return when (len)
  {
    4 -> Floating.IonFloat(ByteBuffer.wrap(v, 1, 4).float)
    8 -> Floating.IonDouble(ByteBuffer.wrap(v, 1, 8).double)
    else -> null
  }
}

fun expand_conn_floating(c: Connection): Floating?
{
  val len = c.readOne().toUByte().toInt()
  if (len == 0) return Floating.IonFloat(0.0f)
  val b = c.read(len)
  return when (len)
  {
    4 -> Floating.IonFloat(ByteBuffer.wrap(b).float)
    8 -> Floating.IonDouble(ByteBuffer.wrap(b).double)
    else -> null
  }
}

fun squeeze_ints(v: List<Int>): ByteArray = if (v.isEmpty()) byteArrayOf(0) else
  squeeze_int(v.size).toList().plus(v.flatMap
  { it: Int -> squeeze_int(it).toList() }).toByteArray()

fun expand_ints(v: ByteArray): Pair<List<Int>, ByteArray>
{
  if (v.isEmpty()) return Pair(emptyList(), v)
  val (sz, rest1) = expand_int(v)
  return when (sz)
  {
    is Varint.IonInt ->
    {
      if (sz.value == 0) return Pair(emptyList(), rest1)
      var r = rest1
      val ints = mutableListOf<Int>()
      for (i in 0 until sz.value)
      {
        val (int, rest2) = expand_int(r)
        r = rest2
        when (int)
        {
          is Varint.IonInt -> ints.add(int.value)
          else -> return Pair(emptyList(), byteArrayOf())
        }
      }
      Pair(ints.toList(), r)
    }
    else -> Pair(emptyList(), byteArrayOf())
  }
}

fun squeeze_floats(v: List<Float>): ByteArray = if (v.isEmpty()) byteArrayOf(0) else
  squeeze_int(v.size).toList().plus(v.flatMap
  { it: Float -> squeeze_floating(Floating.IonFloat(it)).toList() }).toByteArray()