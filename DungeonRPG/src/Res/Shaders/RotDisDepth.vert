uniform sampler2D RotMap;
uniform sampler2D AxisMap;
uniform float WindScale;

varying vec3 normal;
varying float mdepth;

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
		
		gl_Vertex.xyz = newVertex + rotationpoint;
	}
	
	

	mdepth = gl_Vertex.y;
	
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	normal = ((gl_Normal.xyz) + 1) / 2;
	//mdepth = gl_Position.z/4000;
	//normal = gl_Position.z/100;
    
}