import javax.swing.*;
import java.awt.*;

public class Main 
{
	public static void main(String[] args)
	{
		Game game = new Game();
		game.display();
		
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(500, 650);
		frame.setTitle("Game Window");
		System.out.println("Working...");
		frame.setBackground(new Color (150,150,20));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}