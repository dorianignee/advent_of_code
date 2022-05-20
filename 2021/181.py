import aoc

class Pair:
    def __init__(self, value):
        input_pair = eval(value) if type(value) is str else value

        if type(input_pair[0]) is list:
            self.left = Pair(input_pair[0])
        elif type(input_pair[0]) is int:
            self.left = BoxedInt(input_pair[0])
        else:
            self.left = input_pair[0]

        if type(input_pair[1]) is list:
            self.right = Pair(input_pair[1])
        elif type(input_pair[1]) is int:
            self.right = BoxedInt(input_pair[1])
        else:
            self.right = input_pair[1]
        

    def __repr__(self):
        return f"[{self.left},{self.right}]"

    def __add__(self, other):
        return Pair([self, other])

    def __radd__(self, other):
        if other == None:
            return self
        return self + other

    def chain(self):
        # store all ints in a list, so you can easily decide, which is previous
        # and which is next
        global chain
        if type(self.left) is BoxedInt:
            chain.append(self.left)
        else:
            self.left.chain()
            
        if type(self.right) is BoxedInt:
            chain.append(self.right)
        else:
            self.right.chain()
                
    def reduce(self, nesting_level = 0):
        global was_reduced, chain

        if type(self.left) is BoxedInt:
            if int(self.left) > 9: # split left element
                self.left = split(self.left)
                return
        elif nesting_level == 3: # explode left element
            chain_index = chain.index(self.left.left)
            if 
            
                                
# With a BoxedInt, we are able to save an int as reference,
# so a Pair can change a value of a different Pair
class BoxedInt:
    def __init__(self, value):
        self.value = value

    def __add__(self, other):
        return BoxedInt(int(self) + int(other))

    def __mul__(self, other):
        return BoxedInt(int(self) * int(other))

    def __int__(self):
        return self.value

    def __repr__(self):
        return str(int(self))

def split(boxed_int):
    global was_reduced
    was_reduced = True
    return Pair(int(boxed_int) // 2, round(int(boxed_int)/2))

def explode(pair):
    global chain, was_reduced
    pos = chain.index(pair.left)
    if pos > 0:
        chain[pos-1] += pair.left
    if pos < len(chain)-2:
        chain[pos+2] += pair.right
    return BoxedInt(0)
    
result = None
for line in aoc.lines("18_in.txt"):
    result += Pair(line)
    was_reduced = True
    while was_reduced:
        # Build int chain
        chain = list()
        result.chain()
        chain[0].prev = None
        chain[-1].next = None
        for index in range(len(chain)-1):
            chain[index].next = chain[index+1]
            chain[index+1].prev = chain[index]

        # reduce
        was_reduced = False
        result.reduce()
