varying vec3 normal;
varying vec3 position;
varying mat4 MyMat;

void main()
{
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    position = gl_ModelViewProjectionMatrix * gl_Vertex;
    normal = normalize((gl_NormalMatrix * gl_Normal).xyz);
    MyMat = gl_ModelViewMatrix;
}