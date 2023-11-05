package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound (){

        soundURL[0] = getClass().getResource("/Sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/Sounds/coin.wav");
        soundURL[2] = getClass().getResource("/Sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/Sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/Sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/Sounds/hitmonster.wav");
        soundURL[6] = getClass().getResource("/Sounds/receivedamage.wav");
        soundURL[7] = getClass().getResource("/Sounds/swingweapon.wav");
        soundURL[8] = getClass().getResource("/Sounds/levelup.wav");
        soundURL[9] = getClass().getResource("/Sounds/cursor.wav");
        soundURL[10] = getClass().getResource("/Sounds/burning.wav");
        soundURL[11] = getClass().getResource("/Sounds/cuttree.wav");
        soundURL[12] = getClass().getResource("/Sounds/gameover.wav");
        soundURL[13] = getClass().getResource("/Sounds/stairs.wav");
        soundURL[14] = getClass().getResource("/Sounds/sleep.wav");
        soundURL[15] = getClass().getResource("/Sounds/blocked.wav");
        soundURL[16] = getClass().getResource("/Sounds/parry.wav");
        soundURL[17] = getClass().getResource("/Sounds/speak.wav");
        soundURL[18] = getClass().getResource("/Sounds/Merchant.wav");
        soundURL[19] = getClass().getResource("/Sounds/Dungeon.wav");
        soundURL[20] = getClass().getResource("/Sounds/chipwall.wav");
        soundURL[21] = getClass().getResource("/Sounds/dooropen.wav");
        soundURL[22] = getClass().getResource("/Sounds/FinalBattle.wav");
    }

    public void setFile (int i) {

        try{

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip .getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        }catch (Exception ignored){

        }
    }

    public  void play () {
        clip.start();
    }

    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop () {
        clip.stop();
    }
    public void checkVolume(){

        switch (volumeScale){
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        fc.setValue(volume);
    }
}
