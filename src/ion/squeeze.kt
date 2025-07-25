import ion.Connection
import java.nio.ByteBuffer

// Encode an integer in the fewest number of bytes, plus a 1-byte length to tell us how many bytes that is.
fun squeeze_int(value: Int): ByteArray
{
  // The only case where the length can be zero is for the number 0.
  if (value == 0)
  {
    return byteArrayOf(0)
  }

  // Remember if the integer was positive or negative. If negative, we will make the length negative later.
  // Regardless, we end up with a positive integer for encoding.
  // Positive integers are more straightforward to encode.
  val negative = value < 0
  var positiveValue = if (negative) -value else value

  // Determine how many bytes we need to encode the integer by removing zero bytes from the top end.
  // Note that this algorithm does not care about endianness and accommodates any word size.
  val bytes = mutableListOf<Byte>()

  while (positiveValue != 0)
  {
    // Get each byte of the integer separately so that we can count how many bytes we need.
    bytes.add(0, (positiveValue and 0xFF).toByte())
    positiveValue = positiveValue ushr 8
  }

  var length = bytes.size.toByte()

  // If the integer is negative, we make the length byte negative as well
  // The length byte goes at the beginning of the encoding
  if (negative)
  {
    length = (length.toInt() or 0x80).toByte()
  }

  // Create result array with length byte at the beginning
  val result = ByteArray(bytes.size + 1)
  result[0] = length
  for (i in bytes.indices)
  {
    result[i + 1] = bytes[i]
  }

  return result
}

// Encode a big integer (represented as List<Int>) in bytes, plus a 1-byte length
fun squeeze_bigint(value: List<Int>): ByteArray
{
  // Remember if the integer was positive or negative. If negative, we will make the length negative later.
  // Regardless, we end up with a positive integer for encoding.
  // Positive integers are more straightforward to encode.
  val negative = value.first() != 0
  val remainingValue = value.drop(1) // Remove first element (equivalent to erase(begin()))

  val result = mutableListOf<Byte>()

  for(signedInteger in remainingValue)
  {
    var unsignedInteger = signedInteger.toUInt()

    val integerBytes = mutableListOf<Byte>()

    // Process all bytes of the integer (4 bytes for Int)
    repeat(Int.SIZE_BYTES)
    {
      // Get each byte of the integer separately, encoding all bytes, including zeros
      integerBytes.add(0, (unsignedInteger and 0xFFu).toByte())
      unsignedInteger = unsignedInteger shr 8
    }

    result.addAll(integerBytes)
  }

  var length = result.size.toByte()

  // If the integer is negative, we make the length byte negative as well
  // The length byte goes at the beginning of the encoding
  if(negative)
  {
    length = (length.toInt() or 0x80).toByte()
  }

  // Create final result with length byte at the beginning
  val finalResult = ByteArray(result.size + 1)
  finalResult[0] = length
  for(i in result.indices)
  {
    finalResult[i + 1] = result[i]
  }

  return finalResult
}


// Convert a Varint to bytes using appropriate squeeze function
//fun squeezeVarint(value: Varint): ByteArray
//{
//  return when (value)
//  {
//    is Varint.IonInt ->
//    {
//      val integer = value.value
//      squeeze_int(integer)
//    }
//
//    is Varint.IonInts ->
//    {
//      val integers = value.value
//      squeeze_ints(integers)  // Assuming you have this function
//    }
//  }
//}

// Expand a byte array into a Varint and remaining bytes
fun expand_int(value: ByteArray): Pair<Varint, ByteArray>
{
  // If input array is empty, we have nothing to parse
  if(value.isEmpty())
  {
    return Pair(Varint.IonInt(0), byteArrayOf())
  }

  // The first byte of the input array is the length at the beginning of the array 
  // with the integer for us to parse, the rest of the array is extra
  var integerLength = value.first().toUByte().toInt()

  // The only case where the length can be zero is for the number 0.
  if(integerLength == 0)
  {
    val rest = value.sliceArray(1 until value.size)
    return Pair(Varint.IonInt(0), rest)
  }

  // We encode if the integer is positive or negative by using a positive or negative length
  val negative = (integerLength and 0x80) != 0 // Check the sign bit & 0b10000000
  if(negative)
  {
    integerLength = integerLength and 0x7F // Clear the sign bit & 0b01111111
  }

  if(value.size < 1 + integerLength)
  {
    return Pair(Varint.IonInt(0), byteArrayOf())
  }

  val integerData = value.sliceArray(1 until 1 + integerLength)
  val rest = value.sliceArray(1 + integerLength until value.size)

  var i = expand_int_from_bytes(integerData)

  if(negative)
  {
    i = when (i)
    {
      is Varint.IonInt -> 
      {
        val integer = i.value
        Varint.IonInt(-integer)
      }
      is Varint.IonInts -> 
      {
        val integers = i.value.toMutableList()
        integers[0] = -integers[0]
        Varint.IonInts(integers)
      }
    }
  }

  return Pair(i, rest)
}

// Expand a varint from a Connection
fun expand_conn(conn: Connection): Varint
{
  var length = conn.readOne().toUByte().toInt()

  if(length == 0)
  {
    return Varint.IonInt(0)
  }

  val negative = (length and 0x80) != 0
  if(length and 0x80 != 0)
  {
    length = length and 0x7F
  }

  val integerBytes = conn.read(length)

  var i = expand_int_from_bytes(integerBytes)

  when(i)
  {
    is Varint.IonInt ->
    {
      if(negative)
      {
        val integer = i.value
        i = Varint.IonInt(-integer)
      }
    }
    is Varint.IonInts ->
    {
      val integers = i.value.toMutableList()

      if(negative)
      {
        integers.add(0, 1)
      }
      else
      {
        integers.add(0, 0)
      }

      i = Varint.IonInts(integers)
    }
  }

  return i
}

// Expand bytes into a Varint (inverse of squeeze operations)
fun expand_int_from_bytes(bytes: ByteArray): Varint
{
  // We have to use an unsigned int here because in the case that we end up with a bigint, 
  // all the limbs should be unsigned. If we use a (signed) int, we will lose the top bit.
  val integers = mutableListOf<Int>()

  for(count in 1..bytes.size)
  {
    // We ran out of bytes in the queued integers
    if(count % Int.SIZE_BYTES == 1)
    {
      integers.add(0, 0)
    }

    // Shift all the integers one byte left
    for(index in 0 until integers.size)
    {
      if(index == integers.size - 1)
      {
        integers[index] = ((integers[index].toUInt() shl 8) or bytes[count - 1].toUByte().toUInt()).toInt()
      }
      else
      {
        val current = integers[index].toUInt()
        val next = integers[index + 1].toUInt()

        val shift = (Int.SIZE_BYTES - 1) * 8
        val nextHighByte = (next shr shift) and 0xFFu

        integers[index] = ((current shl 8) or nextHighByte).toInt()
      }
    }
  }

  // In this special case, we might have a valid max size (signed) int, or we might have 
  // a max size unsigned int that is too big to fit into a signed int because of the sign bit.
  if(integers.size == 1)
  {
    val unsignedInteger = integers[0].toUInt()

    if(unsignedInteger <= Int.MAX_VALUE.toUInt())
    {
      return Varint.IonInt(unsignedInteger.toInt())
    }
  }

  return Varint.IonInts(integers)
}

// Serialize floating point numbers as big endian byte arrays
fun squeeze_floating(value: Floating): ByteArray
{
  return when(value)
  {
    is Floating.IonFloat ->
    {
      val f = value.value

      if(f == 0.0f)
      {
        byteArrayOf(0)
      }
      else
      {
        val result = mutableListOf<Byte>()
        result.add(Float.SIZE_BYTES.toByte()) // 4 bytes for float

        val buffer = ByteBuffer.allocate(Float.SIZE_BYTES)
        buffer.putFloat(f) // Uses big endian by default
        val floatBytes = buffer.array()

        result.addAll(floatBytes.toList())
        result.toByteArray()
      }
    }
    is Floating.IonDouble ->
    {
      val d = value.value

      if(d == 0.0)
      {
        byteArrayOf(0)
      }
      else
      {
        val result = mutableListOf<Byte>()
        result.add(Double.SIZE_BYTES.toByte()) // 8 bytes for double

        val buffer = ByteBuffer.allocate(Double.SIZE_BYTES)
        buffer.putDouble(d) // Uses big endian by default
        val doubleBytes = buffer.array()

        result.addAll(doubleBytes.toList())
        result.toByteArray()
      }
    }
  }
}

