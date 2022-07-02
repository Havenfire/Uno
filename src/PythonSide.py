from py4j.java_gateway import JavaGateway
import matplotlib.pyplot as plt

gateway = JavaGateway()                   # connect to the JVM

uno_game = gateway.entry_point.getUnoGame()      

uno_data = uno_game.runSimulation() #This is an array of all uno Game Data

data_list = []

for x in uno_data:
    data_list.append(x)

max_data_list = max(data_list)
bin_size = 25
bin_list = [-25]

for i in range(int(max_data_list/25)+2):
    bin_list.append(i*25)
    
title = 'Uno Flip Game of ' + str(uno_game.getPlayerCount()) + ' players (N = ' + str(uno_game.getDataCount()) + ')'
plt.title(title)
plt.xlabel('Number of Turns')
plt.ylabel('Frequency')
plt.hist(data_list,bins=bin_list)
plt.show()

