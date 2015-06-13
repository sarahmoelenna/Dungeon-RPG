package MyDungeonPackage;

import java.util.Random;

public class TreeObject extends DungeonObject {

	Random Temp;
	
	public TreeObject(int X, int Y,int Z, float size) {
		super("palmtree", "palmtree", X, Y, Z, DisplayObjectType.TexturedObject, size);
		Collidable = true;
		Temp = new Random();
		MyObject.SetRotation(RanInt(0,360));
	}
	
	public void Update(int delta){
		//super.Update(delta);
		MyObject.SetPosition(PosX * Size + Size/2 + offsetX, PosY * Size + offsetY, PosZ * Size + Size/2 + offsetZ);
	}

    private int RanInt(int Min, int Max){
    	return Temp.nextInt(Max - Min) + Min;
    
    }
    
}
