#Hilbert Hotel

It`s a simple logical game written in Java 8 (important!) using library JLine.
It uses a lot of ANSI escape codes, so may not work or work improperly on terminals not supporting these.

##Compile instruction

```
javac -d . -cp jline-1.0.jar src/Logic.java src/Person.java src/QuestionBar.java src/Scene.java src/Main.java
```

##Run instruction

```
java -cp .:jline-1.0.jar  game.Main
```

or optionally with arguments

```
java -cp .:jline-1.0.jar  game.Main --not not.txt --and and.txt --or or.txt --impl impl.txt
````
