
public class Card {

	
	String cVal = null;
	String color = null;
	
	Card(String newValue, String newColor){
		cVal = newValue;
		color = newColor;
	}
	
	public String setColor(String newColor){
		color = newColor;
		return color;
	}
	
	
	public String toString() {
			
		return cVal + " " + color;
		
	}
	
}