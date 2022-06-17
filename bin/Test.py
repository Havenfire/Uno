import matplotlib.pyplot as plt

uno_data = [1,1,1,1,2,3,4,5,6,7,8,9,10,10,12,13,14,15,16,17,17]

plt.hist(uno_data,bins=[0,5,10,15,20])

plt.title('Uno Turn Count')
plt.xlabel('Turn Count')
plt.ylabel('Frequency')

plt.show()