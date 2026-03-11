#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define ceild(n,d) ceil(((double)(n))/((double)(d)))
#define floord(n,d) floor(((double)(n))/((double)(d)))
#define max(x,y) ((x) > (y)? (x) : (y))
#define min(x,y) ((x) < (y)? (x) : (y))

int main() {
    int n = 100;
    int a_org[100][100];
    int a_doc[100][100];
    int i, j, c0, c1;

    // Inicjalizacja
    for(i=0; i<n; i++) {
        for(j=0; j<n; j++) {
            a_org[i][j] = i+j;
            a_doc[i][j] = i+j;
        }
    }

    // Oryginalna pętla
    for(i=2; i<=n; i++) {
        for(j=1; j<=n; j++) {
            a_org[i][j] = a_org[2*i-2][j-1];
        }
    }

    // Przekształcona pętla (docelowa)
    for(c0=6; c0<=5*n; c0+=1) {
        _Pragma("openmp parallel for")
        for(c1=max(1, (int)ceild(c0-n, 4)); c1<=min(n, (int)floord(c0-2, 4)); c1+=1) {
            a_doc[(c0-4*c1)][c1] = a_doc[2*(c0-4*c1)-2][c1-1];
        }
    }

    // Weryfikacja
    int error = 0;
    for(i=2; i<=n; i++) {
        for(j=1; j<=n; j++) {
            if(a_org[i][j] != a_doc[i][j]) error = 1;
        }
    }

    if(error) printf("Blad\n");
    else printf("Zgodne\n");

    return 0;
}