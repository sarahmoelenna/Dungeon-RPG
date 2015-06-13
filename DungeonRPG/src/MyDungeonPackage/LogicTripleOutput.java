package MyDungeonPackage;

public class LogicTripleOutput extends LogicObject {

	public LogicTripleOutput(int X, int Z, int Position, int PositionA, int PositionB) {
		super(X, Z);
		PowerPosition[0] = Position;
		PowerPosition[1] = PositionA;
		PowerPosition[2] = PositionB;
	}
	
	public void ActivateLogicObject(int power){
		if(power > 0){
			Powered = true;
		}
		else{
			Powered = false;
		}
	}

}
