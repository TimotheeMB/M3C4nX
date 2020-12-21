import javax.swing.*;
import java.io.File;

public class FiguresPanel extends JPanel {

    void refresh(){
        File images = new File("./images");
        for(File file:images.listFiles()){
            if(!file.getName().equals("blank_figure.png")) {
                JLabel image=new JLabel(new ImageIcon(file.getAbsolutePath()));
                image.setBounds(0,0,500,500);
                this.add(image);
            }
        }
        this.setVisible(true);
    }
}
