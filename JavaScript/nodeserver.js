import { createServer } from "http";

const hostName = "127.0.0.1";
const port = 8000;

const server = createServer((req, res) => {
    res.statusCode = 200;
    res.setHeader("Content-Type", "text/plain");
    res.end("Server Working");
});

server.listen(port, hostName, () =>
    console.log(`Serving at ${hostName}:${port}`)
);
