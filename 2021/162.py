import aoc

class BitStream:
    def __init__(self, data, read_raw = False):
        self.index = 0
        if read_raw:
            self.bin_data = data
        else:          
            self.bin_data = "".join([f"{int(digit,16):04b}" for digit in aoc.lines(data)[0]])

    def next(self, bits = 1):
        data = self.bin_data[self.index:self.index+bits]
        self.index += bits
        return data

    def next_int(self, bits = 1):
        return int(self.next(bits),2)

    def has_next(self):
        return self.index < len(self.bin_data)

class Packet:
    def __init__(self, bit_stream):
        self.version = bit_stream.next_int(3)
        self.type = bit_stream.next_int(3)
        self.value = 0
        self.subpackets = []

        if self.type == 4:
            read_another = '1'
            while read_another == '1':
                read_another = bit_stream.next()
                self.value = self.value * 16 + bit_stream.next_int(4)
        else:
            length_type = bit_stream.next()
            if length_type == '0':
                length = bit_stream.next_int(15)
                sub_stream = BitStream(bit_stream.next(length), read_raw = True)
                while sub_stream.has_next():
                    self.subpackets.append(Packet(sub_stream))
            else:
                length = bit_stream.next_int(11)
                for _ in range(length):
                    self.subpackets.append(Packet(bit_stream))
            if self.type == 0:
                self.value = sum([packet.value for packet in self.subpackets])
            elif self.type == 1:
                product = 1
                for packet in self.subpackets:
                    product *= packet.value
                self.value = product
            elif self.type == 2:
                self.value = min([packet.value for packet in self.subpackets])
            elif self.type == 3:
                self.value = max([packet.value for packet in self.subpackets])
            elif self.type == 5:
                self.value = 1 if self.subpackets[0].value > self.subpackets[1].value else 0
            elif self.type == 6:
                self.value = 1 if self.subpackets[0].value < self.subpackets[1].value else 0
            elif self.type == 7:
                self.value = 1 if self.subpackets[0].value == self.subpackets[1].value else 0

    def __repr__(self):
        return f"V:{self.version} T:{self.type} val:{self.value} sub:{self.subpackets}"

print(Packet(BitStream("16_in.txt")).value)
