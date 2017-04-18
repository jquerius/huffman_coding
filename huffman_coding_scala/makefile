all:
	@cd src && scalac *.scala
clean:
	@rm -rf src/*.class
run:
	@cd src && scala HuffmanDriver ../resources/test.txt ../resources/test-enc.txt false
debug:
	@cd src && scala HuffmanDriver ../resources/test.txt ../resources/test-enc.txt true
