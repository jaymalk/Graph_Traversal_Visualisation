make:
	@javac ./lib/gui/*.java
run:
	@javac ./lib/gui/*.java
	@mv ./lib/gui/*.class .
	@java GraphTraversal
	@rm *.class
clean:
	@rm *.class
