package hwolf.validation

import hwolf.validation.constraints.In
import hwolf.validation.constraints.isIn
import hwolf.validation.utils.hasExactlyViolations
import hwolf.validation.utils.isValid
import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderIfPresentTest {

    class TestBean(val field: String?)

    private val validator = validator<TestBean> {
        TestBean::field ifPresent {
            isIn("x1", "x2")
        }
    }

    @Test
    fun `Optional field is not set`() {
        expectThat(validator.validator(TestBean(field = null))).isValid()
    }

    @Test
    fun `Optional field is set but value is invalid`() {
        expectThat(validator.validator(TestBean(field = "xx")))
            .hasExactlyViolations(ConstraintViolation(
                propertyName = "field",
                propertyType = "String",
                propertyValue = "xx",
                constraint = In(allowedValues = listOf("x1", "x2"))
            ))
    }

    @Test
    fun `Optional field is set and value is valid`() {
        expectThat(validator.validator(TestBean(field = "x1"))).isValid()
    }
}