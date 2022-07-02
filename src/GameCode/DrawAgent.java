package GameCode;

import java.util.ArrayList;

public class DrawAgent extends Agent{

	int playerNum;
	ArrayList<FlipCard> playableHand = new ArrayList<FlipCard>();
	ArrayList<FlipCard> heldHand = new ArrayList<FlipCard>();

	DrawAgent(int newPlayerNum){
		playerNum = newPlayerNum;
	}


	public int getPlayerNum(){
		return playerNum;
	}
	
	public ArrayList<FlipCard> getHand(){
		return hand;
		
	}

	public FlipCard playCard(String currentVal, String currentColor) {
		formPlayableHand(currentVal, currentColor);
		if(playableHand.size() == 0){
			return null;
		}

		FlipCard playedCard = playableHand.remove((int)(Math.random()*playableHand.size()));
		hand.remove(hand.indexOf(playedCard));
		playableHand.clear();
		heldHand.clear();

		return playedCard;
	}

	public String playedWildCard(){
		return colors[(int)(Math.random()*4)];
	}

	public void formPlayableHand(String curVal, String curColor) {

		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).cVal.equals(curVal) || hand.get(i).color.equals(curColor) || hand.get(i).color.equals("")){
				playableHand.add(hand.get(i));
			}
		}

		for(int i = 0; i < playableHand.size(); i++){
			if(playableHand.get(i).cVal.equals("draw2") || (playableHand.get(i).cVal.equals("draw1"))){
				heldHand.add(playableHand.get(i));
			}

		}
		if(heldHand.size() > 0){
			playableHand = heldHand;
			heldHand.clear();
		}
	}


	@Override
	public FlipCard playBCard(String currentBVal, String currentBColor) {
		formPlayableBHand(currentBVal, currentBColor);
		if(playableHand.size() == 0){
			return null;
		}

		FlipCard playedCard = playableHand.remove((int)(Math.random()*playableHand.size()));
		hand.remove(hand.indexOf(playedCard));
		playableHand.clear();
		heldHand.clear();

		return playedCard;
	}

	public String playedBWildCard(){
		return bColors[(int)(Math.random()*4)];
	}

	private void formPlayableBHand(String currentBVal, String currentBColor) {
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).bVal.equals(currentBVal) || hand.get(i).bColor.equals(currentBColor) || hand.get(i).bColor.equals("")){
				playableHand.add(hand.get(i));
			}
		}

		for(int i = 0; i < playableHand.size(); i++){
			if(playableHand.get(i).bVal.equals("drawX") || (playableHand.get(i).bVal.equals("draw5"))){
				heldHand.add(playableHand.get(i));
			}

		}
		if(heldHand.size() > 0){
			playableHand = heldHand;
			heldHand.clear();
		}
	
	}

	
	
	
}
