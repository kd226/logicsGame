package game;

public class QuestionBar {

	private int currentCursorY;
	private int currentCursorX;
	private int currentMaxY;
	private int currentMaxX;
	private String[][] questions[];
	private int personAsked;
	private int level;
	private int question[];
	private String firstLine;
	private Person suspects[];
	
	public QuestionBar(Person suspects[]){
		questions = new String[3][][];
		level = 0;
		firstLine = "";
		for (int i = 0; i< suspects.length; i++) 
			firstLine += suspects[i].name + "\t\t";
		firstLine += "\n"; 
		this.suspects = suspects;
		
		questions[0] = new String[2][suspects.length];
		for (int i = 0; i<suspects.length; i++){
			questions[0][0][i] = "Question";
			questions[0][1][i] = "Accuse\t";
		}
		
		questions[1] = new String[5][1];
		questions[1][0][0] = "Simple statement";
		questions[1][1][0] = "And statement";
		questions[1][2][0] = "Or statement";
		questions[1][3][0] = "Implication statement";
		questions[1][4][0] = "Back";
		
		questions[2] = new String[6][suspects.length-1];
		for (int i = 0; i<suspects.length-1; i++){
			questions[2][0][i] = "has weapon";
			questions[2][1][i] = "has no weapon";
			questions[2][2][i] = "has motive";
			questions[2][3][i] = "has no motive";
			questions[2][4][i] = "is smoking";
			questions[2][5][i] = "is not smoking";
		}
		currentMaxX = suspects.length - 1;
		currentMaxY = 1;

	}
	
	public void moveUp(){
		if (currentCursorY>0) currentCursorY--;
	}
	
	public void moveDown(){
		if (currentCursorY<currentMaxY) currentCursorY++;
		System.out.print(currentCursorY);
	}

	public void moveLeft(){
		if (currentCursorX>0) currentCursorX--;
	}

	public void moveRight(){
		if (currentCursorX<currentMaxX) currentCursorX++;
	}
	
	public String nextQuestion(){
		
		String ret = "";
		switch(level){
		case 0:
			if (currentCursorY == 1){
				if (suspects[currentCursorX].killer){
					ret = "Well played!";
				} else {
					ret = "You lost!";
				}
			} else {
				level = 1;
				personAsked = currentCursorX;
			}
			firstLine = "\n";
			break;
		case 1:
			if(currentCursorY == 0){
				question = new int[1];
				question[0] = 0;
				firstLine = "";
				for (int i = 0; i<suspects.length; i++){
					if (i != personAsked) firstLine += suspects[i].name + ":\t\t";
				}
				firstLine += "\n";
				level = 2;
			} else if (currentCursorY == 4){
				level = 0;
				firstLine = "";
				for (int i = 0; i< suspects.length; i++) 
					firstLine += suspects[i].name + "\t\t";
				firstLine += "\n"; 
			} else {
				question = new int[3];
				question[0] = currentCursorY;
				question[1] = -1;
				level = 2;
				firstLine = "\033[47m\t\t\033[49m";
				if (currentCursorY == 1) firstLine += " and \t";
				if (currentCursorY == 2) firstLine += " or \t";
				if (currentCursorY == 3) firstLine +=" then \t";
				firstLine += "\n";
				for (int i = 0; i<suspects.length; i++){
					if (i != personAsked) firstLine += suspects[i].name + ":\t\t";
				}
				firstLine += "\n";
			}
			break;
		case 2:
			int statementAbout = currentCursorX;
			if (currentCursorX>=personAsked) statementAbout++;
			if (question[0] == 0){
				
				int reality = 0;
				if (currentCursorY /2 == 0) reality = suspects[statementAbout].weapon;
				if (currentCursorY /2 == 1) reality = suspects[statementAbout].motive;
				if (currentCursorY /2 == 2) reality = suspects[statementAbout].smoking;
				if (currentCursorY%2 == 1) reality = Logic.notValue(reality);

				ret += suspects[personAsked].name + " says: \t" + suspects[statementAbout].name 
						+ " " + questions[2][currentCursorY][0] + ". " + suspects[personAsked].answear(reality);
				
				firstLine = "";
				for (int i = 0; i< suspects.length; i++) 
					firstLine += suspects[i].name + "\t\t";
				firstLine += "\n"; 
				level = 0;
			} else if (question[1] == -1){
				question[1] = currentCursorY * 3 + statementAbout; // Now statement about is modulo of this value
				firstLine = suspects[statementAbout].name;
				firstLine += " " + questions[2][currentCursorY][0];
				if (question[0] == 1) firstLine += " and ";
				if (question[0] == 2) firstLine += " or ";
				if (question[0] == 3) firstLine += " then ";
				firstLine += "\033[47m\t\t\033[49m\n";
				for (int i = 0; i<suspects.length; i++){
					if (i != personAsked) firstLine += suspects[i].name + ":\t\t";
				}
				firstLine += "\n";
			} else {
				
				if (question[0] == 1) ret = " and ";
				if (question[0] == 2) ret = " or ";
				if (question[0] == 3) ret = " then ";
				
				int firstSuspect = question[1]%3;
				int firstStatement = (question[1] - firstSuspect)/3;
				
				ret = suspects[personAsked].name + " says: \t" + suspects[firstSuspect].name 
						+ " " + questions[2][(question[1] - firstSuspect)/3][0] + ret;
				
				ret += suspects[statementAbout].name + " " + questions[2][currentCursorY][0] + ". ";
				
				int first = 0;
				if (firstStatement /2 == 0) first = suspects[firstSuspect].weapon;
				if (firstStatement /2 == 1) first = suspects[firstSuspect].motive;
				if (firstStatement /2 == 2) first = suspects[firstSuspect].smoking;
				if (firstStatement%2 == 1) first = Logic.notValue(first);
				
				int second = 0;
				if (currentCursorY /2 == 0) second = suspects[statementAbout].weapon;
				if (currentCursorY /2 == 1) second = suspects[statementAbout].motive;
				if (currentCursorY /2 == 2) second = suspects[statementAbout].smoking;
				if (currentCursorY%2 == 1) second = Logic.notValue(first);
				
				int answear = 0;	
				if (question[0] == 1) answear = Logic.andValue(first, second);
				if (question[0] == 2) answear = Logic.orValue(first, second);
				if (question[0] == 3) answear = Logic.implValue(first, second);
				
				ret+=suspects[personAsked].answear(answear);
				
				firstLine = "";
				for (int i = 0; i< suspects.length; i++) 
					firstLine += suspects[i].name + "\t\t";
				firstLine += "\n"; 
				level = 0;
			}
			break;
		}
		
		currentCursorX = 0;
		currentCursorY = 0;
		currentMaxX = questions[level][0].length-1;
		currentMaxY = questions[level].length-1;
		
		return ret;
	}
	
	public String render(){
		String ret = "";
		for (int i = 0; i<80; i++){
			ret+="_";
		}
		ret+="\n" + firstLine;
		for (int i = 0; i<questions[level].length; i++){
			for (int j = 0; j<questions[level][0].length; j++){
				if (currentCursorY == i && currentCursorX == j) ret+="\033[1m";
				ret+=questions[level][i][j]+"\t";
				if (currentCursorY == i && currentCursorX == j) ret+="\033[22m";
			}
			if (i != questions[level].length-1)ret+="\n"; 
		}
		
		return ret;
	}
}
