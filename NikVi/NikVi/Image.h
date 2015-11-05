#ifndef IMAGE_H
#define IMAGE_H

class ImageMatrix
{
public:
	ImageMatrix();
	ImageMatrix(int numRows, int numCols, int grayLevels);
	~ImageMatrix();
	ImageMatrix(const ImageMatrix& oldImage);
	void operator=(const ImageMatrix&);
	void setImageInfo(int numRows, int numCols, int maxVal);
	void getImageInfo(int &numRows, int &numCols, int &maxVal);
	int getPixelVal(int row, int col);
	void setPixelVal(int row, int col, int value);
	bool inBounds(int row, int col);
	void getSubImage(int upperLeftRow, int upperLeftCol,
		int lowerRightRow, int lowerRightCol, ImageMatrix& oldImage);
	int meanGray();
	void enlargeImage(int value, ImageMatrix& oldImage);
	void shrinkImage(int value, ImageMatrix& oldImage);
	void reflectImage(bool flag, ImageMatrix& oldImage);
	void translateImage(int value, ImageMatrix& oldImage);
	/*
	r' = r + t
	c' = c + t
	*/
	void rotateImage(int theta, ImageMatrix& oldImage);
	ImageMatrix operator+(const ImageMatrix &oldImage);
	ImageMatrix operator-(const ImageMatrix& oldImage);
	void negateImage(ImageMatrix& oldImage);
	
	//this functions for reed the image file and init the image array2d
	int readImage(char fname[], ImageMatrix& image);
	int readImageHeader(char fname[], int& N, int& M, int& Q, bool& type);
	int writeImage(char fname[], ImageMatrix& image);

private:
	int N; // number of rows
	int M; // number of columns
	int Q; // number of gray levels
	int **pixelVal;
};

#endif
