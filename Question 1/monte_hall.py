import numpy as np
import custom_random


# Simulates the condition when the contestant never
# swaps his/her choice
def never_swap(correct, selection):
    n = len(correct)
    # Omitting redundant step of removing wrong answer
    num_wins = np.sum(correct == selection)
    return num_wins / n


# Simulates the condition when the contestant always
# swaps his/her choice
def always_swap(correct, selection):
    n = len(correct)
    num_wins = 0

    for i in range(n):
        # Getting the set of available choices for
        # the host to remove
        # Host cannot remove the correct choice
        available_choices = [0, 1, 2]
        available_choices.remove(correct[i])

        # Host cannot remove the initial selection as well
        # Checking if initial selection was already removed
        # or not, if not then removing it
        if available_choices.__contains__(selection[i]):
            available_choices.remove(selection[i])

        # If initial selection was not correct answer
        # then contestant will win after swapping
        # Since list of available choices for the host
        # to remove will have only one element
        if len(available_choices) == 1:
            num_wins += 1

    return num_wins / n


# Simulates the condition when the user tosses a coin
# To select the new door
def coin_toss(correct, selection):
    n = len(correct)
    num_wins = 0

    for i in range(n):
        # Getting the set of available choices for
        # the host to remove
        # Host cannot remove the correct choice
        available_choices = [0, 1, 2]
        available_choices.remove(correct[i])

        # Host cannot remove the initial selection as well
        # Checking if initial selection was already removed
        # or not, if not then removing it
        if available_choices.__contains__(selection[i]):
            available_choices.remove(selection[i])

        # Now the list only contains wrong answers
        # Randomly removing one from the list
        available_choices.remove(custom_random.choice(available_choices))

        # Adding the correct choice back
        available_choices.append(correct[i])

        # Adding the selected choice back
        # If the initial selection was the correct choice
        # then no need to add it again
        if not available_choices.__contains__(selection[i]):
            available_choices.append(selection[i])

        # Randomly selecting one door from the remaining two
        new_selection = custom_random.choice(available_choices)

        if new_selection == correct[i]:
            num_wins += 1

    return num_wins / n


num_simulations = int(input('Enter the number of simulations: '))
correct = custom_random.randint(low=0, high=3, size=num_simulations)
selection = custom_random.randint(low=0, high=3, size=num_simulations)

print('Never Swap:', never_swap(correct, selection))
print('Always Swap:', always_swap(correct, selection))
print('Coin Toss:', coin_toss(correct, selection))
