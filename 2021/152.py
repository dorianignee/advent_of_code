import aoc

class Chiton:
    def __init__(self, risk):
        self.risk = int(risk)
        self.total_risk = 10000
        self.neighbors = []

    def find_way(self):
        global recalls
        for cell in self.neighbors:
            if cell.total_risk > (self.total_risk + cell.risk):
                cell.total_risk = self.total_risk + cell.risk
                recalls.add(cell)

    def __repr__(self):
        return str(self.risk)

# initialize grid
input_grid = [[Chiton(digit) for digit in line] for line in aoc.lines("15_in.txt")]
grid = [[None]*(len(input_grid[0])*5) for _ in range(len(input_grid)*5)]
for row_id in range(len(input_grid)):
    grid[row_id][0:len(input_grid[row_id])] = input_grid[row_id]

# extend grid
# - first column
for row_id in range(len(input_grid), len(grid)):
    for col_id in range(0, len(input_grid[0])):
        grid[row_id][col_id] = Chiton(grid[row_id - len(input_grid)][col_id].risk % 9 + 1)

# - other columns
for row_id in range(0, len(grid)):
    for col_id in range(len(input_grid[0]), len(grid[row_id])):
        grid[row_id][col_id] = Chiton(grid[row_id][col_id - len(input_grid[0])].risk % 9 + 1)

aoc.neighbors(grid, with_diagonals = False)

# special values for first cell
first_cell = grid[0][0]
first_cell.risk = 0
first_cell.total_risk = 0

# find a way
recalls = {first_cell}
while len(recalls) > 0:
    current_queue = recalls.copy()
    recalls = set()
    for cell in current_queue:
        cell.find_way()

# print result
print(grid[-1][-1].total_risk)
