package com.cshep4.monsterattack.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.graphics.Rect;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;


//@SuppressLint("WrongCall")//Needed to suppress the lint error
public class ScreenView /*extends SurfaceView implements Runnable*/ {
//
//    Paint paint = new Paint();
//
//	//Global Variables
//	MyApp myApp = MyApp.getInstance();
//
//	//bullets
//	private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
//	private ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
//
//	//enemies
//    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
//    private ArrayList<ProducerEnemy> producerEnemies = new ArrayList<ProducerEnemy>();
//
//
//	public static PauseButton shootButton;
//	public static PauseButton pauseButton;
//
//	private Mutation mutation = new Mutation();
//
//
//	//collision handling
//	private Collision collision = new Collision();
//
//	//player
//	public Player player;
//
//    //Width and height of screen
//    private int width;
//    private int height;
//
//    //Handler thread to update screen
//    private Handler hHandler;
//
//    private static Bitmap bitmap;
//    private Resources res = getResources();
//
//    private Sound sound = new Sound();
//
//	SurfaceHolder holder; //Surface holder for the canvas
//	private boolean ok = false; //Boolean to control pause and resume
//	Thread t = null; //Thread for the game logic
//
//	public boolean paused = false;
//
//	private int s1Spawned, s2Spawned, s3Spawned, s4Spawned, sPSpawned, b1Spawned, b2Spawned, b3Spawned, b4Spawned, bPSpawned = 0;
//
//	//CONSTRUCTOR
//	public ScreenView(Context context, int w, int h) {
//        super(context);
//        holder = getHolder();//Used for the screenview
//
//        this.width = w;
//        this.height = h;
//        myApp.setScreenWidth(w);
//        myApp.setScreenHeight(h);
//        myApp.res = getResources();
//
//        player = new Player(100,100, width / Constants.CHARACTER_WIDTH_DIVIDER, width / Constants.CHARACTER_HEIGHT_DIVIDER);
//
//        if (shootButton == null) {
//            shootButton = new PauseButton(width / Constants.BUTTON_SIZE_DIVIDER, width / Constants.BUTTON_SIZE_DIVIDER, getResources(),
//                    width - (width / Constants.BUTTON_SIZE_DIVIDER), height - (width / Constants.BUTTON_SIZE_DIVIDER), R.drawable.shoot_button);
//        }
//        if (pauseButton == null) {
//            pauseButton = new PauseButton(width / Constants.BUTTON_SIZE_DIVIDER, width / Constants.BUTTON_SIZE_DIVIDER, getResources(),
//                    width - (width / Constants.BUTTON_SIZE_DIVIDER), 0, R.drawable.pause_button);
//        }
//        if (bitmap == null) {
//            bitmap = BitmapFactory.decodeResource(res, R.drawable.background_small);
//            bitmap = Bitmap.createScaledBitmap(bitmap, this.width, this.height, false);
//        }
//
//        newGame();
//
//        hHandler = new Handler();
//	}
//
//
//	protected void onDraw(Canvas canvas){
//		canvas.drawBitmap(bitmap,0,0,null);
//		drawText(canvas);
//		if (!gameOver()) {
//			spawnEnemies();
//            player.update();
//			player.drawObject(paint, canvas);
//			shootButton.drawButton(canvas);
//			pauseButton.drawButton(canvas);
//			updateEnemies(canvas);
//			updateBullets(canvas);
//		} else {
//			gameOverActivity();
//		}
//
//    	//hHandler.postDelayed(r, Constants.FRAME_RATE);
//    }
//
//    private void updateBullets(Canvas canvas){
//        if (enemyBullets != null) {
//            for (int bulletLoop =0; bulletLoop < enemyBullets.size(); bulletLoop++){
//                if (enemyBullets.get(bulletLoop) != null) {
//                    enemyBullets.get(bulletLoop).update();
//                    if (enemyBullets.get(bulletLoop) != null) {
//                        enemyBullets.get(bulletLoop).drawRect(paint, canvas);
//                        if (enemyBullets.get(bulletLoop).getRect().right<0){
//                            enemyBullets.remove(bulletLoop);
//                            bulletLoop--;
//                            continue;
//                        }
//                    }
//                    if (Collision.bulletCollision(enemyBullets.get(bulletLoop),player)){
//                        Log.v("Death", "SHOT!");
//                        player.setHealth(player.getHealth()-100);
//                        enemyBullets.get(bulletLoop).collisionSound();
//                        enemyBullets.remove(bulletLoop);
//                        bulletLoop--;
//                        continue;
//                    }
//                }
//            }
//        }
//
//        if (playerBullets != null) {
//            for (int bulletLoop =0; bulletLoop < playerBullets.size(); bulletLoop++){
//
//                if (playerBullets.get(bulletLoop) != null) {
//
//                    playerBullets.get(bulletLoop).update();
//                    if (enemyShot(playerBullets.get(bulletLoop))){
//                        playerBullets.remove(bulletLoop);
//                        bulletLoop--;
//                        continue;
//                    }
//                    if (playerBullets.get(bulletLoop) != null) {
//                        playerBullets.get(bulletLoop).drawRect(paint, canvas);
//                        if (playerBullets.get(bulletLoop).getRect().left>width){
//                            playerBullets.remove(bulletLoop);
//                            bulletLoop--;
//                            continue;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private Runnable r = new Runnable() {
//        @Override
//        public void run() {
//        		invalidate();
//        }
//    };
//
//	public void run() {
//		//Remove conflict between the UI thread and the game thread.
//		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
//
//		while (ok == true){
//			//perform canvas drawing
//			if(!holder.getSurface().isValid()){//if surface is not valid
//				continue;//skip anything below it
//			}
//			Canvas c = holder.lockCanvas(); //Lock canvas, paint canvas, unlock canvas
//			this.onDraw(c);
//			holder.unlockCanvasAndPost(c);
//		}
//	}
//
//	public void pause(){
//		sound.playSelect();
//		ok = false;
//		String text;
//		Canvas c = holder.lockCanvas();
//		paint.setColor(Color.RED);
//		paint.setTextSize(height /8);
//		paint.setStyle(Style.FILL);
//		paint.setFakeBoldText(true);
//		if (gameOver()){
//			text = "GAME OVER";
//		} else {
//			text = "PAUSED";
//		}
//	    Rect bounds = new Rect();
//	    paint.getTextBounds(text, 0, text.length(), bounds);
//	    int x = c.getWidth() / 2 - (bounds.width() / 2);
//	    int y = c.getHeight() / 2 - (bounds.height() / 2);
//		while(true){
//			c.drawText(text, x, y, paint);
//			holder.unlockCanvasAndPost(c);
//			try{
//				t.join();
//			}catch(InterruptedException e){
//				e.printStackTrace();
//			}
//			break;
//		}
//		t = null;
//	}
//
//	public void resume(){
//		sound.playSelect();
//		ok = true;
//		t = new Thread(this);
//		t.start();
//	}
//
//	public void shoot(){
//		this.playerBullets.add(this.player.shoot());
//	}
//
//	private boolean enemyShot(Bullet aBullet){
//		if (enemies != null) {
//			for (int enemyLoop =0; enemyLoop < enemies.size(); enemyLoop++){
//				if (enemies.get(enemyLoop) != null) {
//					if (Collision.bulletCollision(aBullet,enemies.get(enemyLoop))){
//						if (!enemies.get(enemyLoop).sheilded) {
//                            enemies.get(enemyLoop).setHealth(enemies.get(enemyLoop).getHealth()-100);
//						}
//						if (enemies.get(enemyLoop).getHealth()<=0) {
//							sound.playEnemyDie();
//                            enemies.remove(enemyLoop);
//							myApp.setNumKills(myApp.getNumKills()+1);
//						} else {
//							sound.playEnemyHit();
//						}
//						return true;
//					}
//				}
//			}
//		}
//		if (producerEnemies != null) {
//			for (int enemyLoop =0; enemyLoop < producerEnemies.size(); enemyLoop++){
//				if (producerEnemies.get(enemyLoop) != null) {
//					if (Collision.bulletCollision(aBullet,producerEnemies.get(enemyLoop))){
//                        producerEnemies.get(enemyLoop).setHealth(producerEnemies.get(enemyLoop).getHealth()-100);
//						if (producerEnemies.get(enemyLoop).getHealth()<=0) {
//							sound.playEnemyDie();
//                            producerEnemies.remove(enemyLoop);
//							myApp.setNumKills(myApp.getNumKills()+1);
//						} else {
//							sound.playEnemyHit();
//						}
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	private void updateEnemies(Canvas canvas){
//		mutation.mutate(enemies);
//		if (enemies != null) {
//			for (int enemyLoop =0; enemyLoop < enemies.size(); enemyLoop++){
//				if (enemies.get(enemyLoop) != null) {
//					if (Collision.objectCollision(enemies.get(enemyLoop), player)) {
//                        Log.v("Death", "COLLIDED!");
//						player.setHealth(player.getHealth()-100);
//					}
//					if (enemies.get(enemyLoop).getXPos() < 0) {
//                        int type = enemies.get(enemyLoop).getType();
//                        Log.v("Death", "LET THROUGH! Type: " + type);
//						player.setHealth(player.getHealth()-100);
//					}
//                    enemies.get(enemyLoop).decisionTree(player, playerBullets, enemyBullets);
//                    enemies.get(enemyLoop).drawObject(paint, canvas);
//				}
//			}
//		}
//		if (producerEnemies != null) {
//			for (int enemyLoop =0; enemyLoop < producerEnemies.size(); enemyLoop++){
//				if (producerEnemies.get(enemyLoop) != null) {
//					if (Collision.objectCollision(producerEnemies.get(enemyLoop), player)) {
//                        Log.v("Death", "COLLIDED!");
//						player.setHealth(player.getHealth()-100);
//					}
//                    producerEnemies.get(enemyLoop).decisionTree(enemies);
//                    producerEnemies.get(enemyLoop).drawObject(paint, canvas);
//				}
//			}
//		}
//	}
//
//	private void drawText(Canvas c) {
//		paint.setColor(Color.BLACK);
//		paint.setTextSize(height /20);
//		paint.setStyle(Style.FILL);
//		paint.setFakeBoldText(true);
//		String text = "No. Kills: " + myApp.getNumKills();
//	    Rect bounds = new Rect();
//	    paint.getTextBounds(text, 0, text.length(), bounds);
//	    int x = 0;
//	    int y = height;
//		c.drawText(text, x, y, paint);
//
////		text = "Wave: " + this.currentWave.get(waveNumber).getId();
////	    bounds = new Rect();
////	    paint.getTextBounds(text, 0, text.length(), bounds);
////	    x = 0;
////	    y = bounds.height();
////		c.drawText(text, x, y, paint);
//
////        text = "Left In Wave: " + enemiesLeftInWave();
////        bounds = new Rect();
////        paint.getTextBounds(text, 0, text.length(), bounds);
////        x = width/2 - (bounds.width() / 2);
////        y = height;
////        c.drawText(text, x, y, paint);
//    }
//
//	private boolean gameOver() {
//        return player.getHealth() <= 0;
//	}
//
//	private void gameOverActivity() {
//		sound.playGameOver();
//	}
//
//	public void spawnEnemies() {
//		Random rand = new Random();
//		int maxHeight = this.height - (width/ Constants.CHARACTER_HEIGHT_DIVIDER *2);
//		int y = rand.nextInt(maxHeight) + 0;
//        rand = new Random();
//        int type = rand.nextInt(4) + 1;
//		if (checkSpawnDelay()) {
////			if (this.currentWave.get(waveNumber).getS1()>0 && s1Spawned < this.wavesStart.get(waveNumber).getS1()) {
//				enemies.add(new Standard(this.width,y,type));
////				s1Spawned++;
//				myApp.setSpawnTime(System.currentTimeMillis());
////			}
//		}
////		if (checkSpawnDelay()) {
//////			if (this.currentWave.get(waveNumber).getS2()>0 && s2Spawned < this.wavesStart.get(waveNumber).getS2()) {
////                enemies.add(new Standard(this.width,y,2));
////				s2Spawned++;
////				myApp.setSpawnTime(System.currentTimeMillis());
//////			}
////		}
////		if (checkSpawnDelay()) {
//////			if (this.currentWave.get(waveNumber).getS3()>0 && s3Spawned < this.wavesStart.get(waveNumber).getS3()) {
////                enemies.add(new Standard(this.width,y,3));
////				s3Spawned++;
////				myApp.setSpawnTime(System.currentTimeMillis());
//////			}
////		}
////		if (checkSpawnDelay()) {
//////			if (this.currentWave.get(waveNumber).getS4()>0 && s4Spawned < this.wavesStart.get(waveNumber).getS4()) {
////                enemies.add(new Standard(this.width,y,4));
////				s4Spawned++;
////				myApp.setSpawnTime(System.currentTimeMillis());
//////			}
////		}
//		if (checkSpawnDelay()) {
////			if (this.currentWave.get(waveNumber).getSProducer()>0 && sPSpawned < this.wavesStart.get(waveNumber).getSProducer()) {
//				producerEnemies.add(new StandardProducer(this.width,y));
//				sPSpawned++;
//				myApp.setSpawnTime(System.currentTimeMillis());
////			}
//		}
//		if (checkSpawnDelay()) {
////			if (this.currentWave.get(waveNumber).getB1()>0 && b1Spawned < this.wavesStart.get(waveNumber).getB1()) {
//            enemies.add(new Bomber(this.width,y,type));
//				b1Spawned++;
//				myApp.setSpawnTime(System.currentTimeMillis());
////			}
//		}
////		if (checkSpawnDelay()) {
//////			if (this.currentWave.get(waveNumber).getB2()>0 && b2Spawned < this.wavesStart.get(waveNumber).getB2()) {
////            enemies.add(new Bomber(this.width,y,2));
////				b2Spawned++;
////				myApp.setSpawnTime(System.currentTimeMillis());
//////			}
////		}
////		if (checkSpawnDelay()) {
//////			if (this.currentWave.get(waveNumber).getB3()>0 && b3Spawned < this.wavesStart.get(waveNumber).getB3()) {
////            enemies.add(new Bomber(this.width,y,3));
////				b3Spawned++;
////				myApp.setSpawnTime(System.currentTimeMillis());
//////			}
////		}
////		if (checkSpawnDelay()) {
//////			if (this.currentWave.get(waveNumber).getB4()>0 && b4Spawned < this.wavesStart.get(waveNumber).getB4()) {
////            enemies.add(new Bomber(this.width,y,4));
////				b4Spawned++;
////				myApp.setSpawnTime(System.currentTimeMillis());
//////			}
////		}
//		if (checkSpawnDelay()) {
////			if (this.currentWave.get(waveNumber).getBProducer()>0 && bPSpawned < this.wavesStart.get(waveNumber).getBProducer()) {
//            producerEnemies.add(new BomberProducer(this.width,y));
//				bPSpawned++;
//				myApp.setSpawnTime(System.currentTimeMillis());
////			}
//		}
//	}
//
//	private boolean checkSpawnDelay() {
//        Random rand = new Random();
//        int delay = rand.nextInt(Constants.SPAWN_DELAY_MAX) + Constants.SPAWN_DELAY_MIN;
//
//        return System.currentTimeMillis() - myApp.getSpawnTime() > delay;
//	}
//
//    public void newGame() {
//    	myApp.setNumKills(0);
//    }
}

