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