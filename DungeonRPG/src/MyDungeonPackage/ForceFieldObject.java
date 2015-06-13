package MyDungeonPackage;

public class ForceFieldObject extends DungeonObject {

	int type = 0;
	
	public ForceFieldObject(int X, int Y,int Z, float size, int PowerCondition) {
		super("forcefield", "forcefield", X, Y, Z, DisplayObjectType.AlphaObject, size);
		MyObject.SetAdditionalTexture("forcefieldalpha");
		type = PowerCondition;
		// TODO Auto-generated constructor stub
	}
	
	public void Update(int delta){
		super.Update(delta);
		if(type == 0){
			if(PowerInput > 0){
				Collidable = true;
			}
			else{
				Collidable = false;
			}
		}
		else{
			if(PowerInput == 0){
				Collidable = true;
			}
			else{
				Collidable = false;
			}
		}
	}
	
	public void RenderObject(){
		
		if(Collidable == true){
			MyObject.renderDisplayObject();
		}
	}
	

}
