import java.util.ArrayList;

public class RandomAgent extends Agent{

	int playerNum;
	ArrayList<Card> playableHand = new ArrayList<Card>();
	RandomAgent(int newPlayerNum){
		playerNum = newPlayerNum;
	}
	
	public Card decideCard(Card inPlay) {
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).canPlay(inPlay)){
				playableHand.add(hand.get(i));
			}
		}
		if(playableHand.size() == 0){
			return null;
		}
		Card x = playableHand.get((int) (Math.random() * playableHand.size()));
		System.out.println("Player Number " + playerNum + " played " + x);

		return playCard(x);
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
	
}
