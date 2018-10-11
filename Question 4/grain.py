import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import custom_random


# Returns an array which contains the sizes of different grains
def get_grain_sizes(psi):
    sizes = []

    # Iterating through 50 columns chosen randomly
    for i in custom_random.randint(low=0, high=512, size=50):
        column = psi[i]
        start_j = 0  # Stores the index of start of a grain
        end_j = 0    # Stores the index of end of a grain

        for j in range(1, 512):

            # Grain starts
            if column[j] - column[j - 1] > 0.1:
                start_j = j

            # Grain ends
            elif column[j] - column[j - 1] < -0.1:
                end_j = j
                sizes.append(end_j - start_j)

    return sizes


# Divides the grain sizes according to the range they lie in
def get_distribution(sizes, bin_size):
    dist = init_distribution_dict(bin_size, max(sizes))

    for s in sizes:
        rs = get_elem_range_str(s, bin_size)
        dist[rs] += 1

    return dist


# Initializes all entries of distribution dictionary to 0
# The size range string is used as the key to get the
# number of grains in that range
def init_distribution_dict(bin_size, max_element):
    dist = {}

    for r in range(1, max_element, bin_size):
        dist[get_elem_range_str(r, bin_size)] = 0

    return dist


# Returns range string for an element
# Eg. if bin_size = 10, elem = 15, return value is '11_20'
def get_elem_range_str(elem, bin_size):
    i = 1
    # Finding range in which the element lies
    while bin_size*i < elem:
        i += 1

    rs = str(bin_size*(i-1) + 1) + '_' + str(bin_size*i)

    return rs


def process_psi(file_name, bin_size=10):
    psi = pd.read_csv(filepath_or_buffer=file_name,
                      delimiter='  ', engine='python',
                      header=None)

    grain_sizes = get_grain_sizes(psi)
    dist = get_distribution(grain_sizes, bin_size)

    mean_size = np.mean(grain_sizes)
    frequency = 1 / (mean_size**2)
    print(file_name, 'Mean Size: ', mean_size)
    print(file_name, 'Frequency: ', frequency)
    plt.figure()
    plt.bar(list(dist.keys()), list(dist.values()))
    plt.title(file_name[0:file_name.rindex('.')])
    plt.xlabel('Sizes')
    plt.ylabel('Number of Grains')


bin_size = int(input('Enter the bin size: '))
process_psi('psi_1.dat', bin_size)
process_psi('psi_2.dat', bin_size)
plt.show()
