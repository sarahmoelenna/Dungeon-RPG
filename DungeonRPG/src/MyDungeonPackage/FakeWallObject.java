package MyDungeonPackage;

public class FakeWallObject extends DungeonObject {

	float Up;
	float WaitTimer;
	boolean PowerDirection;
	
	public FakeWallObject(int X, int Y,int Z, float size, boolean PowerDir, int Rotation) {
		super("fakewall", "Temple", X, Y, Z, DisplayObjectType.TexturedObject, size);
		// TODO Auto-generated constructor stub
		
		if(Rotation == 1 || Rotation == 3){
			MyObject.SetRotation(0);
		}
		else{
			MyObject.SetRotation(90);
		}
		
		if(PowerDir == false){
			Up = 0;
		}
		else{
			Up = -1;
		}
		WaitTimer = 0;
		PowerDirection = PowerDir;
	}
	
	public void Update(int delta){
		if(delta > 1000){
			delta = 0;
		}
		
		if(PowerDirection == true){
			if(PowerInput > 0){
				Up += 0.0005f * delta;
				if(Up >= 0){Up = 0;}
				Collidable = true;
			}
			else{
				Up -= 0.0005f * delta;
				if(Up <= -1f){Up = -1f;}
				if(Up < -0.8f){
					Collidable = false;
				}
			}
		}
		else{
			if(PowerInput == 0){
				Up += 0.0005f * delta;
				if(Up >= 0){Up = 0;}
				Collidable = true;
			}
			else{
				Up -= 0.0005f * delta;
				if(Up <= -1f){Up = -1f;}
				if(Up < -0.8f){
					Collidable = false;
				}
			}
		}
		
		
		//PosY = Up;
		MyObject.SetPosition(PosX * Size + Size/2 + offsetX, Up * Size + offsetY, PosZ * Size + Size/2 + offsetZ);
		//super.Update(delta);
		//MyLight.UpdatePostion(PosX * Size + Size/2, PosY * Size + Size, PosZ * Size + Size/2);
	}
	

}
