package org.operatorfoundation.iota

import kotlin.math.abs

import org.operatorfoundation.ion.storage.StorageType
import org.operatorfoundation.ion.storage.SymbolType
import org.operatorfoundation.iota.nouns.Real
import org.operatorfoundation.iota.nouns.Integer
import org.operatorfoundation.iota.nouns.IotaString
import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.Word
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.FloatArray
import org.operatorfoundation.ion.storage.I
import org.operatorfoundation.ion.storage.MixedArray
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.iota.nouns.ErrorCode
import org.operatorfoundation.iota.nouns.Character
import org.operatorfoundation.iota.nouns.Expression
import java.math.BigInteger

sealed class IotaValue {
    data class IntValue(val value: Int) : IotaValue()
    data class BigIntValue(val value: BigInteger) : IotaValue()
    data class FloatValue(val value: Float) : IotaValue()
    data class CharValue(val value: Char) : IotaValue()
    data class StringValue(val value: String) : IotaValue()
    data class ListValue(val values: List<IotaValue>) : IotaValue()
    data class ErrorValue(val error: Error) : IotaValue()
    data class StorageValue(val storage: Storage) : IotaValue()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IotaValue) return false
        
        return when (this) {
            is IntValue -> other is IntValue && value == other.value
            is BigIntValue -> other is BigIntValue && value == other.value
            is FloatValue -> other is FloatValue && 
                abs(value - other.value) < Float.MIN_VALUE // Similar to Float::precision
            is CharValue -> other is CharValue && value == other.value
            is StringValue -> other is StringValue && value == other.value
            is ListValue -> other is ListValue && values == other.values
            is ErrorValue -> other is ErrorValue && error == other.error
            is StorageValue -> other is StorageValue && storage == other.storage
        }
    }

    override fun hashCode(): Int = when (this) {
        is IntValue -> value.hashCode()
        is BigIntValue -> value.hashCode()
        is FloatValue -> value.hashCode()
        is CharValue -> value.hashCode()
        is StringValue -> value.hashCode()
        is ListValue -> values.hashCode()
        is ErrorValue -> error.hashCode()
        is StorageValue -> storage.hashCode()
    }
}

typealias IotaValues = List<IotaValue>

// Convenience for creating nested arrays - matches C++ 'a' alias
fun a(vararg values: Any?): Array<Any?> = arrayOf(*values)

val nil = emptyList<IotaValue>()

// API.kt - Main API

object KotlinValue {
    val t: IotaValue = IotaValue.IntValue(1)
    val f: IotaValue = IotaValue.IntValue(0)

    fun allInts(values: IotaValues): Boolean =
        values.all { it is IotaValue.IntValue }

    fun allFloats(values: IotaValues): Boolean =
        values.all { it is IotaValue.FloatValue }
}

// Conversion from Kotlin types to IotaValue
fun Any?.toIotaValue(): IotaValue = when (this) {
    is Int -> IotaValue.IntValue(this)
    is Long -> {
        if (this in Int.MIN_VALUE..Int.MAX_VALUE) {
            IotaValue.IntValue(this.toInt())
        } else {
            IotaValue.BigIntValue(BigInteger.valueOf(this))
        }
    }
    is ULong -> {
        if (this <= Int.MAX_VALUE.toULong()) {
            IotaValue.IntValue(this.toInt())
        } else {
            IotaValue.BigIntValue(BigInteger(this.toString()))
        }
    }
    is BigInteger -> {
        if (this >= BigInteger.valueOf(Int.MIN_VALUE.toLong()) &&
            this <= BigInteger.valueOf(Int.MAX_VALUE.toLong())) {
            IotaValue.IntValue(this.toInt())
        } else {
            IotaValue.BigIntValue(this)
        }
    }
    is Float -> IotaValue.FloatValue(this)
    is Double -> IotaValue.FloatValue(this.toFloat())
    is Char -> IotaValue.CharValue(this)
    is String -> IotaValue.StringValue(this)
    is Array<*> -> IotaValue.ListValue(this.map { it.toIotaValue() })
    is List<*> -> IotaValue.ListValue(this.map { it.toIotaValue() })
    is IotaValue -> this
    is Storage -> IotaValue.StorageValue(this)
    null -> IotaValue.ListValue(emptyList())
    else -> throw IllegalArgumentException("Cannot convert $this to IotaValue")
}

// Object conversion functions
object IotaObject {
    fun fromKotlinExpression(values: IotaValues): Storage {
        val results = values.map { fromKotlin(it) }
        return MixedArray.make(results, NounType.EXPRESSION.value)
    }

    fun fromKotlin(value: IotaValue): Storage = when (value) {
        is IotaValue.IntValue -> Integer.make(value.value)
        is IotaValue.BigIntValue -> Integer.make(value.value)
        
        is IotaValue.FloatValue -> Real.make(value.value)
        
        is IotaValue.ListValue -> {
            val values = value.values
            when {
                KotlinValue.allInts(values) -> {
                    val ints = values.map { (it as IotaValue.IntValue).value }
                    WordArray.make(ints, NounType.LIST.value)
                }
                KotlinValue.allFloats(values) -> {
                    val floats = values.map { (it as IotaValue.FloatValue).value }
                    FloatArray.make(floats, NounType.LIST.value)
                }
                else -> {
                    val mixed = values.map { fromKotlin(it) }
                    MixedArray.make(mixed, NounType.LIST.value)
                }
            }
        }
        
        is IotaValue.CharValue -> {
            // FIXME - This only works for ASCII, fix it to work with Unicode.
            Character.make(value.value)
        }
        
        is IotaValue.StringValue -> {
            // FIXME - This only works for ASCII, fix it to work with Unicode.
            val integers = value.value.map { it.code }
            IotaString.make(integers)
        }
        
        is IotaValue.StorageValue -> value.storage
        
        is IotaValue.ErrorValue -> 
            Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR.value)
    }

    fun toKotlin(storage: Storage): IotaValue
    {
        when (storage.o) {
            NounType.INTEGER.value -> {
                when (val ii = storage.i) {
                    is I.Word -> return IotaValue.IntValue(ii.value)
                    is I.WordArray -> return IotaValue.BigIntValue(Integer.toBigInteger(storage))
                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.REAL.value -> {
                when (val ii = storage.i) {
                    is I.IonFloat -> return IotaValue.FloatValue(ii.value)
                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.LIST.value -> {
                when (storage.t) {
                    StorageType.WORD_ARRAY.value -> {
                        when (val ii = storage.i) {
                            is I.WordArray -> return IotaValue.ListValue(ii.value.map { IotaValue.IntValue(it) })
                            else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                        }
                    }

                    StorageType.FLOAT_ARRAY.value -> {
                        when (val ii = storage.i) {
                            is I.FloatArray -> return IotaValue.ListValue(ii.value.map { IotaValue.FloatValue(it) })
                            else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                        }
                    }

                    StorageType.MIXED_ARRAY.value -> {
                        when (val ii = storage.i) {
                            is I.MixedArray -> return IotaValue.ListValue(ii.value.map { toKotlin(it) })
                            else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                        }
                    }

                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.CHARACTER.value -> {
                when (val ii = storage.i)
                {
                    is I.Word -> return IotaValue.CharValue(ii.value.toChar())
                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.STRING.value -> {
                when (val ii = storage.i) {
                    is I.WordArray ->
                    {
                        val string = String(ii.value.toIntArray(), 0, ii.value.size)
                        return IotaValue.StringValue(string)
                    }
                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.BUILTIN_SYMBOL.value -> {
                when(val ii = storage.i) {
                    is I.Word -> {
                        when(ii.value) {
                            SymbolType.x.value -> return IotaValue.StringValue(":x")
                            SymbolType.y.value -> return IotaValue.StringValue(":y")
                            SymbolType.z.value -> return IotaValue.StringValue(":z")
                            SymbolType.f.value -> return IotaValue.StringValue(":f")
                            SymbolType.undefined.value -> return IotaValue.StringValue(":undefined")
                            else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                        }
                    }
                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.EXPRESSION.value -> {
                when(val ii = storage.i) {
                    is I.MixedArray -> {
                        return IotaValue.ListValue(ii.value.map { toKotlin(it) } )
                    }
                    else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
                }
            }

            NounType.ERROR.value -> {
                return IotaValue.StringValue(errorToString(storage))
            }

            else -> return IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
        }
    }
}

// Evaluation functions
fun evalExpression(evalRegister: EvalRegister, vararg values: Any?): IotaValue {
    val converted = values.map { it.toIotaValue() }
    val storage = IotaObject.fromKotlinExpression(converted)
    val result = evalRegister.eval(storage)
    return result?.let { IotaObject.toKotlin(it) }
        ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT.toString()))
}

fun evalExpression(evalRegister: EvalRegister, storage: Storage): Storage {
    return evalRegister.eval(storage)
        ?: Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR.value)
}

fun evalNoun(evalRegister: EvalRegister, value: IotaValue): IotaValue {
    val storage = IotaObject.fromKotlin(value)
    val result = evalRegister.eval(storage)
    return result?.let { IotaObject.toKotlin(it) }
        ?: IotaValue.StringValue("Error: unsupported object")
}

fun eval(evalRegister: EvalRegister, expression: List<Storage>): Storage {
    val result = evalRegister.eval(Expression.make(expression))
    return result ?: Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR.value)
}

// Error handling
fun testError(): Storage =
    Word.make(ErrorCode.TEST_ERROR, NounType.ERROR.value)

fun errorToString(error: Storage): String {
    if (error.o != NounType.ERROR.value) return "Unknown error"
    if (error.t != StorageType.WORD.value) return "Unknown error"

    when(val ii = error.i) {
        is I.Word -> {
            return codeToString(ii.value)
        }
        else -> return "Unknown error"
    }
}

fun codeToString(code: Int): String = when (code) {
    ErrorCode.BAD_INDEX_TYPE -> "unsupported index type"
    ErrorCode.BAD_INITIALIZATION -> "bad initialization value"
    ErrorCode.BAD_STORAGE -> "this object type does not support this storage type"
    ErrorCode.BAD_OPERATION -> "this operation is not supported by this object type with this storage type"
    ErrorCode.EMPTY -> "empty"
    ErrorCode.INVALID_ARGUMENT -> "invalid argument type"
    ErrorCode.INVALID_ADVERB_ARGUMENT -> "invalid adverb argument"
    ErrorCode.OUT_OF_BOUNDS -> "out of bounds"
    ErrorCode.SHAPE_MISMATCH -> "mismatched shapes"
    ErrorCode.TEST_ERROR -> "test error"
    ErrorCode.UNSUPPORTED_OBJECT -> "operation is not supported by this object type"
    ErrorCode.UNSUPPORTED_SUBJECT -> "unsupported subject type"
    ErrorCode.UNKNOWN_KEY -> "unknown key"
    ErrorCode.UNEQUAL_ARRAY_LENGTHS -> "unequal array lengths"
    ErrorCode.DIVISION_BY_ZERO -> "division by zero"
    else -> "unknown error"
}

// Iota symbols - namespace object
object iota {
    val x = Word.make(SymbolType.x.value, NounType.BUILTIN_SYMBOL.value)
    val y = Word.make(SymbolType.y.value, NounType.BUILTIN_SYMBOL.value)
    val z = Word.make(SymbolType.z.value, NounType.BUILTIN_SYMBOL.value)
    val f = Word.make(SymbolType.f.value, NounType.BUILTIN_SYMBOL.value)
}