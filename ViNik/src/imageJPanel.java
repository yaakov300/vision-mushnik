import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class imageJPanel extends JPanel{

	int[][] matrixImage;
	StraightLine straightLine = null;

	public imageJPanel(int[][] image) 
	{
		matrixImage = image;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		drawMatrixImage(g2d);
		setBorder(BorderFactory.createLineBorder(Color.black));
		if(straightLine != null)
		{
			drawStraightLine(g2d);
		}

	}

	public boolean drawMatrixImage(Graphics2D g2d)
	{	
		int col = matrixImage.length;
		int row = matrixImage[0].length;

		for (int i = 0; i < col; i++)
		{ 
			for (int j = 0; j < row; j++) 
			{
				if(matrixImage[i][j] != -1)//the pixel is not white
				{
					g2d.setColor(Color.BLACK);
					g2d.drawLine(j, i, j, i);
				}
				else
				{
					//g2d.setColor(Color.WHITE);
					//g2d.drawLine(j, i, j, i);
				}
			}
		}

		return false;
	}


	public void drawStraightLine(Graphics2D g2d)
	{
		int h = getHeight();
		int w = getWidth();
		g2d.setColor(Color.GREEN);

		for (int i = 0; i < h; i++) 
		{
			for (int j = 0; j < w; j++) 
			{
				if(i >= (j *  straightLine.getM()[1]) + straightLine.getN()[1] - 3 &&
						i <= (j *  straightLine.getM()[1]) + straightLine.getN()[1] + 3)
				{			
					
					g2d.drawLine(j, i, j, i);
				}
				if(i >= (j *  straightLine.getM()[0]) + straightLine.getN()[0] - 3 &&
						i <= (j *  straightLine.getM()[0]) + straightLine.getN()[0] + 3)
				{			
					
					g2d.drawLine(j, i, j, i);
				}
			}
		}


		
	}


	//Seters
	public void setStraightLine(StraightLine straightLine) 
	{
		this.straightLine = straightLine;
	}
}
