import py4j.GatewayServer;

public class UnoGameEntryPoint {
    
    UnoGame u;
    public UnoGameEntryPoint(){
        u = new UnoGame(3, 500);
    }

    public UnoGame getUnoGame(){
        return u;
    }    
      public static void main(String[] args) {

        GatewayServer gatewayServer = new GatewayServer(new UnoGameEntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

}
