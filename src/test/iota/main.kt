// Updated main.kt

package ion

fun main() {
    // Set up USB connection
    val usbConnection = UsbSerialConnection(/* ... */)
    val storageConnection = UsbStorageConnection(usbConnection)
    
    // Create remote eval register
    val evalRegister = RemoteEvalRegister(storageConnection)
    evalRegister.initialize()
    
    // Run tests with the eval register
    val atomTests = AtomTests(evalRegister)
    atomTests.runAllTests()
}
