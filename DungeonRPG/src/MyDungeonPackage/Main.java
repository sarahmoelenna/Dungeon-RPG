package MyDungeonPackage;


import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
//import org.lwjgl.opengl.glu.GLU;
import org.lwjgl.input.Keyboard;

public class Main {
    private boolean done = false;
    private boolean fullscreen = false;
    private final String windowTitle = "Moe's World Game";
    private boolean f1 = false;
    private DisplayMode displayMode;
    
    
    float rotation = 0.0f;
    int Temp = 0;
    long time;
    long temptime;
    long tempdrawtime;
    
    final int WIDTH = 1920;
    final int HEIGHT = 1080;
    
    FBO DepthRender;
    FBO XBlurFBO;
    FBO YBlurFBO;
    FBO FinalRenderFBO;
    FBO LightFBO;
    SixMRTFBO LevelRenderFBO;

    Controller MyController;
    MyTextureClass SwordSlash;
    MyTextureClass SwordSlashMod;
    
    // delta time
    /** time at last frame */
	long lastFrame;
 
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
	GameHandler MyGame;
	
	

    /**
     * Everything starts and ends here.  Takes 1 optional command line argument.
     * If fullscreen is specified on the command line then fullscreen is used,
     * otherwise windowed mode will be used.
     * @param args command line arguments
     */
    public static void main(String args[]) {
        boolean fullscreen = false;
        if(args.length>0) {
            if(args[0].equalsIgnoreCase("fullscreen")) {
                fullscreen = true;
            }
        }
        Main l1 = new Main();
        l1.run(fullscreen);
    }

    /**
     * Launch point
     * @param fullscreen boolean value, set to true to run in fullscreen mode
     */
    public void run(boolean fullscreen) {
        this.fullscreen = fullscreen;
        try {
            init();					//INITALISE AREA
            ShaderHandler.ShaderSetup();
            
            getDelta(); // call once before loop to initialise lastFrame
    		lastFPS = getTime(); // call before loop to initialise fps timer
    		MyGame = new GameHandler();
            
            while (!done) {
            	int delta = getDelta();
            	
            	
                mainloop();
                render();
                update(delta);
                Display.update();
                //Display.sync(120);

            }
            cleanup();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * All updating is done here.  Key and mouse polling as well as window closing and
     * custom updates, such as AI.
     */
    private void mainloop() {
        if(GameData.CloseGame == true) {       // Exit if Escape is pressed
            done = true;
        }
        if(Display.isCloseRequested()) {                     // Exit if window is closed
            done = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F1) && !f1) {    // Is F1 Being Pressed?
            f1 = true;                                      // Tell Program F1 Is Being Held
            switchMode();                                   // Toggle Fullscreen / Windowed Mode
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_F1)) {          // Is F1 Being Pressed?
            f1 = false;
        }
    }

    private void switchMode() {
        fullscreen = !fullscreen;
        try {
            Display.setFullscreen(fullscreen);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * For rendering all objects to the screen
     * @return boolean for success or not
     */
    private boolean render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
      
        SwitchToDepthPer();
        
        //depth
        DepthRender.BindBuffer(); 
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity(); 
        MyGame.RenderGameDepth();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        GameData.DepthTexture = DepthRender.fb_texture;
        
        SwitchToPer();
        
        //regular
        //glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        
        LevelRenderFBO.BindBuffer();
        
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity(); 
        
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        
        LevelRenderFBO.BindBuffer();
        
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity(); 
        
        
        GL11.glEnable(GL11.GL_BLEND);
        GL14.glBlendEquation(GL14.GL_FUNC_ADD);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        MyGame.RenderGame();
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        RenderLight();
        
        //blur time mofo
        SwitchToOrtho();
        //blurx
        XBlurFBO.BindBuffer();
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        ShaderHandler.XBlurShader.Activate();
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_6);
        int XBlurLoc = GL20.glGetUniformLocation(ShaderHandler.XBlurShader.shaderProgram, "ColourMap");
        GL20.glUniform1i(XBlurLoc, 0);
        drawTexture(0, 0 ,WIDTH, HEIGHT);
        ShaderHandler.XBlurShader.DeActivate();
        //blur Y
        YBlurFBO.BindBuffer();
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        ShaderHandler.YBlurShader.Activate();
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, XBlurFBO.fb_texture);
        int YBlurLoc = GL20.glGetUniformLocation(ShaderHandler.YBlurShader.shaderProgram, "ColourMap");
        GL20.glUniform1i(YBlurLoc, 0);
        drawTexture(0, 0 ,WIDTH, HEIGHT);
        ShaderHandler.YBlurShader.DeActivate();
        
        
        
        
        
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        GL11.glClearColor (GameData.SkyClear.x, GameData.SkyClear.y, GameData.SkyClear.z, 0.0f);
        if(Input.GetF2() != true && Input.GetF3() != true && Input.GetF4() != true && Input.GetF5() != true && Input.GetF6() != true && Input.GetF7() != true && Input.GetF8() != true && Input.GetF9() != true && Input.GetF10() != true && Input.GetF11() != true){
        
        SwitchToOrtho();
        
        GL11.glLoadIdentity();
        ShaderHandler.FinalShader.Activate();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture);
        int ColourLoc = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "ColourMap");
        GL20.glUniform1i(ColourLoc, 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_2);
        int ShadowLoc = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "ShadowMap");
        GL20.glUniform1i(ShadowLoc, 1);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_3);
        int lightLoc = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "LightMap");
        GL20.glUniform1i(lightLoc, 2);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LightFBO.fb_texture);
        int lightpLoc = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "PointLightMap");
        GL20.glUniform1i(lightpLoc, 3);
        
        drawTexture(0, 0 ,WIDTH, HEIGHT);
        ShaderHandler.FinalShader.DeActivate();
        
        
        
        GL11.glLoadIdentity();
        ShaderHandler.FinalGlowShader.Activate();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_6);
        int GlowLoc = GL20.glGetUniformLocation(ShaderHandler.FinalGlowShader.shaderProgram, "GlowMap");
        GL20.glUniform1i(GlowLoc, 0);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, YBlurFBO.fb_texture);
        int BlurLoc = GL20.glGetUniformLocation(ShaderHandler.FinalGlowShader.shaderProgram, "BlurMap");
        GL20.glUniform1i(BlurLoc, 1);
        
        drawTexture(0, 0 ,WIDTH, HEIGHT);
        ShaderHandler.FinalGlowShader.DeActivate();
        
        //sword slash
        ShaderHandler.SlashShader.Activate();
    	GL11.glLoadIdentity();
    	
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	int ColLoc = GL20.glGetUniformLocation(ShaderHandler.SlashShader.shaderProgram, "ColourMap");
    	SwordSlash.Draw(ColLoc);
    	
    	GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_5);
        int DepLoc = GL20.glGetUniformLocation(ShaderHandler.SlashShader.shaderProgram, "DepthMap");
        GL20.glUniform1i(DepLoc, 1);
        
        int ModLoc = GL20.glGetUniformLocation(ShaderHandler.SlashShader.shaderProgram, "ModMap");
    	SwordSlashMod.DrawTwo(ModLoc);
    	
    	int ALoc = GL20.glGetUniformLocation(ShaderHandler.SlashShader.shaderProgram, "AttackData");
        GL20.glUniform1f(ALoc, GameData.AttackData);
    	
        if(GameData.AttackDirection == false){
        	drawTexture((WIDTH-HEIGHT*0.7f)/2, HEIGHT/20 ,(int)(HEIGHT*0.7f), (int)(HEIGHT*0.7f));
        }
        else{
        	drawTexture(WIDTH - (WIDTH-HEIGHT*0.7f)/2, HEIGHT/20 ,-(int)(HEIGHT*0.7f), (int)(HEIGHT*0.7f));
        }
    	ShaderHandler.SlashShader.DeActivate();
    	//GL11.glEnable(GL11.GL_CULL_FACE);
        //end of sword
    	MyGame.RenderUI();
        }
        else if(Input.GetF2() == true){
        	SwitchToOrtho();
        	ShaderHandler.TextShader.Activate();
        	GL11.glLoadIdentity();
        	GL11.glEnable(GL11.GL_TEXTURE_2D);
        	GL13.glActiveTexture(GL13.GL_TEXTURE0);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_5);
        	drawTexture(0, 0 ,WIDTH, HEIGHT);
        	ShaderHandler.TextShader.DeActivate();
        	SwitchToPer();
        }
        else if(Input.GetF3() == true){
        	SwitchToOrtho();
            ShaderHandler.TextShader.Activate();
            GL11.glLoadIdentity();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, LightFBO.fb_texture);
            drawTexture(0, 0 ,WIDTH, HEIGHT);
            ShaderHandler.TextShader.DeActivate();
            SwitchToPer();
        }
        else if(Input.GetF4() == true){
        	 SwitchToOrtho();
             ShaderHandler.TextShader.Activate();
             GL11.glLoadIdentity();
             GL11.glEnable(GL11.GL_TEXTURE_2D);
             GL13.glActiveTexture(GL13.GL_TEXTURE0);
             GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_4);
             drawTexture(0, 0 ,WIDTH, HEIGHT);
             ShaderHandler.TextShader.DeActivate();
             SwitchToPer();
        }
        else if(Input.GetF5() == true){
       	 SwitchToOrtho();
            ShaderHandler.TextShader.Activate();
            GL11.glLoadIdentity();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture);
            drawTexture(0, 0 ,WIDTH, HEIGHT);
            ShaderHandler.TextShader.DeActivate();
            SwitchToPer();
       }
        else if(Input.GetF6() == true){
       	 SwitchToOrtho();
            ShaderHandler.TextShader.Activate();
            GL11.glLoadIdentity();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_2);
            drawTexture(0, 0 ,WIDTH, HEIGHT);
            ShaderHandler.TextShader.DeActivate();
            SwitchToPer();
       }
        else if(Input.GetF7() == true){
       	 SwitchToOrtho();
            ShaderHandler.TextShader.Activate();
            GL11.glLoadIdentity();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_3);
            drawTexture(0, 0 ,WIDTH, HEIGHT);
            ShaderHandler.TextShader.DeActivate();
            SwitchToPer();
       }
        else if(Input.GetF8() == true){
       	 SwitchToOrtho();
            ShaderHandler.TextShader.Activate();
            GL11.glLoadIdentity();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_6);
            drawTexture(0, 0 ,WIDTH, HEIGHT);
            ShaderHandler.TextShader.DeActivate();
            SwitchToPer();
       }
        else if(Input.GetF9() == true){
          	 SwitchToOrtho();
               ShaderHandler.TextShader.Activate();
               GL11.glLoadIdentity();
               GL11.glEnable(GL11.GL_TEXTURE_2D);
               GL13.glActiveTexture(GL13.GL_TEXTURE0);
               GL11.glBindTexture(GL11.GL_TEXTURE_2D, DepthRender.fb_texture);
               drawTexture(0, 0 ,WIDTH, HEIGHT);
               ShaderHandler.TextShader.DeActivate();
               SwitchToPer();
          }
        else if(Input.GetF10() == true){
         	 SwitchToOrtho();
              ShaderHandler.TextShader.Activate();
              GL11.glLoadIdentity();
              GL11.glEnable(GL11.GL_TEXTURE_2D);
              GL13.glActiveTexture(GL13.GL_TEXTURE0);
              GL11.glBindTexture(GL11.GL_TEXTURE_2D, YBlurFBO.fb_texture);
              drawTexture(0, 0 ,WIDTH, HEIGHT);
              ShaderHandler.TextShader.DeActivate();
              SwitchToPer();
         }
        
        return true;
        
        
    }

    /**
     * Create a window depending on whether fullscreen is selected
     * @throws Exception Throws the Window.create() exception up the stack.
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(fullscreen);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == WIDTH
                && d[i].getHeight() == HEIGHT
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        Display.create();
        Display.setResizable(true);
        Display.setFullscreen(false);
    }

    /**
     * Do all initilization code here.  Including Keyboard and OpenGL
     * @throws Exception Passes any exceptions up to the main loop to be handled
     */
    private void init() throws Exception {
        createWindow();

        initGL();
        
        //check for folder
        String username = System.getProperty("user.name");
		String FileOpen = "C:\\\\Users\\\\" + username + "\\\\AppData\\\\Roaming\\\\EndlessSanctuary\\\\";
        if (Files.isDirectory(Paths.get("FileOpen"))) {
        	
        }
        else{
        	new File(FileOpen).mkdirs();
        	System.out.println("Created Map Save Folder at: " + FileOpen);
        }
        
        
        DepthRender = new FBO(HEIGHT*4, WIDTH*4);
        LevelRenderFBO = new SixMRTFBO(HEIGHT, WIDTH);
        XBlurFBO = new FBO(HEIGHT, WIDTH);
        YBlurFBO = new FBO(HEIGHT, WIDTH);
        FinalRenderFBO = new FBO(HEIGHT, WIDTH);
        LightFBO = new FBO(HEIGHT, WIDTH);
        
        LoadScreen.LoadLoadTexture();
        FontHandler.SetupFonts();
        ModelHandler.AddTexture("swordslash");
        SwordSlash = ModelHandler.FindTexture("swordslash");
        
        ModelHandler.AddTexture("swordslashMod");
        SwordSlashMod = ModelHandler.FindTexture("swordslashMod");
        
      //create the controller
        try{
        	Controllers.create();
        } catch (LWJGLException e) {
        	e.printStackTrace();
        }
        Controllers.poll();
        
        System.out.println(Controllers.getControllerCount());
        
       for(int i = 0; i<Controllers.getControllerCount(); i++){
    	   MyController = Controllers.getController(i);
    	   //System.out.println(MyController.getName());
    	   
    	   if(MyController.getName().equals("Controller (XBOX 360 For Windows)")){
        	   System.out.println("Xbox 360 Controller found");
        	   Input.MyController = MyController;
        	   Input.ControllerAvaliable = true;
           }
       }
       
     
       
       

    }

    /**
     * Initialize OpenGL
     *
     */
    private void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0.52f, 0.81f, 0.92f, 0.0f); // Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do

        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        
       
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping 
        

    	GL11.glEnable(GL11.GL_LIGHT1); 
    	GL11.glEnable ( GL11.GL_LIGHTING );

        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          10000.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix

        // Really Nice Perspective Calculations
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    private void cleanup() {
        Display.destroy();
    }
       
    private void update(int time){
    	Controllers.poll();
    	MyGame.UpdateGame(time);
    }
    
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
     
        return delta;
    }
    
private void SwitchToPer(){
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
    	GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          3.5f,
          400.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
    	
    }
 
private void SwitchToDepthPer(){
	
	GL11.glViewport(0, 0, WIDTH*4, HEIGHT*4);
	GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
    GL11.glLoadIdentity(); // Reset The Projection Matrix

    // Calculate The Aspect Ratio Of The Window
    GLU.gluPerspective(
      45f,
      (float) displayMode.getWidth() / (float) displayMode.getHeight(),
      5,
      1000.0f);
    GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
	
}

private void SwitchToOrtho(){
    	
    	GL11.glLoadIdentity(); 
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity(); 
        GL11.glOrtho(0, WIDTH, 0, HEIGHT, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	
    }
 
private void drawTexture(float x, float y, int width, int height){
	GL11.glBegin(GL11.GL_QUADS);
	GL11.glTexCoord2f(0f, 0f);
	GL11.glVertex2f(x, y);
    
	GL11.glTexCoord2f(1f, 0f);
	GL11.glVertex2f(x + width, y);
    
	GL11.glTexCoord2f(1f, 1f);
	GL11.glVertex2f(x + width, y + height);
    
	GL11.glTexCoord2f(0f, 1f);
	GL11.glVertex2f(x, y + height);
	GL11.glEnd();
 }

private void RenderLight(){
	
	SwitchToPer();
	
	 LightFBO.BindBuffer(); 
     GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
     GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
     GL11.glLoadIdentity(); 
     
     GL11.glEnable(GL11.GL_BLEND);
     GL14.glBlendEquation(GL14.GL_FUNC_ADD);
     GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
     GL11.glDisable(GL11.GL_DEPTH_TEST);
     
     GL11.glEnable(GL11.GL_CULL_FACE);
     GL11.glCullFace(GL11.GL_BACK);

     ShaderHandler.PointLightShader.Activate();
     //System.out.println("START");
     if(GameData.DayTime == false && GameData.Lights == true){
	for(PointLight MyLight: ModelHandler.LightList){
		GL11.glLoadIdentity();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_4);
        int NormalLoc = GL20.glGetUniformLocation(ShaderHandler.PointLightShader.shaderProgram, "NormalMap");
        GL20.glUniform1i(NormalLoc, 0);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture_5);
        int DepthLoc = GL20.glGetUniformLocation(ShaderHandler.PointLightShader.shaderProgram, "DepthMap");
        GL20.glUniform1i(DepthLoc, 1);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelRenderFBO.fb_texture);
        int ColourLoc = GL20.glGetUniformLocation(ShaderHandler.PointLightShader.shaderProgram, "ColourMap");
        GL20.glUniform1i(ColourLoc, 2);
        
		MyLight.Render();
		//System.out.println(MyLight.MyX + " " + MyLight.MyY + " " + MyLight.MyZ);
		//System.out.println(GameData.CameraPosition);
		
	}
     }
	GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    
    GL11.glDisable(GL11.GL_CULL_FACE);
    //GL11.glCullFace(GL11.GL_BACK);
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    
	ShaderHandler.PointLightShader.DeActivate();
	
}

}




