package MyDungeonPackage;

import org.lwjgl.opengl.GL20;

class ShaderHandler {
	
	static ShaderProgram ShipShader;
	static ShaderProgram ColNormShader;
	static ShaderProgram TextNormShader;
	static ShaderProgram WindDepthShader;
	static ShaderProgram NormDepthShader;
	static ShaderProgram GradDisplaceShader;
	static ShaderProgram RotDisplaceShader;
	static ShaderProgram RotDisplaceDepthShader;
	static ShaderProgram TextShader;
	static ShaderProgram TextAlphaShader;
	static ShaderProgram XBlurShader;
	static ShaderProgram YBlurShader;
	static ShaderProgram FinalShader;
	static ShaderProgram PointLightShader;
	static ShaderProgram FlatColourShader;
	static ShaderProgram FinalGlowShader;
	static ShaderProgram SlashShader;
	static ShaderProgram AlphaMapTextShader;
	static ShaderProgram ParticleColourShader;
	
	static int GradColourloc;
	static int GradScaleloc;
	
	static int GlowColourloc;
	
	static int WindDepthColourloc;
	static int WindDepthScaleLoc;
	
	static int WindColourloc;
	static int WindScaleLoc;
	static int WindShadowLoc;
	static int WindProjLoc;
	static int WindModLoc;
	static int WindLightLoc;
	
	static int TexturedColourloc;
	static int TexturedShadowLoc;
	static int TexturedProjLoc;
	static int TexturedModLoc;
	static int TexturedLightLoc;
	static int TexturedGlowLoc;
	
	static int RotColourloc;
	static int RotShadowLoc;
	static int RotAxisloc;
	static int RotRotLoc;
	static int RotScaleLoc;
	static int RotProjLoc;
	static int RotModLoc;
	static int RotLightLoc;
	
	static int RotDepthAxisloc;
	static int RotDepthRotLoc;
	static int RotDepthScaleLoc;
	
	static int AlphaColourLoc;
	static int AlphaMapLoc;
	
	
	static void ShaderSetup(){
		
		ShipShader = new ShaderProgram("/Res/Shaders/shipshader.vert", "/Res/Shaders/shipshader.frag");
		System.out.println("shipshader done");
		
		ColNormShader = new ShaderProgram("/Res/Shaders/ColNormShader.vert", "/Res/Shaders/ColNormShader.frag");
		System.out.println("ColNormShader done");
		
		TextNormShader = new ShaderProgram("/Res/Shaders/TextNorm.vert", "/Res/Shaders/TextNorm.frag");
		System.out.println("TextNormShader done");
		
		WindDepthShader = new ShaderProgram("/Res/Shaders/WindDepth.vert", "/Res/Shaders/WindDepth.frag");
		System.out.println("WindDepthShader done");
		
		NormDepthShader = new ShaderProgram("/Res/Shaders/NormDepth.vert", "/Res/Shaders/NormDepth.frag");
		System.out.println("NormDepthShader done");
		
		GradDisplaceShader = new ShaderProgram("/Res/Shaders/GradientDisplacementShader.vert", "/Res/Shaders/GradientDisplacementShader.frag");
		System.out.println("GradDisplaceShader done");
		
		RotDisplaceShader = new ShaderProgram("/Res/Shaders/RotationDisplace.vert", "/Res/Shaders/RotationDisplace.frag");
		System.out.println("RotDisplaceShader done");
		
		RotDisplaceDepthShader = new ShaderProgram("/Res/Shaders/RotDisDepth.vert", "/Res/Shaders/WindDepth.frag");
		System.out.println("RotDisplaceDepthShader done");
		
		TextShader = new ShaderProgram("/Res/Shaders/TextShader.vert", "/Res/Shaders/TextShader.frag");
		System.out.println("TextShader done");
		
		TextAlphaShader = new ShaderProgram("/Res/Shaders/AlphaTextShader.vert", "/Res/Shaders/AlphaTextShader.frag");
		System.out.println("TextAlphaShader done");
		
		XBlurShader = new ShaderProgram("/Res/Shaders/glowblurxshader.vert", "/Res/Shaders/glowblurxshader.frag");
		System.out.println("XBlurShader done");
		
		YBlurShader = new ShaderProgram("/Res/Shaders/glowbluryshader.vert", "/Res/Shaders/glowbluryshader.frag");
		System.out.println("YBlurShader done");
		
		FinalShader = new ShaderProgram("/Res/Shaders/FinalShader.vert", "/Res/Shaders/FinalShader.frag");
		System.out.println("FinalShader done");
		
		PointLightShader = new ShaderProgram("/Res/Shaders/PointLightShader.vert", "/Res/Shaders/PointLightShader.frag");
		System.out.println("PointLightShader done");
		
		FlatColourShader = new ShaderProgram("/Res/Shaders/FlatColour.vert", "/Res/Shaders/FlatColour.frag");
		System.out.println("FlatColourShader done");
		
		FinalGlowShader = new ShaderProgram("/Res/Shaders/finalglow.vert", "/Res/Shaders/finalglow.frag");
		System.out.println("FinalGlowShader done");
		
		SlashShader = new ShaderProgram("/Res/Shaders/SwordSlash.vert", "/Res/Shaders/SwordSlash.frag");
		System.out.println("SlashShader done");
		
		AlphaMapTextShader = new ShaderProgram("/Res/Shaders/AlphaMapTextShader.vert", "/Res/Shaders/AlphaMapTextShader.frag");
		System.out.println("AlphaMapTextShader done");
		
		ParticleColourShader = new ShaderProgram("/Res/Shaders/ParticleColour.vert", "/Res/Shaders/ParticleColour.frag");
		System.out.println("ParticleColourShader done");
		
		GradColourloc = GL20.glGetUniformLocation(ShaderHandler.GradDisplaceShader.shaderProgram, "ColourMap");
		GradScaleloc = GL20.glGetUniformLocation(ShaderHandler.GradDisplaceShader.shaderProgram, "WindScale");
		
		GlowColourloc = GL20.glGetUniformLocation(ShaderHandler.FlatColourShader.shaderProgram, "MyColour");
		
		WindColourloc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ColourMap");
		WindScaleLoc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "WindScale");
		WindShadowLoc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ShadowMap");
		WindProjLoc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ProjectionMatrix4x4");
		WindModLoc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ModelMatrix4x4");
		WindLightLoc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "LightColour");
		
		WindDepthColourloc = GL20.glGetUniformLocation(ShaderHandler.WindDepthShader.shaderProgram, "ColourMap");
		WindDepthScaleLoc = GL20.glGetUniformLocation(ShaderHandler.WindDepthShader.shaderProgram, "WindScale");

		TexturedColourloc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ColourMap");
		TexturedShadowLoc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ShadowMap");
		TexturedLightLoc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "LightColour");
		TexturedProjLoc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ProjectionMatrix4x4");
		TexturedModLoc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "ModelMatrix4x4");
		TexturedGlowLoc = GL20.glGetUniformLocation(ShaderHandler.TextNormShader.shaderProgram, "GlowMap");
		
		
		RotColourloc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ColourMap");
		RotShadowLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ShadowMap");
		RotAxisloc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "AxisMap");
		RotRotLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "RotMap");
		RotScaleLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "WindScale");
		RotProjLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ProjectionMatrix4x4");
		RotModLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "ModelMatrix4x4");
		RotLightLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceShader.shaderProgram, "LightColour");
		
		RotDepthAxisloc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceDepthShader.shaderProgram, "AxisMap");
		RotDepthRotLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceDepthShader.shaderProgram, "RotMap");
		RotDepthScaleLoc = GL20.glGetUniformLocation(ShaderHandler.RotDisplaceDepthShader.shaderProgram, "WindScale");
		
		AlphaColourLoc = GL20.glGetUniformLocation(ShaderHandler.AlphaMapTextShader.shaderProgram, "ColourMap");
		AlphaMapLoc = GL20.glGetUniformLocation(ShaderHandler.AlphaMapTextShader.shaderProgram, "AlphaMap");
		
		
	}

}
