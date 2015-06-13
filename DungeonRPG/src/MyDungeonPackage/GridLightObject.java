package MyDungeonPackage;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class GridLightObject {
	
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer TextBuffer;

	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_texture_handle;
	
	int Size;
	
	MyTextureClass MyDayTexture;
	MyTextureClass MyNightTexture;
	MyTextureClass MyGlowTexture;
	
	FloatBuffer modelview;
	FloatBuffer projection;
	
	public GridLightObject(TileSet MyTileSet, int Width, int Height, int TypeArray[]){
		
		ModelHandler.AddTexture("lampday");
		MyDayTexture = ModelHandler.FindTexture("lampday");
		
		ModelHandler.AddTexture("lampnight");
		MyNightTexture = ModelHandler.FindTexture("lampnight");
		
		ModelHandler.AddTexture("lampglow");
		MyGlowTexture = ModelHandler.FindTexture("lampglow");
		
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
		int lights = 0;
		
		for(int h = 0; h < Height; h++){
			for(int w = 0; w < Width; w++){
			int i = h * Width + w;
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
				
				if(GameData.CustomDungeon == false){
					if(TypeArray[i] == 3 || TypeArray[i] == 6){
						if(Up == 1 || Down == 1 || Left == 1 || Right == 1){
							if(lights == 4){
								Size += MyTileSet.LampData.Faces;
								lights = 0;
							}
							lights++;
						}
					}
				}
				else{
					if(TypeArray[i] == 3){
						if(Up == 1 || Down == 1 || Left == 1 || Right == 1){
							if(lights == 4){
								Size += MyTileSet.LampData.Faces;
								lights = 0;
							}
							lights++;
						}
					}
					else{
						if(TypeArray[i] == 11 || TypeArray[i] == 12 || TypeArray[i] == 13 || TypeArray[i] == 14){
							Size += MyTileSet.LampData.Faces;
						}
					}
				}
				
				//Size += MyTileSet.GetObject(Center, Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight).Faces;
			}
			
			
			}
		}
		
		//System.out.println(Size);
		
		//create the object
		VertBuffer = BufferUtils.createFloatBuffer(Size * 9);
        NormBuffer = BufferUtils.createFloatBuffer(Size * 9);
        TextBuffer = BufferUtils.createFloatBuffer(Size * 6);
		lights = 0;
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
				if(GameData.CustomDungeon == false){
					if(TypeArray[i] == 3 || TypeArray[i] == 6){
						if(Up == 1 || Down == 1 || Left == 1 || Right == 1){
							if(lights==4){
								float Direction = 0;
								if(Up == 1){
									Direction = 90;
								}
								if(Down == 1){
									Direction = 270;
								}
								if(Left == 1){
									Direction = 0;
								}
								if(Right == 1){
									Direction = 180;
								}
							
							
								VertBuffer.put(MyTileSet.LampData.GetTranslatedAndRotatedVertexArray(w, 0, h, Direction));
								NormBuffer.put(MyTileSet.LampData.GetRotatedNormArray(Direction));
								TextBuffer.put(MyTileSet.LampData.FinTextArray);
							
								PointLight TempLight = new PointLight(w*GameData.GameScale + GameData.GameScale/2, 0.6f * GameData.GameScale, h*GameData.GameScale + GameData.GameScale/2, 200, new Vector3f(0.45f, 1.0f, 0.13f));
								TempLight.SetStaticLight();
								ModelHandler.AddLight(TempLight);
								lights=0;
							}
							lights++;
						}
					}
				}
				else{
					if(TypeArray[i] == 3){
						if(Up == 1 || Down == 1 || Left == 1 || Right == 1){
							if(lights==4){
								float Direction = 0;
								if(Up == 1){
									Direction = 90;
								}
								if(Down == 1){
									Direction = 270;
								}
								if(Left == 1){
									Direction = 0;
								}
								if(Right == 1){
									Direction = 180;
								}
							
							
								VertBuffer.put(MyTileSet.LampData.GetTranslatedAndRotatedVertexArray(w, 0, h, Direction));
								NormBuffer.put(MyTileSet.LampData.GetRotatedNormArray(Direction));
								TextBuffer.put(MyTileSet.LampData.FinTextArray);
							
								PointLight TempLight = new PointLight(w*GameData.GameScale + GameData.GameScale/2, 0.6f * GameData.GameScale, h*GameData.GameScale + GameData.GameScale/2, 200, new Vector3f(0.45f, 1.0f, 0.13f));
								TempLight.SetStaticLight();
								ModelHandler.AddLight(TempLight);
								lights=0;
							}
							lights++;
						}
					}
					else{
						if(TypeArray[i] == 11){
							float Direction = 90;
							VertBuffer.put(MyTileSet.LampData.GetTranslatedAndRotatedVertexArray(w, 0, h, Direction));
							NormBuffer.put(MyTileSet.LampData.GetRotatedNormArray(Direction));
							TextBuffer.put(MyTileSet.LampData.FinTextArray);
							PointLight TempLight = new PointLight(w*GameData.GameScale + GameData.GameScale/2, 0.6f * GameData.GameScale, h*GameData.GameScale + GameData.GameScale/2, 200, new Vector3f(0.45f, 1.0f, 0.13f));
							TempLight.SetStaticLight();
							ModelHandler.AddLight(TempLight);
						}
						if(TypeArray[i] == 12){
							float Direction = 180;
							VertBuffer.put(MyTileSet.LampData.GetTranslatedAndRotatedVertexArray(w, 0, h, Direction));
							NormBuffer.put(MyTileSet.LampData.GetRotatedNormArray(Direction));
							TextBuffer.put(MyTileSet.LampData.FinTextArray);
							PointLight TempLight = new PointLight(w*GameData.GameScale + GameData.GameScale/2, 0.6f * GameData.GameScale, h*GameData.GameScale + GameData.GameScale/2, 200, new Vector3f(0.45f, 1.0f, 0.13f));
							TempLight.SetStaticLight();
							ModelHandler.AddLight(TempLight);
						}
						if(TypeArray[i] == 13){
							float Direction = 270;
							VertBuffer.put(MyTileSet.LampData.GetTranslatedAndRotatedVertexArray(w, 0, h, Direction));
							NormBuffer.put(MyTileSet.LampData.GetRotatedNormArray(Direction));
							TextBuffer.put(MyTileSet.LampData.FinTextArray);
							PointLight TempLight = new PointLight(w*GameData.GameScale + GameData.GameScale/2, 0.6f * GameData.GameScale, h*GameData.GameScale + GameData.GameScale/2, 200, new Vector3f(0.45f, 1.0f, 0.13f));
							TempLight.SetStaticLight();
							ModelHandler.AddLight(TempLight);
						}
						if(TypeArray[i] == 14){
							float Direction = 0;
							VertBuffer.put(MyTileSet.LampData.GetTranslatedAndRotatedVertexArray(w, 0, h, Direction));
							NormBuffer.put(MyTileSet.LampData.GetRotatedNormArray(Direction));
							TextBuffer.put(MyTileSet.LampData.FinTextArray);
							PointLight TempLight = new PointLight(w*GameData.GameScale + GameData.GameScale/2, 0.6f * GameData.GameScale, h*GameData.GameScale + GameData.GameScale/2, 200, new Vector3f(0.45f, 1.0f, 0.13f));
							TempLight.SetStaticLight();
							ModelHandler.AddLight(TempLight);
						}
					}
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
			if(GameData.DayTime == true || GameData.Lights == false){
				MyNightTexture.Draw(ShaderHandler.TexturedColourloc);
			}
			else{
				MyDayTexture.Draw(ShaderHandler.TexturedColourloc);
			}
			
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
