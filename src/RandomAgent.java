import java.util.ArrayList;

public class RandomAgent extends Agent{

	int playerNum;
	RandomAgent(int newPlayerNum){
		playerNum = newPlayerNum;
	}
	
	@Override
	public Card decideCard() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPlayerNum(){
		return playerNum;
	}
	
	public ArrayList<Card> getHand(){
		System.out.println(hand);
		return hand;
		
	}
	
}
