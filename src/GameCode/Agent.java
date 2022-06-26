package GameCode;

import java.util.ArrayList;

public abstract class Agent {
	ArrayList<FlipCard> hand = new ArrayList<FlipCard>();
	ArrayList<FlipCard> playableHand = new ArrayList<FlipCard>();
	final String[] colors = {"red","blue","green","yellow"};
	
	public void drawCard(FlipCard flipCard){
		hand.add(flipCard);
	}
	
	protected abstract ArrayList<FlipCard> getHand();

	public abstract String playedWildCard();

	public abstract FlipCard playCard(String currentVal, String currentColor);

	public abstract void formPlayableHand(String currentVal, String currentColor);

	public FlipCard playDrawnCard(String currentVal, String currentColor, FlipCard c){
		if(c.cVal == currentVal || c.color == currentColor || c.cVal == "Draw 4" || c.cVal == "Wild Card"){
			return c;
		}
		return null;
	}

}

