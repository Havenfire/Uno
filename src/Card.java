
public class Card {

	
	String cVal = null;
	String color = null;
	
	Card(String newValue, String newColor){
		cVal = newValue;
		color = newColor;
	}
	
	public boolean canPlay(Card inPlay) {
	
		if(this.cVal == inPlay.cVal || this.color == inPlay.color){
			return true;
		}
		return false;
	}
	
	
	
	
	public String toString() {
			
		return cVal + " " + color;
		
	}
	
}