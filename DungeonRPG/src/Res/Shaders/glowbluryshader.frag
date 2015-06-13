uniform sampler2D ColourMap;

const float blurSize = 1.0/640;
 
void main(void)
{
   vec4 sum = vec4(0.0);
   float BlurScale = 2.0;
   // blur in x (horizontal)
   // take nine samples, with the distance blurSize between them
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s - 4.0*blurSize *BlurScale , gl_TexCoord[0].t)) * 0.05;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s - 3.0*blurSize*BlurScale, gl_TexCoord[0].t )) * 0.09;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s - 2.0*blurSize*BlurScale, gl_TexCoord[0].t )) * 0.12;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s - blurSize*BlurScale, gl_TexCoord[0].t)) * 0.15;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s, gl_TexCoord[0].t)) * 0.16;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s + blurSize*BlurScale, gl_TexCoord[0].t)) * 0.15;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s + 2.0*blurSize*BlurScale, gl_TexCoord[0].t)) * 0.12;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s + 3.0*blurSize*BlurScale, gl_TexCoord[0].t)) * 0.09;
   sum += texture2D(ColourMap, vec2(gl_TexCoord[0].s + 4.0*blurSize*BlurScale, gl_TexCoord[0].t)) * 0.05;
 
   gl_FragColor = sum;
}