package GameCode;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

	private static final int STARTING_HAND_SIZE = 7;
	ArrayList<FlipCard> deck = new ArrayList<FlipCard>();
	ArrayList<FlipCard> discard = new ArrayList<FlipCard>();

	int playerCount;
	Agent[] playerTurn;
	FlipCard inPlay;
	boolean gameOver;
	String gameColor;
	String gameVal;
	int gameDirection;
	int currentTurn = Integer.MAX_VALUE;
	int victim;
	FlipCard inQuestion;
	int numberOfTurns;
	boolean isFlipped;

	//custom rules
	boolean stackingDrawCardsRule;
	boolean drawUntilPlayableRule;
	boolean drawnCardPlayableRule;

	
	GameBoard(int newPlayerCount, boolean setStackingDrawCardsRule, boolean setDrawUntilPlayableRule, boolean setDrawnCardPlayableRule){
		if(newPlayerCount > 8 || newPlayerCount < 2) {
			throw new Error("Check Number of Players");
		}
		stackingDrawCardsRule = setStackingDrawCardsRule; //TODO
		drawUntilPlayableRule = setDrawUntilPlayableRule;
		drawnCardPlayableRule = setDrawnCardPlayableRule;
		playerCount = newPlayerCount;
	}
	
	public void initialize(){
		playerTurn = new Agent[playerCount];
		for(int i = 0; i < playerCount; i++) {
			RandomAgent r = new RandomAgent(i);
			playerTurn[i] = r;			
		}
		gameDirection = 1;
		numberOfTurns = 0;
		gameOver = false;
		isFlipped = false;

		createDeck();
		Collections.shuffle(deck);;
		dealHands();
	}
	
	public int startGame(){
		inPlay = dealCard();
		discard.add(inPlay);
		System.out.println("First Card is " + inPlay);
		gameColor = inPlay.color;
		gameVal = inPlay.cVal;
		FlipCard playerCard;


		while(!gameOver) {


			//currentTurn is set initially to 0, then sets itself to a new turn
			currentTurn = whoTurn(currentTurn);
			numberOfTurns++;
			if(numberOfTurns > 1000){
				System.out.println("Game Lasted Too Long");
				break;
			}

			System.out.println("");
			System.out.println("It is Player Number " + currentTurn + "'s Turn");
			
			System.out.println(playerTurn[currentTurn].getHand());

			//player plays a card OR draws
			System.out.println("Current Board Values: " + gameVal + " " + gameColor);
			playerCard = (playerTurn[currentTurn]).playCard(gameVal,gameColor);

			//Cannot play, draws a card
			if(playerCard == null){
				if(drawUntilPlayableRule){
					while(playerCard == null){
						playerTurn[currentTurn].drawCard(dealCard());
						System.out.println("Player Number " + currentTurn + " drew a " + "card");
						playerCard = (playerTurn[currentTurn]).playCard(gameVal,gameColor);
					}
					activatePlapCard(playerCard);

				} else {
					//draws a card
					playerTurn[currentTurn].drawCard(dealCard());
					if(drawnCardPlayableRule){
						playerCard = (playerTurn[currentTurn]).playCard(gameVal,gameColor);
						//if drawn card is playable
						if(playerCard != null){
							activatePlapCard(playerCard);
						}
					}

					if(gameOver){
						break;
					}
					System.out.println("Player Number " + currentTurn + " drew a " + "card");
				
				}
			} else {
				//Plays a card
				activatePlapCard(playerCard);
			
			}

			//checks if the player has won
			for(int i = 0; i < playerCount; i++)
				if(playerTurn[i].hand.size() == 0) {
					gameOver = true;
					System.out.println("Player Number " + i + " has won!");
					System.out.println("This game lasted " + numberOfTurns + " Turns!");
					break;
				}
		
		}

		if(deck.size() == 0 && discard.size() == 0){
			System.out.println("Game has gone infinite");
			return -1;
		}
		return numberOfTurns;
	}
	
	
	private void activatePlapCard(FlipCard thePlayerCard){
		System.out.println("Player Number " + currentTurn + " played a " + thePlayerCard);

		if(thePlayerCard.cVal.equals("flip")){
			
			isFlipped = !isFlipped;
			flipDeck();

			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("reverse")){
			gameDirection *= -1;
			System.out.println("Game direction has been reversed!");
			if(playerCount == 2){
				currentTurn = whoTurn(currentTurn);
			}
			
			
			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("skip")){
			victim = whoTurn(currentTurn);
			System.out.println("Player Number " + victim + " has been skipped!");
			currentTurn = whoTurn(currentTurn);

			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("fwild")){
			gameColor = playerTurn[currentTurn].playedWildCard();
			System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
		
			inPlay = thePlayerCard;
			inPlay.setColor(gameColor);
			discard.add(inPlay);
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("bwild")){
			gameColor = playerTurn[currentTurn].playedBWildCard();
			System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
		
			inPlay = thePlayerCard;
			inPlay.setColor(gameColor);
			discard.add(inPlay);
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("draw1")){
			victim = whoTurn(currentTurn);
			playerTurn[victim].drawCard(dealCard());
			System.out.println("Player Number " + victim + " drew 1 card!");
		
			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("skipall")){
			
			for(int i = 0; i < playerCount - 1; i++){
				currentTurn = whoTurn(currentTurn);
			}
			System.out.println("Player Number " + currentTurn + " skipped all other players!");
		

			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}
		
		else if(thePlayerCard.cVal.equals("draw2")){
			victim = whoTurn(currentTurn);
			playerTurn[victim].drawCard(dealCard());
			playerTurn[victim].drawCard(dealCard());
			gameColor = playerTurn[currentTurn].playedWildCard();

			System.out.println("Player Number " + victim + " drew 2 cards!");
			System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");

		
			inPlay = thePlayerCard;
			inPlay.setColor(gameColor);
			discard.add(inPlay);
			gameVal = thePlayerCard.cVal;

		}

		else if(thePlayerCard.cVal.equals("drawX")){
			gameColor = playerTurn[currentTurn].playedWildCard();
			System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
			victim = whoTurn(currentTurn);

			while(playerTurn[victim].playableHand.size() == 0){
				playerTurn[victim].drawCard(dealCard());
				System.out.println("Player Number " + victim + " drew 1 card!");
				playerTurn[victim].formPlayableHand(gameVal, gameColor);
			}
			currentTurn = whoTurn(currentTurn);


			inPlay = thePlayerCard;
			inPlay.setColor(gameColor);
			discard.add(inPlay);
			gameVal = thePlayerCard.cVal;

			

		}

		else {
			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}
	}

	private FlipCard dealCard(){
		
		if(deck.size() != 0){
			inQuestion = deck.remove(0);
			return inQuestion;
		}

		if(deck.size() == 0){
			shuffleDeck();
			System.out.println("Deck has been shuffled");
		}

		if(deck.size() == 0 && discard.size() == 0){
			gameOver = true;
		}

		return inQuestion;
	}
		
	private void createDeck() {

		//4 Draw2 Cards
		deck.add(new FlipCard("draw2", "wild", "4", "orange"));
		deck.add(new FlipCard("draw2", "wild", "3", "pink"));
		deck.add(new FlipCard("draw2", "wild", "7", "orange"));
		deck.add(new FlipCard("draw2", "wild", "9", "purple"));

		//4 FWild Cards
		deck.add(new FlipCard("fwild", "wild", "7", "purple"));
		deck.add(new FlipCard("fwild", "wild", "flip", "pink"));
		deck.add(new FlipCard("fwild", "wild", "5", "pink"));
		deck.add(new FlipCard("fwild", "wild", "3", "lBlue"));

		//8 Flip Cards
		deck.add(new FlipCard("flip", "green", "3", "lBlue"));
		deck.add(new FlipCard("flip", "green", "drawX", "wild"));
		deck.add(new FlipCard("flip", "red", "8", "pink"));
		deck.add(new FlipCard("flip", "red", "3", "purple"));
		deck.add(new FlipCard("flip", "blue", "6", "purple"));
		deck.add(new FlipCard("flip", "blue", "7", "purple"));
		deck.add(new FlipCard("flip", "yellow", "4", "pink"));
		deck.add(new FlipCard("flip", "yellow", "8", "orange"));

		//8 Draw1 Cards
		deck.add(new FlipCard("draw1", "green", "6", "orange"));
		deck.add(new FlipCard("draw1", "green", "6", "lblue"));
		deck.add(new FlipCard("draw1", "red", "4", "pink"));
		deck.add(new FlipCard("draw1", "red", "3", "pink"));
		deck.add(new FlipCard("draw1", "blue", "6", "blue"));
		deck.add(new FlipCard("draw1", "blue", "6", "pink"));
		deck.add(new FlipCard("draw1", "yellow", "1", "pink"));
		deck.add(new FlipCard("draw1", "yellow", "8", "purple"));

		//8 Skip Cards
		deck.add(new FlipCard("skip", "green", "9", "orange"));
		deck.add(new FlipCard("skip", "green", "4", "purple"));
		deck.add(new FlipCard("skip", "red", "draw5", "orange"));
		deck.add(new FlipCard("skip", "red", "bwild", "wild"));
		deck.add(new FlipCard("skip", "blue", "1", "lblue"));
		deck.add(new FlipCard("skip", "blue", "9", "pink"));
		deck.add(new FlipCard("skip", "yellow", "3", "orange"));
		deck.add(new FlipCard("skip", "yellow", "flip", "lblue"));

		//Reverse Cards
		deck.add(new FlipCard("reverse", "green", "7", "pink"));
		deck.add(new FlipCard("reverse", "green", "1", "orange"));
		deck.add(new FlipCard("reverse", "red", "7", "lblue"));
		deck.add(new FlipCard("reverse", "red", "3", "purple"));
		deck.add(new FlipCard("reverse", "blue", "4", "orange"));
		deck.add(new FlipCard("reverse", "blue", "bwild", "wild"));
		deck.add(new FlipCard("reverse", "yellow", "flip", "lblue"));
		deck.add(new FlipCard("reverse", "yellow", "bwild", "wild"));

		//Number Cards
		deck.add(new FlipCard("1", "green", "5", "orange"));
		deck.add(new FlipCard("1", "green", "flip", "orange"));
		deck.add(new FlipCard("1", "red", "3", "pink"));
		deck.add(new FlipCard("1", "red", "2", "purple"));
		deck.add(new FlipCard("1", "blue", "skipall", "purple"));
		deck.add(new FlipCard("1", "blue", "skipall", "purple"));
		deck.add(new FlipCard("1", "yellow", "skipall", "pink"));
		deck.add(new FlipCard("1", "yellow", "bwild", "wild"));

		deck.add(new FlipCard("2", "green", "skipall", "lblue"));
		deck.add(new FlipCard("2", "green", "draw5", "lblue"));
		deck.add(new FlipCard("2", "red", "reverse", "orange"));
		deck.add(new FlipCard("2", "red", "draw5", "purple"));
		deck.add(new FlipCard("2", "blue", "8", "orange"));
		deck.add(new FlipCard("2", "blue", "6", "pink"));
		deck.add(new FlipCard("2", "yellow", "8", "lblue"));
		deck.add(new FlipCard("2", "yellow", "1", "lblue"));
		
		deck.add(new FlipCard("3", "green", "flip", "pink"));
		deck.add(new FlipCard("3", "green", "2", "purple"));
		deck.add(new FlipCard("3", "red", "drawX", "wild"));
		deck.add(new FlipCard("3", "red", "7", "pink"));
		deck.add(new FlipCard("3", "blue", "2", "lblue"));
		deck.add(new FlipCard("3", "blue", "8", "purple"));
		deck.add(new FlipCard("3", "yellow", "draw5", "pink"));
		deck.add(new FlipCard("3", "yellow", "1", "purple"));

		deck.add(new FlipCard("4", "green", "8", "pink"));
		deck.add(new FlipCard("4", "green", "9", "lblue"));
		deck.add(new FlipCard("4", "red", "flip", "orange"));
		deck.add(new FlipCard("4", "red", "draw5", "purple"));
		deck.add(new FlipCard("4", "blue", "draw5", "lblue"));
		deck.add(new FlipCard("4", "blue", "1", "purple"));
		deck.add(new FlipCard("4", "yellow", "draw5", "pink"));
		deck.add(new FlipCard("4", "yellow", "flip", "purple"));

		deck.add(new FlipCard("5", "green", "4", "lblue"));
		deck.add(new FlipCard("5", "green", "7", "orange"));
		deck.add(new FlipCard("5", "red", "2", "pink"));
		deck.add(new FlipCard("5", "red", "5", "lblue"));
		deck.add(new FlipCard("5", "blue", "reverse", "orange"));
		deck.add(new FlipCard("5", "blue", "9", "pink"));
		deck.add(new FlipCard("5", "yellow", "8", "lblue"));
		deck.add(new FlipCard("5", "yellow", "9", "purple"));

		deck.add(new FlipCard("6", "green", "5", "pink"));
		deck.add(new FlipCard("6", "green", "drawX", "wild"));
		deck.add(new FlipCard("6", "red", "9", "orange"));
		deck.add(new FlipCard("6", "red", "skipall", "pink"));
		deck.add(new FlipCard("6", "blue", "skipall", "lblue"));
		deck.add(new FlipCard("6", "blue", "reverse", "purple"));
		deck.add(new FlipCard("6", "yellow", "skipall", "orange"));
		deck.add(new FlipCard("6", "yellow", "drawX", "wild"));
		
		deck.add(new FlipCard("7", "green", "6", "orange"));
		deck.add(new FlipCard("7", "green", "2", "lblue"));
		deck.add(new FlipCard("7", "red", "1", "orange"));
		deck.add(new FlipCard("7", "red", "5", "purple"));
		deck.add(new FlipCard("7", "blue", "skipall", "orange"));
		deck.add(new FlipCard("7", "blue", "3", "orange"));
		deck.add(new FlipCard("7", "yellow", "2", "orange"));
		deck.add(new FlipCard("7", "yellow", "6", "purple"));

		deck.add(new FlipCard("8", "green", "reverse", "pink"));
		deck.add(new FlipCard("8", "green", "9", "lblue"));
		deck.add(new FlipCard("8", "red", "7", "lblue"));
		deck.add(new FlipCard("8", "red", "reverse", "purple"));
		deck.add(new FlipCard("8", "blue", "4", "lblue"));
		deck.add(new FlipCard("8", "blue", "reverse", "lblue"));
		deck.add(new FlipCard("8", "yellow", "1", "pink"));
		deck.add(new FlipCard("8", "yellow", "2", "orange"));

		deck.add(new FlipCard("9", "green", "reverse", "pink"));
		deck.add(new FlipCard("9", "green", "draw5", "orange"));
		deck.add(new FlipCard("9", "red", "reverse", "lblue"));
		deck.add(new FlipCard("9", "red", "5", "purple"));
		deck.add(new FlipCard("9", "blue", "5", "orange"));
		deck.add(new FlipCard("9", "blue", "flip", "purple"));
		deck.add(new FlipCard("9", "yellow", "5", "lblue"));
		deck.add(new FlipCard("9", "yellow", "4", "purple"));
		
	}
	
	private void shuffleDeck() {
		deck.addAll(discard);
		discard.clear();
		Collections.shuffle(deck);
		deck.remove(inPlay);
		discard.add(inPlay);
	}

	private void flipDeck(){

	}

	private void dealHands() {
		for(int i = 0; i < playerCount; i++) {
			for(int j = 0; j < STARTING_HAND_SIZE; j++) {
				playerTurn[i].drawCard(dealCard());
			}
		}
	}

	private int whoTurn(int lastTurn){
		int currentTurn = lastTurn + gameDirection;
		if(currentTurn < 0){
			return playerCount-1;
		}

		if(currentTurn > playerCount-1){
			return 0;
		}
		return lastTurn + gameDirection;
	}
	
}
