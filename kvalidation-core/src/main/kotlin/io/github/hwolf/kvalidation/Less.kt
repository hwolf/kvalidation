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

import kotlin.reflect.KProperty1

/** A constraint that validate if the value is less than another value. */
data class Less<V>(val value: V) : Constraint

/** A constraint that validate if the value is less than another property within the same object. */
data class LessWith(val property: String) : Constraint

/** Validates if the [Comparable] property is less than another value. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.less(value: V) =
    validate(Less(value)) { v, _ -> v < value }

/** Validates if the [Comparable] property is less than property within the same object. */
fun <T, V : Comparable<V>> ValidationBuilder<T, out V>.less(property: KProperty1<T, V?>) =
    validate(LessWith(property.name)) { v, bean -> property(bean)?.let { v < it } ?: true }
