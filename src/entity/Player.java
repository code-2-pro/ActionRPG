package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.*;


public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
    }
    public void setDefaultValues (){

//        worldX = gp.tileSize * 23;
//        worldY = gp.tileSize * 21;

        gp.currentMap = 3;
        gp.currentArea = gp.dungeon;
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 30;

//        worldX = gp.tileSize * 12;
//        worldY = gp.tileSize * 10;

        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 100; // The more strength he has, the more damage he gives.
        dexterity = 1; // The more dexterity he has, the less damage he receives.
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Fireball(gp);
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); // The total defense value is decided by dexterity and shield

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }
    public void setDefaultPositions(){

        gp.currentMap = 0;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void setDialogue(){

        dialogues[0][0] = "You are level " + level + " now!\n" + "You feel stronger!";
    }
    public void restoreStatus(){

        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }
    public void setItems(){

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Pickaxe(gp));

    }
    public int getAttack (){
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return  attack = strength * currentWeapon.attackValue;
    }
    public int getDefense (){
        return  defense = dexterity * currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getImage () {
        up1 = setup("/Player/Walking_sprites/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Player/Walking_sprites/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Player/Walking_sprites/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Player/Walking_sprites/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Player/Walking_sprites/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Player/Walking_sprites/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Player/Walking_sprites/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Player/Walking_sprites/boy_right_2", gp.tileSize, gp.tileSize);
    }
    public void getAttackImage (){

        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/Player/Attacking_sprites/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/Player/Attacking_sprites/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/Player/Attacking_sprites/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/Player/Attacking_sprites/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/Player/Attacking_sprites/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/Player/Attacking_sprites/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/Player/Attacking_sprites/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/Player/Attacking_sprites/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/Player/Attacking_sprites/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/Player/Attacking_sprites/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/Player/Attacking_sprites/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/Player/Attacking_sprites/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/Player/Attacking_sprites/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/Player/Attacking_sprites/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/Player/Attacking_sprites/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/Player/Attacking_sprites/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setup("/Player/Attacking_sprites/boy_pick_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/Player/Attacking_sprites/boy_pick_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/Player/Attacking_sprites/boy_pick_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/Player/Attacking_sprites/boy_pick_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/Player/Attacking_sprites/boy_pick_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/Player/Attacking_sprites/boy_pick_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/Player/Attacking_sprites/boy_pick_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/Player/Attacking_sprites/boy_pick_right_2", gp.tileSize*2, gp.tileSize);
        }
    }
    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
    public void getGuardImage(){

        guardUp = setup("/Player/Guarding_sprites/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("/Player/Guarding_sprites/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("/Player/Guarding_sprites/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("/Player/Guarding_sprites/boy_guard_right", gp.tileSize, gp.tileSize);
    }
    public void update(){

        if(knockBack){

            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if (!collisionOn) {
                switch(knockBackDirection){
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking){
            attacking();
        }
        else if (keyH.spacePressed) {
            guarding = true;
            guardCounter++;
        }
        else if(keyH.upPressed || keyH.downPressed ||
           keyH.leftPressed || keyH.rightPressed || keyH.enterPressed){
            if(keyH.upPressed){
                direction = "up";
            }
            else if (keyH.downPressed){
                direction = "down";
            }
            else if (keyH.leftPressed){
                direction = "left";
            }
            else if (keyH.rightPressed){
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            gp.cChecker.checkEntity(this, gp.iTile);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed){

                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if(gp.keyH.enterPressed && !attackCanceled){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
        }

        if(gp.keyH.shotKeyPressed && !projectile.alive &&
           shotAvailableCounter == 30 && projectile.haveResource(this)){

            // SET DEFAULT COORDINATES, DIRECTIONS AND USER
           projectile.set(worldX, worldY, direction, true, this);

           // SUBTRACT THE COST (MANA, AMMO, ETC.)
            projectile.subtractResource(this);

            // CHECK VACANCY
            for(int i = 0; i < gp.projectile[1].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;

            gp.playSE(10);
        }

        // This needs to be outside of key if statement!
        if(invincible){
           invincibleCounter++;
           if(invincibleCounter > 60){
              invincible = false;
              transparent = false;
              invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(!keyH.godModeOn){
            if(life <= 0){
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum = -1;
                gp.stopMusic();
                gp.playSE(12);
            }
        }
    }
    public  void pickUpObject (int i){

        if(i != 999){

            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){ // FIXED

                gp.obj[gp.currentMap][i].use(this); // FIXED
                gp.obj[gp.currentMap][i] = null; // FIXED
            }
            // OBSTACLE
            else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(keyH.enterPressed){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // INVENTORY ITEMS
            else{

                String text;

                if(canObtainItem(gp.obj[gp.currentMap][i])){

                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!"; // FIXED
                }
                else{
                    text = "You cannot carry anymore!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null; // FIXED DON'T THIS!!!
            }
            }


    }
    public void interactNPC (int i) {

            if (i != 999) {

                if (gp.keyH.enterPressed) {
                    attackCanceled = true;
                    gp.npc[gp.currentMap][i].speak(); // FIXED
            }

            gp.npc[gp.currentMap][i].move(direction);
    }
    }
    public void contactMonster(int i){

        if(i != 999){
            if(!invincible && !gp.monster[gp.currentMap][i].dying){ // FIXED
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense; // FIXED
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }

        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower, String direction){

        if(i != 999){

            if(!gp.monster[gp.currentMap][i].invincible){ // FIXED

                gp.playSE(5);

                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance){
                    attack *= 5;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense; // FIXED
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage; // FIXED
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true; // FIXED
                gp.monster[gp.currentMap][i].damageReaction(); // FIXED

                if(gp.monster[gp.currentMap][i].life <= 0){ // FIXED
                    gp.monster[gp.currentMap][i].dying = true; // FIXED
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!"); // FIXED
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp + "!"); // FIXED
                    exp += gp.monster[gp.currentMap][i].exp; // FIXED
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i){

        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this)
           && !gp.iTile[gp.currentMap][i].invincible){ // FIXED

            gp.iTile[gp.currentMap][i].playSE(); // FIXED
            gp.iTile[gp.currentMap][i].life--; // FIXED
            gp.iTile[gp.currentMap][i].invincible = true; // FIXED

            // Generate particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]); // FIXED

            if(gp.iTile[gp.currentMap][i].life == 0){
//                gp.iTile[gp.currentMap][i].checkDrop();
               gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm(); // FIXED
            }
        }
    }
    public void damageProjectile(int i){

        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }
    public void checkLevelUp(){

        if(exp >= nextLevelExp){

            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;

            setDialogue();
            startDialogue(this,0);
        }
    }
    public void selectItem (){

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()){

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe){

                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield){

                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){

                if(currentLight == selectedItem){
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_consumable){

                if(selectedItem.use(this)){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }
                    else{
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        // CHECK IF STACKABLE
        if(newItem.stackable){

            int index = searchItemInInventory(newItem.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else { // New item so need to check vacancy
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else { // NOT STACKABLE so check vacancy
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if(!attacking){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if(attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                 if(guarding){
                    image = guardUp;
                }
            }
            case "down" -> {
                if(!attacking){
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if(attacking){
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                if(guarding){
                    image = guardDown;
                }
            }
            case "left" -> {
                if(!attacking){
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if(attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                if(guarding){
                    image = guardLeft;
                }
            }
            case "right" -> {
                if(!attacking){
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if(attacking){
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                if(guarding){
                    image = guardRight;
                }
            }
        }

        if(transparent){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(drawing){
            g2.drawImage(image, tempScreenX, tempScreenY,null);
        }

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
//        g2.setFont(new Font("Arial",Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter, 10, 400);

        // DEBUG
        // AttackArea
//        tempScreenX = screenX + solidArea.x;
//        tempScreenY = screenY + solidArea.y;
//        switch(direction) {
//            case "up": tempScreenY = screenY - attackArea.height; break;
//            case "down": tempScreenY = screenY + gp.tileSize; break;
//            case "left": tempScreenX = screenX - attackArea.width; break;
//            case "right": tempScreenX = screenX + gp.tileSize; break;
//        }
//        g2.setColor(Color.red);
//        g2.setStroke(new BasicStroke(1));
//        g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
    }

}
