package MyDungeonPackage;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class DisplayObject {
	
	Model MyModel;
	MyTextureClass MyTexture;
	MyTextureClass MyAdditionalTexture;
	MyTextureClass MyThirdTexture;
	MyTextureClass GlowMaptexture;
	Vector3f Position;
	float Rotation;
	float WindVal;
	float WindSin;
	Vector3f Scale;
	Vector3f MyColour;
	
	DisplayObjectType MyObjectType;
	
	FloatBuffer modelview;
	FloatBuffer projection;
	
	public DisplayObject(String ModelName, String TextureName, DisplayObjectType myobjecttype){
		
		ModelHandler.AddModel(ModelName);
		ModelHandler.AddTexture(TextureName);
		MyTexture = ModelHandler.FindTexture(TextureName);
		MyModel = ModelHandler.FindModel(ModelName);
		ModelHandler.AddTexture("blank");
		GlowMaptexture = ModelHandler.FindTexture("blank");
		MyColour = new Vector3f(1,1,1);
		Position = new Vector3f(0,0,-20);
		Rotation = 0;
		Scale = new Vector3f(2,2,2);
		WindSin = 0;
		WindVal = 0;
		MyObjectType = myobjecttype;
		
		modelview = BufferUtils.createFloatBuffer(16);
    	projection = BufferUtils.createFloatBuffer(16);
		
	}
	
	public void SetAdditionalTexture(String TextureName){
		
		ModelHandler.AddTexture(TextureName);
		MyAdditionalTexture = ModelHandler.FindTexture(TextureName);
		
	}
	
	public void SetThirdTexture(String TextureName){
		
		ModelHandler.AddTexture(TextureName);
		MyThirdTexture = ModelHandler.FindTexture(TextureName);
		
	}
	
	public void UpdateDiplayObject(float delta){
		
		WindVal += 0.001f * delta;
		if(MyObjectType == DisplayObjectType.WindObject){
			WindSin = ((float) Math.sin(WindVal) + 1)/2;
		}
		if(MyObjectType == DisplayObjectType.GradientDisplacementObject){
			WindSin = (float) Math.sin(WindVal);
			//System.out.println(WindSin);
		}
		
	}
	
	public void IncrementWindScale(float increment, float max, float delta){
		WindSin+= increment * delta;
		if(WindSin > max){WindSin = max;}
	}
	
	public void DecrementWindScale(float increment, float min, float delta){
		WindSin-= increment * delta;
		if(WindSin < min){WindSin = min;}
	}
	
	public void renderDisplayObject(){
		GL11.glLoadIdentity();
		
		GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
		GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
		
		GL11.glTranslatef(Position.x, Position.y, Position.z);
		GL11.glRotatef(Rotation, 0, 1, 0);
		
		GL11.glScalef(Scale.x, Scale.y, Scale.z);
		
		if(MyObjectType == DisplayObjectType.WindObject){
			ShaderHandler.ShipShader.Activate();
			GL20.glUniform1f(ShaderHandler.WindScaleLoc, WindSin);
			MyTexture.Draw(ShaderHandler.WindColourloc);
			
			GL20.glUniform1f(ShaderHandler.WindScaleLoc, WindSin);
			
			//int Shadowloc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ShadowMap");
			GameData.Draw(ShaderHandler.WindShadowLoc);
			//int locMM= GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ModelMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.WindModLoc,false,modelview);
		    //int locPM= GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ProjectionMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.WindProjLoc,false,projection);
		    GL20.glUniform3f(ShaderHandler.WindLightLoc, GameData.LightColour.x, GameData.LightColour.y, GameData.LightColour.z);
		}
		
		if(MyObjectType == DisplayObjectType.GradientDisplacementObject){
			ShaderHandler.GradDisplaceShader.Activate();
			//int Windloc = GL20.glGetUniformLocation(ShaderHandler.GradDisplaceShader.shaderProgram, "ColourMap");
			//int WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.GradDisplaceShader.shaderProgram, "WindScale");
			GL20.glUniform1f(ShaderHandler.GradColourloc, WindSin);
			MyTexture.Draw(ShaderHandler.WindColourloc);
		}
		
		if(MyObjectType == DisplayObjectType.TexturedObject){
			ShaderHandler.TextNormShader.Activate();
			//int Texturedloc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ColourMap");
			MyTexture.Draw(ShaderHandler.TexturedColourloc);
			
			GlowMaptexture.DrawTwo(ShaderHandler.TexturedGlowLoc);
			
			//int Shadowloc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ShadowMap");
			GameData.Draw(ShaderHandler.TexturedShadowLoc);
			//int locMM= GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ModelMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.TexturedModLoc,false,modelview);
		    //int locPM= GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ProjectionMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.TexturedProjLoc,false,projection);
		    GL20.glUniform3f(ShaderHandler.TexturedLightLoc, GameData.LightColour.x, GameData.LightColour.y, GameData.LightColour.z);
		}
		
		if(MyObjectType == DisplayObjectType.GlowObject){
			ShaderHandler.FlatColourShader.Activate();
			 //int ColourLoc = GL20.glGetUniformLocation(ShaderHandler.FlatColourShader.shaderProgram, "MyColour");
		 	GL20.glUniform3f(ShaderHandler.GlowColourloc, MyColour.x, MyColour.y, MyColour.z);
			//int Texturedloc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ColourMap");
			//MyTexture.Draw(ShaderHandler.TexturedColourloc);
		}
		
		if(MyObjectType == DisplayObjectType.RotationDisplacemntObject){
			ShaderHandler.RotDisplaceShader.Activate();
			//int Texturedloc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ColourMap");
			MyTexture.Draw(ShaderHandler.RotColourloc);
			
			//int Texturedloc2 = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "RotMap");
			MyAdditionalTexture.DrawTwo(ShaderHandler.RotRotLoc);
			
			//int Texturedloc3 = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "AxisMap");
			MyThirdTexture.DrawThree(ShaderHandler.RotAxisloc);
			
			//int WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "WindScale");
			GL20.glUniform1f(ShaderHandler.RotScaleLoc, WindSin);
			
			//int Shadowloc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ShadowMap");
			GameData.Draw(ShaderHandler.RotShadowLoc);
			//int locMM= GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ModelMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.RotModLoc,false,modelview);
		    //int locPM= GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ProjectionMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.RotProjLoc,false,projection);
		    GL20.glUniform3f(ShaderHandler.RotLightLoc, GameData.LightColour.x, GameData.LightColour.y, GameData.LightColour.z);
		}
		
		if(MyObjectType == DisplayObjectType.AlphaObject){
			ShaderHandler.AlphaMapTextShader.Activate();
			MyTexture.Draw(ShaderHandler.AlphaColourLoc);
			MyAdditionalTexture.DrawTwo(ShaderHandler.AlphaMapLoc);
		}
		
		MyModel.Draw();
		
		ShaderHandler.ShipShader.DeActivate();
		ShaderHandler.TextNormShader.DeActivate();
		ShaderHandler.GradDisplaceShader.DeActivate();
		ShaderHandler.RotDisplaceShader.DeActivate();
		ShaderHandler.FlatColourShader.DeActivate();
		ShaderHandler.AlphaMapTextShader.DeActivate();
	}
	
	public void renderDisplayObjectDepth(){
		GL11.glLoadIdentity();
		
		//GL11.glRotatef(GameData.DepthRotation.y, 0, 1, 0);
		
		GL11.glRotatef(GameData.DepthRotation.x, 1, 0, 0);
		GL11.glRotatef(GameData.DepthRotation.y, 0, 1, 0);
		GL11.glTranslatef(GameData.DepthPosition.x, GameData.DepthPosition.y, GameData.DepthPosition.z);
		
		GL11.glTranslatef(Position.x, Position.y, Position.z);
		GL11.glRotatef(Rotation, 0, 1, 0);
		
		GL11.glScalef(Scale.x, Scale.y, Scale.z);
		
		if(MyObjectType == DisplayObjectType.WindObject){
			ShaderHandler.WindDepthShader.Activate();
			//int Windloc = GL20.glGetUniformLocation(ShaderHandler.WindDepthShader.shaderProgram, "ColourMap");
			//int WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.WindDepthShader.shaderProgram, "WindScale");
			GL20.glUniform1f(ShaderHandler.WindDepthScaleLoc, WindSin);
			MyTexture.Draw(ShaderHandler.WindDepthColourloc);
		}
		
		if(MyObjectType == DisplayObjectType.TexturedObject){
			ShaderHandler.NormDepthShader.Activate();
		}
		
		if(MyObjectType == DisplayObjectType.RotationDisplacemntObject){
			ShaderHandler.RotDisplaceDepthShader.Activate();
			//int Texturedloc2 = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceDepthShader.shaderProgram, "RotMap");
			MyAdditionalTexture.DrawTwo(ShaderHandler.RotDepthRotLoc);
			
			//int Texturedloc3 = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceDepthShader.shaderProgram, "AxisMap");
			MyThirdTexture.DrawThree(ShaderHandler.RotDepthAxisloc);
			
			//int WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceDepthShader.shaderProgram, "WindScale");
			GL20.glUniform1f(ShaderHandler.RotDepthScaleLoc, WindSin);
		}
		
		 GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
	     GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		
	     if(MyObjectType != DisplayObjectType.GlowObject){
	    	 MyModel.Draw();
	     }
		
		ShaderHandler.WindDepthShader.DeActivate();
		ShaderHandler.NormDepthShader.DeActivate();
		ShaderHandler.RotDisplaceDepthShader.DeActivate();
	}
	
	public void SetScale(float scale){
		
		Scale = new Vector3f(scale,scale,scale);
	
	}

	public void SetPosition(float x, float y, float z){
		Position = new Vector3f(x,y,z);
	}
	
	public void SetColour(float r, float g, float b){
		MyColour = new Vector3f(r,g,b);
	}
	
	
	public void SetRotation(float y){
		Rotation = y;
	}
	
}
