package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {

    GamePanel gp;
    public static final String objectName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp){
        super(gp);

        this.gp = gp;

        type = type_pickupOnly;
        name = objectName;
        down1 = setup("/Objects/blueheart", gp.tileSize, gp.tileSize);

        setDialogue();
    }
    public void  setDialogue(){
        dialogues[0][0] = "You pick up a beautiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
    }
    public boolean use (Entity entity){

        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}
