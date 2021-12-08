import aoc

crabs = aoc.ints("07_in.txt")
fuel_usages = []

for position in range(min(crabs), max(crabs)):
    fuel_usages.append(sum([abs(crab-position) for crab in crabs]))

print("min fuel:", min(fuel_usages))
