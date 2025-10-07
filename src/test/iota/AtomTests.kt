// Updated test code in AtomTests.kt

package ion

import ion.verbs.atom

// Custom assertion helper
fun assertIotaEquals(expected: Any?, actual: IotaValue) {
    assert(expected.toIotaValue() == actual) { "Expected ${expected.toIotaValue()}, got $actual" }
}

class AtomTests(private val evalRegister: EvalRegister) {
    
    fun testAtomInteger() {
        println("Testing atom integer...")
        assertIotaEquals(1, evalExpression(evalRegister, 1, atom))
        println("✓ testAtomInteger passed")
    }

    fun testAtomReal() {
        println("Testing atom real...")
        assertIotaEquals(1, evalExpression(evalRegister, 1.0f, atom))
        println("✓ testAtomReal passed")
    }

    fun testAtomList() {
        println("Testing atom list...")
        assertIotaEquals(1, evalExpression(evalRegister, a(), atom))
        assertIotaEquals(0, evalExpression(evalRegister, a(2, 3), atom))
        assertIotaEquals(0, evalExpression(evalRegister, a(2.0f, 3.0f), atom))
        assertIotaEquals(0, evalExpression(evalRegister, a(2, 3.0f), atom))
        println("✓ testAtomList passed")
    }

    fun testAtomCharacter() {
        println("Testing atom character...")
        assertIotaEquals(1, evalExpression(evalRegister, 'a', atom))
        println("✓ testAtomCharacter passed")
    }

    fun testAtomString() {
        println("Testing atom string...")
        assertIotaEquals(1, evalExpression(evalRegister, "", atom))
        assertIotaEquals(0, evalExpression(evalRegister, "abc", atom))
        println("✓ testAtomString passed")
    }

    fun runAllTests() {
        println("=== Running Atom Tests ===")
        try {
            testAtomInteger()
            testAtomReal()
            testAtomList()
            testAtomCharacter()
            testAtomString()
            println("=== All atom tests passed! ===")
        } catch (e: AssertionError) {
            println("✗ Test failed: ${e.message}")
            throw e
        }
    }
}
