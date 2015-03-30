package isel.mpd.misc;


public class Utils {
	  public static void say(String what) {
		System.out.println("saying " + what);
	  }
	  
	  public static String getInUppercase(String what) {
		return "saying in uppercase " + what.toUpperCase();
	  }
	  
	  public static int chooseRandom(int max) {
		  return (int)(Math.random()*max) +1;
	  }

	public static String getDefaultName() {
		return "default";
	}
}
