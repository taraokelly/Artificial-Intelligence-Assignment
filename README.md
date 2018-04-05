# Artificial-Intelligence-Assignment
A Java console application that uses simulated annealing to break a playfair cipher. Fourth Year, Artificial Intelligence, Software Development.

**IMPORTANT: working temperature and transition controls for** ```ciphertext-2018.txt``` **are _20_ and _50,000_.**

## Table of Contents

+ [Requirements](#initial-plan)
+ [Additional Features](#additional-features)
+ [UML Diagram](#uml-diagram)
+ [How to Run](#how-to-run)
+ [References](#references)

## Requirements

+ A menu-driven command line UI.
+ User specified cipher text source.
+ User specified plain text destination.
+ A simulated annealing algorithm to break a Playfair Cipher, using a log-probability
and *n*-gram statistics as a heuristic evaluation function.
+ The package name **ie.gmit.sw.ai** must be used.
+ Must be completed and submitted by the *8th of April 2018*.

## Additional Features

+ ~~Calculation suggestion.~~ Not accurate for this version of the S.A. Playfair algorithm.
+ ~~Optimized to end when satisfied with key.~~ Any optimizations I considered were fairly hacky. I decided to fully iterate through the loops to heighten the chance of getting the ciphertext decrypted correctly, at the expense of not reducing the time of the loops.
+ Input and file validation - adding to the robustness of the application.
+ Object Oriented Design ([UML](#uml-diagram)):
  + Cipher breaker factory.
  + Amply abstracted.
  
## UML Diagram

![alt text](https://github.com/taraokelly/Artificial-Intelligence-Assignment/blob/master/img/UML.PNG "UML")

## How to Run

Open the root of this repository in the command prompt, and run the following command: 

```
java –cp ./playfair.jar ie.gmit.sw.ai.CipherBreaker
```

## References

http://marxsoftware.blogspot.ie/2015/02/determining-file-types-in-java.html

https://stackoverflow.com/questions/2696063/java-util-scanner-error-handling

https://www.tutorialspoint.com/design_pattern/factory_pattern.htm

https://learnonline.gmit.ie/pluginfile.php/329076/mod_resource/content/1/sa-cryptologia.pdf

https://github.com/mafunk92/java/blob/master/cs4840/hwk1/Playfair.java

https://github.com/gmabley/JavaPlayfair/blob/master/MableyPlayfair.java

-----

__*Tara O'Kelly - G00322214@gmit.ie*__
