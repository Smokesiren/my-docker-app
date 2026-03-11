int main() {
    int i, j, n;
    int a[n][n];
#pragma scop
    for(i=2; i<=n; i++) {
        for(j=1; j<=n; j++) {
            a[i][j] = a[2*i-2][j-1];
        }
    }
#pragma endscop
    return 0;
}