package MyDungeonPackage;

public class SignObject extends DungeonObject{

	public SignObject(int X, int Y,int Z, float size, int rotation) {
		super("sign", "sign", X, Y, Z, DisplayObjectType.TexturedObject, size);
		if(rotation == 1){
			MyObject.SetRotation(270);
		}
		if(rotation == 2){
			MyObject.SetRotation(180);
		}
		if(rotation == 3){
			MyObject.SetRotation(90);
		}
		if(rotation == 4){
			MyObject.SetRotation(0);
		}
		// TODO Auto-generated constructor stub
	}
	
	public void Update(int delta){
		MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
	}

}
