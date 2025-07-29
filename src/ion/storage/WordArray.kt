package ion.storage

import ion.Varint
import ion.expand_int_from_bytes
import ion.squeeze_int
import ion.expand_conn
import ion.Connection

object WordArray
{
  fun nil(): Storage
  {
    return make(emptyList(), NounType.LIST.value)
  }

  fun nil1(i: Storage): Storage
  {
    return make(emptyList(), NounType.LIST.value)
  }

  fun nil2(i: Storage, x: Storage): Storage
  {
    return make(emptyList(), NounType.LIST.value)
  }

  // Storage::from_bytes decodes a byte array into a WordArray object
  fun from_bytes(data: ByteArray, o: Int): Storage?
  {
    // FIXME
    return Storage(0, StorageType.WORD.value, I.Word(0))
  }

  // Encodes a WordArray into a byte array
  // Format: {((size x) squeeze) join x} (i each {x squeeze} over join)
  fun to_bytes(storage: Storage): ByteArray
  {
    return when(storage.t)
    {
      StorageType.WORD_ARRAY.value ->
      {
        val result = mutableListOf<Byte>()

        val i: I = storage.i
        return when(i)
        {
          is I.WordArray ->
          {
            val length = i.value.size
            val lengthBytes = squeeze_int(length)

            result.addAll(lengthBytes.toList())

            for(integer in i.value)
            {
              val integerBytes = squeeze_int(integer)
              result.addAll(integerBytes.toList())
            }

            return result.toByteArray()
          }

          else -> byteArrayOf()
        }
      }

      else -> byteArrayOf()
    }
  }

  fun from_conn(conn: Connection, objectType: Int): Storage?
  {
    val varsize = expand_conn(conn)
    return when(varsize)
    {
      is Varint.IonInt ->
      {
        val size = varsize.value
        val integers = mutableListOf<Int>()

        for(y in 0 until size)
        {
          val varinteger = expand_conn(conn)
          when(varinteger)
          {
            is Varint.IonInt ->
            {
              val integer = varinteger.value
              integers.add(integer)
            }
            is Varint.IonInts ->
            {
              // Varint elements in Word arrays not yet supported
              return null
            }
          }
        }

        make(integers, objectType)
      }
      is Varint.IonInts ->
      {
        // Varint sizes not yet fully implemented
        null
      }
    }
  }

  fun to_conn(conn: Connection, i: Storage)
  {
    when(i.i)
    {
      is I.WordArray ->
      {
        // Always include type in to_conn implementation
        val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
        conn.write(typeBytes)

        val integers = i.i.value

        val length = integers.size
        val lengthBytes = squeeze_int(length)

        conn.write(lengthBytes)

        for(integer in integers)
        {
          val integerBytes = squeeze_int(integer)
          conn.write(integerBytes)
        }
      }
      else ->
      {
        // Handle other cases if needed
      }
    }
  }

  fun make(x: List<Int>, o: Int): Storage
  {
    return Storage(o, StorageType.WORD_ARRAY.value, I.WordArray(x))
  }
}