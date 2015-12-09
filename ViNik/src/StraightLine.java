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
	
	
	//this function move all the black array pixel to center ov mass.
	public int[][] moveToCenterOfMass(int[][] imageArray)
	{	
		int sumX = 0, sumY = 0;
		int[][] imageArrayMoveToCenterOfMass = new int[imageArray.length][2];
		
		for (int i = 0; i < imageArray.length; i++) 
		{
			sumX += imageArray[i][0];
			sumY += imageArray[i][1];
		}
		
		sumX = sumX/imageArray.length;
		sumY = sumY/imageArray.length;
		
		for (int i = 0; i < imageArray.length; i++)
		{
			imageArrayMoveToCenterOfMass[i][0] = imageArray[i][0] - sumX;
			imageArrayMoveToCenterOfMass[i][1] = imageArray[i][1] - sumY;
		}
		
		return imageArrayMoveToCenterOfMass;
	}
}
