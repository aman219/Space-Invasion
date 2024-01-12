import java.awt.event.MouseListener;

import javax.swing.JComponent;

import java.awt.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class PanelGame extends JComponent {
    //instance variable
    private static final long serialVersionUID = 1L;
	private Graphics2D g2;
    private BufferedImage image;
    private Image img;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;
    private int mouseX, mouseY;
    private int mat[][] = new int[3][3];
    private boolean turn = true;
    static int cnt = 0;
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
					}else {
						mat[i][j] = 2;
						turn = true;
					}
					cnt++;
				}
				System.out.println("Clicked at : "+i+" "+j+"mat : "+mat[i][j]);
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
    		if (mat[i][0] == mat[i][1] && mat[i][1] == mat[i][2])
    			if (mat[i][0] != 0)
    				return mat[i][0];
    		if (mat[0][i] == mat[1][i] && mat[1][i] == mat[2][i])
    			if (mat[0][i] != 0)
    				return mat[0][i];
    	}
    	if (mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2])
    		if (mat[0][0] != 0)
				return mat[0][0];
    	if (mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0])
    		if (mat[0][2] != 0)
				return mat[0][2];
    	return 0;
    }
public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        img = new ImageIcon(getClass().getResource("/image/bitmap.png")).getImage();
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
        g2.setColor(new Color(102, 255, 102));
        g2.fillRect(0, 0, width, height);
    }
private int[] winnerCord()
    {
    	int arr[] = new int[4];
    	for (int i=0; i<3; i++)
    	{
    		if (mat[i][0] == mat[i][1] && mat[i][1] == mat[i][2])
    			if (mat[i][0] != 0)
    			{
    				arr[0] = i;
    				arr[1] = 0;
    				arr[2] = i;
    				arr[3] = 2;
    				return arr;
    			}
    		if (mat[0][i] == mat[1][i] && mat[1][i] == mat[2][i])
    			if (mat[0][i] != 0)
    			{
    				arr[0] = 0;
    				arr[1] = i;
    				arr[2] = 2;
    				arr[3] = i;
    				return arr;
    			}
	    	if (mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2])
	    		if (mat[0][i] != 0)
				{
					arr[0] = 0;
					arr[1] = 0;
					arr[2] = 2;
					arr[3] = 2;
					return arr;
				};
	    	if (mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0])
	    		if (mat[0][i] != 0)
				{
					arr[0] = 0;
					arr[1] = 2;
					arr[2] = 2;
					arr[3] = 0;
					return arr;
				}
    	}
    	return arr;
    }

    private void drawGame() {
    	g2.setColor(Color.WHITE);
    	g2.setStroke(new BasicStroke(3.0f));
    	int thickness = 5;
    	int w = width/3;
    	int h = height/3;
    	for (int i=1; i<=2; i++)
    	{
    		g2.fillRect(w*i, 0, thickness, height);
    		g2.fillRect(0, h*i, width, thickness);
    	}
    	
    	for (int i=0; i<3; i++)
    	{
    		for (int j=0; j<3; j++)
    		{
    			if (mat[i][j] == 1) {
    				g2.drawOval(w*i+10, h*j+27, w-15, w-15);
    			}
    			else if (mat[i][j] == 2) {
    				int aspect = 15;
    				g2.drawLine(w*i+20, h*j+20+aspect, (w*i)+w-20, (h*j)+h-20-aspect);
    				g2.drawLine(w*i+20, h*j+h-20-aspect, (w*i)+w-20, h*j+20+aspect);
    			}
    		}
    	}
    	
    	int win = winner();
    	if (win != 0)
    	{
 
    		g2.setColor(new Color(255, 10, 25));
    		Font font = new Font("Serif", Font.PLAIN, 48);
    		g2.setFont(font);
    		int cord[] = winnerCord();
    		
    		g2.drawLine(cord[0]*w+w/2, cord[1]*h+h/2, cord[2]*w+w/2, cord[3]*h+h/2);
    		
    		g2.setColor(new Color(0, 0, 102));
    		g2.drawString(win==1?"player1 Win":"Player2 Win", width/2-110, height/2);
    		start = false;
    	}
    	if (cnt == 9 && win == 0)
    	{
    		g2.setColor(new Color(255, 0, 0));
    		Font font = new Font("Serif", Font.PLAIN, 48);
    		g2.setFont(font);
    		g2.drawString("DRAW", width/2-70, height/2);
    		start = false;
    	}
    	
    	g2.drawImage(img, 0, 0, null);
        
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
