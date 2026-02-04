import javax.swing.JFrame;

public class GameFrame extends JFrame {

  GameFrame(){
    // GamePanel Panel = new GamePanel();
    // this.add(Panel);

    this.add(new GamePanel());
    this.setTitle("Snake_Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.pack(); // this actually going to snugly around all of the components thet we add to the frame

    this.setVisible(true);
    this.setLocationRelativeTo(null); //to appear window screen in the middle 
  }

    
    
}
