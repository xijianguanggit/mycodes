#include <stdio.h>
#include <netdb.h>
#define NI_MAXHOST 1025
int main()
{
        struct hostent *he;

        char ipaddr[40]={0};

        char hostname[NI_MAXHOST+1];

          hostname[0] = '\0';

        if (::gethostname(hostname, NI_MAXHOST)) {
            /* Something went wrong, maybe networking is not setup? */
            strcpy(hostname, "localhost");
         }
        he = gethostbyname(hostname);

        printf("%d-handle-gethostbyname\n",he);
        printf("hostname=%s\n",hostname);
   /*
    char destIP[128];
    char **phe = NULL;
    for( phe=he->h_addr_list ; NULL != *phe ; ++phe)
    {
        inet_ntop(he->h_addrtype,*phe,destIP,sizeof(destIP));
        printf("addr:%s\n",destIP);

    }
    */
}

