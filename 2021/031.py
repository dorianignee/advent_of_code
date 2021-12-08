import aoc
lines = aoc.lines("03_in.txt")

sums = [0] * len(lines[0])

for line in lines:
    for i in range(len(line)):
        sums[i] += int(line[i])

gamma = int("".join([str(int(bit_sum >= len(lines)//2)) for bit_sum in sums]),2)
epsilon = int("".join([str(int(bit_sum < len(lines)//2)) for bit_sum in sums]),2)

print(gamma*epsilon)
