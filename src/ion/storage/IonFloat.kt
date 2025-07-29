package ion.storage

import ion.Floating
import ion.expand_floating
import ion.squeeze_floating
import ion.expand_conn_floating
import ion.Connection

object IonFloat
{
  const val tolerance = 1e-14f // for match
  const val precision = 6 // for format

  // Storage::from_bytes decodes a byte array into a Float object
  // Be careful with this function, it moves x.
  fun from_bytes(x: ByteArray, o: Int): Storage?
  {
    val maybeFloating = expand_floating(x)
    if(maybeFloating != null)
    {
      val f = when(maybeFloating)
      {
        is Floating.IonFloat -> maybeFloating.value
        is Floating.IonDouble -> maybeFloating.value.toFloat()
      }

      return make(f, o)
    }
    else
    {
      return null
    }
  }

  // Encodes a Float into a byte array
  // Format: IEEE Float
  fun to_bytes(i: Storage): ByteArray?
  {
    return when(i.t)
    {
      StorageType.FLOAT.value ->
      {
        val data: I = i.i
        return when(data)
        {
          is I.IonFloat ->
          {
            val f = data.value
            squeeze_floating(Floating.IonFloat(f))
          }
          else -> null
        }
      }
      else -> null
    }
  }

  fun from_conn(conn: Connection, objectType: Int): Storage?
  {
    val maybeFloating = expand_conn_floating(conn)
    if(maybeFloating != null)
    {
      val f = when(maybeFloating)
      {
        is Floating.IonFloat -> maybeFloating.value
        is Floating.IonDouble -> maybeFloating.value.toFloat()
      }

      return make(f, objectType)
    }
    else
    {
      return null
    }
  }

  fun to_conn(conn: Connection, i: Storage)
  {
    when(i.t)
    {
      StorageType.FLOAT.value ->
      {
        val data: I = i.i
        when(data)
        {
          is I.IonFloat ->
          {
            // Always include type in to_conn implementation
            val typeBytes = byteArrayOf(i.t.toByte(), i.o.toByte())
            conn.write(typeBytes)

            val f = data.value
            val result = squeeze_floating(Floating.IonFloat(f))
            conn.write(result)
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

  fun make(x: kotlin.Float, o: Int = NounType.REAL.value): Storage
  {
    return Storage(o, StorageType.FLOAT.value, I.IonFloat(x))
  }
}