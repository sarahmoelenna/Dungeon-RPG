package MyDungeonPackage;

public class DialogueData {
	
	String LineOne;
	String LineTwo;
	String LineThree;
	int reference;
	
	public DialogueData(int ref, String A, String B, String C){
		reference = ref; 
		LineOne = A;
		LineTwo = B;
		LineThree = C;
	}
	
}
