package generation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class imageGen {

    static int PICTURE_NUM = 0;

    public static void main(String[] args) throws IOException, AWTException {
        imageGen runner = new imageGen();
        for (int i = 0; i < 3; i++) {
            runner.capture_image();
            runner.cropImage(CONSTANTS.IMG_FILE+Integer.toString(i)+".png");
            runner.scroll();
        }
        runner.splice();
    }

    public  void capture_image() throws IOException, AWTException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        ImageIO.write(capture, "png", new File(CONSTANTS.IMG_FILE + Integer.toString(PICTURE_NUM)));
        PICTURE_NUM++;
    }

    public void scroll() throws AWTException {
        Robot mouse = new Robot();
        mouse.mouseWheel(CONSTANTS.SCROLL_DISTANCE);
    }

    public void cropImage(String fileName) throws IOException {
        BufferedImage original = ImageIO.read(new File(fileName));
        BufferedImage croppedImage = original.getSubimage(CONSTANTS.START_X, CONSTANTS.START_Y, CONSTANTS.PIC_WIDTH, CONSTANTS.PIC_HEIGHT);
        ImageIO.write(croppedImage, "png", new File(fileName));
    }

    public void splice() throws IOException {
        BufferedImage result = new BufferedImage(CONSTANTS.PIC_WIDTH, CONSTANTS.FINAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        int x = 0, y =0;
        for (int i = 0; i < 2 ; i++) {
            BufferedImage bi = ImageIO.read(new File(CONSTANTS.IMG_FILE + Integer.toString(i)));
            g.drawImage(bi, x, y, null);
            y += CONSTANTS.PIC_HEIGHT;
        }
        ImageIO.write(result,"png",new File("full-image.png"));
    }


}