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

# initialize grid
grid = [[Chiton(digit) for digit in line] for line in aoc.lines("15_in.txt")]
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
