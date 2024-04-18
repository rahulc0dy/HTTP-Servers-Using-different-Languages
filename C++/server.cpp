#include "http_tcpServer.h"

int main(int argc, char const *argv[])
{
    using namespace http;
    TcpServer server = TcpServer("0.0.0.0", 8080);
    SOCKET socket;
    return 0;
}
