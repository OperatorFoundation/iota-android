// EvalRegister.kt

package ion

import ion.storage.Storage
import ion.storage.Word
import ion.storage.WordArray
import ion.nouns.Noun
import ion.nouns.NounType
import ion.symbols.Monads

class EvalRegister private constructor(
    private var i: Storage = Word.make(0, NounType.INTEGER),
    private var r: Storage? = null,
    private var effectsRegister: EffectsProvider? = null,
    private val logs: MutableList<Pair<Storage, Storage>> = mutableListOf()
) {
    companion object {
        private var instance: EvalRegister? = null

        fun initialize() {
            Noun.initialize()
        }

        fun eval(i: Storage): Storage? {
            val evalRegister = EvalRegister()
            evalRegister.storeI(i)
            evalRegister.eval()
            return evalRegister.fetchR()
        }

        fun registerEffectsProvider(provider: EffectsProvider) {
            // Implementation depends on your EffectsProvider requirements
        }

        fun printLog(li: Storage, lr: Storage) {
            li.print()
            print(" -> ")
            lr.print()
            println()
        }
    }

    constructor(i: Storage) : this() {
        this.i = i
    }

    fun storeI(newI: Storage) {
        i = newI
    }

    fun fetchI(): Storage {
        return i
    }

    // Be careful with this function, it moves data
    fun loadI(data: ByteArray) {
        Noun.fromBytes(data)?.let { result ->
            i = result
        }
    }

    fun fetchR(): Storage? {
        return r
    }

    fun retrieveR(): ByteArray? {
        return r?.let { value ->
            Noun.toBytes(value)
        }
    }

    fun eval() {
        r = Noun.dispatchMonad(i, Word.make(Monads.EVALUATE, NounType.BUILTIN_MONAD))

        if (r != null) {
            logs.add(Pair(i, r!!))
            printLog(i, r!!)
        } else {
            val nilArray = WordArray.nil()
            logs.add(Pair(i, nilArray))
            printLog(i, nilArray)
        }
    }

    fun getLogs(): List<Pair<Storage, Storage>> {
        return logs.toList()
    }

    fun printLogs() {
        for ((li, lr) in logs) {
            printLog(li, lr)
        }
        println(".")
    }
}
