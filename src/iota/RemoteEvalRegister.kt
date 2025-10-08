// RemoteEvalRegister.kt

package ion

import ion.storage.Storage
import ion.storage.Word
import ion.storage.WordArray
import ion.nouns.Noun
import ion.nouns.NounType
import ion.nouns.ErrorCode

class RemoteEvalRegister(
    private val connection: StorageConnection
) : EvalRegister {
    
    private var i: Storage = Word.make(0, NounType.INTEGER)
    private var r: Storage? = null
    private val logs: MutableList<Pair<Storage, Storage>> = mutableListOf()

    override fun initialize() {
        Noun.initialize()
    }

    override fun storeI(newI: Storage) {
        i = newI
    }

    override fun fetchI(): Storage {
        return i
    }

    override fun loadI(data: ByteArray) {
        Noun.fromBytes(data)?.let { result ->
            i = result
        }
    }

    override fun fetchR(): Storage? {
        return r
    }

    override fun retrieveR(): ByteArray? {
        return r?.let { value ->
            Noun.toBytes(value)
        }
    }

    override fun eval() {
        // Serialize and send over USB serial to Arduino
        connection.writeStorage(i)
        
        // Read result back from Arduino
        r = connection.readStorage()

        if (r != null) {
            logs.add(Pair(i, r!!))
            EvalRegister.printLog(i, r!!)
        } else {
            val nilArray = WordArray.nil()
            logs.add(Pair(i, nilArray))
            EvalRegister.printLog(i, nilArray)
        }
    }

    override fun getLogs(): List<Pair<Storage, Storage>> {
        return logs.toList()
    }

    override fun printLogs() {
        for ((li, lr) in logs) {
            EvalRegister.printLog(li, lr)
        }
        println(".")
    }
}
