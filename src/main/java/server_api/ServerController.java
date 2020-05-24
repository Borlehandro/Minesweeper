package server_api;

import api.ServerCommand;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ServerController {

    private final Socket socket;
    private final PrintWriter socketWriter;
    private final ObjectInputStream objectInput;

    public ServerController() throws IOException {
        Properties properties = new Properties();
        properties.load(ServerController.class.getClassLoader().getResourceAsStream("config.properties"));
        socket = new Socket(properties.getProperty("server_url"), Integer.parseInt(properties.getProperty("server_port")));
        socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        objectInput = new ObjectInputStream(socket.getInputStream());
    }

    public String send(ServerCommand command) throws IOException {
        socketWriter.print(command);
        if (command.hasArgs())
            command.getArgs().forEach(item -> socketWriter.print(" " + item));
        socketWriter.println();
        socketWriter.flush();
        StringBuilder builder = new StringBuilder();
        String line = objectInput.readUTF();
        builder.append(line);
        return builder.toString();
    }

    public Object sendWithObjectResult(ServerCommand command) throws IOException, ClassNotFoundException {

        socketWriter.print(command);
        if (command.hasArgs())
            command.getArgs().forEach(item -> socketWriter.print(" " + item));
        socketWriter.println();
        socketWriter.flush();
        return objectInput.readObject();
    }

    public void close() throws IOException {
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
    }
}