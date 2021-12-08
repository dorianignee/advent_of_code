import aoc
commands = aoc.lines("02_in.txt")
x = 0
y = 0
aim = 0

for command in commands:
    direction, distance = command.split(" ")
    distance = int(distance)
    
    if direction == "down":
        aim += distance
    elif direction == "up":
        aim -= distance
    else:
        x += distance
        y += aim * distance

print(x*y)
