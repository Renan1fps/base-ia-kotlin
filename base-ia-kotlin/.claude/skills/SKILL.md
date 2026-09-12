| name        | code-standards |
| ----------- | -------------- |
| description | Apply this project's coding standards to any Kotlin code you write, refactor, or review. Use this skill WHENEVER you create or change application code — new functions, endpoints, controllers, services, bug fixes, refactors — or whenever the user asks to clean up, refactor, simplify, or review code for style, even if they don't mention "standards" or "style" by name. Triggers include: "refatora isso", "limpa esse código", "está bem escrito?", "revisa meu código", writing any new function/class/controller/service, or anything touching files under `src/main/kotlin/`. This skill encodes the project's rules on method length, parameters, variable scope, error handling, comments, magic numbers, nesting, and conditional expressions so new code matches the rest of the codebase instead of drifting. |

# Coding Standards

This skill is the contract for how code is written in this project: how long functions
may get, how many parameters they take, where variables are declared, how errors are
handled, and how branching is structured. It is the single source of truth for code
style here — apply it as you write, and check against it before you call a change done.

The goal behind every rule is the same: code that a reader can understand in a few
seconds, where intent is obvious from names and structure rather than from comments or
careful tracing. These rules apply to all application code (`src/main/kotlin/`), not to
tests — tests have their own conventions in the `test-automation` skill (if present in
this project).

## The rules, as a working checklist

Apply these while writing. Each links to a worked before/after in
[`references/examples.md`](references/examples.md) — read it when a rule is unclear.

1. **Functions under ~30 lines.** A function that runs long is usually doing several
   jobs. Extract the steps into smaller, well-named functions that each do one thing; the
   caller then reads as a summary of what happens. The 30 lines is a smell threshold, not
   a hard limit — a single cohesive block that happens to be longer is fine.
2. **At most 3 parameters; prefer a data class.** Long parameter lists are easy to pass
   in the wrong order and hard to extend, even with Kotlin's named/default arguments.
   Once you reach four, group them into a `data class` (`data class CreateAccountInput(...)`)
   and pass one object.
3. **Declare variables close to first use.** Don't hoist `val`/`var` declarations to
   the top of a function "to get them out of the way" — declare each variable right where
   it's first needed, so its purpose and lifetime are visible together.
4. **Never an empty `catch`, and avoid `!!`.** Swallowing an error hides failures and
   makes debugging miserable. At minimum log it with context; usually also rethrow or
   translate it into a domain exception. If you genuinely intend to ignore an error, say
   why in a comment. In the same spirit, don't reach for the non-null assertion (`!!`) to
   silence the compiler — handle the null case explicitly (`?:`, `requireNotNull`, a guard
   clause) so a null never surfaces as an unexplained `NullPointerException`.
5. **No blank lines inside a function body.** Within a function, blank lines fragment
   a single train of thought. If you feel the need to separate "sections" of a function
   with blank lines, that's a signal to extract those sections into their own functions
   (see rule 1).
6. **Avoid unnecessary comments.** A comment that just restates the code is noise that
   drifts out of date. Make the code self-explanatory through good names instead. Keep
   comments that explain *why* something non-obvious is done, not *what* the line does.
7. **Name meaningful literals.** A bare `3` or `"PAID"` in a condition hides intent.
   Promote significant numbers and strings to named constants (in a `companion object`,
   e.g. `const val MAX_LOGIN_ATTEMPTS = 3`) so the meaning is explicit and the value has
   one place to change.
8. **At most 2 levels of `if`/`else`; prefer early returns.** Deep nesting forces the
   reader to hold every branch in their head. Handle edge/guard cases first with early
   returns or thrown exceptions, then let the main path flow at the top indentation level.
9. **No nested conditional expressions.** Kotlin has no ternary operator, but chaining
   `if (a) b else if (c) d else e` as an inline expression has the same effect: it's hard
   to read and harder to change. For more than two outcomes, use a `when` expression, or a
   small function with early returns, instead.

## How to apply this

- **When writing new code**, follow the rules from the start — it's cheaper than
  cleaning up after. Match the surrounding files' naming and idiom.
- **When refactoring or reviewing**, walk the checklist against the changed code. For
  each violation, prefer the structural fix the rule points to (extract a function,
  introduce a data class, add a guard clause, replace a conditional chain with `when`)
  over a cosmetic patch.
- **Don't over-correct.** These are heuristics for readability, not laws to enforce
  mechanically. If applying a rule literally would make the code *less* clear (e.g.
  extracting a one-line "function" used once, or naming a constant that's obvious in
  context), keep the clearer version and note why.

## Before you finish

A change isn't done until the code you touched satisfies the checklist: functions stay
short and single-purpose, signatures take few/grouped parameters, variables sit next to
their use, no errors are silently swallowed and no `!!` hides a possible null, function
bodies have no blank-line padding, comments earn their place, literals are named,
branching is shallow with early returns, and no conditional expression is nested. If
you left a rule deliberately unapplied, say so and why.

## Reference

- [`references/examples.md`](references/examples.md) — the bad/good pair for every rule
  above, in this project's Kotlin/Spring Boot style. Read it when you need a concrete
  model for a refactor.