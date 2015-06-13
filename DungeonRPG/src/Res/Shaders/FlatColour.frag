uniform vec3 MyColour;
varying vec3 normal;
varying vec3 position;
varying mat4 MyMat;

void main()
{
	float Intensity;
	vec3 lightposition = (MyMat * gl_LightSource[1].position).xyz;
	
	Intensity = dot(normalize(lightposition - position),normal);
	Intensity /= 2;
	Intensity = clamp(Intensity, 0.2, 1);

    gl_FragData[5] = vec4(MyColour * (Intensity * 3), 1);
    
}