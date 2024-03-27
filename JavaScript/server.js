import { existsSync } from "fs";
import { createServer } from "net";

const port = 9000;

const server = createServer((socket) => {
    socket.on("data", (buffer) => {
        const requestString = buffer.toString("utf-8");
        const req = parseRequest(requestString);
        switch (req.path) {
            case "GET": {
                if (existsSync(`.${req.path}`)) {
                    socket.write("HTTP/1.0 200 OK");
                } else socket.write("HTTP/1.0 404 NOT FOUND");
                break;
            }
            case "POST":
                console.log("POST");
                break;
            case "PUT":
                console.log("PUT");
                break;
            case "DELETE":
                console.log("DELETE");
                break;

            default:
                break;
        }
    });
});

server.listen(port, () => console.log("Listening at", port));

const parseRequest = (requstString) => {
    const [method, path, protocol] = requstString.split(" ");
    return {
        method,
        path,
        protocol,
    };
};
