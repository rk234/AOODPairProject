package backend;
import java.util.List;
import java.util.Scanner;

import utils.TextureManager;
import utils.Vector2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LevelManager {
    public static Level getLevel(int level) {
        try {
            Scanner sc = new Scanner(new File("levels/Level"+level+".txt"));
            boolean completed = sc.next().equals("True");
            
            int numEntities = sc.nextInt();
            Entity[] entities = new Entity[numEntities];
            for(int i = 0; i < numEntities; i++) {
                String objectType = sc.next();
                if(objectType.equals("Planet")) {
                    String name = sc.next();
                    String texture = sc.next();
                    float x = sc.nextFloat();
                    float y = sc.nextFloat();
                    float mass = sc.nextFloat();
                    float radius = sc.nextFloat();

                    Planet p = new Planet(new Vector2(x,y), mass, radius, TextureManager.main.getTexture(texture));
                    p.setName(name);
                    entities[i] = p;
                }
            }
            
            Objective objective = null;
            String objectiveType = sc.next();
            switch (objectiveType) {
                case "AltitudeObjective":
                    objective = new AltitudeObjective((Planet)entities[sc.nextInt()], sc.nextFloat());
                    break;
                case "LandObjective":
                    objective = new LandObjective((Planet)entities[sc.nextInt()]);
                    break;
                case "OrbitObjective":
                    objective = new OrbitObjective((Planet)entities[sc.nextInt()], sc.nextFloat());
                    break;
            }
            Rocket rocket = null;
            if(sc.next().equals("Rocket")) {
                float x = sc.nextFloat();
                float y = sc.nextFloat();

                float initialFuel = sc.nextFloat();
                rocket = new Rocket(new Vector2(x, y), initialFuel, TextureManager.main.getTexture("rocket"));
            }
            float zoom = sc.nextFloat();

            String hint = "";
            sc.nextLine();
            while(sc.hasNextLine()) {
                hint+=sc.nextLine()+"\n";
            }
            return new Level(rocket, entities, objective, completed, level, zoom, hint);
        } catch(IOException ex) {
            return null;
        }
    }

    public static void setComplete(int level, boolean complete) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("levels/Level"+level+".txt"));
        lines.set(0, complete ? "True" : "False");
        Files.write(Paths.get("levels/Level"+level+".txt"), lines);
    }
    
    public static void resetLevels() throws IOException {
        for (int i = 0; i < numLevels(); i++) {
            setComplete(i, false);
        }
    }

    public static int numLevels() {
        return new File("levels/").listFiles().length;
    }

    public static Level[] getAllLevels() {
        Level[] levels = new Level[numLevels()];
        for(int i = 0; i < levels.length; i++) {
            levels[i] = getLevel(i);
        }

        return levels;
    }
}