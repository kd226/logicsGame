package game;

public class Person {
	public int weapon;
	public int motive;
	public int smoking;
	Boolean lying;
	Boolean killer;
	public String name;
	
	public Person(int tab[]){
		weapon = tab[0];
		motive = tab[1];
		smoking = tab[2];
	}
	
	String answear(int a){
		if (lying) a = Logic.notValue(a);
		return "It is "+ Logic.nameValue(a) +".";
	}
}
