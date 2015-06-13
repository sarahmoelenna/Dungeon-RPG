varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;
varying mat4 MyMat;

void main()
{
	
	vec4 MyColour;
	float Intensity;
	vec3 lightposition = (MyMat * gl_LightSource[1].position).xyz;
	
	Intensity = dot(normalize(lightposition - position),normal);
	
	Intensity = clamp(Intensity, 0.2, 1);
	
	lightdirection = vec3(lightposition-position);
	
	vec3 reflectionDirection = normalize(reflect(-lightdirection, normal));	//
	//float diffuseLightIntensity = max(0, dot(normal, lightdirection));	//
	float specular = max(0.0, dot(normal, reflectionDirection));
	
	float fspecular = pow(specular, 255);
	
	varyingColour.rgb += vec3(fspecular, fspecular, fspecular);
	
    gl_FragData[0] = vec4(0,0,1, 0.5);
    gl_FragData[1] = vec4(0,0,0, 0);
    gl_FragData[2] = vec4(0,0,0, 0);
    gl_FragData[3] = vec4(0,0,0, 0);
    gl_FragData[4] = vec4(0,0,0, 0);
    gl_FragData[5] = vec4(0,0,0, 0);
 
    
}