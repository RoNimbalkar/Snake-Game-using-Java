import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 500;
	static final int SCREEN_HEIGHT = 500;
	static final int UNIT_SIZE = 25;

	//No of possible units on screen 
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;

    //Higher the no of delay the slower the game will and vice versa 
    static final int DELAY = 175;

    //All co-ordinates for body part of snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    //Initial Body parts
    int bodyParts = 6;

    int applesEaten;

    //X and Y co-oardinates of Apple
    int appleX;
    int appleY;

    //Direction os snake intially to right 
    char direction = 'R';

    boolean running = true;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		
		//Setting Preffered size of Game Panel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		
		if(running) {
			/*for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
				//Grid Craetion 
	
				//For Vertical lines
				g.drawLine(i * UNIT_SIZE, 0 , i * UNIT_SIZE,SCREEN_HEIGHT);
				
				//For Horizantal lines  
				g.drawLine(0, i * UNIT_SIZE ,SCREEN_HEIGHT, i * UNIT_SIZE);
			}*/

			g.setColor(Color.red);
			g.fillOval(appleX,appleY, UNIT_SIZE,UNIT_SIZE);
		
			//Draw head of snake and body 
			for(int i = 0; i < bodyParts; i++){
				if(i == 0){
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else{
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}

			//To display score while playing game 
			g.setColor(Color.red);
			g.setFont( new Font("Times New Roman",Font.BOLD, 40));

			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}

	public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

	public void move() {
        //To Move Snake
        for(int i = bodyParts; i > 0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
		
		//To change direction 
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break; 
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break; 
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break; 
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break; 
        }
    }

	public void checkApple() {
		//Checks does snake graps apple or not
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}

	public void checkCollisions() {

		//Checks if head collides with body
		for(int i = bodyParts; i > 0 ; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}

		//check if head touches left border
		if(x[0] < 0) {
			running = false;
		}

		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}

		//check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		//To display Score
		g.setColor(Color.red);
		g.setFont( new Font("Times New Roman",Font.BOLD, 40));

		//For align text center of screen
		FontMetrics metrics1 = getFontMetrics(g.getFont());

		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("ALGERIAN",Font.BOLD, 75));

		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	//Inner Class
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			switch(e.getKeyCode()) 
			{
				//Dosen't allow snake to go in oppsosite direction
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;

				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;

				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;

				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
			}
		}
	}
}