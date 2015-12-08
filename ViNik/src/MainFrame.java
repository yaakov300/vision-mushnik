import java.awt.EventQueue; 
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;

public class MainFrame {

	private JFrame frame;
	private JLabel lblNewLabel;
	private BufferedImage image;
	int[][] imageArrayint;

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//Create the application.	
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 863, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//open Button
		final JButton btnOfenFile = new JButton("Open File");
		//listener of open file button 
		btnOfenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser fc = new JFileChooser();//create a file open dialog
				FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());//filter for image only "jpg jpeg bmp png wbmp gif"
				fc.addChoosableFileFilter(imageFilter);
				fc.setCurrentDirectory(new File(System.getProperty("user.home")));//the folder that is open
				fc.setAcceptAllFileFilterUsed(true);
				if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
				{

					lblNewLabel.setText(fc.getSelectedFile().getAbsolutePath());
					try
					{
						image = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));
					}catch(IOException e)
					{
						System.out.println("ERROR read image");
					}
					imageArrayint = convertTo2DWithoutUsingGetRGB(image);
				}
			}
		});

		//properties of stuff
		btnOfenFile.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnOfenFile);

		lblNewLabel = new JLabel("No  file chosen");
		lblNewLabel.setBounds(126, 15, 711, 14);
		frame.getContentPane().add(lblNewLabel);
	}

	//this function return array[][] after convert from BufferedImage image: 
	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) 
				{
					col = 0;
					row++;
				}
			}
		}

		return result;
	}
	
}

