package ion.storage

import ion.*

object FloatArray
{
  fun from_bytes(data: ByteArray, o: Int): Storage?
  {
    // FIXME
    return null
  }

  // Encodes a FloatArray into a byte array
  // Format: {((size x) squeeze) join x} (i each {x IEEE<32|64>} over join)
  fun to_bytes(i: Storage): ByteArray
  {
    return when(i.t)
    {
      StorageType.FLOAT_ARRAY.value ->
      {
        val result = mutableListOf<Byte>()

        val data: I = i.i
        return when(data)
        {
          is I.FloatArray ->
          {
            val fs = data.value

            val length = fs.size
            val lengthBytes = squeeze_int(length)

            result.addAll(lengthBytes.toList())

            for(f in fs)
            {
              val floatBytes = squeeze_floating(Floating.IonFloat(f))
              result.addAll(floatBytes.toList())
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
        val fs = mutableListOf<Float>()

        for(y in 0 until size)
        {
          val maybeFloating = expand_conn_floating(conn)
          if(maybeFloating != null)
          {
            val f = when(maybeFloating)
            {
              is Floating.IonFloat -> maybeFloating.value
              is Floating.IonDouble -> maybeFloating.value.toFloat()
            }
            fs.add(f)
          }
          else
          {
            return null
          }
        }

        return make(fs, objectType)
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
      StorageType.FLOAT_ARRAY.value ->
      {
        val data: I = i.i
        when(data)
        {
          is I.FloatArray ->
          {
            val floats = data.value

            // Always include type in to_conn implementation
            val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
            conn.write(typeBytes)

            val length = floats.size
            val lengthBytes = squeeze_int(length)

            conn.write(lengthBytes)

            for(f in floats)
            {
              val floatBytes = squeeze_floating(Floating.IonFloat(f))
              conn.write(floatBytes)
            }
          }
          else ->
          {
          }
        }
      }
      else ->
      {
      }
    }
  }

  fun make(x: List<Float>, o: Int): Storage
  {
    return Storage(o, StorageType.FLOAT_ARRAY.value, I.FloatArray(x))
  }
}