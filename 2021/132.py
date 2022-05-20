import aoc

# read file
raw_coords, raw_instr = aoc.blocks("13_in.txt")

# determine max x and y values
coords = [(aoc.ints(coord)) for coord in aoc.lines(raw_coords)]
max_x = max([x for x, y in coords])
max_y = max([y for x, y in coords])

# setup grid
grid = [[False]*(max_x+1) for y in range(max_y+1)]

# read True cells
for x, y in coords:
    grid[y][x] = True

# read instructions
for instr_line in aoc.lines(raw_instr):
    instr = instr_line.split(" ")[2]
    dimension, value = instr.split("=")
    value = int(value)

    # execute instructions
    if dimension == "x":
        # new width
        width = max(len(grid[0])-value, value)
        new_grid = [[False]*width for y in range(len(grid))]

        # go through all rows
        for row_id in range(len(grid)):
            # generate overlapping subrows
            sub1 = grid[row_id][:value]
            sub2 = grid[row_id][:value:-1]

            # swap if list 2 is larger than list 1
            if len(sub2) > len(sub1):
                temp = sub1
                sub1 = sub2
                sub2 = temp

            # OR together the contents of both lists
            for cell_id in range(-len(sub2),0):
                sub1[cell_id] = sub1[cell_id] or sub2[cell_id]

            # save new row in new_grid
            new_grid[row_id] = sub1
    else:
        #new height
        height = max(len(grid)-value, value)
        new_grid = [[False]*len(grid[0]) for y in range(height)]

        # generate overlapping subgrids
        sub1 = grid[:value]
        sub2 = grid[:value:-1]

        # swap if list 2 is larger than list 1
        if len(sub2) > len(sub1):
            temp = sub1
            sub1 = sub2
            sub2 = temp

        new_grid = sub1[:]

        # OR together the contents of both lists
        for row_id in range(-len(sub2),0):
            new_grid[row_id] = [a or b for a, b in zip(sub1[row_id], sub2[row_id])]

    grid = new_grid

# print contents of grid
for row in grid:
    print("".join(["#" if cell else "." for cell in row]))
