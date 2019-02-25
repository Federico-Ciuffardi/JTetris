import java.awt.event.KeyEvent;
public class Engine implements Runnable{
	public static int defaultDelay = 60;
	private int delay = defaultDelay;
	public static boolean isRunning = true;
	private int i = 0;
	private int j = 0;
	public GridManager gridManager = new GridManager();
	private Display display;
	public static boolean[] activeKeys = new boolean[223];
	public static boolean alreadyExec = false;
	public void run(){
		display = new Display();
        Sound.background.loop();
		while(true){
			if (isRunning){
				i++;
				j++;
					if (activeKeys[KeyEvent.VK_DOWN])
						delay = 3;
					else
						delay = defaultDelay;
				if (!alreadyExec){
					if (activeKeys[KeyEvent.VK_R]){
						gridManager.clearGrid();
						display.repaintGame();
					}
					if (activeKeys[KeyEvent.VK_Z] && gridManager.active){
						gridManager.rotate2();
						display.repaintGame();
						Sound.rotate.play();
					}
					if (activeKeys[KeyEvent.VK_X] && gridManager.active){
						gridManager.rotate1();
						display.repaintGame();
						Sound.rotate.play();
					}
				}
				if (j % 5 == 0){
					if (gridManager.active){
						if (activeKeys[KeyEvent.VK_RIGHT]&& !activeKeys[KeyEvent.VK_DOWN]){
							gridManager.moveRight();
							display.repaintGame();
						}
						if (activeKeys[KeyEvent.VK_LEFT]&& !activeKeys[KeyEvent.VK_DOWN]){
							gridManager.moveLeft();
							display.repaintGame();
						}
					}
					j=0;
				}
				if (i % delay == 0){
					gridManager.update();
					display.repaintGame();
					i = 0;
				}
			}
			if (!alreadyExec){
				if (activeKeys[KeyEvent.VK_ESCAPE])
					if (isRunning){
						isRunning = false;
						display.repaintGame();
					}
					else
						isRunning = true;
				alreadyExec = true;
			}
			try{Thread.sleep(10);}catch(InterruptedException e){e.printStackTrace();}
		}

	}
}
