/*    */ package com.cometproject.games.snowwar.gameevents;
/*    */ 
/*    */

import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
import com.cometproject.games.snowwar.gameobjects.SnowBallGameObject;

/*    */
/*    */

/*    */
/**
 * Describes create snow ball behavior for the Snow War game subsystem.
 */
/*    */ public class CreateSnowBall
/*    */   extends Event {
/*    */   public SnowBallGameObject ball;
/*    */   public HumanGameObject player;
/*    */   public int x;
/*    */   public int y;
/*    */   public int type;
/*    */   
/**
 * Creates a create snow ball instance for the Snow War game subsystem.
 *
 * @param ball Ball supplied by the caller.
 * @param player Player supplied by the caller.
 * @param x X supplied by the caller.
 * @param y Y supplied by the caller.
 * @param type Type supplied by the caller.
 */
/*    */   public CreateSnowBall(SnowBallGameObject ball, HumanGameObject player, int x, int y, int type) {
/* 16 */     this.EventType = 8;
/* 17 */     this.ball = ball;
/* 18 */     this.player = player;
/* 19 */     this.x = x;
/* 20 */     this.y = y;
/* 21 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/**
 * Executes apply for this Snow War game contract.
 */
/*    */   public void apply() {
/* 26 */     this.ball.initialize(this.player.location3D().x(), this.player.location3D().y(), this.type, this.x, this.y, this.player);
/* 27 */     this.ball.GenerateCHECKSUM(this.player.currentSnowWar, 1);
/* 28 */     this.player.currentSnowWar.addGameObject(this.ball);
/*    */   }
/*    */ }