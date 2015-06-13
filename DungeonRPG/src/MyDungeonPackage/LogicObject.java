package MyDungeonPackage;

public class LogicObject {

	int PositionX;
	int PositionZ;
	boolean Powered;
	int PowerPosition[];
	
	public LogicObject(int X, int Z){
		PositionX = X;
		PositionZ = Z;
		Powered = false;
		PowerPosition = new int [3];
	}
	
	public void Update(int delta){
		
	}
	
	public boolean IsPowered(){
		return Powered;
	}
	
	public int[] GetPowered(){
		return PowerPosition;
	}
	
	public int GetData(){
		return 0;
	}
	
	public void ActivateLogicObject(int power){
		
	}
	
}
