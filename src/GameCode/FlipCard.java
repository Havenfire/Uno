package GameCode;

public class FlipCard {

	
	String cVal = null;
	String color = null;
    String bVal = null;
	String bColor = null;
	
	FlipCard(String newFValue, String newFColor, String newBValue, String newBColor){
		cVal = newFValue;
		color = newFColor;
        bVal = newBValue;
		bColor = newBColor;
	}
	
	public String setColor(String newColor){
		color = newColor;
		return color;
	}
    
    public String setBColor(String newColor){
		bColor = newColor;
		return bColor;
	}
	
	
	public String toString() {
			
		return cVal + " " + color + "/" + bVal + " " + bColor;
		
	}
	
}