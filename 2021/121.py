import aoc

class Cave:
    def __init__(self, name):
        self.multi_visit = name.isupper()
        self.connections = set()
        self.active_connections = set()
        self.is_end = name == "end"

    def count_ways(self, from_cave):
        # add a path if this is the end cave
        if self.is_end:
            global paths
            paths += 1
            return

        # don't go further if this is a small cave that is already connected
        if not self.multi_visit and len(self.active_connections) > 0:
            return

        # visit all caves that are not visited from this cave yet
        for cave in self.connections^self.active_connections:
            self.active_connections.add(cave)
            cave.count_ways(self)
            self.active_connections.remove(cave)

caves = {}
paths = 0

# build up all connections
for line in aoc.lines("12_in.txt"):
    a, b = line.split("-")

    if not a in caves:
        caves[a] = Cave(a)
    if not b in caves:
        caves[b] = Cave(b)
    
    caves[a].connections.add(caves[b])
    caves[b].connections.add(caves[a])

start = caves["start"]
start.count_ways(None)
print(paths)
