uniform sampler2D ColourMap;
uniform sampler2D ShadowMap;
uniform sampler2D LightMap;
uniform sampler2D PointLightMap;



void main()
{

	vec4 MyColour = (texture2D(ColourMap, gl_TexCoord[0].st)) * ((texture2D(ShadowMap, gl_TexCoord[0].st)) * (texture2D(LightMap, gl_TexCoord[0].st)) + (texture2D(PointLightMap, gl_TexCoord[0].st)));
    

    gl_FragColor = MyColour;
    
}