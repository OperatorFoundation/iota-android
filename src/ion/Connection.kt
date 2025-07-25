package ion

interface Connection
{
  fun readOne(): Byte
  fun read(length: Int): ByteArray
}