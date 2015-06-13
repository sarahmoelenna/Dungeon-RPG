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

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;


public class FiveMRTFBO {
	
	public int fbo;
    public int depthbuffer;
    public int fb_texture;
    public int fb_texture_2;
    public int fb_texture_3;
    public int fb_texture_4;
    public int fb_texture_5;
	
	public FiveMRTFBO(int HEIGHT, int WIDTH){
		
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
          
          
          
        //create texture to render to
          fb_texture_2 = GL11.glGenTextures();
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture_2);
          GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
          GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, WIDTH, HEIGHT, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (java.nio.ByteBuffer)null);
          
          //attach texture to the fbo
          glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL30.GL_COLOR_ATTACHMENT1 , GL11.GL_TEXTURE_2D, fb_texture_2, 0); //ANOTHER OF THIS FOR MRT
          
        //create texture to render to
          fb_texture_2 = GL11.glGenTextures();
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture_2);
          GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
          GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, WIDTH, HEIGHT, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (java.nio.ByteBuffer)null);
          
          //attach texture to the fbo
          glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL30.GL_COLOR_ATTACHMENT1 , GL11.GL_TEXTURE_2D, fb_texture_2, 0); //ANOTHER OF THIS FOR MRT
          
          
        //create texture to render to
          fb_texture_3 = GL11.glGenTextures();
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture_3);
          GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
          GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, WIDTH, HEIGHT, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (java.nio.ByteBuffer)null);
          
          //attach texture to the fbo
          glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL30.GL_COLOR_ATTACHMENT2 , GL11.GL_TEXTURE_2D, fb_texture_3, 0); //ANOTHER OF THIS FOR MRT
          
        //create texture to render to
          fb_texture_4 = GL11.glGenTextures();
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture_4);
          GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
          GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, WIDTH, HEIGHT, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (java.nio.ByteBuffer)null);
          
          //attach texture to the fbo
          glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL30.GL_COLOR_ATTACHMENT3 , GL11.GL_TEXTURE_2D, fb_texture_4, 0); //ANOTHER OF THIS FOR MRT
          
          //create texture to render to
          fb_texture_5 = GL11.glGenTextures();
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture_5);
          GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
          GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, WIDTH, HEIGHT, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (java.nio.ByteBuffer)null);
          
          //attach texture to the fbo
          glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL30.GL_COLOR_ATTACHMENT4 , GL11.GL_TEXTURE_2D, fb_texture_5, 0); //ANOTHER OF THIS FOR MRT
          
          
          
          
          //check completeness
          if(glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) == GL_FRAMEBUFFER_COMPLETE_EXT){
             System.out.println("Frame buffer created sucessfully.");
          }
          else{
             System.out.println("An error occured creating the frame buffer.");
      	  }
          
          
          IntBuffer buffers = BufferUtils.createIntBuffer(5);

          buffers.put(GL30.GL_COLOR_ATTACHMENT0);
          buffers.put(GL30.GL_COLOR_ATTACHMENT1);
          buffers.put(GL30.GL_COLOR_ATTACHMENT2);
          buffers.put(GL30.GL_COLOR_ATTACHMENT3);
          buffers.put(GL30.GL_COLOR_ATTACHMENT4);
          buffers.flip();
          
          GL20.glDrawBuffers(buffers);
		
	}
	
	public void BindBuffer(){
		
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);
		
	}

}
