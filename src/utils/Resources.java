package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Resources{
     
        
        public static BufferedImage loadImage(String resourcePath) {
        BufferedImage image = null;
        try{
            InputStream input=Resources.class.getResourceAsStream("/res/" + resourcePath);
            if(image == null){
                image = ImageIO.read(input);         
            }
        } catch(IOException ex){
            throw new RuntimeException(ex);
    }
        return image;
    }
}
