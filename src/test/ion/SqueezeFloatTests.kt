package ion

class SqueezeFloatTests
{
  fun testSqueezeFloat0()
  {
    println("Testing squeeze float 0.0...")
    val input = 0.0f
    val data = squeeze_floating(Floating.IonFloat(input))
    val result = expand_floating(data)

    assert(result != null) { "Expected non-null result" }
    if(result != null)
    {
      assert(result is Floating.IonFloat) { "Expected Floating.IonFloat, got ${result::class}" }

      if(result is Floating.IonFloat)
      {
        val f = result.value
        assert(f == input) { "Expected $input, got $f" }
      }
    }
    println("✓ testSqueezeFloat0 passed")
  }

  fun testSqueezeFloat1()
  {
    println("Testing squeeze float 1.0...")
    val input = 1.0f
    val data = squeeze_floating(Floating.IonFloat(input))
    val result = expand_floating(data)

    assert(result != null) { "Expected non-null result" }
    if(result != null)
    {
      assert(result is Floating.IonFloat) { "Expected Floating.IonFloat, got ${result::class}" }

      if(result is Floating.IonFloat)
      {
        val f = result.value
        assert(f == input) { "Expected $input, got $f" }
      }
    }
    println("✓ testSqueezeFloat1 passed")
  }

  fun testSqueezeDouble1()
  {
    println("Testing squeeze double 1.0...")
    val input = 1.0
    val data = squeeze_floating(Floating.IonDouble(input))
    val result = expand_floating(data)

    assert(result != null) { "Expected non-null result" }
    if(result != null)
    {
      assert(result is Floating.IonDouble) { "Expected Floating.IonDouble, got ${result::class}" }

      if(result is Floating.IonDouble)
      {
        val d = result.value
        assert(d == input) { "Expected $input, got $d" }
      }
    }
    println("✓ testSqueezeDouble1 passed")
  }

  fun testSqueezeFloatNeg1()
  {
    println("Testing squeeze float -1.0...")
    val input = -1.0f
    val data = squeeze_floating(Floating.IonFloat(input))
    val result = expand_floating(data)

    assert(result != null) { "Expected non-null result" }
    if(result != null)
    {
      assert(result is Floating.IonFloat) { "Expected Floating.IonFloat, got ${result::class}" }

      if(result is Floating.IonFloat)
      {
        val f = result.value
        assert(f == input) { "Expected $input, got $f" }
      }
    }
    println("✓ testSqueezeFloatNeg1 passed")
  }

  fun runAllTests()
  {
    println("=== Running Float Tests ===")
    try {
      testSqueezeFloat0()
      testSqueezeFloat1()
      testSqueezeDouble1()
      testSqueezeFloatNeg1()
      println("=== All float tests passed! ===")
    } catch (e: AssertionError) {
      println("❌ Test failed: ${e.message}")
      throw e
    }
  }
}