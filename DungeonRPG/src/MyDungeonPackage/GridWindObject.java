package MyDungeonPackage;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class GridWindObject {
	
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer TextBuffer;

	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_texture_handle;
	
	int Size;
	
	int grassNumber;
	float WindVal;
	float WindSin;
	
	MyTextureClass MyTexture;
	
	ModelData GrassData;
	
	Random Temp;
	
	FloatBuffer modelview;
	FloatBuffer projection;
	
	public GridWindObject(int Width, int Height, int TypeArray[], String TextureName){
		WindSin = 0;
		WindVal = 0;
		Temp = new Random();
		GrassData = new ModelData("grass", false);
		
		modelview = BufferUtils.createFloatBuffer(16);
    	projection = BufferUtils.createFloatBuffer(16);
		
		ModelHandler.AddTexture(TextureName);
		MyTexture = ModelHandler.FindTexture(TextureName);
		
		int TempArray[] = new int[Width*Height];
		
		for(int i = 0; i < Width*Height; i++){
			TempArray[i] = 0;
			
			if(TypeArray[i] == 2){
				int number = RanInt(0,13);
				//int number = 1;
				TempArray[i] = number;
				grassNumber+= number;
			}
		}
		
		Size = grassNumber * GrassData.Faces;
		//System.out.println(Size);
		
		//create the object
		VertBuffer = BufferUtils.createFloatBuffer(Size * 9);
		NormBuffer = BufferUtils.createFloatBuffer(Size * 9);
		TextBuffer = BufferUtils.createFloatBuffer(Size * 6);
		
		 for(int h = 0; h < Height; h++){
	        	for(int w = 0; w < Width; w++){
	        		
	        		int i = h*Width + w;
	        		
	        		if(TempArray[i] > 0){
	        		
	        			for(int x = 0; x < TempArray[i]; x++){
	        				float RanX = (float)(RanInt(0,100))/100;
	        				float RanZ = (float)(RanInt(0,100))/100;
	        				
	        				VertBuffer.put(GrassData.GetTranslatedAndRotatedVertexArray(w + RanX, 0, h + RanZ, 0));
	        				NormBuffer.put(GrassData.GetRotatedNormArray(0));
	        				TextBuffer.put(GrassData.FinTextArray);
	        			}
	        		
	        		}
	        	}
		 }
		 
		 
		 VertBuffer.rewind();
	     NormBuffer.rewind();
	     TextBuffer.rewind();
	        
	     vbo_vertex_handle = GL15.glGenBuffers();
	     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
	     GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_STATIC_DRAW);
	     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	     vbo_normal_handle = GL15.glGenBuffers();
	     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
	     GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_STATIC_DRAW);
	     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	     vbo_texture_handle = GL15.glGenBuffers();
	     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
	     GL15.glBufferData(GL15.GL_ARRAY_BUFFER, TextBuffer, GL15.GL_STATIC_DRAW);
	     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	public void Update(int delta){
		WindVal += 0.001f * delta;
		WindSin = ((float) Math.sin(WindVal) + 1)/2;
	}
	
	public void Draw(){
		 
		 	GL11.glLoadIdentity();
		 	ShaderHandler.ShipShader.Activate();
			
		 	
			GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
			GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
			GL11.glScalef(GameData.GameScale, GameData.GameScale, GameData.GameScale);
			
			//int Texturedloc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ColourMap");
			//int WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "WindScale");
			GL20.glUniform1f(ShaderHandler.WindScaleLoc, WindSin);
			MyTexture.Draw(ShaderHandler.WindColourloc);
			
			
			//int Shadowloc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ShadowMap");
			GameData.Draw(ShaderHandler.WindShadowLoc);
			//int locMM= GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ModelMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.WindModLoc,false,modelview);
		    //int locPM= GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ProjectionMatrix4x4");
		    GL20.glUniformMatrix4(ShaderHandler.WindProjLoc,false,projection);
		    GL20.glUniform3f(ShaderHandler.WindLightLoc, GameData.LightColour.x, GameData.LightColour.y, GameData.LightColour.z);
	    	
	    	GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	  		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
	  		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	  		  
	  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
	  		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

	  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
	  		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
	  		  
	  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
	  		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);

	  		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Size*9);
	  		
	  		ShaderHandler.ShipShader.DeActivate();

	    }
	
	public void DrawDepth(){
		 
	 	GL11.glLoadIdentity();
	 	ShaderHandler.WindDepthShader.Activate();
		
	 	GL11.glRotatef(GameData.DepthRotation.x, 1, 0, 0);
		GL11.glRotatef(GameData.DepthRotation.y, 0, 1, 0);
		GL11.glTranslatef(GameData.DepthPosition.x, GameData.DepthPosition.y, GameData.DepthPosition.z);
		GL11.glScalef(GameData.GameScale, GameData.GameScale, GameData.GameScale);
		
		//int Texturedloc = GL20.glGetUniformLocation(ShaderHandler.WindDepthShader.shaderProgram, "ColourMap");
		//int WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.WindDepthShader.shaderProgram, "WindScale");
		GL20.glUniform1f(ShaderHandler.WindDepthScaleLoc, WindSin);
		MyTexture.Draw(ShaderHandler.WindDepthColourloc);
	 
    	
    	GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
  		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
  		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
  		  
  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
  		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
  		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
  		  
  		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
  		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);

  		
  		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
	    GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
  		
  		
  		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Size*9);
  		
  		ShaderHandler.WindDepthShader.DeActivate();

    }

	 private int RanInt(int Min, int Max){
	    	return Temp.nextInt(Max - Min) + Min;
	  }
	 
}
