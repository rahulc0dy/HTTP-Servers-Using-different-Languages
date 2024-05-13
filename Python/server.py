from http.server import BaseHTTPRequestHandler, HTTPServer

class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        self.wfile.write(b"<html><body><h1>Hello, World!</h1></body></html>")

def main():
    server_address = ('', 8000)  # Empty string means localhost
    httpd = HTTPServer(server_address, SimpleHTTPRequestHandler)
    print('Server running on port 8000...')
    httpd.serve_forever()

if __name__ == '__main__':
    main()


# by far the easiest after js