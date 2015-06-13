package MyDungeonPackage;

public class LogicFlipFlop extends LogicObject {

	boolean On;
	boolean Released;
	
	public LogicFlipFlop(int X, int Z, int Position, int Start) {
		super(X, Z);

		PowerPosition[0] = Position;
		
		if(Start > 0){
			On = true;
		}
		else{
			On = false;
		}
		Released = false;
		
	}
	
	public void ActivateLogicObject(int power){
		//System.out.println(power);
		if(power == 0){
			Released = true;
		}
		
		if(Released == true && power > 0){
			//System.out.println("Flipped");
			On = !On;
			Released = false;
		}
	}
	
	public boolean IsPowered(){
		return On;
	}

}
