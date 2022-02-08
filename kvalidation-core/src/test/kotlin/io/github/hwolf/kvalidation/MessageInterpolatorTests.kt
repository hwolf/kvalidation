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
import strikt.assertions.isEqualTo

class MessageInterpolatorTests : FunSpec({

    fun constraintViolation() = ConstraintViolation(
        propertyPath = listOf(PropertyName("test")),
        propertyType = PropertyType("String"),
        propertyValue = "xxx",
        constraint = Equal(1234))

    context("Order of codes for simple property") {
        withData(
            Pair(messageSourceMap(
                "a" to "Level 3",
                "a.b" to "Level 2",
                "a.b.c" to "Level 1"),
                "Level 1"),
            Pair(messageSourceMap(
                "a" to "Level 3",
                "a.b" to "Level 2"),
                "Level 2"),
            Pair(messageSourceMap(
                "a" to "Level 3"),
                "Level 3"),
            Pair(messageSourceMap(),
                "io.github.hwolf.kvalidation.Equal")
        ) { (messageSource, expected) ->
            val sut = MessageInterpolator(
                messageSource = messageSource,
                messageFormatter = { template, _, _ -> template },
                messageCodeResolver = { listOf("a.b.c", "a.b", "a") })
            val actual = sut.interpolate(constraintViolation(), Locale.GERMAN)
            expectThat(actual).isEqualTo(expected)
        }
    }
})
