import aoc

fishes = aoc.ints("06_in.txt")

for day in range(80):
    for fish_id in range(len(fishes)):
        if fishes[fish_id] == 0:
            fishes[fish_id] = 6
            fishes.append(8)
        else:
            fishes[fish_id] -= 1

print(len(fishes))
