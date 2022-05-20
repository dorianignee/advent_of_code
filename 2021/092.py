import aoc

class Point:
    def __init__(self, value, x, y):
        self.value = value
        self.x = x
        self.y = y
        self.neighbors = []
        self.basin_id = 0
        
    def get_neighbors(self):
        if self.x > 0:
            self.neighbors.append(points[self.y][self.x-1])
        if self.y > 0:
            self.neighbors.append(points[self.y-1][self.x])
        if self.x < len(points[self.y])-1:
            self.neighbors.append(points[self.y][self.x+1])
        if self.y < len(points)-1:
            self.neighbors.append(points[self.y+1][self.x])

    def is_low_point(self):
        return all([self.value < neighbor.value for neighbor in self.neighbors])

    def set_basin(self, basin_id):
        if self.value < 9 and self.basin_id == 0:
            self.basin_id = basin_id
            for point in self.neighbors:
                if point.value >= self.value:
                    point.set_basin(basin_id)



lines = aoc.lines("09_in.txt")
points = []
for y in range(len(lines)):
    lpoints = []
    for x in range(len(lines[y])):
        lpoints.append(Point(int(lines[y][x]),x,y))
    points.append(lpoints)

all_points = [point for line in points for point in line]

for point in all_points:
    point.get_neighbors()

low_points = [point for point in all_points if point.is_low_point()]

basin_id = 1
for point in low_points:
    point.set_basin(basin_id)
    basin_id += 1

basin_sizes = [0]*len(low_points)

for point in all_points:
    if point.basin_id > 0:
        basin_sizes[point.basin_id - 1] += 1
basin_sizes.sort(reverse=True)

print(basin_sizes[0] * basin_sizes[1] * basin_sizes[2])

