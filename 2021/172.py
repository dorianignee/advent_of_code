import aoc
import re

inp = aoc.lines("17_in.txt")[0]
min_x, max_x, min_y, max_y = re.match('.+x=(-?\d+)\.+(-?\d+), y=(-?\d+)\.+(-?\d+)', inp).groups()
min_x, max_x, min_y, max_y = int(min_x), int(max_x), int(min_y), int(max_y)

# find possible step counts of x
x_step_counts = []
for vx_start in range(0, max_x+1):
    steps = 0
    pos_x = 0
    for vx in range(vx_start, -1, -1):
        steps += 1
        pos_x += vx
        if pos_x in range(min_x, max_x+1):
            x_step_counts.append((steps, vx_start, vx))

# x_zero_velocities contains the step count after which a certain
# start velocity leads to a horizontal stop inside the target area
# these are valid for all step counts equal or larger the value in the list
x_zero_velocities = [step_count[0:2] for step_count in x_step_counts if step_count[2] == 0]

# the remaining values in x_step_counts are only valid on
# the exact step they appear
x_step_counts = [step_count[0:2] for step_count in x_step_counts if step_count[2] > 0]

# find possible step counts of y
values = []
for vy_start in range(min_y, 1000):
    steps = 0
    pos_y = 0
    for vy in range(vy_start, -1002, -1):
        steps += 1
        pos_y += vy
        if pos_y < min_y:
            break
        if pos_y in range(min_y, max_y+1):
            # add single step counts
            values += [(step_count[1], vy_start) for step_count in x_step_counts if step_count[0] == steps]
            # add multiple step counts
            values += [(step_count[1], vy_start) for step_count in x_zero_velocities if step_count[0] <= steps]

# print result
print(len(set(values)))
