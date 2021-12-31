package hwolf.kvalidation

/**
 * Represents a constraint violation
 *
 * @constructor creates a constraint violation
 * @property propertyPath the path of the property that violated the constraint
 * @property propertyType the type of the property that violated the constraint
 * @property propertyValue the invalid value
 * @property constraint the violated constraint
 */
data class ConstraintViolation(
    val violationId: String,
    val propertyPath: List<PropertyName>,
    val propertyType: PropertyType?,
    val propertyValue: Any?,
    val constraint: Constraint
) {
    constructor(
        propertyPath: List<PropertyName>,
        propertyType: PropertyType?,
        propertyValue: Any?,
        constraint: Constraint
    ) : this(constraint.messageKey, propertyPath, propertyType, propertyValue, constraint)
}

data class PropertyName(
    val name: String,
    val key: Any? = null
) {
    override fun toString() = when (key == null) {
        true -> name
        else -> "$name[$key]"
    }
}

@JvmInline
value class PropertyType(
    val type: String
) {
    override fun toString() = type
}
