all:
	@javac src/*.java
clean:
	@rm -rf src/*.class
run:
	@cd src && java HuffmanDriver ../resources/test.txt ../resources/test-enc.txt false
debug:
	@cd src && java HuffmanDriver ../resources/test.txt ../resources/test-enc.txt true
