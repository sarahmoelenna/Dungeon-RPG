package MyDungeonPackage;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class PointLight {
	
	Vector3f MyColour;
	float MyX;
	float MyY;
	float MyZ;
	float Scale;
	Model MyModel;
	boolean staticlight;
	
	public PointLight(float x, float y, float z, float radius, Vector3f Colour){
		
		MyColour = Colour;
		MyX = x;
		MyY = y;
		MyZ = z;
		Scale = radius;
		
		ModelHandler.AddModel("light");
		MyModel = ModelHandler.FindModel("light");
		staticlight = false;
		
	}
	
	public void SetStaticLight(){
		staticlight = true;
	}
	
	public void UpdatePostion(float x, float y, float z){
		MyX = x;
		MyY = y;
		MyZ = z;
	}
	
	public void Render(){
		GL11.glLoadIdentity();
		//System.out.println(MyX + " " + MyY + " " + MyZ);
        //int NormalLoc = GL20.glGetUniformLocation(ShaderHandler.TexturedColourloc.shaderProgram, "NormalMap");
        //GL20.glUniform1i(ShaderHandler.TexturedColourloc, 0);
		
		int ColourLoc = GL20.glGetUniformLocation(ShaderHandler.PointLightShader.shaderProgram, "Colour");
        GL20.glUniform3f(ColourLoc, MyColour.x, MyColour.y, MyColour.z);
		
		
		GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
		GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
		GL11.glRotatef(0, 0, 1, 0);
		GL11.glTranslatef(MyX, MyY, MyZ);
		
		GL11.glScalef(100, 100, 100);
		
		
		MyModel.Draw();
	}
	

}
