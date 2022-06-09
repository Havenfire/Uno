import java.util.ArrayList;

public abstract class Agent {
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> playableHand = new ArrayList<Card>();
	
	
	public void drawCard(Card c){
		hand.add(c);
	}
	
	protected abstract ArrayList<Card> getHand();
}

