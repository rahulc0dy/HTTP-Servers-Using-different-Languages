#ifndef HTTP_TCPSERVER_INCLUDE
#define HTTP_TCPSERVER_INCLUDE

#include <stdio.h>
#include <winsock.h>
#include <stdlib.h>
#include <string>

struct sockaddr_in
{
    short sin_family;        // e.g. AF_INET
    unsigned short sin_port; // e.g. htons(8080)
    struct in_addr sin_addr; // see struct in_addr, below
    char sin_zero[8];        // zero this if you want to
};
struct in_addr
{
    unsigned long s_addr;
};

int bind(int sock, const struct sockaddr *addr, socklen_t addrlen);

namespace http
{
    class TcpServer
    {
    private:
        /* data */
        std::string m_ip_address;
        int m_port;
        SOCKET m_socket;
        SOCKET m_new_socket;
        long m_incomingMessage;
        struct sockaddr_in m_sockAddress;
        int m_sockAddress_len;
        std::string m_serverMessage;
        WSADATA m_wsaData;
        int startServer();
        void closeServer();

    public:
        TcpServer(std::string ip_address, int port);
        ~TcpServer();
    };

} // namespace http

#endif