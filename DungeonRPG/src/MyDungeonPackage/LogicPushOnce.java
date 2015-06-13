package MyDungeonPackage;

public class LogicPushOnce extends LogicObject {

	boolean On;
	boolean Released;
	
	public LogicPushOnce(int X, int Z, int Start, int Position) {
		super(X, Z);

		PowerPosition[0] = Position;
		
		if(Start > 0){
			On = true;
			Powered = true;
		}
		else{
			On = false;
			Powered = false;
		}
		Released = false;
		
	}
	
	public void ActivateLogicObject(int power){
		//System.out.println(power);
		if(power > 0){
			Powered = !On;
		}
	}

}
