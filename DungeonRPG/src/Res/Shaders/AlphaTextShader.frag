uniform sampler2D ColourMap;
uniform float Alpha;

varying vec3 color;

void main()
{
    gl_FragColor = (texture2D(ColourMap, gl_TexCoord[0].st)) * vec4(1,1,1,Alpha);
    
}