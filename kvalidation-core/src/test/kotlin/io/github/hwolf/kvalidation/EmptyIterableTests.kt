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
package io.github.hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class EmptyIterableTests : FunSpec({

    val validator = validator { IterableBean::range { empty() } }

    context("is empty") {
        withData(nameFn = Any::toString, IntRange(11, 0), IntRange(1, 0)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is not empty") {
        withData(nameFn = Any::toString, IntRange(0, 1), IntRange(1, 1)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("range")),
                propertyType = PropertyType("Iterable"),
                propertyValue = value,
                constraint = Empty))
        }
    }
})