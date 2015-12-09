import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class imageJPanel extends JPanel{
	
	int[][] matrixImage;
	
	public imageJPanel(int[][] image) 
	{
	matrixImage = image;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		drawMatrixImage(g2d); 
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
					g2d.setColor(Color.WHITE);
					g2d.drawLine(j, i, j, i);
				}
			}
		}
		
		
		
		return false;
	}

}
