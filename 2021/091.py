import aoc

depths = [[int(z) for z in line] for line in aoc.lines("09_in.txt")]
low_points = []

for y in range(len(depths)):
    for x in range(len(depths[0])):
        adjacent = []
        if x > 0:
            adjacent.append(depths[y][x-1])
        if y > 0:
            adjacent.append(depths[y-1][x])
        if x < len(depths[0])-1:
            adjacent.append(depths[y][x+1])
        if y < len(depths)-1:
            adjacent.append(depths[y+1][x])
        if all([depths[y][x] < neighbor for neighbor in adjacent]):
            low_points.append(depths[y][x])

print(sum(low_points)+len(low_points))
        
