package ion

sealed class Varint
{
  data class IonInt(val value: Int) : Varint()
  data class IonInts(val value: List<Int>) : Varint()
}

sealed class Floating
{
  data class IonFloat(val value: Float) : Floating()
  data class IonDouble(val value: Double) : Floating()
}