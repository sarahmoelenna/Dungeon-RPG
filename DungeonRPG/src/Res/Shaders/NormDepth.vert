varying vec3 normal;
varying float mdepth;

void main()
{

	mdepth = gl_Vertex.y;
	
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = ((gl_Normal.xyz) + 1) / 2;
    
	
    
}