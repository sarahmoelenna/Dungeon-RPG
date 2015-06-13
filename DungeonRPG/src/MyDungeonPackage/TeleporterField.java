package MyDungeonPackage;

import org.lwjgl.util.vector.Vector3f;

public class TeleporterField extends DungeonObject {

	ParticleEmitter MyEmitter;
	int type;
	
	public TeleporterField(int X, int Y,int Z, float size, int powered) {
		super("teleporter", "forcefield", X, Y, Z, DisplayObjectType.AlphaObject, size);
		// TODO Auto-generated constructor stub
		MyObject.SetAdditionalTexture("teleporteralpha");
		MyEmitter = new ParticleEmitter(X, Y, Z, new Vector3f(1,1,1), 2);
		ParticleHandler.AddEmitter(MyEmitter);
		MyEmitter = ParticleHandler.FindEmitter(MyEmitter);
		type = powered;
	}
	
	public void Update(int delta){
		super.Update(delta);
		MyEmitter.Position = new Vector3f(PosX * Size - offsetX + Size/2, PosY * Size + Size/2, PosZ * Size - offsetZ + Size/2);
		//MyEmitter.Position.y = Size/2;
		MyEmitter.UpdateEmitter(delta);
		if(type == 0){
			if(PowerInput > 0){
				MyEmitter.UpdateEmitter(delta);
				MyEmitter.Render = true;
			}
			else{
				MyEmitter.Render = false;
			}
		}
		else{
			if(PowerInput == 0){
				MyEmitter.UpdateEmitter(delta);
				MyEmitter.Render = true;
			}
			else{
				MyEmitter.Render = false;
			}
		}
		
	}
	
	public void RenderObject(){
		if(type == 0){
			if(PowerInput > 0){
				MyObject.renderDisplayObject();
			}
		}
		else{
			if(PowerInput == 0){
				MyObject.renderDisplayObject();
			}
		}
	}
	
	public void RenderObjectDepth(){
		
	}

}
