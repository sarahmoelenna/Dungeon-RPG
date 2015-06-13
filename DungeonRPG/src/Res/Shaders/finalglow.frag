uniform sampler2D GlowMap;
uniform sampler2D BlurMap;

void main()
{

	vec4 MyColour = (texture2D(GlowMap, gl_TexCoord[0].st) + texture2D(BlurMap, gl_TexCoord[0].st))/2;
    

    gl_FragColor = MyColour;
    
}