import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.BasicStroke;

public class Display{
	Color orange = new Color(255,165,0);
	Color purple = new Color(128,0,128);
	private JFrame window = new JFrame();
	private JPanel mainPanel = new JPanel(){
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.black);
			g2.setFont(new Font("Impact", Font.PLAIN, 18));
			g2.drawString("HIGHSCORE: " + GridManager.highScore,gamePanelX + gamePanelWidth + 20,50);
			g2.drawString("NEXT: ",gamePanelX + gamePanelWidth + 20,80);
			g2.drawString("LEVEL: " + GridManager.level, 20,50);
			g2.drawString("SCORE: " + GridManager.score, 20,80);
			g2.drawString("LINES: " + GridManager.lines, 20,110);
			g2.drawString("CONTROLS:", 20,600);
			g2.drawString("ARROWS - Move ", 20,630);
			g2.drawString("Z,X    - Rotate", 20,650);
			g2.drawString("R      - Restart", 20,670);
			for(int i = 0; i < 4; i++)
				for(int j = 0; j< 4; j++)
					if (GridManager.nextGrid[i][j].id != 0){
						switch(GridManager.nextGrid[i][j].id){
							case 1: g2.setColor(orange); break;
							case 2: g2.setColor(Color.red);break;
							case 3: g2.setColor(Color.yellow);break;
							case 4: g2.setColor(Color.cyan);break;
							case 5: g2.setColor(Color.blue);break;
							case 6: g2.setColor(purple);break;
							case 7: g2.setColor(Color.green);break;
						}
						g2.fillRect(gamePanelX + gamePanelWidth + 70+j*20,60+i*20,20,20);
						g2.setColor(Color.black);
						g2.drawRect(gamePanelX + gamePanelWidth + 70+j*20,60+i*20,20,20);
					}
									g2.setColor(Color.black);
			Toolkit.getDefaultToolkit().sync();
		}
	};
		private final int windowWidth       = 700;
		private final int windowHeight      = 710;
	private JPanel gamePanel = new JPanel(){//---------------paintComponent
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g){
			super.paint(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(2));
			for(int i = 2; i < GridManager.rows; i++)
				for(int j = 0; j< GridManager.cols; j++)
					if (GridManager.gameGrid[i][j].id != 0){
						switch(GridManager.gameGrid[i][j].id){
							case 1: g2.setColor(orange); break;
							case 2: g2.setColor(Color.red);break;
							case 3: g2.setColor(Color.yellow);break;
							case 4: g2.setColor(Color.cyan);break;
							case 5: g2.setColor(Color.blue);break;
							case 6: g2.setColor(purple);break;
							case 7: g2.setColor(Color.green);break;
						}
						g2.fillRect(3+j*gridSize,3+(i-2)*gridSize,32,32);
						g2.setColor(Color.black);
						g2.drawRect(3+j*gridSize,3+(i-2)*gridSize,32,32);
					}
			/*for(int i = 0; i< 20;i++)
				g2.drawLine(0,i*gridSize,gamePanelWidth,i*gridSize);
			for(int i = 0; i< 20;i++)
				g2.drawLine(i*gridSize,0,i*gridSize,gamePanelHeight);*/
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
			if (!Engine.isRunning){
				g2.setColor(Color.white);
				g2.setFont(new Font("Impact", Font.PLAIN, 64));
				g2.drawString("PAUSED", 70,300);
			}
			Toolkit.getDefaultToolkit().sync();
		}
	};
		private final int gamePanelWidth    = 320;
		private final int gamePanelHeight   = 640;
		private final int gamePanelX        = 190;
		private final int gamePanelY        = 20  ;
		private final int gridSize          = 32 ;

	public Display(){
		window.setTitle("Tetris");
		window.setSize(windowWidth,windowHeight);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(mainPanel);
			mainPanel.addKeyListener(new KeyListener(){
				@Override
				public void keyPressed(KeyEvent e){
					if (!Engine.activeKeys[e.getKeyCode()]){
						Engine.activeKeys[e.getKeyCode()] = true;
						Engine.alreadyExec = false;
					}
				}
				@Override
				public void keyReleased(KeyEvent e){
					Engine.activeKeys[e.getKeyCode()] = false;
				}
				@Override
				public void keyTyped(KeyEvent e){}
			});
			mainPanel.setFocusable(true);
			mainPanel.setBounds(0,0,windowWidth,windowHeight);
			mainPanel.setLayout(null);
			mainPanel.setBackground(new Color(44, 62, 80));
			mainPanel.add(gamePanel);
				gamePanel.setBounds(gamePanelX-3,gamePanelY-3,gamePanelWidth+6,gamePanelHeight+6);
				gamePanel.setBackground(new Color(52, 73, 94));
				gamePanel.setDoubleBuffered(true);
				gamePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		window.setVisible(true);
	}
	public void repaintGame(){
		gamePanel.repaint();
		mainPanel.repaint();
	}
}
