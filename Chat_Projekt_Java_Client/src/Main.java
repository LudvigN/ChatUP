import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args)
	{
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your name: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);

		if(selectedOption == 0)
		{
		    String text = txt.getText();
		    
		    if(!text.equals("")){
				Client client = new Client("127.0.0.1", text);
				client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.startRunning();	
		    }
		    
		}
		

		
	}
	
	
}
