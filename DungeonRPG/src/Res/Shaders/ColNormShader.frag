varying vec3 varyingColour;
varying vec3 MyColour;

void main()
{
    gl_FragColor = vec4(MyColour.r * varyingColour.r, MyColour.g * varyingColour.g, MyColour.b * varyingColour.b, 1);
    
}