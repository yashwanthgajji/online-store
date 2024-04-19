compile:
	@echo "Compiling all Java files..."
	@mkdir bin
	@javac -d bin server/RemoteStore.java
	@javac -d bin client/ClientStore.java
	@echo "Done compiling..."
clean:
	@echo "Removing .class files..."
	@rm -rf bin
	@rm -f online-store.jar
	@echo "Done..."

package:
	@echo "Creating JAR file..."
	@jar cf online-store.jar -C bin .
	@echo "JAR file created: online-store.jar..."

run-server:
	@java -cp online-store.jar server.RemoteStore

run-client:
	@java -cp online-store.jar client.ClientStore