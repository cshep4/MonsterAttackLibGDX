package com.cshep4.monsterattack.game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;

//import org.xmlpull.v1.XmlSerializer;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Environment;
//import android.util.Log;
//import android.util.Xml;

public class MyApp {
    private boolean soundOn;
    
    public ArrayList<HighScore> highScores;
    
//    public AssetManager assetManager;
    private static MyApp instance;
    
    private long enemySpawnTime = System.currentTimeMillis();
    private int screenWidth;
    private int screenHeight;
    
    private int numKills = 0;
    
//    public Resources res;
    
//    public Bitmap[] standardMoveIdle;
//    public Bitmap[] bomberMoveIdle;
//    public Bitmap[] standardGotHitProduce;
//    public Bitmap[] bomberGotHitProduce;
//    public Bitmap standardShield;
//    public Bitmap playerGotHit;
//    public Bitmap playerIdle;
//    public Bitmap playerRun;
//    public Bitmap[] standardMoveIdleLeft;
//    public Bitmap[] bomberMoveIdleLeft;
//    public Bitmap[] standardGotHitProduceLeft;
//    public Bitmap[] bomberGotHitProduceLeft;
//    public Bitmap standardShieldLeft;
//    public Bitmap playerGotHitLeft;
//    public Bitmap playerIdleLeft;
//    public Bitmap playerRunLeft;
//    public Bitmap explosion;
    
//    public Context appContext;
    
    private MyApp() {}
    
//    public void setAssetManager(AssetManager am) {
//    	assetManager = am;
//    }
    
    public static synchronized MyApp getInstance(){
        if(instance==null){
          instance=new MyApp();
        }
        return instance;
      }    

    public boolean getSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }	
    
//    public void parseHighscores(String fileName, Resources resource) {
//    	highScores = null;
//    	final int readLimit = 16 * 1024;
//        try {
//            XMLPullParserHandler parser = new XMLPullParserHandler();
//            InputStream is=assetManager.open(fileName);
//            copyToStorage(is, fileName);
//            File f = new File(Environment.getExternalStorageDirectory(), "monsterattack/highscores/" + fileName);
//            is = new BufferedInputStream(new FileInputStream(f));
//            //InputStream is= aResource.openRawResource(R.raw.highscores);
//            highScores = parser.parseHighScores(is);
//
//        } catch (IOException e) {e.printStackTrace();
//        }
//
//	}
    
    public long getSpawnTime() {
    	return enemySpawnTime;
    }

    public void setSpawnTime(long spawnTime) {
    	enemySpawnTime = spawnTime;
    }

    public int getScreenWidth() {
    	return screenWidth;
    }
    
    public int getScreenHeight() {
    	return screenHeight;
    }  
    
    public void setScreenWidth(int width) {
    	screenWidth = width;
    }
    
    public void setScreenHeight(int height) {
    	screenHeight = height;
    }   
    
    public int getNumKills() {
    	return numKills;
    }  
    
    public void setNumKills(int numKills) {
    	this.numKills = numKills;
    }  
    
   
    public boolean checkHighscores() {
        return false;//NumKills > highScores.get(4).getNumkills();
    }
    
//    public void saveHighscores(String name) {
//    	HighScore highscore = new HighScore();
//    	highscore.setName(name);
//    	highscore.setNumkills(numKills);
//    	for (int highscoreLoop = 0; highscoreLoop < 5; highscoreLoop++) {
//    		if (numKills > highScores.get(highscoreLoop).getNumkills()) {
//    			highscore.setPlace(highscoreLoop+1);
//    			highScores.add(highscoreLoop,highscore);
//    			writeToHighscoreXML();
//    			return;
//    		}
//    	}
//    }
    
//    private void writeToHighscoreXML() {
//    	try {
//    	    FileOutputStream fos = new  FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/monsterattack/highscores/highscores.xml");
//    	    //FileOutputStream fileos= getApplicationContext().openFileOutput("highscores.xml", Context.MODE_PRIVATE);
//    	    XmlSerializer xmlSerializer = Xml.newSerializer();
//    	    StringWriter writer = new StringWriter();
//    	    xmlSerializer.setOutput(writer);
//    	    xmlSerializer.startDocument("UTF-8", true);
//    	    xmlSerializer.startTag(null, "highscores");
//    	    for (int highscoreLoop = 0; highscoreLoop < 5; highscoreLoop++) {
//	    	    xmlSerializer.startTag(null, "score");
//	    	    xmlSerializer.startTag(null, "id");
//	    	    xmlSerializer.text(String.valueOf(highscoreLoop+1));
//	    	    xmlSerializer.endTag(null, "id");
//	    	    xmlSerializer.startTag(null, "name");
//	    	    xmlSerializer.text(highScores.get(highscoreLoop).getName());
//	    	    xmlSerializer.endTag(null, "name");
//	    	    xmlSerializer.startTag(null, "kills");
//	    	    xmlSerializer.text(String.valueOf(highScores.get(highscoreLoop).getNumkills()));
//	    	    xmlSerializer.endTag(null, "kills");
//	    	    xmlSerializer.endTag(null, "score");
//    	    }
//    	    xmlSerializer.endTag(null, "highscores");
//    	    xmlSerializer.endDocument();
//    	    xmlSerializer.flush();
//    	    String dataWrite = writer.toString();
//    	    fos.write(dataWrite.getBytes());
//    	    fos.close();
//    	}
//    	catch (FileNotFoundException e) {
//    	    // TODO Auto-generated catch block
//    	    e.printStackTrace();
//    	}
//    	catch (IllegalArgumentException e) {
//    	    // TODO Auto-generated catch block
//    	    e.printStackTrace();
//    	}
//    	catch (IllegalStateException e) {
//    	    // TODO Auto-generated catch block
//    	    e.printStackTrace();
//    	}
//    	catch (IOException e) {
//    	    // TODO Auto-generated catch block
//    	    e.printStackTrace();
//    	}
//    }
//
//    private void copyToStorage(InputStream is, String filename) throws IOException {
//    	File f = new File(Environment.getExternalStorageDirectory(), "monsterattack/highscores/" + filename);
//    	String root = Environment.getExternalStorageDirectory().toString();
//    	File dir = new File(root + "/monsterattack/highscores");
//    	while (!dir.exists()) {
//            dir.mkdirs();
//        }
//    	if (f.createNewFile()) {
//	    	OutputStream os = new FileOutputStream(f, true);
//
//            final int buffer_size = 1024 * 1024;
//	    	try
//	    	{
//	    	    byte[] bytes = new byte[buffer_size];
//	    	    for (;;)
//	    	    {
//	    	        int count = is.read(bytes, 0, buffer_size);
//	    	        if (count == -1)
//	    	            break;
//	    	        os.write(bytes, 0, count);
//	    	    }
//				is.close();
//	    	    os.close();
//	    	}
//	    	catch (Exception ex)
//	    	{
//	    	    ex.printStackTrace();
//	    	}
//    	}
//    }
//
//    public void createBitmaps(Resources resources) {
//		if (this.standardMoveIdle == null) {
//			this.standardMoveIdle = new Bitmap[5];
//			this.standardMoveIdle[0] = BitmapFactory.decodeResource(resources, R.drawable.s1_move);
//			this.standardMoveIdle[1] = BitmapFactory.decodeResource(resources, R.drawable.s2_move);
//			this.standardMoveIdle[2] = BitmapFactory.decodeResource(resources, R.drawable.s3_move);
//			this.standardMoveIdle[3] = BitmapFactory.decodeResource(resources, R.drawable.s4_move);
//			this.standardMoveIdle[4] = BitmapFactory.decodeResource(resources, R.drawable.sp_idle);
//		}
//		if (this.bomberMoveIdle == null) {
//			this.bomberMoveIdle = new Bitmap[5];
//			this.bomberMoveIdle[0] = BitmapFactory.decodeResource(resources, R.drawable.b1_move);
//			this.bomberMoveIdle[1] = BitmapFactory.decodeResource(resources, R.drawable.b2_move);
//			this.bomberMoveIdle[2] = BitmapFactory.decodeResource(resources, R.drawable.b3_move);
//			this.bomberMoveIdle[3] = BitmapFactory.decodeResource(resources, R.drawable.b4_move);
//			this.bomberMoveIdle[4] = BitmapFactory.decodeResource(resources, R.drawable.bp_idle);
//		}
//		if (this.playerIdle == null) {
//			this.playerIdle = BitmapFactory.decodeResource(resources, R.drawable.idle);
//		}
//		if (this.playerGotHit == null) {
//			this.playerGotHit = BitmapFactory.decodeResource(resources, R.drawable.gothit);
//		}
//		if (this.playerRun == null) {
//			this.playerRun = BitmapFactory.decodeResource(resources, R.drawable.character_running);
//		}
//		if (this.explosion == null) {
//			this.explosion = BitmapFactory.decodeResource(resources, R.drawable.explosion);
//		}
//		if (this.standardShield == null) {
//			this.standardShield = BitmapFactory.decodeResource(resources, R.drawable.s4_shield);
//		}
//		if (this.standardMoveIdleLeft == null) {
//			this.standardMoveIdleLeft = new Bitmap[5];
//			this.standardMoveIdleLeft[0] = BitmapFactory.decodeResource(resources, R.drawable.s1_move1);
//			this.standardMoveIdleLeft[1] = BitmapFactory.decodeResource(resources, R.drawable.s2_move1);
//			this.standardMoveIdleLeft[2] = BitmapFactory.decodeResource(resources, R.drawable.s3_move1);
//			this.standardMoveIdleLeft[3] = BitmapFactory.decodeResource(resources, R.drawable.s4_move1);
//			this.standardMoveIdleLeft[4] = BitmapFactory.decodeResource(resources, R.drawable.sp_idle);
//		}
//		if (this.standardMoveIdleLeft == null) {
//			this.bomberMoveIdleLeft = new Bitmap[5];
//			this.bomberMoveIdleLeft[0] = BitmapFactory.decodeResource(resources, R.drawable.b1_move);
//			this.bomberMoveIdleLeft[1] = BitmapFactory.decodeResource(resources, R.drawable.b2_move);
//			this.bomberMoveIdleLeft[2] = BitmapFactory.decodeResource(resources, R.drawable.b3_move);
//			this.bomberMoveIdleLeft[3] = BitmapFactory.decodeResource(resources, R.drawable.b4_move);
//			this.bomberMoveIdleLeft[4] = BitmapFactory.decodeResource(resources, R.drawable.bp_idle);
//		}
//		if (this.playerIdleLeft == null) {
//			this.playerIdleLeft = BitmapFactory.decodeResource(resources, R.drawable.idle);
//		}
//		if (this.playerGotHitLeft == null) {
//			this.playerGotHitLeft = BitmapFactory.decodeResource(resources, R.drawable.gothit);
//		}
//		if (this.playerRunLeft == null) {
//			this.playerRunLeft = BitmapFactory.decodeResource(resources, R.drawable.character_running1);
//		}
//	}

}
