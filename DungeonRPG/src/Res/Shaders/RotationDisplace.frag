varying vec3 normal;
varying vec3 lightdirection;
varying vec3 varyingColour;
varying vec3 position;
varying mat4 MyMat;

varying float mdepth;
varying vec4 world;

varying vec3 snormal;
varying vec4 shadowPos;
uniform sampler2D ShadowMap;

uniform sampler2D ColourMap;
uniform vec3 LightColour;

void main()
{
	
	vec4 MyColour;
	float Intensity;
	vec3 lightposition = (MyMat * gl_LightSource[1].position).xyz;
	
	
	shadowPos.xyz = (shadowPos.xyz / shadowPos.w + 1.0) / 2.0; //shadow
	float visibility = 1.0;
	vec3 Shad = (texture2D( ShadowMap, shadowPos.xy)).xyz;

	
	float bias = 0.004;
	if(Shad.x >= mdepth + bias || Shad.x <= mdepth - bias || Shad.z > 0.5){
		visibility = 0.5;
	}
	
	
	Intensity = dot(normalize(lightposition - position),normal);
	
	Intensity = clamp(Intensity, 0.2, 1);
	
	lightdirection = vec3(lightposition-position);
	
	vec3 reflectionDirection = normalize(reflect(-lightdirection, normal));	//
	//float diffuseLightIntensity = max(0, dot(normal, lightdirection));	//
	float specular = max(0.0, dot(normal, reflectionDirection));
	
	float fspecular = pow(specular, 255);
	
	varyingColour.rgb += vec3(fspecular, fspecular, fspecular);
	
    float FinalLight = Intensity * visibility;
    //gl_FragColor = vec4((texture2D(ColourMap, gl_TexCoord[0].st)).xyz, 1) * vec4(vec3((FinalLight,FinalLight,FinalLight) + vec3(varyingColour)), 1);
 
 	gl_FragData[0] = vec4((texture2D(ColourMap, gl_TexCoord[0].st)).xyz, 1);
   	gl_FragData[1] = vec4(visibility, visibility, visibility, 1);
   	gl_FragData[2] = vec4(vec3((FinalLight * LightColour) + vec3(varyingColour)), 1);
   	gl_FragData[3] = vec4((normal+ 1)/2, 1);
   	//gl_FragData[4] = vec4(world.xyz, 1);
   	float MyDepth = gl_FragCoord.z;
   	gl_FragData[4] = vec4(MyDepth, MyDepth, MyDepth, 1);
   	
    
}