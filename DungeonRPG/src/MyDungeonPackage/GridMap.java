package MyDungeonPackage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

public class GridMap {
	
	float MapArray[];
	float Visible[];
	
	MyTextureClass MyTexture;
	
	int width;
	int height;
	
	public GridMap(int Width, int Height, int TypeArray[], String TextureName){
		
		ModelHandler.AddTexture(TextureName);
		MyTexture = ModelHandler.FindTexture(TextureName);
		
		width = Width;
		height = Height;
		
		MapArray = new float [Width*Height];
		Visible = new float[Width*Height];
		
		for(int h = 0; h < Height; h++){
			for(int w = 0; w < Width; w++){
			int i = h*Width + w;
			Visible[i] = 0;
			if(TypeArray[i] == 0){
				MapArray[i] = 0;
			}
			else if(TypeArray[i] == 1){
				MapArray[i] = 1;
			}
			else{
				MapArray[i] = 2;
			}
			
			}
		}
		
	}

	public void UpdateVisibilty(int PlayerX, int PlayerZ){
		
		Visible[PlayerZ * width + PlayerX] = 1;
		Visible[(PlayerZ + 1) * width + PlayerX] = 1;
		Visible[(PlayerZ - 1) * width + PlayerX] = 1;
		Visible[(PlayerZ + 1) * width + (PlayerX - 1)] = 1;
		Visible[(PlayerZ - 1) * width + (PlayerX - 1)] = 1;
		Visible[(PlayerZ + 1) * width + (PlayerX + 1)] = 1;
		Visible[(PlayerZ - 1) * width + (PlayerX + 1)] = 1;
		Visible[(PlayerZ) * width + (PlayerX - 1)] = 1;
		Visible[(PlayerZ) * width + (PlayerX + 1)] = 1;
		
	}
	
	public void RenderMap(float x, float y, int PlayerX, int PlayerZ){
		
		for(int h = 0; h < height; h++){
			for(int w = 0; w < width; w++){
				
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				//GL13.glActiveTexture(GL13.GL_TEXTURE0);
			    //GL11.glBindTexture(GL11.GL_TEXTURE_2D, FontTexture.getTextureID());
			    int locET = GL20.glGetUniformLocation(ShaderHandler.TextShader.shaderProgram, "ColourMap");
				//GL20.glUniform1i(locET, 0);
			    MyTexture.Draw(locET);
			    Vector2f TopLeft = new Vector2f(0,0);
			    if(MapArray[h*width + w] == 0){
			    	TopLeft.x = 0.375f;
			    }
			    else if(MapArray[h*width + w] == 1){
			    	TopLeft.x = 0.0f;
			    }
			    else if(MapArray[h*width + w] == 2){
			    	TopLeft.x = 0.625f;
			    	//System.out.println("eurgh");
			    }
			    if(Visible[h*width + w] == 0){
			    	TopLeft.x = 0.375f;
			    }
			    if(PlayerX == w && PlayerZ == h){
			    	TopLeft.x = 0.125f;
			    }
			    
			    float scale = 30;
				GL11.glLoadIdentity();
				GL11.glTranslatef(x, y, 0);
							
				GL11.glBegin(GL11.GL_QUADS);
		    	GL11.glTexCoord2f(TopLeft.x, TopLeft.y + 0.125f);
		    	GL11.glVertex2f(0 + w*scale, scale + h*scale);
		        
		    	GL11.glTexCoord2f(TopLeft.x + 0.125f, TopLeft.y + 0.125f);
		    	GL11.glVertex2f(scale + w*scale, scale + h*scale);
		        
		    	GL11.glTexCoord2f(TopLeft.x + 0.125f, TopLeft.y);
		    	GL11.glVertex2f(scale + w*scale, 0 + h*scale);
		        
		    	GL11.glTexCoord2f(TopLeft.x, TopLeft.y);
		    	GL11.glVertex2f(0 + w*scale, 0 + h*scale);
		    	GL11.glEnd();
				
		
			}
		}
		
	}
	
	public void RevealMap(){
		for(int i = 0; i < width * height; i++){
			Visible[i] = 1;
		}
	}
	
}
