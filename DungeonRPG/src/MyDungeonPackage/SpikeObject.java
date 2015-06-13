package MyDungeonPackage;

import org.lwjgl.util.vector.Vector3f;

public class SpikeObject extends DungeonObject {

	int state;
	float Up;
	float WaitTimer;
	boolean Powered;
	boolean PowerDirection;
	//PointLight MyLight;
	
	public SpikeObject(String ModelName, String TextureName, int X, int Y,int Z, DisplayObjectType MyType, float size, float startPos) {
		super(ModelName, TextureName, X, Y, Z, MyType, size);
		state = 1;
		Up = -startPos;
		//System.out.println(Up);
		WaitTimer = 0;
		Powered = false;
		PowerDirection = false;
		//PointLight TempLight = new PointLight(X, Y, Z, 200, new Vector3f(0,0,1));
		//ModelHandler.AddLight(TempLight);
		//MyLight = ModelHandler.FindLight(TempLight);
	}
	
	public SpikeObject(String ModelName, String TextureName, int X, int Y,int Z, DisplayObjectType MyType, float size, float startPos, boolean PowerDir) {
		super(ModelName, TextureName, X, Y, Z, MyType, size);
		state = 1;
		Up = -startPos;
		WaitTimer = 0;
		Powered = true;
		PowerDirection = PowerDir;
		//PointLight TempLight = new PointLight(X, Y, Z, 200, new Vector3f(0,0,1));
		//ModelHandler.AddLight(TempLight);
		//MyLight = ModelHandler.FindLight(TempLight);
	}
	
	public void Update(int delta){
		if(delta > 1000){
			delta = 0;
		}
		if(Powered == false){
			if(state == 1){
				Up += 0.0005f * delta;
				//System.out.println(delta);
				WaitTimer = 0;
				if(Up >= 0){state = 2; Up = 0;}
			}
			else if(state == 2){
				Up -= 0.0005f * delta;
				WaitTimer = 0;
				if(Up <= -0.69f){state = 3; Up = -1;}
			}
			else{
				WaitTimer += 0.005f * delta;
				if(WaitTimer >= 10){WaitTimer = 0; state = 1;}
			}
		}
		else{
			if(PowerDirection == true){
				if(PowerInput > 0){
					Up += 0.0005f * delta;
					if(Up >= 0){Up = 0;}
				}
				else{
					Up -= 0.0005f * delta;
					if(Up <= -0.69f){Up = -0.69f;}
				}
			}
			else{
				if(PowerInput == 0){
					Up += 0.0005f * delta;
					if(Up >= 0){Up = 0;}
				}
				else{
					Up -= 0.0005f * delta;
					if(Up <= -0.69f){Up = -0.69f;}
				}
			}
			
		}
		
		
		
		if(Up < -0.6){
			Damage = 0;
		}
		else{
			Damage = 1;
		}
		
		if(Up > -0.2){
			Collidable = true;
		}
		else{
			Collidable = false;
		}
		
		PosY = Up;
		super.Update(delta);
		//MyLight.UpdatePostion(PosX * Size + Size/2, PosY * Size + Size, PosZ * Size + Size/2);
	}
	
	public void RenderObject(){
		super.RenderObject();
		//MyLight.Render();
	}

	
}
