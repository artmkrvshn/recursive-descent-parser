import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParserTest {

    private val parser = Parser()

    @ParameterizedTest
    @ValueSource(strings = ["a+b$", "1+6$", "10-6$", "a-5$", "5+a$"])
    fun simplePlusMinusTest(string: String) = assertDoesNotThrow { parser.parse(string) }

    @ParameterizedTest
    @ValueSource(strings = ["a*b", "1*6", "10/6", "a/5", "5*a"])
    fun simpleMulDivTest(string: String) = assertDoesNotThrow { parser.parse(string) }

    @ParameterizedTest
    @ValueSource(strings = ["-12-34", "-12+34", "12-34", "12+34"])
    fun negativePlusMinusTest(string: String) = assertDoesNotThrow { parser.parse(string) }

    @ParameterizedTest
    @ValueSource(strings = ["-12*-34", "-12*34", "-12/-34", "-12/34", "12/34", "12/-34"])
    fun negativeMulDivTest(string: String) = assertDoesNotThrow { parser.parse(string) }

    @ParameterizedTest
    @ValueSource(strings = ["(a+b)", "(a/35)", "(a/10)+(a/53)", "(a/10)*(a+3)", "(a+b)/3", "2/(a+b)"])
    fun expressions(string: String) = assertDoesNotThrow { parser.parse(string) }

    @ParameterizedTest
    @ValueSource(strings = ["func(a)", "abc(41)", "sq(a31)", "cab(a)/max(20)", "fu(-100)"])
    fun withFunctions(string: String) = assertDoesNotThrow { parser.parse(string) }

    @ParameterizedTest
    @ValueSource(strings = ["(a+b", "1a", "(aaab)/)a/8+35", "1a", "2b", "+a", "-b", "-0123", "012414", "+24141", "+021414"])
    fun shouldThrowAnException(string: String) {
        assertThrows<IllegalStateException> { parser.parse(string) }
    }

}
