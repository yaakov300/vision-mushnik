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
import javax.swing.JLabel;


public class MainFrame {

	private JFrame frame;
	private JLabel lblNewLabel;
	
	
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
}
 