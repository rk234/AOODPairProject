package utils;

import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureManager {
    public static final TextureManager main = new TextureManager();
    private HashMap<String, BufferedImage> textures;
    
    public TextureManager() {
        textures = new HashMap<String, BufferedImage>();
        try {
            loadSprites();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSprites() throws IOException {
        for(int i = 0; i < 10; i++) {
            loadTextureFromDisk("assets/Planets/planet0"+i+".png", "planet"+i);
        }
        loadTextureFromDisk("assets/space/PNG/Sprites/Rockets/spaceRockets_001.png", "rocket");
    }

    public void loadTextureFromDisk(String path, String textureName) throws IOException {
        BufferedImage source = ImageIO.read(new File(path));
        loadTexture(source, textureName);
    }

    public void loadTexture(BufferedImage source, String textureName) {
        textures.put(textureName, source);
    }

    public BufferedImage getTexture(String textureName) {
        return textures.get(textureName);
    }
}
