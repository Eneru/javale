CC = javac

CPATH = src
BPATH = bytecode

vpath %.c src/
vpath %.class bytecode/
vpath %.html html/

main : | bytecode
	$(CC) -sourcepath $(CPATH) -cp $(BPATH) -d $(BPATH) src/main.java

%.class : %.java | bytecode
		$(CC) -sourcepath $(CPATH) -cp $(BPATH) -d $(BPATH) $(CPATH)/$<

bytecode :
		mkdir bytecode
		
html :
		mkdir html

clean :
		rm bytecode/*

cleanall : clean
		rm -r doc/*

archive :
		tar -f projet-POO1-L2S4_javale.tar.gz -cvz src/*.java data/*.txt html/* makefile
