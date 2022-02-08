/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hwolf.kvalidation.common

import io.github.hwolf.kvalidation.Constraint
import io.github.hwolf.kvalidation.ValidationBuilder
import io.github.hwolf.kvalidation.validate
import org.apache.commons.validator.routines.EmailValidator

/** A constraint that validate if the value is an email. */
data class Email(
    val options: Collection<Option>
) : Constraint {
    enum class Option {

        /** Should local addresses be considered valid ? */
        AllowLocal
    }
}

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail() = isEmail(emptySet())

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail(vararg options: Email.Option) = isEmail(options.toSet())

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail(options: Collection<Email.Option>) =
    validate(Email(options)) { v, _ ->
        EmailValidator.getInstance(Email.Option.AllowLocal in options, false).isValid(v)
    }
