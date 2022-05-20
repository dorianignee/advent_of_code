import aoc

# read polymer and templates
polymer, raw_templates = aoc.blocks("14_in.txt")
templates = dict(line.split(" -> ") for line in aoc.lines(raw_templates))

# do 10 iterations of inserting
for _ in range(10):
    new_polymer = ""
    for index in range(len(polymer)-1):
        pair = polymer[index:index+2]
        new_polymer += polymer[index] + templates[pair]
    new_polymer += polymer[-1]
    polymer = new_polymer

# count letters
counts = dict([(char, 0) for char in polymer])
for char in polymer:
    counts[char] = counts[char] + 1

# print result
print(max(counts.values()) - min(counts.values()))
