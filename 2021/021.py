import aoc
commands = aoc.lines("02_in.txt")
x = 0
y = 0

for command in commands:
    direction, distance = command.split(" ")
    distance = int(distance)
    
    if direction == "down":
        y += distance
    elif direction == "up":
        y -= distance
    else:
        x += distance

print(x*y)
