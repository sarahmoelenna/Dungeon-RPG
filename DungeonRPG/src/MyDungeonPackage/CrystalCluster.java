package MyDungeonPackage;

import java.util.Random;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class CrystalCluster extends DungeonObject {
	
	Vector3f MyColour;
	Vector3f ColourTimer;
	PointLight MyLight;
	Random Temp;
	boolean change;
	float MySpeed;
	
	
	public CrystalCluster(String ModelName, String TextureName, int X, int Y, int Z, DisplayObjectType MyType, float size, Vector3f Colour) {
		super(ModelName, TextureName, X, Y, Z, MyType, size);
		Temp = new Random();
		MyColour = Colour;
		MyObject.MyObjectType = DisplayObjectType.GlowObject;
		MyObject.SetColour(Colour.x, Colour.y, Colour.z);
		ColourTimer = new Vector3f(0,0,0);
		
		PointLight TempLight = new PointLight(X, Y, Z, 200, MyColour);
		ModelHandler.AddLight(TempLight);
		MyLight = ModelHandler.FindLight(TempLight);
		
		change = false;
		Collidable = true;
		
	}
	
	public CrystalCluster(String ModelName, String TextureName, int X, int Y, int Z, DisplayObjectType MyType, float size) {
		super(ModelName, TextureName, X, Y, Z, MyType, size);
		Temp = new Random();
		MyColour = new Vector3f(0,0,0);
		MyColour.x = RanInt(0,255)/255.0f;
		MyColour.y = RanInt(0,255)/255.0f;
		MyColour.z = RanInt(0,255)/255.0f;
		MyObject.MyObjectType = DisplayObjectType.GlowObject;
		MyObject.SetColour(MyColour.x, MyColour.y, MyColour.z);
		ColourTimer = new Vector3f(0,0,0);
		
		PointLight TempLight = new PointLight(X, Y, Z, 200, MyColour);
		ModelHandler.AddLight(TempLight);
		MyLight = ModelHandler.FindLight(TempLight);
		
		change = false;
		Collidable = true;
		
	}
	
	public CrystalCluster(String ModelName, String TextureName, int X, int Y, int Z, DisplayObjectType MyType, float size, float speed) {
		super(ModelName, TextureName, X, Y, Z, MyType, size);
		Temp = new Random();
		MyColour = new Vector3f(0,0,0);
		ColourTimer = new Vector3f(0,0,0);
		ColourTimer.x = RanInt(0,360);
		ColourTimer.y = RanInt(0,360);
		ColourTimer.z = RanInt(0,360);
		MyObject.MyObjectType = DisplayObjectType.GlowObject;
		MyObject.SetColour(MyColour.x, MyColour.y, MyColour.z);
		PointLight TempLight = new PointLight(X, Y, Z, 200, MyColour);
		ModelHandler.AddLight(TempLight);
		MyLight = ModelHandler.FindLight(TempLight);
		
		change = true;
		Collidable = true;
		MySpeed = speed;
		
	}
	
	
	public void RenderObject(){
		MyObject.MyObjectType = DisplayObjectType.GlowObject;
		int ColourLoc = GL20.glGetUniformLocation(ShaderHandler.FlatColourShader.shaderProgram, "MyColour");
		GL20.glUniform3f(ColourLoc, MyColour.x, MyColour.y, MyColour.z);
		MyObject.renderDisplayObject();
	}
	
	public void Update(int delta){
		MyObject.SetPosition(PosX * Size + Size/2, PosY * Size, PosZ * Size + Size/2);
		MyLight.UpdatePostion(PosX * Size + Size/2, PosY * Size + Size/2, PosZ * Size + Size/2);
		
		if(GameData.DayTime == true || GameData.Lights == false){
			MyLight.MyColour = new Vector3f(0.2f, 0.2f, 0.2f);
			MyObject.SetColour(0.2f, 0.2f, 0.2f);
		}
		else{
			if(change == true){
				ColourTimer.x = ColourTimer.x += MySpeed * delta;
				ColourTimer.y = ColourTimer.y += MySpeed * delta;
				ColourTimer.z = ColourTimer.z += MySpeed * delta;
				Vector3f TempColour = new Vector3f((float)((Math.sin(ColourTimer.x) +1)/2.5f + 0.2f ), (float)((Math.sin(ColourTimer.y) +1)/2.5f +0.2f), (float)((Math.sin(ColourTimer.z) +1)/2.5f + 0.2f));
				MyLight.MyColour = TempColour;
				MyObject.SetColour(TempColour.x, TempColour.y, TempColour.z);
			}
			else{
				MyLight.MyColour = MyColour;
				MyObject.SetColour(MyColour.x, MyColour.y, MyColour.z);
			}
			
		}
	}

	public void RenderObjectDepth(){
		if(GameData.DayTime == true){
			MyObject.MyObjectType = DisplayObjectType.TexturedObject;
			MyObject.renderDisplayObjectDepth();
		}
	}

	private int RanInt(int Min, int Max){
	    return Temp.nextInt(Max - Min) + Min;
	}
	
}
