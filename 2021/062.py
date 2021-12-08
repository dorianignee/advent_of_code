import aoc

fishes = aoc.ints("06_in.txt")
timer_count = []

for days in range(9):
    timer_count.append(sum([1 for fish in fishes if fish == days]))

for day in range(256):
    new_fishes = timer_count[0]
    timer_count = timer_count[1:]
    timer_count.append(new_fishes)
    timer_count[6] += new_fishes

print(sum(timer_count))
