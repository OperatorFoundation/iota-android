// EvalRegister.kt

package org.operatorfoundation.iota

import org.operatorfoundation.ion.storage.Storage

interface EvalRegister {
    fun initialize()
    fun storeI(newI: Storage)
    fun fetchI(): Storage
    fun loadI(data: ByteArray)
    fun fetchR(): Storage?
    fun retrieveR(): ByteArray?
    fun eval(i: Storage): Storage
    fun getLogs(): List<Pair<Storage, Storage>>
    fun printLogs()
    
    companion object {
        fun printLog(li: Storage, lr: Storage) {
            print(li)
            print(" -> ")
            print(lr)
            println()
        }
    }
}
