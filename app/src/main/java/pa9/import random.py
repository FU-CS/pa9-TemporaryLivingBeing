import random
heads, tails = 0, 0
longest = 0
curr = 0
previous = None
longestStreak = 0
for i in range(1000):
    rand = random.randint(0, 1)

    if rand == 0:
        heads += 1
        print(f"The {i}th number is heads.")
    
    else:
        tails += 1
        print(f"The {i}th number is tails.")

    if (not previous) or (rand == previous):
        previous = rand
        curr += 1
    else:
        curr = 0
    if curr >= longest:
        longest = curr
        longestStreak = rand
    previous = rand
    
print(f"{heads} amount of heads and {tails} amount of tails longest streak {longestStreak} is {longest}")