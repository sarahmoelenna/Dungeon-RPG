package MyDungeonPackage;

public class LeverObject extends DungeonObject{

	int Rotation;
	boolean State;
	float PushTimer;
	int Position;
	
	public LeverObject(int X, int Y,int Z, float size, int rotation, boolean Start, int pos) {
		super("lever", "lever", X, Y, Z, DisplayObjectType.RotationDisplacemntObject, size);
		if(rotation == 1){
			MyObject.SetRotation(270);
		}
		if(rotation == 2){
			MyObject.SetRotation(0);
		}
		if(rotation == 3){
			MyObject.SetRotation(90);
		}
		if(rotation == 4){
			MyObject.SetRotation(180);
		}
		Rotation = rotation;
		MyObject.SetAdditionalTexture("leverrot");
		MyObject.SetThirdTexture("leveraxis");
		
		State = Start;
		PushTimer = 0;
		Position = pos;
		// TODO Auto-generated constructor stub
	}
	
	public void Update(int delta){
		
		PushTimer += 0.008f * delta;
		if(PushTimer > 1){PushTimer = 1;}
		
		if(Rotation == 1){
			MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size);
		}
		else if(Rotation == 2){
			MyObject.SetPosition(PosX * Size, PosY * Size, PosZ * Size + Size/2);
		}
		else if(Rotation == 4){
			MyObject.SetPosition(PosX * Size + Size, PosY * Size, PosZ * Size + Size/2);
		}
		else if(Rotation == 3){
			MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size);
		}
		
		
		if(State){
			MyObject.IncrementWindScale(0.005f, 0, delta);
		}
		else{
			MyObject.DecrementWindScale(0.005f, -1.7f, delta);
		}
	}
	
	public void PlayerInteract(int PlayerX, int PlayerY, int Direction){
		//System.out.println(Direction + " " + Rotation);
		if(Direction == 3 && Rotation == 3 && (MyObject.WindSin == 0 || MyObject.WindSin == -1.7f) && PushTimer == 1){
			State = !State;
			PushTimer = 0;
		}
		if(Direction == 1 && Rotation == 1 && (MyObject.WindSin == 0 || MyObject.WindSin == -1.7f) && PushTimer == 1){
			State = !State;
			PushTimer = 0;
		}
		if(Direction == 2 && Rotation == 4 && (MyObject.WindSin == 0 || MyObject.WindSin == -1.7f) && PushTimer == 1){
			State = !State;
			PushTimer = 0;
		}
		if(Direction == 4 && Rotation == 2 && (MyObject.WindSin == 0 || MyObject.WindSin == -1.7f) && PushTimer == 1){
			State = !State;
			PushTimer = 0;
		}
	}
	
	public boolean GetPowered(){
		return !State;
	}
	
	public int GetPosition(){
		return Position;
	}
	

}
