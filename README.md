# Huffman Coding in Java and Scala
### A Final Project for Computer Science 354, Spring 2017

This project implements a Huffman Encoder/Decoder in Java and Scala. Each program
takes in a file, produces an encoded file, and reads the encoded file to ensure
that no information is lost in the encoding process.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Ensure that the Java runtime environment is installed on your system.
```
which java
usr/bin/java
```

Install Scala on your system. It is available at https://www.scala-lang.org/download/

Mac OS:
```
brew install scala
```

Linux:
```
sudo apt-get install scala
```

### Compiling and Running the Project

The project is split into two subdirectories, the Java coder and the Scala coder.

To compile and run the Java coder, navigate into the huffman_coding_java directory.
```
javac src/*.java
```
To run the program:
```
java src/HuffmanDriver [input file name] [encoded file name] [debug true/false]
```

To compile and run the Scala coder, navigate into the huffman_coding_scala directory.
```
scalac src/*.scala
```
To run the program:
```
scala src/HuffmanDriver [input file name] [encoded file name] [debug true/false]
```
With debug mode enabled, there will be more verbose output to the console, including
a breakdown of time to complete each task in the encoding process.

## Running the tests

Makefiles have been provided for convenience. From the top level directory of the
huffman_coding_java or huffman_coding_scala directories, run

```
make all
```

The class files will be generated, and then a test can be ran with the Makefile.

```
make run
```
Will execute the test with the Alice In Wonderland text file, and print the
runtime stats.

```
make debug
```
will run the same test, but prints out more verbose output to the console.

## Built With

* IntelliJ Idea IDE (project files not included for portability)

## Contributing

* Please contact the author to contribute to this project as the code is contained
in a private repository.

## Authors

* **Zachary Mikel**

## Acknowledgments
* Alvin Alexander's blog on Scala
* TutorialsPoint Scala tips
