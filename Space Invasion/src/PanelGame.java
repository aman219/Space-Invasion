import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class PanelGame extends JComponent {
    //instance variable
    private static final long serialVersionUID = 1L;
	private Graphics2D g2;
    private BufferedImage image;
    private Image img, p1, p2;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;
    private int mouseX, mouseY;
    private int mat[][] = new int[3][3];
    private boolean turn = true;
    static int cnt = 0;
    private JFrame frame;
    //  Game FPS
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;


    private void initMouse() {
    	addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				int i = mouseX/(width/3);
				int j = mouseY/(height/3);

				if (mat[i][j] == 0)
				{
					if (turn) {
						mat[i][j] = 1;
						turn = false;
						frame.setTitle("Player 2 turn");
					}else {
						mat[i][j] = 2;
						turn = true;
						frame.setTitle("Player 1 turn");
					}
					cnt++;
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
    	});
    }


    private int winner()
    {
    	for (int i=0; i<3; i++)
    	{
    		if (mat[i][0] == mat[i][1] && mat[i][1] == mat[i][2]) {
				if (mat[i][0] != 0) {
					return mat[i][0];
				}
			}
    		if (mat[0][i] == mat[1][i] && mat[1][i] == mat[2][i]) {
				if (mat[0][i] != 0) {
					return mat[0][i];
				}
			}
    	}
    	if (mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2]) {
			if (mat[0][0] != 0) {
				return mat[0][0];
			}
		}
    	if (mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0]) {
			if (mat[0][2] != 0) {
				return mat[0][2];
			}
		}
    	return 0;
    }
    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        img = new ImageIcon(getClass().getResource("/image/GRID.png")).getImage();
        p1 = new ImageIcon(getClass().getResource("/image/O.png")).getImage();
        p2 = new ImageIcon(getClass().getResource("/image/X.png")).getImage();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    long startTime = System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time = System.nanoTime() - startTime;
                    if (time < TARGET_TIME) {
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep);
                    }
                }
            }
        });

        initMouse();
        thread.start();

    }
	private void drawBackground() {
        g2.setColor(new Color(25, 25, 25));
        g2.fillRect(0, 0, width, height);
		g2.drawImage(img, -10, -29, null);
	}
	private int[] winnerCord()
    {
    	int arr[] = new int[4];
    	for (int i=0; i<3; i++)
    	{
    		if (mat[i][0] == mat[i][1] && mat[i][1] == mat[i][2]) {
				if (mat[i][0] != 0)
    			{
    				arr[0] = i;
    				arr[1] = 0;
    				arr[2] = i;
    				arr[3] = 2;
    				return arr;
    			}
			}
    		if (mat[0][i] == mat[1][i] && mat[1][i] == mat[2][i]) {
				if (mat[0][i] != 0)
    			{
    				arr[0] = 0;
    				arr[1] = i;
    				arr[2] = 2;
    				arr[3] = i;
    				return arr;
    			}
			}
	    	if (mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2]) {
				if (mat[0][i] != 0)
				{
					arr[0] = 0;
					arr[1] = 0;
					arr[2] = 2;
					arr[3] = 2;
					return arr;
				}
			}
	    	if (mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0]) {
				if (mat[0][i] != 0)
				{
					arr[0] = 0;
					arr[1] = 2;
					arr[2] = 2;
					arr[3] = 0;
					return arr;
				}
			}
    	}
    	return arr;
    }
	
	public void setJFrame(JFrame f)
	{
		try {
			frame = f;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void drawGame() {
    	g2.setColor(Color.WHITE);
    	g2.setStroke(new BasicStroke(3.0f));
    	int w = width/3;
    	int h = height/3;

    	for (int i=0; i<3; i++)
    	{
    		for (int j=0; j<3; j++)
    		{
    			if (mat[i][j] == 1) {
    				g2.drawImage(p1, w*i+10, h*j+27, null);
    			}
    			else if (mat[i][j] == 2) {
    				g2.drawImage(p2, w*i+10, h*j+27, null);
    			}
    		}
    	}

    	int win = winner();
    	if (win != 0)
    	{

    		g2.setColor(new Color(181, 245, 186));
    		Font font = new Font("Serif", Font.PLAIN, 48);
    		g2.setFont(font);
    		int cord[] = winnerCord();

    		g2.drawLine(cord[0]*w+w/2, cord[1]*h+h/2, cord[2]*w+w/2, cord[3]*h+h/2);

    		g2.setColor(new Color(181, 245, 186));
    		g2.drawString(win==1?"player 1 Win":"Player 2 Win", width/2-130, height/2);
    		frame.setTitle(win==1?"player 1 Win":"Player 2 Win");
    		start = false;
    	}
    	if (cnt == 9 && win == 0)
    	{
    		g2.setColor(new Color(102, 255, 102));
    		Font font = new Font("Serif", Font.PLAIN, 48);
    		g2.setFont(font);
    		g2.drawString("DRAW", width/2-70, height/2);
    		start = false;
    	}



    }

    private void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
}
