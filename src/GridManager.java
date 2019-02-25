import java.util.Random;
import java.awt.event.KeyEvent;
public class GridManager{
	private static Random randomGenerator = new Random();
	private int x = 0;
	private int y = 0;
	public int startY = 0;
	public int startX = 0;
	private int size = 0;
	public static final int cols = 10;
	public static final int rows = 22;
	public static int level= 0;
	public static int lines= 0;
	public static int score =0;
	public static int highScore = 0;
	public int drop = 0;
	public static Block[][] gameGrid = new Block[rows][cols];
	public static Block[][] nextGrid = new Block[4][4];
	public boolean addOnNext = false;
	public boolean active = false;
	public int yLimit;
	public int xLimit;
	public static int color = randomGenerator.nextInt(7) + 1;
	private Save saveLoad = new Save();

	public GridManager(){
		clearGrid();

	}
	public void update(){
		if (active){
			currentFall();
			if (Engine.activeKeys[KeyEvent.VK_DOWN]&&y>1)
				drop += 2;
			else
				drop += 1;
		}else{
			addFigure();
		}


	}

	public void rotate1(){
		if (x>=0 && y>=0 && x + size <= cols && y +size < rows &&canRotate()){
			Block aux = new Block();
			for(int i=0;i<size;i++)
				for(int j=0;j<i;j++){
					aux.id =gameGrid[y+i][x+j].id ;
					aux.falling = gameGrid[y+i][x+j].falling;
					aux.current = gameGrid[y+i][x+j].current;
					gameGrid[y+i][x+j].id = 	gameGrid[y+j][x+i].id ;
					gameGrid[y+i][x+j].falling= gameGrid[y+j][x+i].falling;
					gameGrid[y+i][x+j].current= gameGrid[y+j][x+i].current;
					gameGrid[y+j][x+i].id = aux.id;
					gameGrid[y+j][x+i].falling = aux.falling;
					gameGrid[y+j][x+i].current = aux.falling;
				}
			for(int i=0;i<size;i++)
				for(int j=0;j<size/2;j++){
					aux.id =gameGrid[y+i][x+j].id ;
					aux.falling = gameGrid[y+i][x+j].falling;
					aux.current = gameGrid[y+i][x+j].current;
					gameGrid[y+i][x+j].id = 	gameGrid[y+i][x+(size-j-1)].id ;
					gameGrid[y+i][x+j].falling= gameGrid[y+i][x+(size-j-1)].falling;
					gameGrid[y+i][x+j].current= 	gameGrid[y+i][x+(size-j-1)].current;
					gameGrid[y+i][x+(size-j-1)].id = aux.id;
					gameGrid[y+i][x+(size-j-1)].falling = aux.falling;
					gameGrid[y+i][x+(size-j-1)].current = aux.falling;
				}
		}
	}

	public void rotate2(){
		if (x>=0 && y>=0 && x + size <= cols && y +size < rows &&canRotate()){
			Block aux = new Block();
			for(int i=0;i<size;i++)
				for(int j=0;j<size/2;j++){
					aux.id =gameGrid[y+i][x+j].id ;
					aux.falling = gameGrid[y+i][x+j].falling;
					aux.current = gameGrid[y+i][x+j].current;
					gameGrid[y+i][x+j].id = 	gameGrid[y+i][x+(size-j-1)].id ;
					gameGrid[y+i][x+j].falling= gameGrid[y+i][x+(size-j-1)].falling;
					gameGrid[y+i][x+j].current= 	gameGrid[y+i][x+(size-j-1)].current;
					gameGrid[y+i][x+(size-j-1)].id = aux.id;
					gameGrid[y+i][x+(size-j-1)].falling = aux.falling;
					gameGrid[y+i][x+(size-j-1)].current = aux.falling;
				}
			for(int i=0;i<size;i++)
				for(int j=0;j<i;j++){
					aux.id =gameGrid[y+i][x+j].id ;
					aux.falling = gameGrid[y+i][x+j].falling;
					aux.current = gameGrid[y+i][x+j].current;
					gameGrid[y+i][x+j].id = 	gameGrid[y+j][x+i].id ;
					gameGrid[y+i][x+j].falling= gameGrid[y+j][x+i].falling;
					gameGrid[y+i][x+j].current= gameGrid[y+j][x+i].current;
					gameGrid[y+j][x+i].id = aux.id;
					gameGrid[y+j][x+i].falling = aux.falling;
					gameGrid[y+j][x+i].current = aux.falling;
				}

		}
	}
	public boolean canRotate(){
	int i = y + size -1;
	int j = x;

	boolean res = true;
	while (i >= y&&res){
		while(j< x+size &&res){
			res= (gameGrid[i][j].current||gameGrid[i][j].id == 0);
			j++;
		}
		j = startX;
		i--;
		}
	return res ;
	}

	public boolean checkGameOver(){
		int j = 0;
		while (j < cols && gameGrid[1][j].id ==0){
			j++;
		}
		return j < cols;
	}

	public void checkFullrows(){
		int i = rows-1;
		int j = 0;
		int cont = 0;
		while (i>=2){
			while(j<cols && gameGrid[i][j].id !=0){
				j++;
			}
			if(j>=cols){
					try{

					}catch(Exception e){}
				clearRow(i);
				afterFall(i);
				lines++;
				if (level<8)
					Engine.defaultDelay = 80 - (10 * (level-1));
				else
					Engine.defaultDelay= 5;
				cont ++;
				i++;
		}
		j=0;
		i--;
		}
		switch(cont){
			case 1: score += 40* level;
							Sound.clearLine.play();
			break;
			case 2: score += 100* level;
							Sound.clearLine.play();
			break;
			case 3: score += 300* level;
							Sound.clearLine.play();
			break;
			case 4: score += 1200* level;
							Sound.clearLine.play();
			break;
			default: Sound.fall.play();
		}
		level = lines / 10 + 1;
	}

	public void afterFall(int intialY){
		for (int i = intialY-1; i>= 0; i--)
			for (int j = 0; j <cols; j++)
					gameGrid[i][j].falling =(i < rows-1 && gameGrid[i][j].id !=  0);
		for (int i = intialY; i>= 0; i--)
			for (int j = 0; j <cols; j++)
				if(gameGrid[i][j].falling &&gameGrid[i+1][j].id == 0){
					//System.out.println("|"+i+"-"+j+"| is falling");//debug
					gameGrid[i+1][j].id = gameGrid[i][j].id;
					gameGrid[i][j].falling = false;
					setEmpty(gameGrid[i][j]);
				}
	}

	public void clearRow(int i){


		for(int j = 0;j<cols;j++)
			setEmpty(gameGrid[i][j]);
	}

	public void moveLeft(){
		setStartXY();
		if (y+size < rows)
			yLimit = y+size;
		else
			yLimit = rows-1;
		if (x+size < cols)
			xLimit = x+size;
		else
			xLimit = cols;
		if (canMoveLeft()){
			for (int i = yLimit; i>= startY; i--)
				for (int j = startX; j < xLimit; j++)
					if(gameGrid[i][j].current && j > 0){
							gameGrid[i][j-1].id = gameGrid[i][j].id;
							gameGrid[i][j-1].falling = true;
							gameGrid[i][j-1].current = true;
							setEmpty(gameGrid[i][j]);
					}
				x--;
		}
	}
private boolean canMoveLeft(){
	int i = yLimit;
	int j = startX;

	boolean res = true;
	while (i >= startY&&res){
		while(j< xLimit &&res){
			if (gameGrid[i][j].current)
				res= j>0 && (gameGrid[i][j-1].current||gameGrid[i][j-1].id == 0);
			j++;
		}
		j = startX;
		i--;
	}
	return res ;
}

	public void moveRight(){
		setStartXY();
		if (y+size-1 < rows)
			yLimit = y+size-1;
		else
			yLimit = rows-1;
		if (x+size < cols)
			xLimit = x+size;
		else
			xLimit = cols-1;
		if (canMoveRight()){
			for (int i = yLimit; i>= startY; i--)
				for (int j = xLimit; j >= startX ; j--)
					if(gameGrid[i][j].current && j < cols-1){
							gameGrid[i][j+1].id = gameGrid[i][j].id;
							gameGrid[i][j+1].falling = true;
							gameGrid[i][j+1].current = true;
							setEmpty(gameGrid[i][j]);
					}
			x++;
		}
	}
private boolean canMoveRight(){
	int i = yLimit;
	int j = startX;
	boolean res = true;
	while (i >= startY&&res){
		while(j <= xLimit &&res){
			if (gameGrid[i][j].current)
				res= j < cols-1 && (gameGrid[i][j+1].current||gameGrid[i][j+1].id == 0);
			j++;

		}
		j = startX;
		i--;
	}
	return res ;
}

	public void currentFall(){//un chequeo para toda la figura activa y una caida tambien lo mismo para aterrizar
		setStartXY();
		if (y+size-1 < rows)
			yLimit = y+size-1;
		else
			yLimit = rows-1;
		if (x+size < cols)
			xLimit = x+size;
		else
			xLimit = cols;
		if (currentCanFall()){
			for (int i = yLimit; i>= startY; i--)
				for (int j = startX; j < xLimit; j++)
					if(gameGrid[i][j].current && i < rows-1){
							gameGrid[i+1][j].id = gameGrid[i][j].id;
							gameGrid[i+1][j].falling = true;
							gameGrid[i+1][j].current = true;
							setEmpty(gameGrid[i][j]);
					}
			y++;
		}else{
			for (int i = yLimit; i>= startY; i--) {
				for (int j = startX; j < xLimit; j++){
					gameGrid[i][j].falling = false;
					gameGrid[i][j].current = false;
				}
		     }
			score += drop;
			drop = 0;
			active = false;
			checkFullrows();
			if (checkGameOver()) {
				clearGrid();
			}
	    }
	}
	private boolean currentCanFall(){
		int i = yLimit;
		int j = startX;
		boolean res = true;
		while (i >= startY  &&res){
			while(j < xLimit &&res){
				if (gameGrid[i][j].current)
					res= i < rows-1 && (gameGrid[i+1][j].current||gameGrid[i+1][j].id == 0);
				j++;
			}
			j = startX;
			i--;
		}
		return res;
	}
	public void addFigure(){

		y = 0;
		switch(color){
			case 1:
				size = 4;
				x = 3;
				for(int j = x; j < x + size;j++){
					gameGrid[1][j].id = color;
					gameGrid[1][j].falling = true;
					gameGrid[1][j].current = true;
				}break;
			case 2:
				size = 2;
				x = 4;
				for (int i = 0;i<2;i++)
					for(int j = x; j < x + size;j++){
						gameGrid[i][j].id = color;
						gameGrid[i][j].falling = true;
						gameGrid[i][j].current = true;
					}break;
			case 3:
				size = 3;
				x = 3;
				gameGrid[0][4].id = color;
				gameGrid[0][4].falling = true;
				gameGrid[0][4].current = true;
				for(int j = x; j < x + size;j++){
					gameGrid[1][j].id = color;
					gameGrid[1][j].falling = true;
					gameGrid[1][j].current = true;
				}break;
			case 4:
				size = 3;
				x = 3;
				for(int j = x; j < x + size-1;j++){
					gameGrid[0][j].id = color;
					gameGrid[0][j].falling = true;
					gameGrid[0][j].current = true;
				}
				for(int j = x+1; j < x + size;j++){
					gameGrid[1][j].id = color;
					gameGrid[1][j].falling = true;
					gameGrid[1][j].current = true;
				}break;
			case 5:
				size = 3;
				x = 3;
				for(int j = x; j < x + size;j++){
					gameGrid[0][j].id = color;
					gameGrid[0][j].falling = true;
					gameGrid[0][j].current = true;
				}

				gameGrid[1][x].id = color;
				gameGrid[1][x].falling = true;
				gameGrid[1][x].current = true;
				break;
			case 6:
				size = 3;
				x = 3;
				for(int j = x; j < x + size;j++){
					gameGrid[0][j].id = color;
					gameGrid[0][j].falling = true;
					gameGrid[0][j].current = true;
				}
				gameGrid[1][x+size-1].id = color;
				gameGrid[1][x+size-1].falling = true;
				gameGrid[1][x+size-1].current = true;
				break;
			case 7:
				size = 3;
				x = 3;
				for(int j = x; j < x + size-1;j++){
					gameGrid[1][j].id = color;
					gameGrid[1][j].falling = true;
					gameGrid[1][j].current = true;
				}
				for(int j = x+1; j < x + size;j++){
					gameGrid[0][j].id = color;
					gameGrid[0][j].falling = true;
					gameGrid[0][j].current = true;
				}break;
		}
		active = true;
		color = randomGenerator.nextInt(7) + 1;
		for(int i = 0; i<4;i++)
			for(int j = 0; j<4 ; j++)
				nextGrid[i][j] = new Block();//clear nextGrid
		switch(color){
			case 1:
				for(int j = 0; j < 4;j++){
					nextGrid[0][j].id = color;
				}break;
			case 2:
				for (int i = 0;i<2;i++)
					for(int j = 0; j < 2;j++){
						nextGrid[i][j].id = color;
					}break;
			case 3:
				nextGrid[0][1].id = color;
				for(int j = 0; j < 3;j++){
					nextGrid[1][j].id = color;
				}break;
			case 4:
				for(int j = 0; j < 2;j++){
					nextGrid[0][j].id = color;
				}
				for(int j = 1; j <3;j++){
					nextGrid[1][j].id = color;
				}break;
			case 5:
				for(int j = 0; j < 3;j++){
					nextGrid[0][j].id = color;
				}
				nextGrid[1][0].id = color;

				break;
			case 6:
				for(int j = 0; j < 3;j++){
					nextGrid[0][j].id = color;
				}
				nextGrid[1][2].id = color;
				break;
			case 7:
				for(int j = 0; j < 2;j++){
					nextGrid[1][j].id = color;
				}
				for(int j = 1; j <3;j++){
					nextGrid[0][j].id = color;
				}break;
		}
	}
	public void setEmpty(Block block){
		block.id = 0;
		block.falling = false;
		block.current = false;
	}
	public void showGridId(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j< cols; j++)
				System.out.print("|" + gameGrid[i][j].id);
			System.out.println("|");
		}
	}
	public void showGridFalling(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j< cols; j++)
				System.out.print("|" + gameGrid[i][j].falling);
			System.out.println("|");
		}
	}
	public void showGridCurrent(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j< cols; j++)
				System.out.print("|" + gameGrid[i][j].current);
			System.out.println("|");
		}
	}
	public void clearGrid(){
		for(int i = 0; i < rows; i++)
			for(int j = 0; j< cols; j++)
				gameGrid[i][j] = new Block();
		if (score >= highScore){
			highScore = score;
		}
		saveLoad.setHighScore();
		lines = 0;
		score =0;
		level = 0;
		if (level<11)
			Engine.defaultDelay = 80 - (7 * (level-1));
		else
			Engine.defaultDelay= 6;
		active = false;
		addFigure();
	}
	public void randomGrid(){
		for(int i = 0; i < rows; i++)
			for(int j = 0; j< cols; j++)
				gameGrid[i][j].id = randomGenerator.nextInt(5);
	}
	public void setStartXY(){
		if (y < 0)
			startY = 0;
		else
			startY = y;
		if (x < 0)
			startX = 0;
		else
			startX = x;
	}
}
