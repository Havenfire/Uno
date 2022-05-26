import java.util.ArrayList;

public abstract class Agent {
	ArrayList<Card> hand = new ArrayList<Card>();
	
	
	public void drawCard(Card c){
		hand.add(c);
	}
	
	public void playCard(Card c) {
		hand.remove(hand.indexOf(c));
	}
	
	public abstract Card decideCard();

	protected abstract ArrayList<Card> getHand();
}

