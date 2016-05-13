#Hilbert Hotel
It`s a simple logical game written in Java 8 (important!) using library JLine (included here).
It uses a lot of ANSI escape codes, so may not work or work improperly on terminals not supporting them.

##Logic
Logic should be described using natural names like True, False or Possible. 
It is crucial for the logic to be sorted lexicographically in files, first one described will be treated as False, second will be treated as True by the game.

##Compile instruction
```
javac -d . -cp jline-1.0.jar src/Logic.java src/Person.java src/QuestionBar.java src/Scene.java src/Main.java
```
Java 8+ required 

##Run instruction
```
java -cp .:jline-1.0.jar  game.Main
```
or optionally with arguments
```
java -cp .:jline-1.0.jar  game.Main --not not.txt --and and.txt --or or.txt --impl impl.txt
````
