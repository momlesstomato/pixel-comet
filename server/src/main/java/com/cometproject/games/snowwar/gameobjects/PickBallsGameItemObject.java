/*    */ package com.cometproject.games.snowwar.gameobjects;
/*    */ 
/*    */ import com.cometproject.games.snowwar.Direction360;
/*    */ import com.cometproject.games.snowwar.PlayerTile;
/*    */ import com.cometproject.games.snowwar.Tile;
/*    */ 
/*    */ 
/**
 * Describes pick balls game item object behavior for the Snow War game subsystem.
 */
/*    */ public abstract class PickBallsGameItemObject
/*    */   extends GameItemObject
/*    */ {
/*    */   protected int parentFuseId;
/*    */   protected int snowBalls;
/*    */   protected Tile location;
/*    */   public int concurrentUses;
/*    */   
/**
 * Creates a pick balls game item object instance for the Snow War game subsystem.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 * @param _arg3 Arg3 supplied by the caller.
 * @param _arg4 Arg4 supplied by the caller.
 */
/*    */   public PickBallsGameItemObject(int _arg1, Tile _arg2, int _arg3, int _arg4) {
/* 17 */     super(_arg1);
/* 18 */     this.location = _arg2;
/* 19 */     this.snowBalls = _arg3;
/* 20 */     this.parentFuseId = _arg4;
/*    */   }
/*    */ 
/*    */   
/**
 * Executes direction360 for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public Direction360 direction360() {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/**
 * Executes location3 d for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public PlayerTile location3D() {
/* 30 */     return this.location.location();
/*    */   }
/*    */   
/**
 * Executes 4rk for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int _4rk() {
/* 34 */     return this.parentFuseId;
/*    */   }
/*    */   
/**
 * Executes can pick up from here for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public boolean canPickUpFromHere() {
/* 38 */     return (this.snowBalls > this.concurrentUses);
/*    */   }
/*    */   
/**
 * Executes pick up for this Snow War game contract.
 *
 * @param ammount Ammount supplied by the caller.
 * @return Value exposed by the contract.
 */
/*    */   public int pickUp(int ammount) {
/* 42 */     if (this.snowBalls < ammount) {
/* 43 */       ammount = this.snowBalls;
/*    */     }
/* 45 */     onSnowballPickup(ammount);
/* 46 */     return ammount;
/*    */   }
/*    */   
/**
 * Handles the snowball pickup callback for this Snow War game contract.
 *
 * @param paramInt Param int supplied by the caller.
 */
/*    */   public abstract void onSnowballPickup(int paramInt);
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\gameobjects\PickBallsGameItemObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */