package MyDungeonPackage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class PushObject extends DungeonObject {

	PointLight MyLight;
	MyTextureClass MyDayTexture;
	MyTextureClass MyNightTexture;
	MyTextureClass MyGlowTexture;
	float MovementTimer;
	int direction; // 1 z++, 2 z--, 3 x++, 4 x--
	
	public PushObject(int X, int Y,int Z, DisplayObjectType MyType, float size) {
		super("push", "lampday", X, Y, Z, MyType, size);
		// TODO Auto-generated constructor stub
		
		ModelHandler.AddTexture("lampday");
		MyDayTexture = ModelHandler.FindTexture("lampday");
		
		ModelHandler.AddTexture("lampnight");
		MyNightTexture = ModelHandler.FindTexture("lampnight");
		
		ModelHandler.AddTexture("lampglow");
		MyGlowTexture = ModelHandler.FindTexture("lampglow");
		
		//MyObject.MyObjectType = DisplayObjectType.TexturedObject;
		MyObject.GlowMaptexture = MyGlowTexture;
		
		PointLight TempLight = new PointLight(X, Y, Z, 200, new Vector3f(0.45f, 1.0f, 0.13f));
		ModelHandler.AddLight(TempLight);
		MyLight = ModelHandler.FindLight(TempLight);
		direction = 0;
		MovementTimer = 0;
	}

	public void Update(int delta){
		
		MovementTimer += 0.002f * delta;
		if(MovementTimer > 1){MovementTimer = 1;}
		
		if(direction == 1){
			offsetX = 0;
			offsetZ = -(Size * (1 -MovementTimer));
		}
		else if(direction == 2){
			offsetX = 0;
			offsetZ = (Size * (1 -MovementTimer));
		}
		else if(direction == 3){
			offsetZ = 0;
			offsetX = -(Size * (1 -MovementTimer));
		}
		else if(direction == 4){
			offsetZ = 0;
			offsetX = (Size * (1 -MovementTimer));
		}
		
		
		MyLight.UpdatePostion(PosX * Size + Size/2 + offsetX, PosY * Size + Size*0.8f + offsetY, PosZ * Size + Size/2 + offsetZ);
		
		if(GameData.DayTime || GameData.Lights == false){
			MyObject.MyTexture = MyNightTexture;
		}
		else{
			MyObject.MyTexture = MyDayTexture;
		}
		Collidable = true;
		super.Update(delta);
	}
	
	public void PlayerInteract(int PlayerX, int PlayerY,int GridTypeArray[], int GridEntityArray[], int GridWallArray[]){
		if(MovementTimer == 1){
			if(PlayerX == PosX && PlayerY < PosZ){
				if((GridTypeArray[(int) (PosX + PosZ * 32) + 32] == 6 || GridTypeArray[(int) (PosX + PosZ * 32) + 32] == 7) && GridEntityArray[(int) (PosX + PosZ * 32) + 32] == 0){
					if(GridWallArray[(int) (PosX + PosZ * 32)] != 1 && GridWallArray[(int) (PosX + PosZ * 32)] != 3){
						PosZ++;
						MovementTimer = 0;
						direction = 1;
					}
				}
			}
			else if(PlayerX == PosX && PlayerY > PosZ){
				if((GridTypeArray[(int) (PosX + PosZ * 32) - 32] == 6 || GridTypeArray[(int) (PosX + PosZ * 32) - 32] == 7) && GridEntityArray[(int) (PosX + PosZ * 32) - 32] == 0){
					if(GridWallArray[(int) (PosX + PosZ * 32) - 32] != 1 && GridWallArray[(int) (PosX + PosZ * 32) - 32] != 3){
						PosZ--;
						MovementTimer = 0;
						direction = 2;
					}
				}
			}
			else if(PlayerX < PosX && PlayerY == PosZ){
				if((GridTypeArray[(int) (PosX + PosZ * 32) + 1] == 6 || GridTypeArray[(int) (PosX + PosZ * 32) + 1] == 7) && GridEntityArray[(int) (PosX + PosZ * 32) + 1] == 0){
					if(GridWallArray[(int) (PosX + PosZ * 32)] != 2 && GridWallArray[(int) (PosX + PosZ * 32)] != 3){
						PosX++;
						MovementTimer = 0;
						direction = 3;
					}
				}
			}
			else if(PlayerX > PosX && PlayerY == PosZ){
				if((GridTypeArray[(int) (PosX + PosZ * 32) - 1] == 6 || GridTypeArray[(int) (PosX + PosZ * 32) - 1] == 7)  && GridEntityArray[(int) (PosX + PosZ * 32) - 1] == 0){
					if(GridWallArray[(int) (PosX + PosZ * 32) - 1] != 2 && GridWallArray[(int) (PosX + PosZ * 32) - 1] != 3){
						PosX--;
						MovementTimer = 0;
						direction = 4;
					}
				}
			}
		}
	}
	
}
