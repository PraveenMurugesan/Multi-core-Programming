#include <stdio.h>
 
#define N 512
 
__global__
void add( int *a, int *b, int *c )
{
   c[blockIdx.x] = a[blockIdx.x] + b[blockIdx.x];
}
 
int main( void )
{

   int *a, *b, *c;			// host copies of a, b, c
   int *dev_a, *dev_b, *dev_c;		// device copies of a, b, c
   int size = N * sizeof( int );	// space for N integers


   // allocate host copies of a, b, c
   a = ( int * ) malloc( size );
   b = ( int * ) malloc( size );
   c = ( int * ) malloc( size );

   // allocate device copies of a, b, c
   cudaMalloc( ( void** ) &dev_a, size );
   cudaMalloc( ( void** ) &dev_b, size );
   cudaMalloc( ( void** ) &dev_c, size );

   // initialize host copies of a, b
   for( int i = 0; i < N; ++i )
   {
      a[i] = rand( ) % 100;
      b[i] = rand( ) % 100;
   }

   // copy inputs to device
   cudaMemcpy( dev_a, &a, size, cudaMemcpyHostToDevice );
   cudaMemcpy( dev_b, &b, size, cudaMemcpyHostToDevice );

   // launch add( ) kernel on GPU, passing parameters
   add<<< N, 1 >>>( dev_a, dev_b, dev_c );

   // copy device result back to host copy of c
   cudaMemcpy( &c, dev_c, size, cudaMemcpyDeviceToHost );


   // deallocate host copies of a, b, c
   free( a );
   free( b );
   free( c );

   // deallocate device copies of a, b, c
   cudaFree( dev_a ); 
   cudaFree( dev_b );
   cudaFree( dev_c );

   return 0;

}
