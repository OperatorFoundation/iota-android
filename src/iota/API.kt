package iota

sealed class IotaValue {
    data class IntValue(val value: Int) : IotaValue()
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
            is FloatValue -> other is FloatValue && 
                kotlin.math.abs(value - other.value) < Float.MIN_VALUE // Similar to Float::precision
            is CharValue -> other is CharValue && value == other.value
            is StringValue -> other is StringValue && value == other.value
            is ListValue -> other is ListValue && values == other.values
            is ErrorValue -> other is ErrorValue && error == other.error
            is StorageValue -> other is StorageValue && storage == other.storage
        }
    }

    override fun hashCode(): Int = when (this) {
        is IntValue -> value.hashCode()
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
        return MixedArray.make(results, NounType.EXPRESSION)
    }

    fun fromKotlin(value: IotaValue): Storage = when (value) {
        is IotaValue.IntValue -> Integer.make(value.value)
        
        is IotaValue.FloatValue -> Real.make(value.value)
        
        is IotaValue.ListValue -> {
            val values = value.values
            when {
                KotlinValue.allInts(values) -> {
                    val ints = values.map { (it as IotaValue.IntValue).value }
                    WordArray.make(ints)
                }
                KotlinValue.allFloats(values) -> {
                    val floats = values.map { (it as IotaValue.FloatValue).value }
                    FloatArray.make(floats)
                }
                else -> {
                    val mixed = values.map { fromKotlin(it) }
                    MixedArray.make(mixed)
                }
            }
        }
        
        is IotaValue.CharValue -> {
            // FIXME - This only works for ASCII, fix it to work with Unicode.
            Character.make(value.value.code)
        }
        
        is IotaValue.StringValue -> {
            // FIXME - This only works for ASCII, fix it to work with Unicode.
            val integers = value.value.map { it.code }
            IotaString.make(integers)
        }
        
        is IotaValue.StorageValue -> value.storage
        
        is IotaValue.ErrorValue -> 
            Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR)
    }

    fun toKotlin(storage: Storage): IotaValue = when (storage.o) {
        NounType.INTEGER -> {
            storage.i.asInt()?.let { IotaValue.IntValue(it) }
                ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.REAL -> {
            storage.i.asFloat()?.let { IotaValue.FloatValue(it) }
                ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.LIST -> when (storage.t) {
            StorageType.WORD_ARRAY -> {
                storage.i.asIntList()?.let { integers ->
                    IotaValue.ListValue(integers.map { IotaValue.IntValue(it) })
                } ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
            }
            
            StorageType.FLOAT_ARRAY -> {
                storage.i.asFloatList()?.let { floats ->
                    IotaValue.ListValue(floats.map { IotaValue.FloatValue(it) })
                } ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
            }
            
            StorageType.MIXED_ARRAY -> {
                storage.i.asMixedList()?.let { mixed ->
                    IotaValue.ListValue(mixed.map { toKotlin(it) })
                } ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
            }
            
            else -> IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.CHARACTER -> {
            // FIXME - This only works for ASCII, fix it to work with Unicode.
            storage.i.asInt()?.let { IotaValue.CharValue(it.toChar()) }
                ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.STRING -> {
            // FIXME - This only works for ASCII, fix it to work with Unicode.
            storage.i.asIntList()?.let { integers ->
                val string = integers.map { it.toChar() }.joinToString("")
                IotaValue.StringValue(string)
            } ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.BUILTIN_SYMBOL -> {
            storage.i.asInt()?.let { symbolType ->
                when (symbolType) {
                    SymbolType.X -> IotaValue.StringValue(":x")
                    SymbolType.Y -> IotaValue.StringValue(":y")
                    SymbolType.Z -> IotaValue.StringValue(":z")
                    SymbolType.F -> IotaValue.StringValue(":f")
                    SymbolType.UNDEFINED -> IotaValue.StringValue(":undefined")
                    else -> IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
                }
            } ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.EXPRESSION -> {
            storage.i.asMixedList()?.let { mixed ->
                IotaValue.ListValue(mixed.map { toKotlin(it) })
            } ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
        }
        
        NounType.ERROR -> {
            IotaValue.StringValue(errorToString(storage))
        }
        
        else -> IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
    }
}

// Evaluation functions
fun evalExpression(vararg values: Any?): IotaValue {
    val converted = values.map { it.toIotaValue() }
    val storage = IotaObject.fromKotlinExpression(converted)
    val result = EvalRegister.eval(storage)
    return result?.let { IotaObject.toKotlin(it) }
        ?: IotaValue.ErrorValue(Error(ErrorCode.UNSUPPORTED_OBJECT))
}

fun evalExpression(storage: Storage): Storage {
    return EvalRegister.eval(storage)
        ?: Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR)
}

fun evalNoun(value: IotaValue): IotaValue {
    val storage = IotaObject.fromKotlin(value)
    val result = EvalRegister.eval(storage)
    return result?.let { IotaObject.toKotlin(it) }
        ?: IotaValue.StringValue("Error: unsupported object")
}

fun eval(expression: Mixed): Storage {
    val result = EvalRegister.eval(Expression.make(expression))
    return result ?: Word.make(ErrorCode.UNSUPPORTED_OBJECT, NounType.ERROR)
}

// Error handling
fun testError(): Storage =
    Word.make(ErrorCode.TEST_ERROR, NounType.ERROR)

fun errorToString(error: Storage): String {
    if (error.o != NounType.ERROR) return "Unknown error"
    if (error.t != StorageType.WORD) return "Unknown error"
    
    return error.i.asInt()?.let { codeToString(it) } ?: "Unknown error"
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
    val i = Word.make(SymbolType.I, NounType.BUILTIN_SYMBOL)
    val x = Word.make(SymbolType.X, NounType.BUILTIN_SYMBOL)
    val y = Word.make(SymbolType.Y, NounType.BUILTIN_SYMBOL)
    val z = Word.make(SymbolType.Z, NounType.BUILTIN_SYMBOL)
    val f = Word.make(SymbolType.F, NounType.BUILTIN_SYMBOL)
    val causing = Word.make(SymbolType.CAUSING, NounType.BUILTIN_SYMBOL)
}

// Helper extension functions for Storage access (assuming these exist)
private fun Any.asInt(): Int? = this as? Int
private fun Any.asFloat(): Float? = this as? Float
private fun Any.asIntList(): List<Int>? = this as? List<Int>
private fun Any.asFloatList(): List<Float>? = this as? List<Float>
private fun Any.asMixedList(): List<Storage>? = this as? List<Storage>
