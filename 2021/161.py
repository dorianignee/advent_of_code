import aoc

class BitStream:
    def __init__(self, data, read_raw = False):
        self.index = 0
        if read_raw:
            self.bin_data = data
        else:
            raw_data = aoc.lines(data)[0]            
            self.bin_data = "".join([f"{int(digit,16):04b}" for digit in raw_data])

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
        global all_packets
        all_packets.append(self)
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

    def __repr__(self):
        return f"V:{self.version} T:{self.type} val:{self.value} sub:{self.subpackets}"

all_packets = []
Packet(BitStream("16_in.txt"))
print(sum([packet.version for packet in all_packets]))
