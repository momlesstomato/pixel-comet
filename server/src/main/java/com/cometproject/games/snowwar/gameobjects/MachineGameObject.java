/*    */ package com.cometproject.games.snowwar.gameobjects;
/*    */ 
/*    */

import com.cometproject.games.snowwar.*;
import com.cometproject.games.snowwar.gameevents.AddBallToMachine;

/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ 
/**
 * Describes machine game object behavior for the Snow War game subsystem.
 */
/*    */ public class MachineGameObject
/*    */   extends PickBallsGameItemObject
/*    */ {
/*    */   private static final int SNOWBALLGENERATOR_TIME = 100;
/* 15 */   public static int[] boundingData = new int[] { 1200 };
/*    */   
/*    */   private final int snowBallsCapacity;
/*    */   
/*    */   private final Direction8 direction;
/* 20 */   private int snowBallGeneratorTimer = 100;
/*    */   
/*    */   public SnowWarRoom currentSnowWar;
/*    */   
/**
 * Creates a machine game object instance for the Snow War game subsystem.
 *
 * @param x X supplied by the caller.
 * @param y Y supplied by the caller.
 * @param rot Rot supplied by the caller.
 * @param a A supplied by the caller.
 * @param b B supplied by the caller.
 * @param c C supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 * @param room Room supplied by the caller.
 */
/*    */   public MachineGameObject(int x, int y, int rot, int a, int b, int c, SnowWarGameStage _arg2, SnowWarRoom room) {
/* 25 */     super(8, _arg2.getTile(x, y), b, c);
/* 26 */     this.snowBallsCapacity = a;
/* 27 */     this.direction = Direction8.getDirection(rot);
/* 28 */     _arg2._2Av(this);
/* 29 */     this.currentSnowWar = room;
/*    */     
/* 31 */     Tile frontTile = this.location.getNextTileAtRot(Direction8.getDirection((rot + 4) % 8));
/* 32 */     if (frontTile != null) {
/* 33 */       frontTile.pickBallsItem = this;
/*    */     }
/*    */   }
/*    */   
/**
 * Updates the snow balls for this Snow War game contract.
 *
 * @param val Val supplied by the caller.
 */
/*    */   public void setSnowBalls(int val) {
/* 38 */     this.currentSnowWar.checksum += val * 7 - getVariable(6) * 7;
/* 39 */     this.snowBalls = val;
/*    */   }
/*    */ 
/*    */   
/**
 * Returns the variable for this Snow War game contract.
 *
 * @param val Val supplied by the caller.
 * @return Value exposed by the contract.
 */
/*    */   public int getVariable(int val) {
/* 44 */     if (val == 0) {
/* 45 */       return 4;
/*    */     }
/* 47 */     if (val == 1) {
/* 48 */       return this.objectId;
/*    */     }
/* 50 */     if (val == 2) {
/* 51 */       return this.location.location().x();
/*    */     }
/* 53 */     if (val == 3) {
/* 54 */       return this.location.location().y();
/*    */     }
/* 56 */     if (val == 4) {
/* 57 */       return this.direction.getRot();
/*    */     }
/* 59 */     if (val == 5) {
/* 60 */       return this.snowBallsCapacity;
/*    */     }
/* 62 */     if (val == 6) {
/* 63 */       return this.snowBalls;
/*    */     }
/*    */     
/* 66 */     return this.parentFuseId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/**
 * Executes subturn for this Snow War game contract.
 *
 * @param unused Unused supplied by the caller.
 */
/*    */   public void subturn(SynchronizedGameStage unused) {
/* 72 */     if (this.snowBallGeneratorTimer > 0) {
/* 73 */       this.snowBallGeneratorTimer--;
/*    */     } else {
/* 75 */       this.snowBallGeneratorTimer = 100;
/* 76 */       synchronized (this.currentSnowWar.gameEvents) {
/* 77 */         this.currentSnowWar.gameEvents.add(new AddBallToMachine(this));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/**
 * Executes bounding data for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int[] boundingData() {
/* 84 */     return boundingData;
/*    */   }
/*    */   
/**
 * Executes add snow ball for this Snow War game contract.
 */
/*    */   public void addSnowBall() {
/* 88 */     if (this.snowBalls < this.snowBallsCapacity) {
/* 89 */       setSnowBalls(this.snowBalls + 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/**
 * Handles the snowball pickup callback for this Snow War game contract.
 *
 * @param ammount Ammount supplied by the caller.
 */
/*    */   public void onSnowballPickup(int ammount) {
/* 95 */     setSnowBalls(this.snowBalls - ammount);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\gameobjects\MachineGameObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */