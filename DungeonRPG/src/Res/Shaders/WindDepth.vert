uniform sampler2D ColourMap;
uniform float WindScale;

varying vec3 normal;
varying float mdepth;

void main()
{
	gl_TexCoord[0] = gl_MultiTexCoord0;
	vec4 WindDirection = (0.1,0,0.1);
	
	gl_Vertex.x += WindDirection.x * texture2D(ColourMap, gl_TexCoord[0].st).r * WindScale;
	//gl_Vertex.y += WindDirection.y * texture2D(ColourMap, gl_TexCoord[0].st).r * WindScale;
	gl_Vertex.z += WindDirection.z * texture2D(ColourMap, gl_TexCoord[0].st).r * WindScale;

	mdepth = gl_Vertex.y;
	
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	normal = ((gl_Normal.xyz) + 1) / 2;
	//mdepth = gl_Position.z/4000;
	//normal = gl_Position.z/100;
    
}