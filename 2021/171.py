import aoc
import re

inp = aoc.lines("17_in.txt")[0]
min_x, max_x, min_y, max_y = re.match('.+x=(-?\d+)\.+(-?\d+), y=(-?\d+)\.+(-?\d+)', inp).groups()
min_x, max_x, min_y, max_y = int(min_x), int(max_x), int(min_y), int(max_y)

# find max y
max_y_values = []
for vy_start in range(min_y, 1000):
    pos_y = 0
    cur_max_y = 0
    for vy in range(vy_start, -1002, -1):
        pos_y += vy
        cur_max_y = max(cur_max_y, pos_y)
        if pos_y < min_y:
            break
        if pos_y in range(min_y, max_y+1):
            max_y_values.append(cur_max_y)

# print result
print(max(max_y_values))
