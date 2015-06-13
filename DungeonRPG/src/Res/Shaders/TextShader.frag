uniform sampler2D ColourMap;

varying vec3 color;

void main()
{
    gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st));
    
}