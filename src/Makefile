compile:
	@echo "Compiling all Java files..."
	@javac server/RemoteStore.java
	@javac client/StoreView.java
	@echo "Done compiling..."
clean:
	@echo "Removing .class files..."
	@find . -name '*.class' -delete
	@echo "Done..."

package:
	@echo "Creating JAR file..."
	@jar cf online-store.jar $(shell find . -name '*.class' -printf '%P ')
	@echo "JAR file created: online-store.jar..."

run-server:
	@java -cp online-store.jar server.RemoteStore

run-client:
	@java -cp online-store.jar client.StoreView