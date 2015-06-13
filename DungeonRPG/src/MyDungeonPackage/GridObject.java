package MyDungeonPackage;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class GridObject {
	
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer TextBuffer;

	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_texture_handle;
	
	int Size;
	
	MyTextureClass MyTexture;
	MyTextureClass MyGlowTexture;
	
	FloatBuffer modelview;
	FloatBuffer projection;
	
	public GridObject(TileSet MyTileSet, int Width, int Height, int TypeArray[], String TextureName, int WallArray[]){
		
		ModelHandler.AddTexture(TextureName);
		MyTexture = ModelHandler.FindTexture(TextureName);
		
		ModelHandler.AddTexture("blank");
		MyGlowTexture = ModelHandler.FindTexture("blank");
		
		modelview = BufferUtils.createFloatBuffer(16);
    	projection = BufferUtils.createFloatBuffer(16);
		
		Size = 0;
		int Up = 0;
		int UpLeft = 0;
		int UpRight = 0;
		int Down = 0;
		int DownLeft = 0;
		int DownRight = 0;
		int Left = 0;
		int Right = 0;
		int Center = 0;
		
		
		for(int i = 0; i < Width*Height; i++){
			
			Up = 0;
			UpLeft = 0;
			UpRight = 0;
			Down = 0;
			DownLeft = 0;
			DownRight = 0;
			Left = 0;
			Right = 0;
			
			Center = TypeArray[i];
			
			if(Center != 0){
				
				if(i>Width){
					Up = TypeArray[i - Width];
					if(i > Width + 1){
						UpLeft = TypeArray[i - Width - 1];
					}
					UpRight = TypeArray[i - Width + 1];
				}
				if(i < Width*Height - Width){
					Down = TypeArray[i + Width];
					if(i < Width*Height - Width + 1){
						UpRight = TypeArray[i + Width + 1];
					}
					UpLeft = TypeArray[i + Width + 1];
				}
				if(i > 0){
					Left = TypeArray[i - 1];
				}
				if(i < Width*Height - 1){
					Right = TypeArray[i + 1];
				}
				
				
				Size += MyTileSet.GetObject(Center, Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight).Faces;
			}
			
			if(WallArray[i] > 0){
				if(WallArray[i] == 3){
					Size += MyTileSet.GrateData.Faces * 2;
				}
				else{
					Size += MyTileSet.GrateData.Faces;
				}
				
			}
			
			
		}
		
		//System.out.println(Size);
		
		//create the object
		VertBuffer = BufferUtils.createFloatBuffer(Size * 9);
        NormBuffer = BufferUtils.createFloatBuffer(Size * 9);
        TextBuffer = BufferUtils.createFloatBuffer(Size * 6);
		
        for(int h = 0; h < Height; h++){
        	for(int w = 0; w < Width; w++){
        	Up = 0;
			UpLeft = 0;
			UpRight = 0;
			Down = 0;
			DownLeft = 0;
			DownRight = 0;
			Left = 0;
			Right = 0;
			Center = TypeArray[h * Width + w];
			
			if(Center != 0){
				
				int i = h * Width + w;
				if(i>Width){
					Up = TypeArray[i - Width];
					if(i > Width + 1){
						UpLeft = TypeArray[i - Width - 1];
					}
					UpRight = TypeArray[i - Width + 1];
				}
				if(i < Width*Height - Width){
					Down = TypeArray[i + Width];
					if(i < Width*Height - Width + 1){
						UpRight = TypeArray[i + Width + 1];
					}
					UpLeft = TypeArray[i + Width + 1];
				}
				if(i > 0){
					Left = TypeArray[i - 1];
				}
				if(i < Width*Height - 1){
					Right = TypeArray[i + 1];
				}
				
				//ModelData TempModel = new ModelData();
				//TempModel = MyTileSet.GetObject(Center, Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight);
				//float TempRotation = MyTileSet.GetRotation();

				VertBuffer.put(MyTileSet.GetObject(Center, Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight).GetTranslatedAndRotatedVertexArray(w, 0, h, MyTileSet.GetRotation()));
				NormBuffer.put(MyTileSet.GetObject(Center, Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight).GetRotatedNormArray(MyTileSet.GetRotation()));
				TextBuffer.put(MyTileSet.GetObject(Center, Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight).FinTextArray);
			}
			
			if(WallArray[h * Width + w] > 0){
				int i = h * Width + w;
				
				if(WallArray[i] == 3){
					//Size += MyTileSet.GrateData.Faces * 2;
					VertBuffer.put(MyTileSet.GrateData.GetTranslatedAndRotatedVertexArray(w, 0, h, 90));
					NormBuffer.put(MyTileSet.GrateData.GetRotatedNormArray(90));
					TextBuffer.put(MyTileSet.GrateData.FinTextArray);
					
					VertBuffer.put(MyTileSet.GrateData.GetTranslatedAndRotatedVertexArray(w, 0, h, 180));
					NormBuffer.put(MyTileSet.GrateData.GetRotatedNormArray(180));
					TextBuffer.put(MyTileSet.GrateData.FinTextArray);
				}
				else if(WallArray[i] == 2){
					VertBuffer.put(MyTileSet.GrateData.GetTranslatedAndRotatedVertexArray(w, 0, h, 90));
					NormBuffer.put(MyTileSet.GrateData.GetRotatedNormArray(90));
					TextBuffer.put(MyTileSet.GrateData.FinTextArray);
				}
				else if(WallArray[i] == 1){
					VertBuffer.put(MyTileSet.GrateData.GetTranslatedAndRotatedVertexArray(w, 0, h, 180));
					NormBuffer.put(MyTileSet.GrateData.GetRotatedNormArray(180));
					TextBuffer.put(MyTileSet.GrateData.FinTextArray);
				}
				
			}
			
			
        	}
        }
        //System.out.println(VertBuffer.toString());
        
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
	
	 public void Draw(){
		 
		 	GL11.glLoadIdentity();
		 	ShaderHandler.TextNormShader.Activate();
			
			GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
			GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
			GL11.glScalef(GameData.GameScale, GameData.GameScale, GameData.GameScale);
			
			ShaderHandler.TextNormShader.Activate();
			int Texturedloc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ColourMap");
			MyTexture.Draw(Texturedloc);
			
			int Shadowloc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ShadowMap");
			GameData.Draw(Shadowloc);
			
			MyGlowTexture.DrawTwo(ShaderHandler.TexturedGlowLoc);
			
			int locMM= GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ModelMatrix4x4");
		    GL20.glUniformMatrix4(locMM,false,modelview);
		     
		    int locPM= GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ProjectionMatrix4x4");
		    GL20.glUniformMatrix4(locPM,false,projection);
		    
		    GL20.glUniform3f(ShaderHandler.TexturedLightLoc, GameData.LightColour.x, GameData.LightColour.y, GameData.LightColour.z);
		 
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
	  		
	  		ShaderHandler.TextNormShader.DeActivate();
	    }
	 
	 public void DrawDepth(){
		 
		 	GL11.glLoadIdentity();
		 	ShaderHandler.NormDepthShader.Activate();
			
		 	GL11.glRotatef(GameData.DepthRotation.x, 1, 0, 0);
			GL11.glRotatef(GameData.DepthRotation.y, 0, 1, 0);
			GL11.glTranslatef(GameData.DepthPosition.x, GameData.DepthPosition.y, GameData.DepthPosition.z);
			GL11.glScalef(GameData.GameScale, GameData.GameScale, GameData.GameScale);
			
		 
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
	  		
	  		
	  		
	  		ShaderHandler.NormDepthShader.DeActivate();
	    }

}
