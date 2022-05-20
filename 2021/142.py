import aoc

class Pair:
    def __init__(self, pair, insert):
        self.pair = pair
        self.insert = insert
        self.count = 0
        self.new_count = 0

    def add_count(self):
        to_pair1 = self.pair[0] + self.insert
        to_pair2 = self.insert + self.pair[1]
        
        pairs[to_pair1].new_count += self.count
        pairs[to_pair2].new_count += self.count

    def apply_new_count(self):
        self.count = self.new_count
        self.new_count = 0

    def __repr__(self):
        return str(self.count)

# read polymer and templates
pairs = dict()
polymer, raw_templates = aoc.blocks("14_in.txt")
for pair, insert in [line.split(" -> ") for line in aoc.lines(raw_templates)]:
    pairs[pair] = Pair(pair, insert)
for pair_id in range(len(polymer) - 1):
    pair = polymer[pair_id:pair_id+2]
    pairs[pair].count = pairs[pair].count + 1

# do 40 iterations of inserting
for _ in range(40):
    for pair in pairs.values():
        pair.add_count()
    for pair in pairs.values():
        pair.apply_new_count()

# count single letters
counts = dict()
for pair in pairs:
    counts[pair[0]] = 0
    counts[pair[1]] = 0
for pair in pairs.values():
    counts[pair.pair[0]] = counts[pair.pair[0]] + pair.count
    counts[pair.pair[1]] = counts[pair.pair[1]] + pair.count

# print result
print((max(counts.values()) - min(counts.values()) + 1) // 2)
