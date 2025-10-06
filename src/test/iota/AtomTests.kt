// AtomTests.kt

package iota

// Custom assertion helper
fun assertIotaEquals(expected: Any?, actual: IotaValue) {
    assert(expected.toIotaValue() == actual) { "Expected ${expected.toIotaValue()}, got $actual" }
}

class AtomTests {
    fun testAtomInteger() {
        println("Testing atom integer...")
        assertIotaEquals(1, evalExpression(1, atom))
        println("✓ testAtomInteger passed")
    }

    fun testAtomReal() {
        println("Testing atom real...")
        assertIotaEquals(1, evalExpression(1.0f, atom))
        println("✓ testAtomReal passed")
    }

    fun testAtomList() {
        println("Testing atom list...")
        assertIotaEquals(1, evalExpression(a(), atom))
        assertIotaEquals(0, evalExpression(a(2, 3), atom))
        assertIotaEquals(0, evalExpression(a(2.0f, 3.0f), atom))
        assertIotaEquals(0, evalExpression(a(2, 3.0f), atom))
        println("✓ testAtomList passed")
    }

    fun testAtomCharacter() {
        println("Testing atom character...")
        assertIotaEquals(1, evalExpression('a', atom))
        println("✓ testAtomCharacter passed")
    }

    fun testAtomString() {
        println("Testing atom string...")
        assertIotaEquals(1, evalExpression("", atom))
        assertIotaEquals(0, evalExpression("abc", atom))
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
