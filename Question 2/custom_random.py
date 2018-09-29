import numpy as np
import time


# Generates random numbers form [low, high)
# Uses linear congruential generator algorithm
def randint(low, high, size):
    a = 2147483629
    c = 2147483587
    m = 2**31 - 1
    seed = int(10 ** 10 * time.process_time())

    rand = np.zeros(size, dtype=int)
    rand[0] = (a * seed + c) % m

    for i in range(1, size):
        rand[i] = (a * rand[i-1] + c) % m

    # Bringing random numbers to the required range
    rand = low + rand % (high-low)

    return rand


# Choose a random number from an array
def choice(arr):
    arr_len = len(arr)
    rand = randint(0, arr_len, 1)
    return arr[rand[0]]
