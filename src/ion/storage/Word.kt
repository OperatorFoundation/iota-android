package ion.storage

import ion.Varint
import ion.expand_int_from_bytes
import ion.squeeze_int
import ion.expand_conn
import ion.Connection

object Word
{
  // Storage::from_bytes decodes a byte array into a Word object
  fun from_bytes(data: ByteArray, o: Int): Storage?
  {
    val varinteger = expand_int_from_bytes(data)
    return when(varinteger)
    {
      is Varint.IonInt ->
      {
        val integer = varinteger.value
        make(integer, o)
      }
      is Varint.IonInts ->
      {
        // varint results for Words are not yet fully implemented
        null
      }
    }
  }

  // Encodes a Word into a byte array
  // Format: int:i.squeeze
  fun to_bytes(i: Storage): ByteArray
  {
    return when(i.i)
    {
      is I.Word ->
      {
        val integer = i.i.value
        squeeze_int(integer)
      }
      else -> byteArrayOf()
    }
  }

  fun from_conn(conn: Connection, objectType: Int): Storage?
  {
    val varinteger = expand_conn(conn)

    return when(varinteger)
    {
      is Varint.IonInt ->
      {
        val integer = varinteger.value
        make(integer, objectType)
      }
      is Varint.IonInts ->
      {
        val integers = varinteger.value
        WordArray.make(integers, objectType)
      }
    }
  }

  fun to_conn(conn: Connection, i: Storage)
  {
    when(i.i)
    {
      is I.Word ->
      {
        // Always include type in to_conn implementation
        val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
        conn.write(typeBytes)

        val integer = i.i.value
        val result = squeeze_int(integer)

        conn.write(result)
      }
      else -> {
        // Handle other cases if needed
      }
    }
  }

  fun make(x: Int, o: Int): Storage
  {
    return Storage(o, StorageType.WORD.value, I.Word(x))
  }
}