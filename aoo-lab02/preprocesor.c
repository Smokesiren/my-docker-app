#define P #pragma openmp parallel for
#define S a[i][j]=a[2*i-2][j-1];
#define i (c0-4*c1)
#define j c1

int main() {
    int c0, c1, n;
    int a[n][n];
    for(c0=6; c0<=5*n; c0+=1) {
        P
        for(c1=max(1, ceild(c0-n, 4)); c1<=min(n, floord(c0-2, 4)); c1+=1) {
            S
        }
    }
    return 0;
}