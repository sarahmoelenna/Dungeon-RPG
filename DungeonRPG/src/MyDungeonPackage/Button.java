package MyDungeonPackage;

public class Button extends DungeonObject {

	DisplayObject MyButton;
	float MovementTimer;
	int Rotation;
	boolean Pressed;
	
	public Button(int X, int Y, int Z, int rotation, float size) {
		super("buttoncase", "Temple", X, Y, Z, DisplayObjectType.TexturedObject, size);
		MyButton = new DisplayObject("button", "Temple", DisplayObjectType.TexturedObject);
		Rotation = rotation;
		if(rotation == 1){
			MyObject.SetRotation(180);
			MyButton.SetRotation(180);
		}
		else if(rotation == 2){
			MyObject.SetRotation(90);
			MyButton.SetRotation(90);
		}
		else if(rotation == 3){
			MyObject.SetRotation(0);
			MyButton.SetRotation(0);
		}
		else if(rotation == 4){
			MyObject.SetRotation(270);
			MyButton.SetRotation(270);
		}
		MyButton.SetScale(size);
		MovementTimer = 0;
		Pressed = false;
	}
	
	public void PlayerInteract(int PlayerX, int PlayerY){
		if(PlayerX == PosX && PlayerY < PosZ && Rotation == 1){
			//System.out.println("On");
			Pressed = true;
		}
		else if(PlayerX == PosX && PlayerY > PosZ && Rotation == 3){
			//System.out.println("On");
			Pressed = true;
		}
		else if(PlayerX < PosX && PlayerY == PosZ && Rotation == 4){
			//System.out.println("On");
			Pressed = true;
		}
		else if(PlayerX > PosX && PlayerY == PosZ && Rotation == 2){
			//System.out.println("On");
			Pressed = true;
		}
	}
	
	public void Update(int delta){
		MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		
		if(Pressed == true){
			MovementTimer+= 0.003f * delta;
		}
		else{
			MovementTimer-= 0.003f * delta;
		}
		
		if(Rotation == 1){
			offsetZ = MovementTimer * 0.001f * Size;
			offsetX = 0;
		}
		if(Rotation == 3){
			offsetZ = -MovementTimer * 0.001f * Size;
			offsetX = 0;
		}
		if(Rotation == 2){
			offsetX = -MovementTimer * 0.001f * Size;
			offsetZ = 0;
		}
		if(Rotation == 4){
			offsetX = MovementTimer * 0.001f * Size;
			offsetZ = 0;
		}
		
		MyButton.SetPosition(PosX * Size + Size/2 + offsetX, PosY * Size + offsetY, PosZ * Size + Size/2 + offsetZ);
		
		if(MovementTimer>1){MovementTimer = 1;}
		if(MovementTimer<0){MovementTimer = 0;}
		
		Pressed = false;
	}
	
	public void RenderObject(){
		super.RenderObject();
		MyButton.renderDisplayObject();
		//MyObject.renderDisplayObject();
	}

	public void RenderObjectDepth(){
		super.RenderObjectDepth();
		MyButton.renderDisplayObjectDepth();
		//MyObject.renderDisplayObjectDepth();
	}

}
