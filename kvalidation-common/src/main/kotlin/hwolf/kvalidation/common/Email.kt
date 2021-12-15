package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.EmailValidator

/** A constraint that validate if the value is an email. */
data class Email(
    val options: Collection<Options>
) : Constraint {
    enum class Options {

        /** Should local addresses be considered valid ? */
        AllowLocal
    }
}

/** Validates if the property value is an email. */
fun ConstraintBuilder<String>.isEmail() = isEmail(emptySet())

/** Validates if the property value is an email. */
fun ConstraintBuilder<String>.isEmail(vararg options: Email.Options) = isEmail(options.toSet())

/** Validates if the property value is an email. */
fun ConstraintBuilder<String>.isEmail(options: Collection<Email.Options>) = validate(Email(options)) {
    EmailValidator.getInstance(Email.Options.AllowLocal in options, false).isValid(it)
}
