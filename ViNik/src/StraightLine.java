import java.awt.Color;
import java.util.ArrayList;


public class StraightLine {
	
	int[][] imageArray;//black pixel array 
	
	
	public StraightLine(int[][] matrixImage) 
	{	
		int numBlackPixel = countBlackPixel(matrixImage);
		imageArray = new int[numBlackPixel][2];	
	}
	
	
	//this function return number of black pixel.
	public int countBlackPixel(int[][] matrixImage)
	{	
		int counter = 0;
		int col = matrixImage.length;
		int row = matrixImage[0].length;
		
		for (int i = 0; i < col; i++)
		{
			for (int j = 0; j < row; j++) 
			{
				if(matrixImage[i][j] != -1)//the pixel is not white
				{
					counter++;
				}
				else
				{
					
				}
			}
		}
		return counter;
	}
	
	
	//this function initialize the black pixel array.
	public void matrixToBlackArray(int[][] array, int[][] matrix)
	{
		int counter = 0;
		int col = matrix.length;
		int row = matrix[0].length;
		
		for (int i = 0; i < col; i++)
		{
			for (int j = 0; j < row; j++) 
			{
				if(matrix[i][j] != -1)//the pixel is not white
				{
					array[counter][0] = j;
					array[counter][1] = i;
					counter++;
				}
				else
				{
					
				}
			}
		}
	}
	
}
