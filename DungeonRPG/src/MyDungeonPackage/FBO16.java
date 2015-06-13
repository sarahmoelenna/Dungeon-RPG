package MyDungeonPackage;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_RENDERBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glCheckFramebufferStatusEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenRenderbuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glRenderbufferStorageEXT;

import org.lwjgl.opengl.GL11;


public class FBO16 {
	
	public int fbo;
    public int depthbuffer;
    public int fb_texture;
	
	public FBO16(int HEIGHT, int WIDTH){
		
		 //frame buffer
       	fbo = glGenFramebuffersEXT();
          glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);
          
          //depth buffer
          depthbuffer = glGenRenderbuffersEXT();
          glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthbuffer);
          
          //allocate space for the renderbuffer
          glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL11.GL_DEPTH_COMPONENT, WIDTH, HEIGHT);
          
          //attach depth buffer to fbo
          glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthbuffer);
          
          //create texture to render to
          fb_texture = GL11.glGenTextures();
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture);
          GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
          GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, WIDTH, HEIGHT, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (java.nio.ByteBuffer)null);
          
          //attach texture to the fbo
          glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, fb_texture, 0); //ANOTHER OF THIS FOR MRT
          
          //check completeness
          if(glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) == GL_FRAMEBUFFER_COMPLETE_EXT){
             System.out.println("Frame buffer created sucessfully.");
          }
          else{
             System.out.println("An error occured creating the frame buffer.");
      	  }
		
		
	}
	
	public void BindBuffer(){
		
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);
		
	}

}
