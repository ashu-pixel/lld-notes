# Dining Philosophers Problem – Interview Notes

## What is the Dining Philosophers Problem?

The Dining Philosophers Problem is a classic synchronization problem that demonstrates the challenges of concurrent programming when multiple threads compete for limited shared resources.

## Scenario

- There are 5 philosophers sitting around a circular table.
- Between every two philosophers is one fork.
- Each philosopher needs both the left and right fork to eat.
- Philosophers alternate between:
  - Thinking
  - Hungry
  - Eating

Since forks are shared resources, proper synchronization is required.

## Algorithm

1. Philosopher becomes hungry.
2. Pick up the left fork (lock).
3. Pick up the right fork (lock).
4. Eat.
5. Put down both forks (unlock).
6. Think.

#### Pseudo-code

```java
while (true) {
    think();

    lock(leftFork);
    lock(rightFork);

    eat();

    unlock(rightFork);
    unlock(leftFork);
}
```

Although simple, this solution has several problems.

## Problems

### 1. Deadlock

#### Example

- P1 holds Fork 1, waiting for Fork 2
- P2 holds Fork 2, waiting for Fork 3
- P3 holds Fork 3, waiting for Fork 4
- P4 holds Fork 4, waiting for Fork 5
- P5 holds Fork 5, waiting for Fork 1

Everyone is waiting forever, and no philosopher can proceed.

### Coffman Conditions for Deadlock

Deadlock occurs only if all four conditions hold simultaneously.

- Mutual Exclusion: Only one thread can use a resource at a time.
  - Example: One fork can be held by only one philosopher.

- Hold and Wait: A thread holds one resource while waiting for another.
  - Example: Philosopher holds the left fork and waits for the right fork.

- No Preemption: Resources cannot be forcibly taken away.
  - Example: A philosopher will not release the fork until finished.

- Circular Wait: There exists a circular chain of waiting.
  - Example: P1 → waiting for P2 → P3 → P4 → P5 → P1

This creates a cycle.

### How to Prevent Deadlock?

Break any one Coffman condition.

### 2. Starvation

A thread waits indefinitely because other threads continuously get access to the resource.

#### Example

- P1 keeps eating.
- P2 always loses the race for forks.
- P2 never gets to eat.

### 3. Livelock

#### What is Livelock?

Threads are active and continuously changing state, but no thread makes progress.

#### Example

- P1 picks the left fork.
- P2 picks the left fork.
- Both notice the conflict.
- Both release their fork.
- Both immediately retry.
- Again, both collide.

This repeats forever.

#### Unlike deadlock

- Threads are not blocked.
- They keep running but accomplish nothing.

#### Solution

- Random backoff before retrying
- Exponential backoff
- Fair scheduling

### 4. Race Condition

#### What is a Race Condition?

The outcome depends on the order in which multiple threads access shared data.

#### Example

- Fork available = true
- P1 checks fork → available
- P2 checks fork → available
- Both pick the same fork simultaneously.

Incorrect behavior occurs.
