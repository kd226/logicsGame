#Hilbert Hotel

It`s a simple logical game written in Java using library JLine.
It uses a lot of ANSI escape codes, so may not work or work improperly on terminals not supporting those.

##Compile instruction

```
javac -d ./.. -cp ./../jline-1.0.jar Logic.java Person.java QuestionBar.java Scene.java Main.java
```

##Run instruction

```
java -cp .:jline-1.0.jar  game.Main
```

or optionally with arguments

```
java -cp .:jline-1.0.jar  game.Main --not not.txt --and and.txt --or or.txt --impl impl.txt
````
