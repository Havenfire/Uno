import java.util.ArrayList;

public class RandomAgent extends Agent{

	int playerNum;
	ArrayList<Card> playableHand = new ArrayList<Card>();
	RandomAgent(int newPlayerNum){
		playerNum = newPlayerNum;
	}


	public int getPlayerNum(){
		return playerNum;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
		
	}

	//
	public Card playCard(String currentVal, String currentColor) {
		formPlayableHand(currentVal, currentColor);
		if(playableHand.size() == 0){
			return null;
		}

		Card playedCard = playableHand.remove((int)(Math.random()*playableHand.size()));
		hand.remove(hand.indexOf(playedCard));
		playableHand.clear();

		return playedCard;
	}

	public String playedWildCard(){
		return colors[(int)(Math.random()*4)];
	}


	@Override
	public void formPlayableHand(String curVal, String curColor) {
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).cVal.equals(curVal) || hand.get(i).color.equals(curColor) || hand.get(i).color.equals("")){
			
				playableHand.add(hand.get(i));
			}

		}

	}
	
	
	
}
