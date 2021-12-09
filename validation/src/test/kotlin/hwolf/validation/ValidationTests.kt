package hwolf.validation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import hwolf.validation.constraints.In
import hwolf.validation.constraints.NotEmpty
import hwolf.validation.constraints.isIn
import hwolf.validation.constraints.isNotEmpty
import hwolf.validation.utils.hasExactlyViolations
import hwolf.validation.utils.isValid
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

val validator = validator<Department> {
    Department::name {
        isNotEmpty()
    }
    Department::head {
        Employee::name {
            isNotEmpty()
        }
    }
    onlyIf({ name == "X" }) {
        Department::head {
            Employee::name {
                isIn("Mr. X", "Mr. Y")
            }
        }
    }
    Department::coHead ifPresent {
        Employee::name {
            isNotEmpty()
        }
    }
    Department::employees {
        isNotEmpty()
    }
    Department::employees each {
        Employee::name {
            isNotEmpty()
        }
    }
    Department::office ifPresent {
        isNotEmpty()
    }
    onlyIf({ name != "X" }) {
        Department::office required { }
    }
}

@Testable
@Suppress("LongMethod")
fun `Validate a department`() = rootContext<Department> {
    given {
        Department(
            name = "hwolf.test.Test",
            employees = listOf(Employee("Bob"), Employee("Alice")),
            head = Employee("John"),
            coHead = Employee("Mike"),
            office = "X.123.456")
    }
    test("Department is valid") {
        expectThat(validator.validator(this)).isValid()
    }
    test("Department name is empty") {
        expectThat(validator.validator(copy(name = ""))).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "name",
                propertyType = "String",
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Department head name is empty") {
        val actual = validator.validator(copy(head = Employee("")))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "head.name",
                propertyType = "String",
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Department without employees") {
        val actual = validator.validator(copy(employees = listOf()))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "employees",
                propertyType = "List",
                propertyValue = emptyList<Any>(),
                constraint = NotEmpty))
    }
    test("Department without co head") {
        val actual = validator.validator(copy(coHead = null))
        expectThat(actual).isValid()
    }
    test("Department co head name is empty") {
        val actual = validator.validator(copy(coHead = Employee("")))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "coHead.name",
                propertyType = "String",
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Employees without names") {
        val actual = validator.validator(
            copy(employees = listOf(
                Employee("Bob"),
                Employee(""),
                Employee("Alice"),
                Employee(""))))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "employees[1].name",
                propertyType = "String",
                propertyValue = "",
                constraint = NotEmpty),
            ConstraintViolation(
                propertyName = "employees[3].name",
                propertyType = "String",
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Department without office") {
        val actual = validator.validator(copy(office = null))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "office",
                propertyType = "String",
                propertyValue = null,
                constraint = Required))
    }
    test("Department with name 'X'") {
        val actual = validator.validator(copy(name = "X", head = Employee(name = "Mr. Y")))
        expectThat(actual).isValid()
    }
    test("Department with name 'X' but wrong head") {
        val actual = validator.validator(copy(name = "X"))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyName = "head.name",
                propertyType = "String",
                propertyValue = "John",
                constraint = In(allowedValues = listOf("Mr. X", "Mr. Y"))))
    }
}
