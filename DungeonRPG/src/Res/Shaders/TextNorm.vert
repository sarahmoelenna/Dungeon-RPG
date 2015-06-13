varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;
varying mat4 MyMat;

varying float mdepth;
varying vec4 world;

varying vec3 snormal;
varying vec4 shadowPos;
uniform mat4 ModelMatrix4x4;
uniform mat4 ProjectionMatrix4x4;

void main()
{
	mdepth = gl_Vertex.y/2;

	world = gl_ModelViewMatrix * gl_Vertex;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = normalize((gl_NormalMatrix * gl_Normal).xyz);
    gl_TexCoord[0] = gl_MultiTexCoord0;
    MyMat = gl_ModelViewMatrix;
    
    
    snormal = (gl_Normal.xyz + 1.0)/2.0;
    shadowPos = ProjectionMatrix4x4 * ModelMatrix4x4 * gl_Vertex;
    
    
}