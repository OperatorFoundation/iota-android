package ion

class SqueezeIntTests
{
  fun testSqueezeInt0()
  {
    println("Testing squeeze int 0...")
    val input = 0
    val data = squeeze_int(input)
    val (value, rest) = expand_int(data)

    assert(value is Varint.IonInt) { "Expected Varint.IonInt, got ${value::class}" }

    if(value is Varint.IonInt)
    {
      val output = value.value
      assert(input == output) { "Expected $input, got $output" }
    }

    assert(rest.isEmpty()) { "Expected empty rest, got ${rest.size} bytes" }
    println("✓ testSqueezeInt0 passed")
  }

  fun testSqueezeInt1()
  {
    println("Testing squeeze int 1...")
    val input = 1
    val data = squeeze_int(input)
    val (value, rest) = expand_int(data)

    assert(value is Varint.IonInt) { "Expected Varint.IonInt, got ${value::class}" }

    if(value is Varint.IonInt)
    {
      val output = value.value
      assert(input == output) { "Expected $input, got $output" }
    }

    assert(rest.isEmpty()) { "Expected empty rest, got ${rest.size} bytes" }
    println("✓ testSqueezeInt1 passed")
  }

  fun testSqueezeIntNeg1()
  {
    println("Testing squeeze int -1...")
    val input = -1
    val data = squeeze_int(input)
    val (value, rest) = expand_int(data)

    assert(value is Varint.IonInt) { "Expected Varint.IonInt, got ${value::class}" }

    if(value is Varint.IonInt)
    {
      val output = value.value
      assert(input == output) { "Expected $input, got $output" }
    }

    assert(rest.isEmpty()) { "Expected empty rest, got ${rest.size} bytes" }
    println("✓ testSqueezeIntNeg1 passed")
  }

  fun testSqueezeInt256()
  {
    println("Testing squeeze int 256...")
    val input = 256
    val data = squeeze_int(input)
    val (value, rest) = expand_int(data)

    assert(value is Varint.IonInt) { "Expected Varint.IonInt, got ${value::class}" }

    if(value is Varint.IonInt)
    {
      val output = value.value
      assert(input == output) { "Expected $input, got $output" }
    }

    assert(rest.isEmpty()) { "Expected empty rest, got ${rest.size} bytes" }
    println("✓ testSqueezeInt256 passed")
  }

  fun testSqueezeIntNeg256()
  {
    println("Testing squeeze int -256...")
    val input = -256
    val data = squeeze_int(input)
    val (value, rest) = expand_int(data)

    assert(value is Varint.IonInt) { "Expected Varint.IonInt, got ${value::class}" }

    if(value is Varint.IonInt)
    {
      val output = value.value
      assert(input == output) { "Expected $input, got $output" }
    }

    assert(rest.isEmpty()) { "Expected empty rest, got ${rest.size} bytes" }
    println("✓ testSqueezeIntNeg256 passed")
  }

  fun runAllTests()
  {
    println("=== Running Squeeze Int Tests ===")
    try {
      testSqueezeInt0()
      testSqueezeInt1()
      testSqueezeIntNeg1()
      testSqueezeInt256()
      testSqueezeIntNeg256()
      println("=== All squeeze int tests passed! ===")
    } catch (e: AssertionError) {
      println("❌ Test failed: ${e.message}")
      throw e
    }
  }
}