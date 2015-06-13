package MyDungeonPackage;

public class LogicMultiInput extends LogicObject {

	int powerNeeded;
	
	public LogicMultiInput(int X, int Z, int Required, int Position) {
		super(X, Z);
		powerNeeded = Required;
		PowerPosition[0] = Position;
	}
	
	public void ActivateLogicObject(int power){
		//System.out.println(power);
		if(power == powerNeeded){
			Powered = true;
		}
		else{
			Powered = false;
		}
	}

}
