from py4j.java_gateway import JavaGateway
import matplotlib.pyplot as plt
import numpy as np


gateway = JavaGateway()                   # connect to the JVM

uno_game = gateway.entry_point.getUnoGame()      

uno_data = uno_game.runSimulation() #This is an array of all unoGame Data

data_list = []

for x in uno_data:
    data_list.append(x)

plt.hist(data_list)
plt.title('Uno Game')
plt.xlabel('Number of Turns')
plt.ylabel('Frequency')
plt.show()

