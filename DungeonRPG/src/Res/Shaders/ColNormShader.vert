varying vec3 varyingColour;
varying vec3 MyColour;

void main()
{
	vec3 LightPosition = (0,50,0);
	vec3 vertexPosition = (gl_ModelViewMatrix * gl_Vertex).xyz;

	vec3 lightDirection = normalize(LightPosition - vertexPosition);

	vec3 surfaceNormal  = (gl_NormalMatrix * gl_Normal).xyz;

	float diffuseLightIntensity = max(0, dot(surfaceNormal, lightDirection));

	varyingColour.rgb = diffuseLightIntensity * vec3(1,1,1);
	
	MyColour = gl_Color.rgb;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}