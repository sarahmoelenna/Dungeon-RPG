package MyDungeonPackage;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


class MyMath {
	
	public static Vector3f VecBetween(Vector3f PointA, Vector3f PointB){
		return new Vector3f(PointA.x - PointB.x, PointA.y - PointB.y, PointA.z - PointB.z);
	}
	
	public static double DistanceFromOrigin(Vector3f point){
		
		double XY = Math.sqrt(((point.x ) * (point.x )  +  (point.y ) * (point.y )));
		
		double XYZ = Math.sqrt((XY * XY  +  (point.z ) * (point.z )));
		
		return XYZ;
	}
	
	public static double Area(Vector3f VA, Vector3f VB, Vector3f VC){
		
		double AB = Length(VA, VB);
		double AC = Length(VA, VC);
		double BC = Length(VB, VC);
		
		double P = (AB + AC + BC)/2;
		
		double area =  (Math.sqrt(P*(P-AB)*(P-BC)*(P-AC))); 
		
		if (AB == Double.NaN || AC == Double.NaN || BC == Double.NaN){
			System.out.println("Nan");
			return Double.NaN;
		}
		else{
			return area;
		}
	}
	
	public static double TwoDArea(Vector2f VA, Vector2f VB, Vector2f VC){
		
		double AB = TwoDLength(VA, VB);
		double AC = TwoDLength(VA, VC);
		double BC = TwoDLength(VB, VC);
		
		double P = (AB + AC + BC)/2;
		
		double area =  (Math.sqrt(P*(P-AB)*(P-BC)*(P-AC))); 
		
		if (AB == Double.NaN || AC == Double.NaN || BC == Double.NaN){
			System.out.println("Nan");
			return Double.NaN;
		}
		else{
			return area;
		}
	}
	
	public static double Length(Vector3f VA, Vector3f VB){
		
		double XY = ((VA.x - VB.x) * (VA.x - VB.x)  +  (VA.y - VB.y) * (VA.y -  VB.y));
		
				double XYZ = (XY  +  (VA.z - VB.z) * (VA.z - VB.z));
		
		double finxyz = Math.sqrt(XYZ);
		
		return finxyz;
	}
	
	public static double TwoDLength(Vector2f VA, Vector2f VB){
		
		double XY = ((VA.x - VB.x) * (VA.x - VB.x)  +  (VA.y - VB.y) * (VA.y -  VB.y));

		double finxyz = Math.sqrt(XY);
		
		return finxyz;
	}
	
	public static Vector3f LineIntersectWithPlanePoint(Vector3f LineOrigin, Vector3f LineVector, Vector3f PlanePoint, Vector3f PlaneNormal){
		
		double PlaneNP = PlaneNormal.x * PlanePoint.x + PlaneNormal.y * PlanePoint.y +PlaneNormal.z * PlanePoint.z;
		double PlaneNLineP = PlaneNormal.x * LineOrigin.x + PlaneNormal.y * LineOrigin.y + PlaneNormal.z * LineOrigin.z;
		double PlaneNLineV = PlaneNormal.x * LineVector.x + PlaneNormal.y * LineVector.y + PlaneNormal.z * LineVector.z;
		double LineMagnitude = (PlaneNP - PlaneNLineP)/PlaneNLineV;
		
		Vector3f IntersectPoint = new Vector3f();
		IntersectPoint.x = (float) (LineOrigin.x + LineVector.x * LineMagnitude);
		IntersectPoint.y = (float) (LineOrigin.y + LineVector.y * LineMagnitude);
		IntersectPoint.z = (float) (LineOrigin.z + LineVector.z * LineMagnitude);
		
		return IntersectPoint;
	}
	
	public static boolean AreParralel(Vector3f VecA, Vector3f VecB){
		
		if (VecA.x / VecB.x == VecA.y/VecB.y && VecA.z/VecB.z == VecA.y/VecB.y){
			return true;
		}
		else return false;
		
	}
	
	public static boolean PointInTriangle(Vector3f VertA, Vector3f VertB, Vector3f VertC, Vector3f VertPoint){
		
		double ABC = Area(VertA, VertB, VertC);
		double ABP = Area(VertA, VertB, VertPoint);
		double APC = Area(VertA, VertPoint, VertC);
		double PBC = Area(VertPoint, VertB, VertC);
		
		if ((ABP + APC + PBC) < ABC){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean PointInTwoDTriangle(Vector3f VertA, Vector3f VertB, Vector3f VertC, Vector3f VertPoint, Vector3f Ignore){
		
		
		double ABC = 0;
		double ABP = 0;
		double APC = 0;
		double PBC = 0;
		
		
		if (Ignore.x > 0){
			ABC = TwoDArea(new Vector2f(VertA.y, VertA.z), new Vector2f(VertB.y, VertB.z), new Vector2f(VertC.y, VertC.z));
			ABP = TwoDArea(new Vector2f(VertA.y, VertA.z), new Vector2f(VertB.y, VertB.z), new Vector2f(VertPoint.y, VertPoint.z));
			APC = TwoDArea(new Vector2f(VertA.y, VertA.z), new Vector2f(VertPoint.y, VertPoint.z), new Vector2f(VertC.y, VertC.z));
			PBC = TwoDArea(new Vector2f(VertPoint.y, VertPoint.z), new Vector2f(VertB.y, VertB.z), new Vector2f(VertC.y, VertC.z));
		}
		if (Ignore.y > 0){
			ABC = TwoDArea(new Vector2f(VertA.x, VertA.z), new Vector2f(VertB.x, VertB.z), new Vector2f(VertC.x, VertC.z));
			ABP = TwoDArea(new Vector2f(VertA.x, VertA.z), new Vector2f(VertB.x, VertB.z), new Vector2f(VertPoint.x, VertPoint.z));
			APC = TwoDArea(new Vector2f(VertA.x, VertA.z), new Vector2f(VertPoint.x, VertPoint.z), new Vector2f(VertC.x, VertC.z));
			PBC = TwoDArea(new Vector2f(VertPoint.x, VertPoint.z), new Vector2f(VertB.x, VertB.z), new Vector2f(VertC.x, VertC.z));
		}
		if (Ignore.z > 0){
			ABC = TwoDArea(new Vector2f(VertA.y, VertA.x), new Vector2f(VertB.y, VertB.x), new Vector2f(VertC.y, VertC.x));
			ABP = TwoDArea(new Vector2f(VertA.y, VertA.x), new Vector2f(VertB.y, VertB.x), new Vector2f(VertPoint.y, VertPoint.x));
			APC = TwoDArea(new Vector2f(VertA.y, VertA.x), new Vector2f(VertPoint.y, VertPoint.x), new Vector2f(VertC.y, VertC.x));
			PBC = TwoDArea(new Vector2f(VertPoint.y, VertPoint.x), new Vector2f(VertB.y, VertB.x), new Vector2f(VertC.y, VertC.x));
		}
		
		if ((ABP + APC + PBC) <= ABC+ABC/10){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static Vector2f RotateAboutPoint(Vector2f CenterPoint, float Radius, float AngleInDegrees){
		
		 float x = (float)(Radius * Math.cos(AngleInDegrees * Math.PI / 180F)) + CenterPoint.x;
	     float y = (float)(Radius * Math.sin(AngleInDegrees * Math.PI / 180F)) + CenterPoint.y;

		return new Vector2f(x,y);
	}
	
	public static float CalculateYaw(Vector3f CenterPoint, Vector3f Point){
		
		double AB = Length(CenterPoint, Point);
		double AR = Length(CenterPoint, new Vector3f(CenterPoint.x, CenterPoint.y-1, CenterPoint.z));
		double BR = Length(Point, new Vector3f(CenterPoint.x, CenterPoint.y-1, CenterPoint.z));
		
		double CosA = (AR * AR + AB * AB - BR * BR)/(2 * AR * AB);
		
		return (float) (Math.acos(CosA) * (180/Math.PI));
	}
	
	public static float AngleInDegrees(Vector3f AngleVector,Vector3f PointA,Vector3f PointB){
		
		double RA = Length(AngleVector, PointA);
		double RB = Length(AngleVector, PointB);
		double AB = Length(PointB, PointA);
		
		double COSA = (RA * RA + RB * RB - AB * AB)/(2 * RA * RB);
		
		return (float) (Math.acos(COSA) * (180/Math.PI));
	}
	
	public static Vector3f CalculateTrianlgeNormal(Vector3f PointA,Vector3f PointB,Vector3f PointC){
		
		Vector3f U = new Vector3f();
		Vector3f V = new Vector3f();
		Vector3f.sub(PointA, PointB, U);
		Vector3f.sub(PointA, PointC, V);
		
		Vector3f Normal = new Vector3f();
		Normal.x = U.y * V.z - U.z * V.y;
		Normal.y = U.z * V.x - U.x * V.z;
		Normal.z = U.x * V.y - U.y * V.x;
		
		return Normal;
	}
	
	public static Vector3f RotatePointAboutOrigin(Vector3f Point, float Degress, Vector3f IgnoreAxis){
		
		Vector3f ReturnMe = new Vector3f();
		
		if(IgnoreAxis.y == 1){
			ReturnMe.x = RotateAboutPoint(new Vector2f(0,0), (float) TwoDLength(new Vector2f(0,0), new Vector2f(Point.x, Point.z)), Degress).x;
			ReturnMe.z = RotateAboutPoint(new Vector2f(0,0), (float) TwoDLength(new Vector2f(0,0), new Vector2f(Point.x, Point.z)), Degress).y;
			ReturnMe.y = Point.y;
		}
		
		if(IgnoreAxis.x == 1){
			ReturnMe.y = RotateAboutPoint(new Vector2f(0,0), (float) TwoDLength(new Vector2f(0,0), new Vector2f(Point.y, Point.z)), Degress).x;
			ReturnMe.z = RotateAboutPoint(new Vector2f(0,0), (float) TwoDLength(new Vector2f(0,0), new Vector2f(Point.y, Point.z)), Degress).y;
			ReturnMe.x = Point.x;
		}
		
		if(IgnoreAxis.z == 1){
			ReturnMe.x = RotateAboutPoint(new Vector2f(0,0), (float) TwoDLength(new Vector2f(0,0), new Vector2f(Point.y, Point.x)), Degress).x;
			ReturnMe.y = RotateAboutPoint(new Vector2f(0,0), (float) TwoDLength(new Vector2f(0,0), new Vector2f(Point.y, Point.x)), Degress).y;
			ReturnMe.z = Point.z;
		}
		
		return ReturnMe;
	}
	
	public static boolean PointInCube(Vector3f Point, Vector3f CubeCenter, Float Width){
		
		if(Point.x > CubeCenter.x - Width && Point.x < CubeCenter.x + Width){
			if(Point.y > CubeCenter.y - Width && Point.y < CubeCenter.y + Width){
				if(Point.z > CubeCenter.z - Width && Point.z < CubeCenter.z + Width){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean PointInCylinder(Vector3f Point, Vector3f CylinderBase, float Height, float Width){
		if(TwoDLength(new Vector2f(Point.x, Point.z), new Vector2f(CylinderBase.x, CylinderBase.z)) <= Width){
			if(Point.y >= CylinderBase.y && Point.y <= CylinderBase.y + Height){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	
	}
	
	public static int FindPosition(int CurrentPositon, int MaxPosition, int RelativePosition){
		 
		 int TempInt;
		 TempInt = CurrentPositon + RelativePosition;
		 if(TempInt < 0){
			 TempInt = MaxPosition + TempInt + 1;
		 }
		 
		 if(TempInt > MaxPosition){
			 TempInt = TempInt - MaxPosition - 1;
		 }
		 
		 return TempInt;
	 }

	public static Vector2f AngleToHeadingVector(float AngleDegree){
		//System.out.println(AngleDegree);
		float AngleRadians = (float) (AngleDegree * Math.PI/180);
		float XHeading = (float) Math.cos(AngleRadians);
		float YHeading = (float) Math.sin(AngleRadians);
		
		return new Vector2f(XHeading,YHeading);
	}
	
	public static float HeadingVectorToAngle(Vector2f Heading){
		float Angle = 0;
		Angle = (float)Math.atan2(Heading.x,Heading.y);
		float MyAngle = (float) (Angle*(180/Math.PI));
		
		return MyAngle;
	}

	public static Vector3f RotateAboutOrigin(Vector3f Point, float Degrees, boolean Offset){
		Vector3f MyVector = new Vector3f(0,0,0);
		MyVector.y = Point.y;
		
		float MyDegrees = (float) (Degrees * Math.PI / 180);
		
		MyVector.x = (float) (Point.x * Math.cos(MyDegrees) - Point.z * Math.sin(MyDegrees));
		MyVector.z = (float) (Point.x * Math.sin(MyDegrees) + Point.z * Math.cos(MyDegrees));
		
		if(Offset == true){
		if(Degrees == 90){
			MyVector.x +=1;
		}
		if(Degrees == 180){
			MyVector.x +=1;
			MyVector.z +=1;
			//System.out.println(MyVector);
			//System.out.println(Point);
		}
		if(Degrees == 270){
			MyVector.z +=1;
			//System.out.println(MyVector + " M");
			//System.out.println(Point);
		}
		}
		
		return MyVector;
	}
	
}
