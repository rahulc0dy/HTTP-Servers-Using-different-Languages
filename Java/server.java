package Java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Server {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(4596)) {
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }

            }
        }
    }

    private static void handleClient(Socket clienSocket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line + "\r\n");
        }

        String request = requestBuilder.toString();
        String[] requestLines = request.split("\r\n");
        String[] requestLine = requestLines[0].split(" ");
        String method = requestLine[0], path = requestLine[1], version = requestLine[2],
                host = requestLines[1].split(" ")[1];

        List<String> headers = new ArrayList<>();
        for (int h = 2; h < requestLines.length; h++) {
            headers.add(requestLines[h]);
        }

        String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                clienSocket.toString(), method, path, version, host, headers.toString());
        System.out.println(accessLog);

        Path filePath = getFilePath(path);
        if (Files.exists(filePath)) {
            // file exist
            String contentType = guessContentType(filePath);
            sendResponse(clienSocket, "200 OK", contentType, Files.readAllBytes(filePath));
        } else {
            // 404
            byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
            sendResponse(clienSocket, "404 Not Found", "text/html", notFoundContent);
        }
    }

    private static void sendResponse(Socket client, String status, String contentType, byte[] content)
            throws IOException {
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(content);
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }

    private static Path getFilePath(String path) {
        if ("/".equals(path)) {
            path = "/index.html";
        }

        return Paths.get("", path);
    }

    private static String guessContentType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }

}
