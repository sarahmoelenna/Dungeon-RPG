package MyDungeonPackage;

public class GridMessageData {
	
	String MyMessage;
	float alphatimer;
	
	public GridMessageData(String Message){
		MyMessage = Message;
		alphatimer = 1;
	}
	
	public void Update(int delta){
		alphatimer -= 0.001f * delta;
	}

}
