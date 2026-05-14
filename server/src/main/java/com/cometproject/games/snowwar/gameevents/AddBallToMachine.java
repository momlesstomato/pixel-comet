/*    */ package com.cometproject.games.snowwar.gameevents;
/*    */ 
/*    */ import com.cometproject.games.snowwar.gameobjects.MachineGameObject;
/*    */ 
/**
 * Describes add ball to machine behavior for the Snow War game subsystem.
 */
/*    */ public class AddBallToMachine
/*    */   extends Event
/*    */ {
/*    */   public MachineGameObject gameItem;
/*    */   
/**
 * Creates a add ball to machine instance for the Snow War game subsystem.
 *
 * @param gameItem Game item supplied by the caller.
 */
/*    */   public AddBallToMachine(MachineGameObject gameItem) {
/* 11 */     this.EventType = 11;
/* 12 */     this.gameItem = gameItem;
/*    */   }
/*    */ 
/*    */   
/**
 * Executes apply for this Snow War game contract.
 */
/*    */   public void apply() {
/* 17 */     this.gameItem.addSnowBall();
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\gameevents\AddBallToMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */