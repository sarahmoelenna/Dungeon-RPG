package MyDungeonPackage;

public class DungeonObject {
	
	DisplayObject MyObject;
	DisplayObjectType MyType;
	MyTextureClass MyTexture;
	float Size;
	
	float PosX;
	float PosY;
	float PosZ;
	
	float offsetX;
	float offsetY;
	float offsetZ;
	
	boolean Collidable;
	int Damage;
	int PowerInput;
	
	public DungeonObject(String ModelName, String TextureName, int X, int Y, int Z, DisplayObjectType MyType, float size){
		
		PosX = X;
		PosY = Y;
		PosZ = Z;
		PowerInput = 0;
		
		MyObject = new DisplayObject(ModelName, TextureName, MyType);
		Size = size;
		MyObject.SetScale(Size);
		
		Collidable = false;
		Damage = 0;
		
		offsetX = 0;
		offsetY = 0;
		offsetZ = 0;
	}
	
	public void Update(int delta){
		MyObject.SetPosition(PosX * Size + offsetX, PosY * Size + offsetY, PosZ * Size +offsetZ);
	}
	
	public void PlayerInteract(int PlayerX, int PlayerY,int GridTypeArray[], int GridEntityArray[], int GridWallArray[]){
		
	}
	
	public void PlayerInteract(int PlayerX, int PlayerY){
		
	}
	
	public void PlayerInteract(int PlayerX, int PlayerY, int Direction){
		
	}
	
	public void PowerObject(int power){
		PowerInput = power;
	}
	
	public void RenderObject(){
		MyObject.renderDisplayObject();
	}
	
	public void Render2DObject(){
		
	}

	public void RenderObjectDepth(){
		MyObject.renderDisplayObjectDepth();
	}

	public boolean GetCollidable(){
		return Collidable;
	}
	
	public int GetDamage(){
		return Damage;
	}
	
	public boolean GetPowered(){
		return false;
	}
	
	public int GetPosition(){
		return 0;
	}
}
