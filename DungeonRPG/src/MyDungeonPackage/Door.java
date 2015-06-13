package MyDungeonPackage;

public class Door {
	
	DisplayObject MyObject;
	DisplayObject MyObjectTwo;
	float Size;
	
	int PosX;
	int PosY;
	int PosZ;
	
	boolean Open;
	boolean NeedsPower;
	
	public Door(String ModelName, String TextureName, int X, int Y, int Z, float size, int Direction){
		
		MyObject = new DisplayObject(ModelName, TextureName, DisplayObjectType.RotationDisplacemntObject);
		MyObjectTwo = new DisplayObject(ModelName, TextureName, DisplayObjectType.RotationDisplacemntObject);
		Size = size;
		PosX = X;
		PosY = Y;
		PosZ = Z;
		MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyObject.SetScale(Size);
		MyObject.SetAdditionalTexture("doorrot");
		MyObject.SetThirdTexture("dooraxis");
		MyObjectTwo.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyObjectTwo.SetScale(Size);
		MyObjectTwo.SetAdditionalTexture("doorrot");
		MyObjectTwo.SetThirdTexture("dooraxis");
		NeedsPower = false;
		if(Direction == 1){
			MyObject.SetRotation(90);
			MyObjectTwo.SetRotation(270);
		}
		if(Direction == 2){
			MyObject.SetRotation(0);
			MyObjectTwo.SetRotation(180);
		}
		Open = false;
		
	}
	
	public Door(String ModelName, String TextureName, int X, int Y, int Z, float size, int Direction, boolean power){
		
		MyObject = new DisplayObject("powereddoor", TextureName, DisplayObjectType.RotationDisplacemntObject);
		MyObjectTwo = new DisplayObject("powereddoor", TextureName, DisplayObjectType.RotationDisplacemntObject);
		Size = size;
		PosX = X;
		PosY = Y;
		PosZ = Z;
		MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyObject.SetScale(Size);
		MyObject.SetAdditionalTexture("doorrot");
		MyObject.SetThirdTexture("dooraxis");
		MyObjectTwo.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyObjectTwo.SetScale(Size);
		MyObjectTwo.SetAdditionalTexture("doorrot");
		MyObjectTwo.SetThirdTexture("dooraxis");
		if(Direction == 1){
			MyObject.SetRotation(90);
			MyObjectTwo.SetRotation(270);
		}
		if(Direction == 2){
			MyObject.SetRotation(0);
			MyObjectTwo.SetRotation(180);
		}
		Open = false;
		NeedsPower = power;
	}
	
	
	public void Update(float delta){
		if(Open==true){
			MyObject.IncrementWindScale(0.005f, 1.5f, delta);
			MyObjectTwo.DecrementWindScale(0.005f, -1.5f, delta);
			//System.out.println("open");
		}
		else{
			MyObject.DecrementWindScale(0.005f, 0, delta);
			MyObjectTwo.IncrementWindScale(0.005f, 0, delta);
		}
	}
	
	public void RenderChest(){
		MyObjectTwo.renderDisplayObject();
		MyObject.renderDisplayObject();
		
	}
	
	public void RenderChestDepth(){
		MyObjectTwo.renderDisplayObjectDepth();
		MyObject.renderDisplayObjectDepth();
		
	}
	
	public void OpenChest(){
		if(MyObject.WindSin < 0.1f){
			Open = true;
		}
		if(MyObject.WindSin > 1.4f){
			Open = false;
		}
	}
	
	public boolean GetCollidable(){
		if(MyObject.WindSin > 0.7){
			return false;
		}
		else{
			return true;
		}
	}

}
