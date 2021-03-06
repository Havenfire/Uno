package GameCode;

import java.util.ArrayList;

public abstract class Agent {
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> playableHand = new ArrayList<Card>();
	final String[] colors = {"red","blue","green","yellow"};
	
	public void drawCard(Card c){
		hand.add(c);
	}
	
	protected abstract ArrayList<Card> getHand();

	public abstract String playedWildCard();

	public abstract Card playCard(String currentVal, String currentColor);

	public abstract void formPlayableHand(String currentVal, String currentColor);
}

