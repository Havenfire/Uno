from py4j.java_gateway import JavaGateway
gateway = JavaGateway()                   # connect to the JVM




uno_game = gateway.entry_point.getUnoGame()      

uno_data = uno_game.runSimulation()


