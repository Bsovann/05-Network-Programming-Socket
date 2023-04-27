# Makefile for compiling TCPClient.java and TCPServer.java in PowerShell/Linux/MacOS

# Define the compiler to use
JAVAC = javac

# Define the source files
CLIENT_SRC = TCPClient.java
SERVER_SRC = TCPServer.java

# Define the output files
CLIENT_OUT = TCPClient.class
SERVER_OUT = TCPServer.class

# Define the default target to build
all: $(CLIENT_OUT) $(SERVER_OUT)

# Compile the client source file
$(CLIENT_OUT): $(CLIENT_SRC)
	$(JAVAC) $(CLIENT_SRC)

# Compile the server source file
$(SERVER_OUT): $(SERVER_SRC)
	$(JAVAC) $(SERVER_SRC)

# Clean the generated class files
clean:
	Remove-Item $(CLIENT_OUT) $(SERVER_OUT)