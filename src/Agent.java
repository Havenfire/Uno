import java.util.ArrayList;

public abstract class Agent {
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> playableHand = new ArrayList<Card>();
	
	
	public void drawCard(Card c){
		hand.add(c);
	}
	
	
	
	public Card decideCard(Card inPlay) {
		return null;
	}
	public Card playCard() {
		return null;
	}

	protected abstract ArrayList<Card> getHand();
}

