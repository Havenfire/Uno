import java.util.ArrayList;

public class RandomAgent extends Agent{

	int playerNum;
	ArrayList<Card> playableHand = new ArrayList<Card>();
	RandomAgent(int newPlayerNum){
		playerNum = newPlayerNum;
	}
	Card plannedCard;

	public Card decideCard(Card inPlay) {
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).canPlay(inPlay)){
				playableHand.add(hand.get(i));
			}
		}
		if(playableHand.size() == 0){
			return null;
		}
		plannedCard = playableHand.get((int) (Math.random() * playableHand.size()));

		return plannedCard;
	}

	public int getPlayerNum(){
		return playerNum;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
		
	}

	public Card playCard(Card c) {
		return hand.remove(hand.indexOf(c));
	}
	
	public Card playCard() {
		Card x = hand.remove(hand.indexOf(plannedCard));
		plannedCard = null;
		return x;
	}
	
	
}
