package game;

import java.io.IOException;

public class Main {
	
	private static Scene scene = new Scene();

	public static void main(String[] args) {
		new Logic(args);
		
		System.out.println("\nWelcome to Hilbert Hotel!");
		System.out.println("There has been a murder!");
		System.out.println("Someone has been killed and it is your job to find who did it\n");
		
		System.out.println("Only thing that you know is that killer has been smoking.");
		System.out.println("There are four polish logicians in the hotel room and one of them is a killer.");
		System.out.println("They are in shock, so they can only answear in logical values for your statements.\n");

		System.out.println("The purpose of the game is to find who the killer is by asking logical questions.");
		System.out.println("Every statement has some logical value, and it is guaranteed that it is true for the killer to have weapon, motiv and to be smoking.");
		System.out.println("Every other person has at least one false value for this statements.");
		System.out.println("But be careful there is a liar in the room, whose answears are always negated.");
		System.out.println("Good luck, the clock is ticking ;) \n");

		System.out.println("Press enter to start the game...");
		System.out.println("And by the way you exit with esc ");
		
		try {
			System.in.read();
			scene.render();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
