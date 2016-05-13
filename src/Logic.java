package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Logic {
	
	
	public static int valued;
	private static int not[][]; // Not truth table
	private static int and[][]; // And truth table
	private static int or[][]; // Or truth table
	private static int impl[][]; // Implication truth table
	private static String logicNames[]; // Table of names for logical values
	
	public static int notValue(int a){
		return not[a][1];
	}
	
	public static int andValue(int a, int b){
		return and[valued*a + b][2];
		
	}
	
	public static int orValue(int a, int b){
		return or[valued*a + b][2];
	}
	
	public static int implValue(int a, int b){
		return impl[valued*a + b][2];
	}
	
	public static String nameValue(int a){
		return logicNames[a];
	}
	
	private static void wrongArguments(){
		System.out.println("Wrong arguments!!");
		System.out.println("Use either no arguments for 2-valued logic or provide 4 filenames containing truth tables.");
		System.out.println("Filenames must be proceeded by \"--not\", \"--and\", \"--or\", \"--impl\" depending on which truth table they contain");
		System.exit(1);
	}
	
	private String[][] readFileToTab(File file, int n) throws FileNotFoundException{
		// First find out how many lines in a file
		Scanner s = new Scanner(file);
		s.useDelimiter("\n");
		int lines = 0;
		while (s.hasNext()){
			lines++;
			s.next();
		}
		s.close();
		s = new Scanner(file); // Go to the beginning
		String ret[][] = new String[lines][n];
		for (int i = 0; i < lines; i++){
			for (int j = 0; j<n;j++){
				if (s.hasNext()){
					ret[i][j] = s.next(); 
				}
				else {
					System.out.println("Incorrect file " + file.getName());
					System.exit(1);
				}
			}
		}
		s.close();
		return ret;
		
	}
	
	private int[][] stringTabToInt(String tab[][]){
		int ret[][] = new int[tab.length][tab[0].length];
		for (int i = 0; i<ret.length; i++){
			for (int j = 0; j<ret[0].length; j++){
				for (int k = 0; k < valued; k++){
					if (logicNames[k].equals( tab[i][j])) ret[i][j] = k;
				}
				if (!logicNames[ret[i][j]].equals(tab[i][j])){ // Just to check if everything went correctly
					System.out.print("Inconsistent files!!");
					System.exit(1);
				};
			}
		}
		return ret;
	}
	public Logic(String args[]){

		switch (args.length){
		case 0:
			// Initialization of default, two valued logic
			valued = 2;
			
			not = new int[2][2];
			not[0][0] = 0;	not[0][1] = 1;
			not[1][0] = 1;	not[1][1] = 0;
			
			and = new int[4][3];
			and[0][0] = 0;	and[0][1] = 0; and[0][2] = 0;
			and[1][0] = 0;	and[1][1] = 1; and[1][2] = 0;
			and[2][0] = 1;	and[2][1] = 0; and[2][2] = 0;
			and[3][0] = 1;	and[3][1] = 1; and[3][2] = 1;

			or = new int[4][3];
			or[0][0] = 0;	or[0][1] = 0; or[0][2] = 0;
			or[1][0] = 0;	or[1][1] = 1; or[1][2] = 1;
			or[2][0] = 1;	or[2][1] = 0; or[2][2] = 1;
			or[3][0] = 1;	or[3][1] = 1; or[3][2] = 1;
			
			impl = new int[4][3];
			impl[0][0] = 0;	impl[0][1] = 0; impl[0][2] = 1;
			impl[1][0] = 0;	impl[1][1] = 1; impl[1][2] = 1;
			impl[2][0] = 1;	impl[2][1] = 0; impl[2][2] = 0;
			impl[3][0] = 1;	impl[3][1] = 1; impl[3][2] = 1;
			
			logicNames = new String[2];
			logicNames[0] = "False";
			logicNames[1] = "True";

			break;
		case 8:
			// Basic file check and argument correctness check
			String notFileName = null;
			String andFileName = null;
			String orFileName = null;
			String implFileName = null;
			for (int i = 0; i<7; i=i+2){
				if (args[i].equals("--not")) notFileName = args[i+1];
				if (args[i].equals("--and")) andFileName = args[i+1];
				if (args[i].equals("--or")) orFileName = args[i+1];
				if (args[i].equals("--impl")) implFileName = args[i+1];
			}
			if (notFileName == null || andFileName == null || orFileName == null || implFileName == null )
				wrongArguments();

			File notFile = new File(notFileName);
			File andFile = new File(andFileName);
			File orFile = new File(orFileName);
			File implFile = new File(implFileName);
			
			// Read not truth table
			try {
				String notS[][] = readFileToTab(notFile, 2);
				valued = notS.length;
				logicNames = new String[valued];
				for (int i = 0; i<notS.length; i++){
					logicNames[i] = notS[i][0];
				}
				not = stringTabToInt(notS);
			} catch (FileNotFoundException e) {
				System.out.print("File "+notFile.getName()+" not found.");
			}
			// Read and truth table
			try {
				String andS[][] = readFileToTab(andFile, 3);
				and = stringTabToInt(andS);
			} catch (FileNotFoundException e) {
				System.out.print("File "+andFile.getName()+" not found.");
			}
			// Read or truth table
			try {
				String orS[][] = readFileToTab(orFile, 3);
				or = stringTabToInt(orS);
			} catch (FileNotFoundException e) {
				System.out.print("File "+orFile.getName()+" not found.");
			}
			try {
				String implS[][] = readFileToTab(implFile, 3);
				impl = stringTabToInt(implS);
			} catch (FileNotFoundException e) {
				System.out.print("File "+implFile.getName()+" not found.");
			}
			
			
			break;
		default:
			wrongArguments();
			break;
		}
		for (int i = 0; i< valued; i++){
			System.out.println(i+" logical value is "+ logicNames[i]);
		}
	}

}
