uniform sampler2D ColourMap;
uniform sampler2D AlphaMap;

void main()
{

	vec4 MyColour = vec4(texture2D(ColourMap, gl_TexCoord[0].st).rgb, texture2D(AlphaMap, gl_TexCoord[0].st).r*0.7);

    //gl_FragColor = MyColour;
    
    gl_FragData[0] = MyColour;
    gl_FragData[1] = vec4(0,0,0, 0);
    gl_FragData[2] = vec4(0,0,0, 0);
    gl_FragData[3] = vec4(0,0,0, 0);
    gl_FragData[4] = vec4(0,0,0, 0);
    gl_FragData[5] = vec4(0,0,0, 0);
    
}