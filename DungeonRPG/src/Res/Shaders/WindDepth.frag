varying vec3 normal;
varying float mdepth;

void main()
{
	
	//float Depth = (gl_FragCoord.z)/2 + 0.5;
   
   //gl_FragColor = vec4(mdepth, mdepth , mdepth, 1);

  if(gl_FrontFacing == true){
    	gl_FragColor = vec4(mdepth/2, mdepth/2 , 0, 1);
   }
   else{
    	gl_FragColor = vec4(0,0,1,1);
   }
 
    
}