# Signal Exec Adapter 1.0.0

A transport- and engine-neutral contract for managed autonomous skills.

## Purpose
A skill should be more than callable code. It should declare enough operational semantics that agents and Signal Control can decide whether to use it, retry it, inspect it, replace it, or retire it without rebuilding orchestration glue.

## Core contract
`ExecutionAdapter<I,O>` requires `descriptor()` and `execute(...)`.

Optional conformance interfaces add managed behavior: status, cancellation, idempotency, retry, health, observability, diagnostics, and compensation.

## Score
Default weighting:
- Capability: 30%
- Reliability: 30%
- Observability: 20%
- Agent Value: 20%

Agent Value is based on optional post-use ratings (0..100) with confidence (0..1). If no agent has rated a skill, the neutral value is 50.

## Maturity
- Level 1 CallABLE
- Level 2 Managed
- Level 3 Resilient
- Level 4 Autonomous
- Level 5 Recoverable

## Lifecycle comparison
`SkillComparator` can recommend KEEP, UPGRADE, PREFER_VERSION, MERGE, DEPRECATE, or ELIMINATE based on score and capability overlap.

## Agent behavior
Agents may inspect scores before choosing a skill and may submit a rating after use when they have enough evidence to do so. A single rating should never override objective conformance dimensions.
