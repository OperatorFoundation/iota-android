package ion.storage

enum class StorageType(val value: Int)
{
  WORD(0),
  FLOAT(1),
  WORD_ARRAY(2),
  FLOAT_ARRAY(3),
  MIXED_ARRAY(4),
  ANY(255)
}

enum class NounType(val value: Int)
{
  INTEGER(0),
  REAL(1),
  CHARACTER(2),
  STRING(3),
  LIST(4),
  DICTIONARY(5),
  BUILTIN_SYMBOL(6),
  BUILTIN_MONAD(7),
  BUILTIN_DYAD(8),
  BUILTIN_TRIAD(9),
  MONADIC_ADVERB(10),
  DYADIC_ADVERB(11),
  USER_SYMBOL(12),
  USER_MONAD(13),
  USER_DYAD(14),
  USER_TRIAD(15),
  ERROR(16),
  EXPRESSION(17),
  TYPE(18),
  CONDITIONAL(19),
  QUOTED_SYMBOL(20),
  EFFECT_TYPE(21),
  RESOURCE(22),
  CONTINGENCY(23),
  SIGNAL(24),
  SEQUENCE(25),
  DEPENDENCY(26),
  EFFECT_EXPRESSION(27),
  LENS(28),
  EFFECT_CHAIN(29),
  NILADIC_EFFECT(30),
  MONADIC_EFFECT(31),
  DYADIC_EFFECT(32),
  CONJUNCTION(33),
  ANY(255)
}

enum class SymbolType(val value: Int)
{
  i(0),
  x(1),
  y(2),
  z(3),
  f(4),
  causing(5),
  undefined(6)
}

typealias mixed = List<Storage>

sealed class I
{
  data class Word(val value: Int) : I()
  data class IonFloat(val value: Float) : I()
  data class WordArray(val value: List<Int>) : I()
  data class FloatArray(val value: List<Float>) : I()
  data class MixedArray(val value: List<Storage>) : I()
}

data class Storage(val o: Int, val t: Int, val i: I)
{
  data class Storage(val o: Int, val t: Int, val i: I) {

    override fun toString(): String
    {
      return when (i)
      {
        is I.Word -> "WORD:$t:${i.value}"

        is I.IonFloat -> "FLOAT:$t:${"%.1f".format(i.value)}"

        is I.WordArray ->
        {
          val elements = i.value.joinToString(", ")
          "WORDS:$t:[$elements]"
        }

        is I.FloatArray ->
        {
          val elements = i.value.joinToString(", ") { "%.1f".format(it) }
          "FLOATS:$t:[$elements]"
        }

        is I.MixedArray ->
        {
          val elements = i.value.joinToString(", ") { it.toString() }
          "MIXED:$t:[$elements]"
        }
      }
    }
  }
}