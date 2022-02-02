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
package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class HasLengthTests : FunSpec({

    val validator = validator<StringBean> { StringBean::string { hasLength(3, 5) } }

    context("has length") {
        withData("123", "12345") { value ->
            val actual = validator.validate(StringBean(string = value))
            expectThat(actual).isValid()
        }
    }
    context("has not length") {
        withData("12", "123456") { value ->
            val actual = validator.validate(StringBean(string = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("string")),
                propertyType = PropertyType("String"),
                propertyValue = value,
                constraint = Length(3, 5)))
        }
    }
})
