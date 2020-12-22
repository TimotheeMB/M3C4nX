import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FiguresPanel extends JPanel{

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        File[] figures = new File("./figures").listFiles();
        if (figures.length!=0) {
            int gridSize = (int) (Math.sqrt(figures.length) + 0.9);
            int cx = (int) (this.getWidth() / gridSize);
            int cy = (int) (this.getHeight() / gridSize);
            int x = 0;
            int y = 0;

            for (int i = 0; i < figures.length; i++) {
                x = (int) (i % gridSize);
                y = (int) (i / gridSize);
                //if(!file.getName().equals("blank_figure.png")){
                try {
                    BufferedImage img = ImageIO.read(figures[i]);
                    g.drawImage(img.getScaledInstance(cx, cy, Image.SCALE_SMOOTH), x * cx, y * cy, this); // see javadoc for more info on the parameters
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ;
                //}
            }
        }

    }

}
