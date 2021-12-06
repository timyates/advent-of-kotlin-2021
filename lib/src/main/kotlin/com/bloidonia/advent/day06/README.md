Solution for [Day 6](https://adventofcode.com/2021/day/6) -- Lanternfish

[Input data](../../../../../resources/day06input.txt)

---

Ended up doing 3 versions..

1. The original map implementation
2. One that uses an array instead of a map
3. and one that uses a Deque instead of the array (for speed of rotation)

Then to see what was fastest, ran it through JMH

Expected Deque to be quicker, but the array one seems to slightly edge it... 🤔

```
Benchmark               Mode  Cnt      Score      Error  Units
Day06Profile.array     thrpt    4  37080.984 ± 1832.172  ops/s
Day06Profile.deque     thrpt    4  36356.877 ± 1198.486  ops/s
Day06Profile.original  thrpt    4  13748.644 ± 2971.370  ops/s
```