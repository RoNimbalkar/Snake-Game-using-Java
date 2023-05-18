import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GameFrame(){
        this.add(new GamePanel());

        //Title of JFrame 
        this.setTitle("Snake");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);

        //Take JFrame and fixed all components in it
        this.pack();

        this.setVisible(true);

        //Locating JFrame at center of screen
        this.setLocationRelativeTo(null);
    }
}
