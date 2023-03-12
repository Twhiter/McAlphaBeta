From openjdk:19

COPY . /bot

WORKDIR /bot

RUN javac -d ./out -classpath ./jkgp.jar ./src/*

CMD java -classpath "./out:./jkgp.jar" MonteAgent 
