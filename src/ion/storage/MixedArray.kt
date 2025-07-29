package ion.storage

import ion.*

object MixedArray
{
  // Storage::from_bytes decodes a byte array into a MixedArray object
  fun from_bytes(data: ByteArray, o: Int): Storage?
  {
    return Storage(o, StorageType.WORD.value, I.Word(0))
  }

  // Encodes a MixedArray into a byte array
  // Format: {((size x) squeeze) join x} (i each {x to_bytes} over join)
  fun to_bytes(storage: Storage): ByteArray?
  {
    return when(storage.t)
    {
      StorageType.MIXED_ARRAY.value ->
      {
        val data: I = storage.i
        return when(data)
        {
          is I.MixedArray ->
          {
            // Assuming I.MixedArray.value is List<Storage>
            // If it's List<I>, you'll need to convert each I to Storage
            val ii = data.value
            val result = mutableListOf<Byte>()

            val size = ii.size
            val sizeBytes = squeeze_int(size)

            result.addAll(sizeBytes.toList())

            for(y in ii)
            {
              val valueBytes = NOUN_TO_BYTES(y)
              result.addAll(valueBytes.toList())
            }

            return result.toByteArray()
          }

          else -> null
        }
      }

      else -> null
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
        val i = mutableListOf<Storage>()

        for(y in 0 until size)
        {
          val maybeStorage = NOUN_FROM_CONN(conn)
          if(maybeStorage != null)
          {
            i.add(maybeStorage)
          }
          else
          {
            return null
          }
        }

        return make(i, objectType)
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
    when(i.t)
    {
      StorageType.MIXED_ARRAY.value ->
      {
        val data: I = i.i
        when(data)
        {
          is I.MixedArray ->
          {
            val ms = data.value

            // Always include type in to_conn implementation
            val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
            conn.write(typeBytes)

            val length = ms.size
            val lengthBytes = squeeze_int(length)

            conn.write(lengthBytes)

            for(y in ms)
            {
              NOUN_TO_CONN(conn, y)
            }
          }
          else -> {
            // Handle other cases if needed
          }
        }
      }
      else -> {
        // Handle other cases if needed
      }
    }
  }

  fun make(x: List<Storage>, o: Int): Storage
  {
    return Storage(o, StorageType.MIXED_ARRAY.value, I.MixedArray(x))
  }

  fun default_noun_to_bytes(x: Storage): ByteArray
  {
    return when(x.t)
    {
      StorageType.WORD.value ->
        Word.to_bytes(x)

      StorageType.FLOAT.value ->
      {
        val maybeValueBytes = IonFloat.to_bytes(x)
        maybeValueBytes ?: byteArrayOf()
      }

      StorageType.WORD_ARRAY.value ->
        WordArray.to_bytes(x)

      StorageType.FLOAT_ARRAY.value ->
        FloatArray.to_bytes(x)

      StorageType.MIXED_ARRAY.value ->
      {
        val maybeValueBytes = this.to_bytes(x)  // Use 'this' to be explicit
        maybeValueBytes ?: byteArrayOf()
      }

      else -> byteArrayOf()
    }
  }

  fun default_noun_from_conn(conn: Connection): Storage?
  {
    val storageType = conn.readOne().toUByte().toInt()
    val objectType = conn.readOne().toUByte().toInt()

    return when(storageType)
    {
      StorageType.WORD.value ->
        Word.from_conn(conn, objectType)

      StorageType.FLOAT.value ->
        IonFloat.from_conn(conn, objectType)

      StorageType.WORD_ARRAY.value ->
        WordArray.from_conn(conn, objectType)

      StorageType.FLOAT_ARRAY.value ->
        FloatArray.from_conn(conn, objectType)

      StorageType.MIXED_ARRAY.value ->
        MixedArray.from_conn(conn, objectType)

      else -> null
    }
  }

  fun default_noun_to_conn(conn: Connection, x: Storage)
  {
    when(x.t)
    {
      StorageType.WORD.value ->
        Word.to_conn(conn, x)

      StorageType.FLOAT.value ->
        IonFloat.to_conn(conn, x)

      StorageType.WORD_ARRAY.value ->
        WordArray.to_conn(conn, x)

      StorageType.FLOAT_ARRAY.value ->
        FloatArray.to_conn(conn, x)

      StorageType.MIXED_ARRAY.value ->
        MixedArray.to_conn(conn, x)

      else -> return
    }
  }

  // Placeholder functions that represent the #define macros
  // These can be overridden by another library
  fun NOUN_TO_BYTES(i: Storage): ByteArray = default_noun_to_bytes(i)
  fun NOUN_FROM_CONN(conn: Connection): Storage? = default_noun_from_conn(conn)
  fun NOUN_TO_CONN(conn: Connection, i: Storage) = default_noun_to_conn(conn, i)
}