
public class Card {

	
	int vNum = -1;
	String vSpec = null;
	String color = null;
	
	Card(int newValue, String newColor){
		vNum = newValue;
		color = newColor;
	}
	
	Card(String newValue, String newColor){
		vSpec = newValue;
		color = newColor;
	}
	
	
	
	
	
	
	public String toString() {
		if(vNum == -1) {
			return vSpec + " " + color; 
		}
		
		return vNum + " " + color;
		
	}
	
}