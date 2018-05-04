dowload:
	mkdir primes; 
	cd primes; 
	wget http://www.primos.mat.br/dados/2T_part{1..200}.7z
	for i in ./*; do 7z e ${i}; done;
compile:
	javac Primecounter.java
run:
	java Primecounter
clean:
	rm *.class
