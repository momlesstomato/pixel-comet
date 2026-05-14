/*    */ package com.cometproject.games.snowwar.gameobjects;
/*    */ 
/*    */ import com.cometproject.games.snowwar.Direction8;
/*    */ import com.cometproject.games.snowwar.SnowWarGameStage;
/*    */ import com.cometproject.games.snowwar.SnowWarRoom;
/*    */ import com.cometproject.games.snowwar.Tile;
/*    */ 
/**
 * Describes pile game object behavior for the Snow War game subsystem.
 */
/*    */ public class PileGameObject
/*    */   extends PickBallsGameItemObject
/*    */ {
/* 11 */   private static int BALLS_SIZE = 100;
/*    */   
/*    */   public SnowWarRoom currentSnowWar;
/*    */   
/*    */   private int[] boudngingData;
/*    */   private final int snowBallsCapacity;
/*    */   
/**
 * Creates a pile game object instance for the Snow War game subsystem.
 *
 * @param x X supplied by the caller.
 * @param y Y supplied by the caller.
 * @param a A supplied by the caller.
 * @param snowBalls Snow balls supplied by the caller.
 * @param parentFuseId Parent fuse id supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 * @param room Room supplied by the caller.
 */
/*    */   public PileGameObject(int x, int y, int a, int snowBalls, int parentFuseId, SnowWarGameStage _arg2, SnowWarRoom room) {
/* 19 */     super(7, _arg2.getTile(x, y), snowBalls, parentFuseId);
/* 20 */     this.snowBallsCapacity = a;
/* 21 */     if (snowBalls > 0) {
/* 22 */       _arg2._2Av(this);
/*    */     }
/* 24 */     this.boudngingData = new int[] { snowBalls * BALLS_SIZE };
/* 25 */     this.currentSnowWar = room;
/*    */ 
/*    */ 
/*    */     
/* 29 */     Tile pickUpZones = this.location.getNextTileAtRot(Direction8.N);
/* 30 */     if (pickUpZones != null) {
/* 31 */       pickUpZones.pickBallsItem = this;
/*    */     }
/* 33 */     pickUpZones = this.location.getNextTileAtRot(Direction8.S);
/* 34 */     if (pickUpZones != null) {
/* 35 */       pickUpZones.pickBallsItem = this;
/*    */     }
/* 37 */     pickUpZones = this.location.getNextTileAtRot(Direction8.E);
/* 38 */     if (pickUpZones != null) {
/* 39 */       pickUpZones.pickBallsItem = this;
/*    */     }
/* 41 */     pickUpZones = this.location.getNextTileAtRot(Direction8.W);
/* 42 */     if (pickUpZones != null) {
/* 43 */       pickUpZones.pickBallsItem = this;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/**
 * Updates the snow balls for this Snow War game contract.
 *
 * @param val Val supplied by the caller.
 */
/*    */   public void setSnowBalls(int val) {
/* 49 */     this.currentSnowWar.checksum += val * 6 - getVariable(5) * 6;
/* 50 */     this.snowBalls = val;
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
/* 55 */     if (val == 0) {
/* 56 */       return 3;
/*    */     }
/* 58 */     if (val == 1) {
/* 59 */       return this.objectId;
/*    */     }
/* 61 */     if (val == 2) {
/* 62 */       return this.location.location().x();
/*    */     }
/* 64 */     if (val == 3) {
/* 65 */       return this.location.location().y();
/*    */     }
/* 67 */     if (val == 4) {
/* 68 */       return this.snowBallsCapacity;
/*    */     }
/* 70 */     if (val == 5) {
/* 71 */       return this.snowBalls;
/*    */     }
/*    */     
/* 74 */     return this.parentFuseId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/**
 * Executes bounding data for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int[] boundingData() {
/* 80 */     return this.boudngingData;
/*    */   }
/*    */   
/**
 * Executes 4b8 for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int _4b8() {
/* 84 */     return this.snowBallsCapacity;
/*    */   }
/*    */ 
/*    */   
/**
 * Handles the snowball pickup callback for this Snow War game contract.
 *
 * @param ammount Ammount supplied by the caller.
 */
/*    */   public void onSnowballPickup(int ammount) {
/* 89 */     setSnowBalls(this.snowBalls - ammount);
/*    */     
/* 91 */     this.boudngingData = new int[] { this.snowBalls * BALLS_SIZE };
/* 92 */     if (this.snowBalls <= 0)
/* 93 */       this.location.removeGameObject(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\gameobjects\PileGameObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */