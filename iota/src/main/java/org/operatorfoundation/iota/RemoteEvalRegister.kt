// RemoteEvalRegister.kt

package org.operatorfoundation.iota

import org.operatorfoundation.ion.StorageConnection
import org.operatorfoundation.ion.storage.Storage
import org.operatorfoundation.ion.storage.Word
import org.operatorfoundation.ion.storage.WordArray
import org.operatorfoundation.ion.storage.NounType
import org.operatorfoundation.iota.nouns.Noun

class RemoteEvalRegister(
    private val connection: StorageConnection
) : EvalRegister
{
    private var i: Storage = Word.make(0, NounType.INTEGER.value)
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
        Noun.from_bytes(data)?.let { result ->
            i = result
        }
    }

    override fun fetchR(): Storage? {
        return r
    }

    override fun retrieveR(): ByteArray? {
        return r?.let { value ->
            Noun.to_bytes(value)
        }
    }

    override fun eval(i: Storage): Storage {
        // Serialize and send over USB serial to Arduino
        connection.writeStorage(i)

        // Read result back from Arduino
        val result = connection.readStorage()
        r = result

        if (result != null) {
            logs.add(Pair(i, r!!))
            EvalRegister.Companion.printLog(i, r!!)
            return result
        } else {
            val nilArray = WordArray.nil()
            logs.add(Pair(i, nilArray))
            EvalRegister.Companion.printLog(i, nilArray)
            return nilArray
        }
    }

    override fun getLogs(): List<Pair<Storage, Storage>> {
        return logs.toList()
    }

    override fun printLogs() {
        for ((li, lr) in logs) {
            EvalRegister.Companion.printLog(li, lr)
        }
        println(".")
    }
}
