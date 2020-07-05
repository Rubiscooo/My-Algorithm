#include <iostream>
#include <cstring>
#include <cstdio>
#include <cstdlib>
#include <cmath>

using namespace std ;

int euler_phi ( int x ) {
    int i, j, k, ans = x, lim = (int)sqrt(x+0.5) ;
    for ( i = 2 ; i <= lim ; i ++ )
        if ( x % i == 0 ) {
            ans = ans / i * ( i-1 ) ;
            while ( x%i==0 ) x /= i ;
        }
    if ( x > 1 ) ans = ans / x * (x-1) ;
    //如果剩下的x也是原来x的因数
    return ans ;
}

int main() {
    int i, j, k, n, m ;
    while ( scanf ( "%d", &n ) != EOF )
        printf ( "Euler_phi(%d) = %d\n", n, euler_phi(n) ) ;
    return 0 ;
}
