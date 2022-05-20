import aoc

class Cave:
    def __init__(self, name):
        self.name = name
        self.multi_visit = name.isupper()
        self.connections = set()
        self.active_connections = []
        self.is_start = name == "start"
        self.is_end = name == "end"

    def count_ways(self):
        global paths
        global cur_path
        global small_twice

        me_set_small_twice = False
        
        # add a path if this is the end cave
        if self.is_end:
            paths += 1
            return

        # don't go further if this is a small cave that is already visited
        # added rule that one small cave can be visited twice
        if not self.multi_visit:
            if self.is_start and len(cur_path) > 1:
                return
            if len(self.active_connections) > 0:
                if small_twice == "":
                    me_set_small_twice = True
                    small_twice = self.name
                else:
                    return

        # visit all caves that are not visited from this cave yet
        for cave in self.connections:
            cur_path.append(self)
            self.active_connections.append(cave)
            cave.count_ways()
            self.active_connections.pop()
            cur_path.pop()

        if me_set_small_twice:
            small_twice = ""

caves = {}
cur_path = []
paths = 0
small_twice = ""

# build up all connections
for line in aoc.lines("12_in.txt"):
    a, b = line.split("-")

    if not a in caves:
        caves[a] = Cave(a)
    if not b in caves:
        caves[b] = Cave(b)
    
    caves[a].connections.add(caves[b])
    caves[b].connections.add(caves[a])

caves["start"].count_ways()
print(paths)
