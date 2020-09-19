import javax.swing.*;

public class NewMatrixWindow extends JFrame {
    
    public NewMatrixWindow(){
        this.setTitle("new matrix");
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add (new NewMatrixPanel());
        this.pack();
        this.setVisible (true);
    }
}
