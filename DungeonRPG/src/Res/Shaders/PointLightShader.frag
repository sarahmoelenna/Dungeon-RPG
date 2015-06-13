//from vert shader
varying vec4 pos; //location of fragment in view space

//G-Buffer data
uniform sampler2D NormalMap;
uniform sampler2D DepthMap;
uniform sampler2D ColourMap;

//Lighting vars
varying vec4 lpos; //light position in view space
uniform vec3 Colour;

//Makes depth values linear (i.e. inverse of the perpective projection)
float DepthToLinearZ(float dVal)
{
	float near = 4.0;
 	float far = 400.0;

	//dVal = (dVal / 2) + 0.5;

	return (far*near) / (far-(dVal*(far-near)));
}

void main()
{

 	float radius = 20;
 	int screenWidth = 1920;
 	int screenHeight = 1080;
 	float near = 5.0;
 	float far = 1000.0;

   //normalize coord
	vec2 coord = gl_FragCoord.xy;
	coord.x = coord.x / screenWidth;
	coord.y = coord.y / screenHeight;
	
	//Data lookups
	vec4 n = (texture2D(NormalMap, coord)*2.0)-1.0;
   	vec3 p = normalize(pos.xyz)*DepthToLinearZ(texture2D(DepthMap, coord).r); //position in view space of geometry
   	vec4 c = texture2D(ColourMap, coord);
	
   //Lighting Calcs (view space)
   vec3 ltop = lpos.xyz-p;
   float diffuseModifier = max(dot(n.xyz,normalize(ltop)), 0.0);
   float noZTestFix = step(0.0, radius-length(ltop)); //0.0 if dist > radius, 1.0 otherwise
   float attenuation = 1 / ((((length(ltop)/(1-((length(ltop)/radius)*(length(ltop)/radius))))/radius)+1)*(((length(ltop)/(1-((length(ltop)/radius)*(length(ltop)/radius))))/radius)+1));
   vec4 diffuse = diffuseModifier * vec4(Colour, 1.0) * attenuation * noZTestFix;
	
   //Set the color
   gl_FragColor = diffuse;
   
   //gl_FragColor = vec4(p,1);
  
}