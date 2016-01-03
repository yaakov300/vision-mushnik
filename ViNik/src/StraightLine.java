
public class StraightLine {

	private int[][] imageArray;//black pixel array 
	private double[] m;
	private int sumX = 0, sumY = 0;
	private double[] n;

	public StraightLine(int[][] matrixImage) 
	{	
		//initialization array pixel
		int numBlackPixel = countBlackPixel(matrixImage);
		imageArray = new int[numBlackPixel][2];	
		matrixToBlackArray(imageArray, matrixImage);

		//calculate line (shmaya algorithm)
		int[][] imageArrayMoveToCenterOfMass = moveToCenterOfMass(imageArray);
		int gamma = gammaCalculation(imageArrayMoveToCenterOfMass);
		int delta = deltaCalculation(imageArrayMoveToCenterOfMass);
		m = parametersCalculation(gamma, delta);
		n = new double[2];
		n[0] = sumY - m[0] * sumX;
		n[1] = sumY - m[1] * sumX;
		System.out.println("sumX = " + sumX + ", sumY = " + sumY );
		System.out.println("gamma = " + gamma + ", delta = " + delta );
		System.out.println("m[0] = " + m[0] + " m[1] = " + m[1] + ", n[0] = " + n[0] + ", n[1] = " + n[1] );
	}


	//Geters:
	public double[] getM() //get Slop Line 
	{
		return m; //the minimum m.
	}

	public double getSumX() //get Slop Line 
	{
		return sumX; //the minimum m.
	}

	public double getSumY()
	{
		return sumY;
	}
	
	public double[] getN()
	{
		return n;
	}


	//This function return number of black pixel.
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


	//This function initialize the black pixel array.
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


	//This function move all the black array pixel to center ov mass.
	public int[][] moveToCenterOfMass(int[][] imageArray)
	{	

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


	//Calculate gamma = x'*y'
	public int gammaCalculation(int[][] imageArrayMoveToCenterOfMass)
	{
		int gamma = 0;
		for (int i = 0; i < imageArrayMoveToCenterOfMass.length; i++) {
			gamma += (imageArrayMoveToCenterOfMass[i][0] * imageArrayMoveToCenterOfMass[i][1]); //x'*y' 
		}
		return gamma; 
	}


	//Calculate delta = x'^2 - y'^2
	public int deltaCalculation(int[][] imageArrayMoveToCenterOfMass)
	{
		int delta = 0;
		for (int i = 0; i < imageArrayMoveToCenterOfMass.length; i++) {
			delta += Math.pow(imageArrayMoveToCenterOfMass[i][1], 2) - Math.pow(imageArrayMoveToCenterOfMass[i][0], 2); //x'^2 - y'^2 
		}
		return delta; 
	}


	//Calculate Straight line and return slops in double[] (double[0] = minimum slop, double[0] = maximum slop) 
	public double[] parametersCalculation(int gamma, int delta)
	{
		double a1, a2;
		double[] m = new double[2];
		if(gamma != 0)
		{		
			//This case includes delta = 0 ==> a12 = +-1
			double root = Math.pow(delta, 2) + (4 * Math.pow(gamma, 2));
			
			a1 = (delta +  Math.pow(root,0.5)) / (2 * gamma);
			a2 = (delta -  Math.pow(root,0.5)) / (2 * gamma);
			System.out.println("a1 = " + a1 );//TODO
			System.out.println("a2 = " + a2 );//TODO
			m[0] = Math.min(a1, a2);
			m[1] = Math.max(a1, a2);
			return m;
		}
		else //gamma = 0
		{
			if(delta != 0)
			{
				m[0] = 0;
				m[1] = 0;
				return m;
			}
			else//gamma = 0 and delta = o ==> Symmetrical shape.
			{
				return null;
			}
		}

	}
	

}
