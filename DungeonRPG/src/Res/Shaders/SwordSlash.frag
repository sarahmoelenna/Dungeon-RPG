uniform sampler2D ColourMap;
uniform sampler2D DepthMap;
uniform sampler2D ModMap;

uniform float AttackData;

varying vec3 color;

void main()
{
	vec3 MyColour = vec3(0,0,1);

	int screenWidth = 1920;
 	int screenHeight = 1080;

	vec2 coord = gl_FragCoord.xy;
	coord.x = coord.x / screenWidth;
	coord.y = coord.y / screenHeight;

	float Depth = (texture2D(DepthMap, coord)).r;
	Depth = (1 - Depth) - 0.3;
	float Alpha = 0;
	if(Depth > 0){
		Alpha = 1 * (texture2D(DepthMap, coord)).w;
	}
	
	vec4 Mod = texture2D(ColourMap, gl_TexCoord[0].st);
	
	if(Alpha > 0){
		if(Mod.r < AttackData && Mod.r > AttackData - 0.2){
			Alpha = 1;
		}
		else{
			Alpha = 0;
		}
	}
	
	vec4 Colour = vec4(MyColour,1) *  texture2D(ModMap, gl_TexCoord[0].st);

	vec4 FinalColour = vec4(Colour.rgb, Alpha * Colour.w);
	
    gl_FragColor = FinalColour;
    
}