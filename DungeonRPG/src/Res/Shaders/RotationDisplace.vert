uniform float WindScale;

uniform sampler2D RotMap;
uniform sampler2D AxisMap;

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
	gl_TexCoord[0] = gl_MultiTexCoord0;
	
	vec3 displaceVertex = vec3(0,0,0);
	vec3 newVertex = vec3(0,0,0);
	vec3 rotationpoint = ((texture2D(RotMap, gl_TexCoord[0].st).rgb - 0.5))*2;
	vec3 axisignore = texture2D(AxisMap, gl_TexCoord[0].st).rgb;
	
	
	if(axisignore.x < 0.1 && axisignore.y > 0.1 && axisignore.z > 0.1){
		displaceVertex.x = gl_Vertex.x;
		displaceVertex.y = gl_Vertex.y - rotationpoint.y;
		displaceVertex.z = gl_Vertex.z - rotationpoint.z;
	
		newVertex.x = displaceVertex.x;
		newVertex.y = (float) (displaceVertex.y * cos(-WindScale) - displaceVertex.z * sin(-WindScale));
		newVertex.z = (float) (displaceVertex.y * sin(-WindScale) + displaceVertex.z * cos(-WindScale));
		
		gl_Vertex.xyz = newVertex + rotationpoint;
	}
	else if(axisignore.z < 0.1 && axisignore.y > 0.1 && axisignore.x > 0.1){
		displaceVertex.z = gl_Vertex.z;
		displaceVertex.y = gl_Vertex.y - rotationpoint.y;
		displaceVertex.x = gl_Vertex.x - rotationpoint.x;
	
		newVertex.z = displaceVertex.z;
		newVertex.y = (float) (displaceVertex.y * cos(-WindScale) - displaceVertex.x * sin(-WindScale));
		newVertex.x = (float) (displaceVertex.y * sin(-WindScale) + displaceVertex.x * cos(-WindScale));
		
		gl_Vertex.xyz = newVertex + rotationpoint;
	}
	else if(axisignore.y < 0.1 && axisignore.x > 0.1 && axisignore.z > 0.1){
		displaceVertex.y = gl_Vertex.y;
		displaceVertex.x = gl_Vertex.x - rotationpoint.x;
		displaceVertex.z = gl_Vertex.z - rotationpoint.z;
	
		newVertex.y = displaceVertex.y;
		newVertex.x = (float) (displaceVertex.x * cos(-WindScale) - displaceVertex.z * sin(-WindScale));
		newVertex.z = (float) (displaceVertex.x * sin(-WindScale) + displaceVertex.z * cos(-WindScale));
		
		gl_Vertex.xyz = newVertex + rotationpoint;
	}
	

	mdepth = gl_Vertex.y/2;
	
	world = gl_ModelViewMatrix * gl_Vertex;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = normalize((gl_NormalMatrix * gl_Normal).xyz);
    gl_TexCoord[0] = gl_MultiTexCoord0;
    MyMat = gl_ModelViewMatrix;
    
    snormal = (gl_Normal.xyz + 1)/2;
    shadowPos = ProjectionMatrix4x4 * ModelMatrix4x4 * gl_Vertex;
}