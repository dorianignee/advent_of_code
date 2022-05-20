import aoc

class Octo:
    def __init__(self, power):
        self.power = power
        self.flashed = False

    def flash(self):
        global flash_count
        if self.power > 9:
            self.power = 0
            self.flashed = True
            flash_count += 1
            for octo in [octo for octo in self.neighbors if octo.flashed == False]:
                octo.power += 1
    
def turn():
    global flash_count
    global total_flash_count
    
    for octo in aoc.flat_grid(grid):
        octo.flashed = False
        octo.power += 1
        
    flash_count = 1    
    while flash_count > 0:
        flash_count = 0
        for octo in aoc.flat_grid(grid):
            octo.flash()
        total_flash_count += flash_count
    
lines = aoc.lines("11_in.txt")
grid = [[Octo(int(power)) for power in line] for line in lines]
aoc.neighbors(grid)
total_flash_count = 0

for _ in range(100):
    turn()

print(total_flash_count)
