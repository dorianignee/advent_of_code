import aoc

lines = aoc.lines("08_in.txt")

displays = ""

for line in lines:
    displays += line[60:]

print(sum([1 for display in displays.split(" ") if len(display) in (2,3,4,7)]))
