import java.awt.event.MouseListener;

import javax.swing.JComponent;

import java.awt.event.MouseEvent;



public class PanelGame extends JComponent {
    //instance variable
    int mat[][] = new int[3][3];
    private int mouseX , mouseY;
    private int width , height;
    private boolean turn = true;
    private int cnt = 0;

    
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
}
