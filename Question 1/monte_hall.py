import numpy as np
import custom_random


def never_swap(correct, selection):
    n = len(correct)
    # Omitting redundant step of removing wrong answer
    num_wins = np.sum(correct == selection)
    return num_wins / n


def always_swap(correct, selection):
    n = len(correct)
    num_wins = 0

    for i in range(n):
        available_choices = [0, 1, 2]
        available_choices.remove(correct[i])

        if available_choices.__contains__(selection[i]):
            available_choices.remove(selection[i])

        # If initial selection was not correct answer
        # then contestant will win after swapping
        # Since list of available choices for the host
        # to remove will have only one element
        if len(available_choices) == 1:
            num_wins += 1

    return num_wins / n


def coin_toss(correct, selection):
    n = len(correct)
    num_wins = 0

    for i in range(n):
        available_choices = [0, 1, 2]

        # Removing a wrong answer from available choices
        available_choices.remove(correct[i])

        if available_choices.__contains__(selection[i]):
            available_choices.remove(selection[i])

        available_choices.remove(custom_random.choice(available_choices))
        available_choices.append(correct[i])  # Adding the correct choice back

        if not available_choices.__contains__(selection[i]):
            available_choices.append(selection[i])

        # Randomly selecting one door from the remaining two
        new_selection = custom_random.choice(available_choices)

        if new_selection == correct[i]:
            num_wins += 1

    return num_wins / n


NUM_SIMULATIONS = 10000
correct = custom_random.randint(low=0, high=3, size=NUM_SIMULATIONS)
selection = custom_random.randint(low=0, high=3, size=NUM_SIMULATIONS)

print('Never Swap:', never_swap(correct, selection))
print('Always Swap:', always_swap(correct, selection))
print('Coin Toss:', coin_toss(correct, selection))
