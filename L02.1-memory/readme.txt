javac ru/otus/l021/*.java

jar -cvfm Agent007.jar manifest.mf ru/otus/l021/*.class

java -javaagent:Agent007.jar ru.otus.l021.AgentTester2



