// EvalRegister.kt

package ion

import ion.storage.Storage
import ion.storage.Word
import ion.storage.WordArray
import ion.nouns.Noun
import ion.nouns.NounType
import ion.nouns.ErrorCode

interface EvalRegister {
    fun initialize()
    fun storeI(newI: Storage)
    fun fetchI(): Storage
    fun loadI(data: ByteArray)
    fun fetchR(): Storage?
    fun retrieveR(): ByteArray?
    fun eval()
    fun getLogs(): List<Pair<Storage, Storage>>
    fun printLogs()
    
    companion object {
        fun printLog(li: Storage, lr: Storage) {
            li.print()
            print(" -> ")
            lr.print()
            println()
        }
    }
}
