import numpy as np
import matplotlib.pyplot as plt
import custom_random


# Returns array which stores the moving average of the data
def simple_moving_avg(data, points_per_avg):
    n = len(data)
    avg_data = np.zeros(n-points_per_avg)

    # Taking the moving average
    for i in range(n-points_per_avg):
        avg_data[i] = np.mean(data[i:i+points_per_avg])

    return avg_data


DAYS_PER_YEAR = 365
NUM_YEARS = 10
NUM_DATA_POINTS = DAYS_PER_YEAR * NUM_YEARS
NUM_TRAJECTORIES = 10
S = np.array([-2, -1, 0, 1, 2])

points_per_avg = int(input('Enter number of data points per average: '))

for i in range(NUM_TRAJECTORIES):
    points_per_avg = 50

    # Randomly choosing points from S
    data = S[custom_random.randint(low=0, high=5, size=NUM_DATA_POINTS)]
    avg_data = simple_moving_avg(data, points_per_avg)

    # Plotting each trajectory
    plt.figure()
    plt.title('Trajectory: ' + str(i+1))
    plt.xlabel('Day')
    plt.ylabel('Moving Average')
    plt.plot(avg_data)

plt.show()
