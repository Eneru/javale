CC = javac

CPATH = src
BPATH = bytecode

vpath %.c src/
vpath %.class bytecode/
vpath %.html html/

main : | bytecode
	$(CC) -sourcepath $(CPATH) -cp $(BPATH) -d $(BPATH) src/Affichage.java

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
		
lanceur:
	java -cp bytecode/ Affichage

archive :
		tar -f projet-POO1-L2S4_javale.tar.gz -cvz src/*.java data/*.txt html/* makefile
