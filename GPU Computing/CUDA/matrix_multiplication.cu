#include "cuda_runtime.h"
#include <stdio.h>
#include <stdlib.h>
#include <ctime>
int N = 0;

__global__
void dot( int *a, int ra, int ca, int *b, int rb, int cb, int *c )
{
   // shared memory for reesults of multiplication
   __shared__ int temp[N];

   // __shared__ int globalSum;
   
   for( int i = 0;i<ra;i++)
   {
		for(int j=0;j<cb;j++)
		{
			temp[threadIdx.x] = a[i*ca + threadIdx.x] * b[threadIdx.x*cb + j];
			__syncthreads( );


		   // Thread 0 sums the pairwise products
		   if ( threadIDx.x == 0 )
		   {
			  int sum = 0;
		 
			  for( int i = 0; i < N; ++i )
				 sum += temp[i];

			  c[i*cb + j] = sum;

		   }
		}
   }

   
   
}
 

void cpu_matrix_multiplication(int *A, int *B, int *C, int ra, int ca, int rb, int cb)
{
	for(int i=0 ; i < ra;i++)
	{
		for( int j=0; j < cb; j++)
		{
			C[i*cb + j] = 0;
			for(int k=0; k<ca;k++)
			{
				C[i*cb + j] += A[i*ca + k] + B[ k * cb + j];
			}
		}
	}
} 
 
 
 
void fillMatrix(int *mat, int rows, int columns)
{
	for( int i = 0; i < rows; i++ )
    {
		for(int j = 0; j < columns ; j++)
		{
			mat[i*columns + j] = rand( ) % 10;
		}
    }
}
 
void printMatrix(int *mat,int rows, int columns)
{
	for( int i = 0; i < rows; i++ )
    {
		for(int j = 0; j < columns ; j++)
		{
			printf("%d ",mat[i*columns + j]);
		}
		printf("\n");
    }
}
 
int main( void )
{

   int *a, *b, *c;			// host copies of a, b, c
   int *dev_a, *dev_b, *dev_c;		// device copies of a, b, c
   
   /*  My Code */
	//int size = N * sizeof( int );	// space for N integers

	
	int ra = 3;
	int ca = 3;
	
	N = ca;
	
	int a_size = ra * ca * sizeof(int);
	
	int rb = 3;
	int cb = 3;
	
	int b_size = rb * cb * sizeof(int);
	
	int c_size = ra * cb * sizeof(int); 
   
   
   

   // allocate host copies of a, b, c
   a = ( int * ) malloc( a_size );
   b = ( int * ) malloc( b_size );
   c = ( int * ) calloc( c_size );
   d = ( int * ) calloc( c_size );

   // allocate device copies of a, b, c
   cudaMalloc( ( void** ) &dev_a, a_size );
   cudaMalloc( ( void** ) &dev_b, b_size );
   cudaMalloc( ( void** ) &dev_c, c_size);

   // initialize host copies of a, b
   fillMatrix(a,ra,ca);
   fillMatrix(b,rb,cb);

   //printing Matrices
   printf("Matrix A: \n");
   printMatrix(a,ra,ca);
   printf("\n");
   
   printf("Matrix B: \n");
   printMatrix(b,rb,cb);
   printf("\n");
   
   // copy inputs to device
   cudaMemcpy( dev_a, a, a_size, cudaMemcpyHostToDevice );
   cudaMemcpy( dev_b, b, b_size, cudaMemcpyHostToDevice );

   // launch add( ) kernel on GPU, passing parameters
   dot<<< 1, N >>>( dev_a, ra, ca, dev_b, rb, cb, dev_c );

   // copy device result back to host copy of c
   cudaMemcpy( c, dev_c, sizeof( int ), cudaMemcpyDeviceToHost );
   
   printf("Matrix C: \n");
   printMatrix(c,ra,cb);
   printf("\n");
   
   cpu_matrix_multiplication(a,b,d,ra,ca,rb,cb);

   printf("Matrix D: \n");
   printMatrix(d,ra,cb);
   printf("\n");
   
   
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
