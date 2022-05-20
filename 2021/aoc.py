import re

def read_raw(path):
    return "".join(open(path).readlines())

def ints(input):
    # return a list of ints being separated by non-numerical characters
    if input.endswith(".txt"):
        return ints(read_raw(input))
    else:
        return [int(num) for num in re.split("\D+", input) if num != '']

def lines(input):
    # return lines separated by \n.
    if input.endswith(".txt"):
        return lines(read_raw(input))
    else:
        return input.split("\n")

def blocks(input):
    # return blocks separated by \n\n
    if input.endswith(".txt"):
        return blocks(read_raw(input))
    else:
        return input.split("\n\n")

def neighbors(grid, with_diagonals = True):
    # assign neighbors to each element in the grid
    for y in range(len(grid)):
        for x in range(len(grid[y])):
            neighbors = []
            for ny in range(max(0, y-1), y+2):
                for nx in range(max(0, x-1), x+2):
                    if not (x == nx and y == ny):
                        if with_diagonals or x == nx or y == ny:
                            try:
                                neighbors.append(grid[ny][nx])
                            except IndexError:
                                pass
            grid[y][x].neighbors = neighbors

def flat_grid(grid):
    # return each element in 2-D grid
    return [item for row in grid for item in row]
