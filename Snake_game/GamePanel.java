import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int screen_width = 600;
    static final int screen_height = 600;
    static final int unit_Size = 25; //how big object should be
    static final int Game_units = (screen_width * screen_height) / unit_Size ; //how many oobjects fit in the screen
    static final int delay = 75;

//create two arrays for holding all the coordinates for all the body parts of snake 

    final int x[] = new int[Game_units]; 
    final int y[] = new int[Game_units];
    int bodyparts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }
        
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(running){
            // for loop is for gridlines
            // for(int i = 0; i < screen_height/unit_Size; i++){
            //     g.drawLine(i * unit_Size, 0, i * unit_Size, screen_height);
            //     g.drawLine(0, i * unit_Size, screen_width, i * unit_Size);
            // }
    
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, unit_Size, unit_Size); //for apple size
    
            for(int i = 0; i < bodyparts; i++){
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unit_Size, unit_Size);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], unit_Size, unit_Size);
                }
    
            }
            // g.setColor(Color.red);
            // g.setFont(new Font("Ink Free", Font.BOLD, 20));
            // FontMetrics metrics = getFontMetrics(g.getFont()); // for align in the middle 
            // g.drawString("SCORE: " + applesEaten, (screen_width - metrics.stringWidth("SCORE: " + applesEaten)) / 2, screen_width / 3 - screen_height / 20);
        }
        else {
            gameOver(g);
        }
        
    }

    public void newApple(){ //for ppopulating the apple
        appleX = random.nextInt((int)(screen_width / unit_Size)) * unit_Size;
        appleY = random.nextInt((int)(screen_height / unit_Size)) * unit_Size;
    }

    public void move(){ //for direction
        for(int i = bodyparts; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - unit_Size;
                break;
            case 'D':
                y[0] = y[0] + unit_Size;
                break;
            case 'L':
                x[0] = x[0] - unit_Size;
                break;
            case 'R':
                x[0] = x[0] + unit_Size;
                break;
           
        }
    }

    public void checkAplle(){
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyparts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision(){
        // checks if head collides with body
        for(int i = bodyparts; i > 0; i--){
            if((x[0] == x[i] && y[0] == y[i])){
                running = false;
            }
        }
        // checks if the head touches left border
        if(x[0] < 0){
            running = false;
        }
        // checks if the head touches the right border
        if(x[0] > screen_width){
            running = false;
        }
        // checks if the head touches the top border
        if(y[0] < 0){
            running = false;
        }
        // checks if the head touches the bottom border
        if(y[0] > screen_height){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        // game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont()); // for align in the middle 
        g.drawString("GAME OVER", (screen_width - metrics1.stringWidth("GAME OVER")) / 2, screen_height / 2); //it willl give the game over text at the center of the screen

        // score card
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont()); // for align in the middle 
        g.drawString("SCORE: " + applesEaten, (screen_width - metrics2.stringWidth("SCORE: " + applesEaten)) / 2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running) {
            move();
            checkAplle();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                    case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                    case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                    case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            
            }
        }
    }
}
