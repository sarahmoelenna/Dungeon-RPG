package MyDungeonPackage;

class FontHandler {
	
	public static CustomFont ElitePro;
	public static CustomFont EliteProBlack;
	
	public static void SetupFonts(){
		ElitePro = new CustomFont("MyFont");
		System.out.println("ElitePro Font Loaded");
		
		EliteProBlack = new CustomFont("MyFontBlack");
		System.out.println("EliteProBlack Font Loaded");
	}

}
