import { createServer } from "http";

const hostName: string = "localhost";
const port: number = 9000;

const server = createServer((req, res) => {
    console.log(typeof req, typeof res);
    res.statusCode = 200;
    res.setHeader("Content-Type", "text/plain");
    res.end("Server Working");
});

server.listen(port, hostName, () =>
    console.log(`Serving at ${hostName}:${port}`)
);
