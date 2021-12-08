import aoc

def triangle_value(num):
    return (num*(num+1))//2

crabs = aoc.ints("07_in.txt")
fuel_usages = []

for position in range(min(crabs), max(crabs)):
    fuel_usages.append(sum([triangle_value(abs(crab-position)) for crab in crabs]))

print("min fuel:", min(fuel_usages))
