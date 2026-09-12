# Coding Standards — Worked Examples

A bad/good pair for each rule in [`../SKILL.md`](../SKILL.md). Use them as concrete
models when applying or explaining a rule.

## 1. Functions under ~30 lines

Break a long function into smaller, well-named pieces that each do one thing.

```kotlin
// Bad: one function doing parsing, validation, calculation and persistence
suspend fun processOrder(input: OrderInput) {
    // ...30+ lines mixing many responsibilities...
}

// Good
suspend fun processOrder(input: OrderInput) {
    val order = parseOrder(input)
    validateOrder(order)
    val total = calculateTotal(order)
    orderRepository.save(order.copy(total = total))
}
```

## 2. At most 3 parameters; prefer a data class

```kotlin
// Bad
fun createAccount(name: String, email: String, cpf: String, password: String, isAdmin: Boolean) {
    // ...
}

// Good
data class CreateAccountInput(
    val name: String,
    val email: String,
    val cpf: String,
    val password: String,
    val isAdmin: Boolean,
)

fun createAccount(input: CreateAccountInput) {
    // ...
}
```

## 3. Declare variables close to first use

```kotlin
// Bad
fun getDiscount(order: Order): BigDecimal {
    val discount = DISCOUNT_RATE
    // ...many lines that don't use discount...
    return order.total.multiply(discount)
}

// Good
fun getDiscount(order: Order): BigDecimal {
    // ...many lines...
    val discount = DISCOUNT_RATE
    return order.total.multiply(discount)
}
```

## 4. Never an empty `catch`, and avoid `!!`

```kotlin
// Bad
try {
    paymentGateway.charge(order)
} catch (e: Exception) {
}

// Good
try {
    paymentGateway.charge(order)
} catch (e: PaymentGatewayException) {
    logger.error("Failed to charge order ${order.id}", e)
    throw PaymentFailedException(order.id)
}
```

```kotlin
// Bad: silences the compiler and hides a real null case
fun getPrimaryCard(account: Account): Card {
    return account.cards.firstOrNull { it.isPrimary }!!
}

// Good: the null case is explicit and produces a meaningful error
fun getPrimaryCard(account: Account): Card {
    return account.cards.firstOrNull { it.isPrimary }
        ?: throw NoPrimaryCardException(account.id)
}
```

## 5. No blank lines inside a function body

```kotlin
// Bad
fun calculateTotal(order: Order): BigDecimal {
    val subtotal = sumItems(order.items)

    val tax = subtotal.multiply(TAX_RATE)

    return subtotal.add(tax)
}

// Good
fun calculateTotal(order: Order): BigDecimal {
    val subtotal = sumItems(order.items)
    val tax = subtotal.multiply(TAX_RATE)
    return subtotal.add(tax)
}
```

## 6. Avoid unnecessary comments

The code should express its intent clearly and objectively.

```kotlin
// Bad: the comment just repeats what the code says
// increment the counter by one
counter += 1

// Good: a clear name removes the need for the comment
retryCount += 1
```

## 7. Name meaningful literals

```kotlin
// Bad
if (account.failedLoginAttempts > 3) {
    lockAccount(account)
}

// Good
companion object {
    const val MAX_LOGIN_ATTEMPTS = 3
}

if (account.failedLoginAttempts > MAX_LOGIN_ATTEMPTS) {
    lockAccount(account)
}
```

## 8. At most 2 levels of `if`/`else`; prefer early returns

```kotlin
// Bad
fun getShippingCost(order: Order): BigDecimal {
    if (order.items.isNotEmpty()) {
        if (order.total > BigDecimal(100)) {
            return BigDecimal.ZERO
        } else {
            return BigDecimal.TEN
        }
    } else {
        throw EmptyOrderException()
    }
}

// Good
fun getShippingCost(order: Order): BigDecimal {
    if (order.items.isEmpty()) throw EmptyOrderException()
    if (order.total > BigDecimal(100)) return BigDecimal.ZERO
    return BigDecimal.TEN
}
```

## 9. No nested conditional expressions

```kotlin
// Bad: chained if/else used as an inline expression
val label = if (status == OrderStatus.PAID) "Paid"
    else if (status == OrderStatus.PENDING) "Pending"
    else "Cancelled"

// Good: a when expression makes every branch explicit
fun getStatusLabel(status: OrderStatus): String = when (status) {
    OrderStatus.PAID -> "Paid"
    OrderStatus.PENDING -> "Pending"
    OrderStatus.CANCELLED -> "Cancelled"
}
```