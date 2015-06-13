package MyDungeonPackage;

public class LogicActivator extends LogicObject {

	int Requirement; //0 none, // 1 player, // 2 enemy, // 3 push object //5 interact
	
	public LogicActivator(int X, int Z, int input, int Position) {
		super(X, Z);

		Requirement = input;
		PowerPosition[0] = Position;
		PowerPosition[1] = 0;
		PowerPosition[2] = 0;
	}

	public int GetData(){
		return Requirement;
	}
	
}
