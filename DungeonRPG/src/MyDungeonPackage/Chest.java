package MyDungeonPackage;

import java.util.Random;

public class Chest {
	
	DisplayObject MyObject;
	DisplayObject MyTop;
	float Size;
	
	int PosX;
	int PosY;
	int PosZ;
	
	Random Temp;
	
	String AwaitingMessage;
	boolean Message;
	
	String Contents;
	int amount;
	
	Item ChestItem;
	
	boolean Open;
	
	public Chest(String ModelName, String TextureName, int X, int Y, int Z, float size, int Direction, int FloorNumber){
		
		ChestItem = new Item(FloorNumber);
		if(ChestItem.MyType == ItemType.Sword){
			ChestItem = new ItemWeapon(FloorNumber);
		}
		
		MyObject = new DisplayObject(ModelName, TextureName, DisplayObjectType.TexturedObject);
		MyTop = new DisplayObject("ChestTop", TextureName, DisplayObjectType.RotationDisplacemntObject);
		Size = size;
		PosX = X;
		PosY = Y;
		PosZ = Z;
		MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyObject.SetScale(Size);
		MyTop.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyTop.SetScale(Size);
		MyTop.SetAdditionalTexture("chestrotation");
		MyTop.SetThirdTexture("chestaxis");
		if(Direction == 3){
			MyObject.SetRotation(0);
			MyTop.SetRotation(0);
		}
		if(Direction == 4){
			MyObject.SetRotation(90);
			MyTop.SetRotation(90);
		}
		if(Direction == 1){
			MyObject.SetRotation(180);
			MyTop.SetRotation(180);
		}
		if(Direction == 2){
			MyObject.SetRotation(270);
			MyTop.SetRotation(270);
		}
		Open = false;
		Temp = new Random();
		Message = false;
		
		amount = RanInt(0,3)+1;
		int tempint = RanInt(0,4);
		if(tempint == 0){
			Contents = "Health.";
		}
		else if(tempint == 1){
			Contents = "Strength.";
		}
		else if(tempint == 2){
			Contents = "Defense.";
		}
		else{
			Contents = "Potion.";
			amount = 1;
		}
		
		
		
	}
	
	public void Update(float delta){
		if(Open==true){
			MyTop.IncrementWindScale(0.005f, 1, delta);
		}
		else{
			MyTop.DecrementWindScale(0.005f, 0, delta);
		}
	}
	
	public void RenderChest(){
		MyTop.renderDisplayObject();
		MyObject.renderDisplayObject();
		
	}
	
	public void RenderChestDepth(){
		MyTop.renderDisplayObjectDepth();
		MyObject.renderDisplayObjectDepth();
		
	}
	
	public void OpenChest(){
		if(Open == false){
			Message = true;
			AwaitingMessage = "Retireved plus " + amount + " " + Contents;
		}
		Open = true;
	}

	 private int RanInt(int Min, int Max){
	    return Temp.nextInt(Max - Min) + Min;
	 }
	    
	 public Item GetItem(){
		 return ChestItem;
	 }
}
