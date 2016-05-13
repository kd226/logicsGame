package game;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import jline.ConsoleReader;

public class Scene {
	
	private QuestionBar qb;
	private Person suspects[];
	private String chat[];
	private int chatLength;
	private int State; // 0 - game rollin, 1 - won, 2 - lost, 3 - escape pressed
	private int questionsAsked;
	
	private String progressBar(Duration time){ 
		// Print progress bar to console, with color modifications
		String ret = "Q: "+ questionsAsked + " Time: ";
		if (time.toMinutes()<2) ret +="\033[31m"; // Print time in red if less than 2 minutes
		ret += String.format("%02d",time.toMinutes()) + ":" 
		+ String.format("%02d",(time.getSeconds() - time.toMinutes()*60)) + " \033[39m[";
		
		if (time.toMinutes()<2) ret +="\033[31m"; // Print bar in red if less than 2 minutes
		else if (time.toMinutes()<5) ret +="\033[33m"; // Print in orange if less than 5
		else ret +="\033[32m"; // Print in green otherwise
		
		for (int i = 0; i<60; i++){
			if (i<time.getSeconds()/60.0*4) ret += "=";
			else ret += " ";
		}
		ret += "\033[0m]\n";
		for (int i = 0; i< 80; i++){
			ret += "_";
		}
		return ret + "\n";
	}
	
	public void render() throws IOException{
		// Game initialization
		Duration counter = Duration.ofMinutes(15); // Game time count down
		Duration deltaTime = Duration.ZERO; // Time since last tick
		Instant tick = Instant.now(); // Tick instant from clock
	    System.out.print("\033[2J\033[?25l"); // Clear the screen and hide the cursor 
		ConsoleReader reader = new ConsoleReader();
		reader.getTerminal().disableEcho();
	    // Scene initialization
		questionsAsked = 0;
	    chat = new String[13];
	    for (int i = 0; i<chat.length; i++){
	    	chat[i] = "";
	    }
	    chatLength = 0;
	    State = 0;
	    // Initialize suspects
	    suspects = new Person[3];
	    Random generator = new Random();
	    int killer = generator.nextInt(3); // Random killer 
	    int liar = generator.nextInt(3); // Random liar
	    for (int i = 0; i<3; i++){
	    	int guiltTable[] = new int [3];
	    	for (int j = 0; j<guiltTable.length; j++){
	    		guiltTable[j] = generator.nextInt(Logic.valued);
	    	}
	    	guiltTable[generator.nextInt(guiltTable.length)] = 0;
	    	suspects[i] = new Person(guiltTable);
	    	suspects[i].killer = false;
	    	suspects[i].lying = false;
	    }
	    suspects[liar].lying = true;	
	    suspects[killer].killer = true;
	    suspects[killer].motive = 1;
	    suspects[killer].weapon = 1;
	    suspects[killer].smoking = 1;
	    suspects[0].name = "\033[34;1mJan\033[39;22m";
	    suspects[1].name = "\033[33;1mAlfred\033[39;22m";
	    suspects[2].name = "\033[36;1mRoman\033[39;22m";
	    		
	    qb = new QuestionBar(suspects);
		while(State == 0 && !counter.isZero()){
			// Render the game screen
		    System.out.print("\033[H"
		    		+ ""); // Position cursor at 1,1	
			System.out.print(progressBar(counter));
			// Print chat starting from oldest messages
			for (int i = (chatLength+1)%chat.length; i != chatLength;
					i = (i+1)%chat.length){ 
				System.out.println(chat[i]);
			}
			System.out.println(chat[chatLength]);
			
			System.out.print(qb.render());
		    System.out.flush();
		    // Read input
			int key = reader.readVirtualKey();
			if (key != -1){
				switch (key){
					case 27: // Escape
						chatLength = (chatLength + 1)%15;
						chat[chatLength] = "Quitting...";
						State = 3;
						break;
					case 2: // Left arrow
						qb.moveLeft();
						break;
					case 16: // Up arrow
						qb.moveUp();
						break;
					case 6: // Right arrow
						qb.moveRight();
						break;
					case 14: // Down arrow
						qb.moveDown();
						break;
					case 10: // Enter
						String newChat = qb.nextQuestion();
						if (newChat != ""){
							chatLength = (chatLength + 1)%chat.length;
							chat[chatLength] = newChat;
							if (newChat.equals("Well played!")) State = 1; 
							else if (newChat.equals("You lost!")) State = 2;
							else {
								questionsAsked++;
							}
						}
						break;
					default:
				}
				System.out.print("\033[2J");
			}
		
			// Time actualization
			deltaTime = Duration.between(tick, Instant.now());
			tick = Instant.now();
			counter = counter.minus(deltaTime);
		}
		// Game ended
		System.out.print("\n\n" + chat[chatLength] + "\n\n");
		if (State == 1){ // Win message
			System.out.print("You asked " + questionsAsked + " questions in " 
		+ Logic.valued + "-valued logic." + "\n");
			System.out.print("Figuring out the truth took you " 
		+ (Duration.ofMinutes(15).minus(counter).toMinutes()) + " minutes and "
		+ (Duration.ofMinutes(15).minus(counter).getSeconds()-Duration.ofMinutes(15).minus(counter).toMinutes()*60)
		+ " seconds.\n");
			System.out.print("Can you do even better?\n\n");
		}
		
		System.out.print("\033[0m\033[?25h");
		reader.getTerminal().enableEcho();
	}	
}
