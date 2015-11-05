/*
Christopher Ginac

image.cpp
*/

#include <stdlib.h>
#include <iostream>
#include <fstream>
#include "Image.h"
#include <cmath>
using namespace std;

ImageMatrix::ImageMatrix()
/* Creates an ImageMatrix 0x0 */
{
	N = 0;
	M = 0;
	Q = 0;

	pixelVal = NULL;
}

ImageMatrix::ImageMatrix(int numRows, int numCols, int grayLevels)
/* Creates an ImageMatrix of numRows x numCols and creates the arrays for it*/
{

	N = numRows;
	M = numCols;
	Q = grayLevels;

	pixelVal = new int *[N];
	for (int i = 0; i < N; i++)
	{
		pixelVal[i] = new int[M];
		for (int j = 0; j < M; j++)
			pixelVal[i][j] = 0;
	}
}

ImageMatrix::~ImageMatrix()
/*destroy ImageMatrix*/
{
	N = 0;
	M = 0;
	Q = 0;

	for (int i = 0; i < N; i++)
		delete pixelVal[N];

	delete pixelVal;
}

ImageMatrix::ImageMatrix(const ImageMatrix& oldImage)
/*copies oldImage into new ImageMatrix object*/
{
	N = oldImage.N;
	M = oldImage.M;
	Q = oldImage.Q;

	pixelVal = new int*[N];
	for (int i = 0; i < N; i++)
	{
		pixelVal[i] = new int[M];
		for (int j = 0; j < M; j++)
			pixelVal[i][j] = oldImage.pixelVal[i][j];
	}
}

void ImageMatrix::operator=(const ImageMatrix& oldImage)
/*copies oldImage into whatever you = it to*/
{
	N = oldImage.N;
	M = oldImage.M;
	Q = oldImage.Q;

	pixelVal = new int*[N];
	for (int i = 0; i < N; i++)
	{
		pixelVal[i] = new int[M];
		for (int j = 0; j < M; j++)
			pixelVal[i][j] = oldImage.pixelVal[i][j];
	}
}

void ImageMatrix::setImageInfo(int numRows, int numCols, int maxVal)
/*sets the number of rows, columns and graylevels*/
{
	N = numRows;
	M = numCols;
	Q = maxVal;
}

void ImageMatrix::getImageInfo(int &numRows, int &numCols, int &maxVal)
/*returns the number of rows, columns and gray levels*/
{
	numRows = N;
	numCols = M;
	maxVal = Q;
}

int ImageMatrix::getPixelVal(int row, int col)
/*returns the gray value of a specific pixel*/
{
	return pixelVal[row][col];
}


void ImageMatrix::setPixelVal(int row, int col, int value)
/*sets the gray value of a specific pixel*/
{
	pixelVal[row][col] = value;
}

bool ImageMatrix::inBounds(int row, int col)
/*checks to see if a pixel is within the ImageMatrix, returns true or false*/
{
	if (row >= N || row < 0 || col >= M || col < 0)
		return false;
	//else
	return true;
}

void ImageMatrix::getSubImage(int upperLeftRow, int upperLeftCol, int lowerRightRow,
	int lowerRightCol, ImageMatrix& oldImage)
	/*Pulls a sub ImageMatrix out of oldImage based on users values, and then stores it
	in oldImage*/
{
	int width, height;

	width = lowerRightCol - upperLeftCol;
	height = lowerRightRow - upperLeftRow;

	ImageMatrix tempImage(height, width, Q);

	for (int i = upperLeftRow; i < lowerRightRow; i++)
	{
		for (int j = upperLeftCol; j < lowerRightCol; j++)
			tempImage.pixelVal[i - upperLeftRow][j - upperLeftCol] = oldImage.pixelVal[i][j];
	}

	oldImage = tempImage;
}

int ImageMatrix::meanGray()
/*returns the mean gray levels of the Image*/
{
	int totalGray = 0;

	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < M; j++)
			totalGray += pixelVal[i][j];
	}

	int cells = M * N;

	return (totalGray / cells);
}

void ImageMatrix::enlargeImage(int value, ImageMatrix& oldImage)
/*enlarges Image and stores it in tempImage, resizes oldImage and stores the
larger image in oldImage*/
{
	int rows, cols, gray;
	int pixel;
	int enlargeRow, enlargeCol;

	rows = oldImage.N * value;
	cols = oldImage.M * value;
	gray = oldImage.Q;

	ImageMatrix tempImage(rows, cols, gray);

	for (int i = 0; i < oldImage.N; i++)
	{
		for (int j = 0; j < oldImage.M; j++)
		{
			pixel = oldImage.pixelVal[i][j];
			enlargeRow = i * value;
			enlargeCol = j * value;
			for (int c = enlargeRow; c < (enlargeRow + value); c++)
			{
				for (int d = enlargeCol; d < (enlargeCol + value); d++)
				{
					tempImage.pixelVal[c][d] = pixel;
				}
			}
		}
	}

	oldImage = tempImage;
}

void ImageMatrix::shrinkImage(int value, ImageMatrix& oldImage)
/*Shrinks image as storing it in tempImage, resizes oldImage, and stores it in
oldImage*/
{
	int rows, cols, gray;

	rows = oldImage.N / value;
	cols = oldImage.M / value;
	gray = oldImage.Q;

	ImageMatrix tempImage(rows, cols, gray);

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < cols; j++)
			tempImage.pixelVal[i][j] = oldImage.pixelVal[i * value][j * value];
	}
	oldImage = tempImage;
}

void ImageMatrix::reflectImage(bool flag, ImageMatrix& oldImage)
/*Reflects the Image based on users input*/
{
	int rows = oldImage.N;
	int cols = oldImage.M;
	ImageMatrix tempImage(oldImage);
	if (flag == true) //horizontal reflection
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
				tempImage.pixelVal[rows - (i + 1)][j] = oldImage.pixelVal[i][j];
		}
	}
	else //vertical reflection
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
				tempImage.pixelVal[i][cols - (j + 1)] = oldImage.pixelVal[i][j];
		}
	}

	oldImage = tempImage;
}

void ImageMatrix::translateImage(int value, ImageMatrix& oldImage)
/*translates image down and right based on user value*/
{
	int rows = oldImage.N;
	int cols = oldImage.M;
	int gray = oldImage.Q;
	ImageMatrix tempImage(N, M, Q);

	for (int i = 0; i < (rows - value); i++)
	{
		for (int j = 0; j < (cols - value); j++)
			tempImage.pixelVal[i + value][j + value] = oldImage.pixelVal[i][j];
	}

	oldImage = tempImage;
}

void ImageMatrix::rotateImage(int theta, ImageMatrix& oldImage)
/*based on users input and rotates it around the center of the image.*/
{
	int r0, c0;
	int r1, c1;
	int rows, cols;
	rows = oldImage.N;
	cols = oldImage.M;
	ImageMatrix tempImage(rows, cols, oldImage.Q);

	float rads = (theta * 3.14159265) / 180.0;

	r0 = rows / 2;
	c0 = cols / 2;

	for (int r = 0; r < rows; r++)
	{
		for (int c = 0; c < cols; c++)
		{
			r1 = (int)(r0 + ((r - r0) * cos(rads)) - ((c - c0) * sin(rads)));
			c1 = (int)(c0 + ((r - r0) * sin(rads)) + ((c - c0) * cos(rads)));

			if (inBounds(r1, c1))
			{
				tempImage.pixelVal[r1][c1] = oldImage.pixelVal[r][c];
			}
		}
	}

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < cols; j++)
		{
			if (tempImage.pixelVal[i][j] == 0)
				tempImage.pixelVal[i][j] = tempImage.pixelVal[i][j + 1];
		}
	}
	oldImage = tempImage;
}

ImageMatrix ImageMatrix::operator+(const ImageMatrix &oldImage)
/*adds images together, half one image, half the other*/
{
	ImageMatrix tempImage(oldImage);

	int rows, cols;
	rows = oldImage.N;
	cols = oldImage.M;

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < cols; j++)
			tempImage.pixelVal[i][j] = (pixelVal[i][j] + oldImage.pixelVal[i][j]) / 2;
	}

	return tempImage;
}

ImageMatrix ImageMatrix::operator-(const ImageMatrix& oldImage)
/*subtracts images from each other*/
{
	ImageMatrix tempImage(oldImage);

	int rows, cols;
	rows = oldImage.N;
	cols = oldImage.M;
	int tempGray = 0;

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < cols; j++)
		{

			tempGray = abs(pixelVal[i][j] - oldImage.pixelVal[i][j]);
			if (tempGray < 35)// accounts for sensor flux
				tempGray = 0;
			tempImage.pixelVal[i][j] = tempGray;
		}

	}

	return tempImage;
}

void ImageMatrix::negateImage(ImageMatrix& oldImage)
/*negates ImageMatrix*/
{
	int rows, cols, gray;
	rows = N;
	cols = M;
	gray = Q;

	ImageMatrix tempImage(N, M, Q);

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < cols; j++)
			tempImage.pixelVal[i][j] = -(pixelVal[i][j]) + 255;
	}

	oldImage = tempImage;
}


int ImageMatrix::readImage(char fname[], ImageMatrix& image)
{
	int i, j;
	int N, M, Q;
	unsigned char *charImage;
	char header[100], *ptr;
	ifstream ifp;

	ifp.open(fname, ios::in | ios::binary);

	if (!ifp)
	{
		cout << "Can't read image: " << fname << endl;
		exit(1);
	}

	// read header

	ifp.getline(header, 100, '\n');
	if ((header[0] != 80) || (header[1] != 53))
	{
		cout << "Image " << fname << " is not PGM" << endl;
		exit(1);
	}

	ifp.getline(header, 100, '\n');
	while (header[0] == '#')
		ifp.getline(header, 100, '\n');

	M = strtol(header, &ptr, 0);
	N = atoi(ptr);

	ifp.getline(header, 100, '\n');
	Q = strtol(header, &ptr, 0);

	charImage = (unsigned char *) new unsigned char[M*N];

	ifp.read(reinterpret_cast<char *>(charImage), (M*N)*sizeof(unsigned char));

	if (ifp.fail())
	{
		cout << "Image " << fname << " has wrong size" << endl;
		exit(1);
	}

	ifp.close();

	//
	// Convert the unsigned characters to integers
	//

	int val;

	for (i = 0; i<N; i++)
		for (j = 0; j<M; j++)
		{
			val = (int)charImage[i*M + j];
			image.setPixelVal(i, j, val);
		}

	delete[] charImage;


	return (1);

}


int ImageMatrix::readImageHeader(char fname[], int& N, int& M, int& Q, bool& type)
{
	int i, j;
	unsigned char *charImage;
	char header[100], *ptr;
	ifstream ifp;

	ifp.open(fname, ios::in | ios::binary);

	if (!ifp)
	{
		//cout << "Can't read image: " << fname << endl;
		//exit(1);
	}

	// read header

	type = false; // PGM

	ifp.getline(header, 100, '\n');
	if ((header[0] == 80) && (header[1] == 53))
	{
		type = false;
	}
	else if ((header[0] == 80) && (header[1] == 54))
	{
		type = true;
	}
	else
	{
		//cout << "ImageMatrix " << fname << " is not PGM or PPM" << endl;
		//exit(1);
	}

	ifp.getline(header, 100, '\n');
	while (header[0] == '#')
		ifp.getline(header, 100, '\n');

	M = strtol(header, &ptr, 0);
	N = atoi(ptr);

	ifp.getline(header, 100, '\n');

	Q = strtol(header, &ptr, 0);

	ifp.close();

	return(1);
}

int ImageMatrix::writeImage(char fname[], ImageMatrix& image)
{
	int i, j;
	int N, M, Q;
	unsigned char *charImage;
	ofstream ofp;

	image.getImageInfo(N, M, Q);

	charImage = (unsigned char *) new unsigned char[M*N];

	// convert the integer values to unsigned char

	int val;

	for (i = 0; i<N; i++)
	{
		for (j = 0; j<M; j++)
		{
			val = image.getPixelVal(i, j);
			charImage[i*M + j] = (unsigned char)val;
		}
	}

	ofp.open(fname, ios::out | ios::binary);

	if (!ofp)
	{
		cout << "Can't open file: " << fname << endl;
		exit(1);
	}

	ofp << "P5" << endl;
	ofp << M << " " << N << endl;
	ofp << Q << endl;

	ofp.write(reinterpret_cast<char *>(charImage), (M*N)*sizeof(unsigned char));

	if (ofp.fail())
	{
		cout << "Can't write image " << fname << endl;
		exit(0);
	}

	ofp.close();

	delete[] charImage;

	return(1);

}
