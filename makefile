CC = javac

CPATH = src
BPATH = bytecode

vpath %.c src/
vpath %.class bytecode/

main : | bytecode
	$(CC) -sourcepath $(CPATH) -cp $(BPATH) -d $(BPATH) src/main.java

%.class : %.java | bytecode
		$(CC) -sourcepath $(CPATH) -cp $(BPATH) -d $(BPATH) $(CPATH)/$<

bytecode :
		mkdir bytecode

clean :
		rm bytecode/*

cleanall : clean
		rm -r doc/*

archive :
		tar -f projet-POO1-L2S4_javale.tar.gz -cvz src/*.java data/*.txt makefile
