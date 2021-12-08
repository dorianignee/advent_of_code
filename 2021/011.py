import aoc
depths = aoc.ints("01_in.txt")

prev = depths[0]
countDeeper = 0
for depth in depths:
    if depth > prev:
        countDeeper += 1
    prev = depth

print(countDeeper)
