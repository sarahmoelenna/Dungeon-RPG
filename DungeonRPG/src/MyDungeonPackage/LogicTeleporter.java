package MyDungeonPackage;

public class LogicTeleporter extends LogicObject{

	int Type;
	int Rotation;
	int PlayerPosition;
	
	public LogicTeleporter(int X, int Z, int type, int rotation, int Position) {
		super(X, Z);
		PowerPosition[2] = rotation;
		PlayerPosition = Position;
		PlayerPosition = Position;
		Type = type;
		// TODO Auto-generated constructor stub
	}
	
	public void ActivateLogicObject(int power){
		//System.out.println(power);
		if(Type == 0){
			if(power > 0){
				Powered = true;
			}
			else{
				Powered = false;
			}
		}
		else{
			if(power == 0){
				Powered = true;
			}
			else{
				Powered = false;
			}
		}
	}
	
	public int GetData(){
		return PlayerPosition;
	}

}
