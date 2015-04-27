package isel.mpd.misc;

import java.util.Scanner;

public class ChooseStudent {

	public static void main(String[] args) {
		int max;
		if(args.length > 0) {
			max = Integer.parseInt(args[0]);
		} else {
			Scanner s = new Scanner(System.in); 
			System.out.println("Introduza o número máximo: ");
			max = s.nextInt();
			s.close();
		}
		
		System.out.println("O número de candidatos à taluda é: " + max);
			
		int chosenOne = Utils.chooseRandom(max);
		System.out.println("O contemplado é o nº: " + chosenOne);
	}

}
