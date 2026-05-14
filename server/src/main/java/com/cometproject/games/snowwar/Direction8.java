/*     */ package com.cometproject.games.snowwar;
/*     */ 
/*     */ 
/**
 * Describes direction8 behavior for the Snow War game subsystem.
 */
/*     */ public class Direction8
/*     */ {
/*   6 */   public static Direction8[] DIRECTIONS = new Direction8[8];
/*   7 */   public static Direction8 N = new Direction8(0, "N", 0, -1);
/*   8 */   public static Direction8 NE = new Direction8(1, "NE", 1, -1);
/*   9 */   public static Direction8 E = new Direction8(2, "E", 1, 0);
/*  10 */   public static Direction8 SE = new Direction8(3, "SE", 1, 1);
/*  11 */   public static Direction8 S = new Direction8(4, "S", 0, 1);
/*  12 */   public static Direction8 SW = new Direction8(5, "SW", -1, 1);
/*  13 */   public static Direction8 W = new Direction8(6, "W", -1, 0);
/*  14 */   public static Direction8 NW = new Direction8(7, "NW", -1, -1);
/*     */   
/*     */   private final int rot;
/*     */   private final int xDiff;
/*     */   private final int yDiff;
/*     */   private final String rotName;
/*     */   
/**
 * Creates a direction8 instance for the Snow War game subsystem.
 *
 * @param r R supplied by the caller.
 * @param rName R name supplied by the caller.
 * @param diffx Diffx supplied by the caller.
 * @param diffy Diffy supplied by the caller.
 */
/*     */   public Direction8(int r, String rName, int diffx, int diffy) {
/*  22 */     this.rot = r;
/*  23 */     this.rotName = rName;
/*  24 */     this.xDiff = diffx;
/*  25 */     this.yDiff = diffy;
/*  26 */     DIRECTIONS[r] = this;
/*     */   }
/*     */   
/**
 * Returns the direction for this Snow War game contract.
 *
 * @param dir Dir supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static Direction8 getDirection(int dir) {
/*  30 */     if (dir < 0 || dir > 7) {
/*  31 */       return N;
/*     */     }
/*     */     
/*  34 */     return DIRECTIONS[dir];
/*     */   }
/*     */   
/**
 * Executes validate direction8 value for this Snow War game contract.
 *
 * @param dir Dir supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static int validateDirection8Value(int dir) {
/*  38 */     return dir & 0x7;
/*     */   }
/*     */   
/**
 * Returns the rot for this Snow War game contract.
 *
 * @param curX Cur x supplied by the caller.
 * @param curY Cur y supplied by the caller.
 * @param targetX Target x supplied by the caller.
 * @param targetY Target y supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static Direction8 getRot(int curX, int curY, int targetX, int targetY) {
/*  42 */     int deltaX = targetX - curX;
/*  43 */     int deltaY = targetY - curY;
/*     */     
/*  45 */     if (deltaX == 0) {
/*  46 */       if (deltaY < 0) {
/*  47 */         return N;
/*     */       }
/*     */       
/*  50 */       if (deltaY > 0) {
/*  51 */         return S;
/*     */       }
/*     */     } 
/*     */     
/*  55 */     if (deltaX > 0) {
/*  56 */       if (deltaY < 0) {
/*  57 */         return NE;
/*     */       }
/*     */       
/*  60 */       if (deltaY == 0) {
/*  61 */         return E;
/*     */       }
/*     */       
/*  64 */       if (deltaY > 0) {
/*  65 */         return SE;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     if (deltaX < 0) {
/*  70 */       if (deltaY < 0) {
/*  71 */         return NW;
/*     */       }
/*     */       
/*  74 */       if (deltaY == 0) {
/*  75 */         return W;
/*     */       }
/*     */       
/*  78 */       if (deltaY > 0) {
/*  79 */         return SW;
/*     */       }
/*     */     } 
/*  82 */     System.out.println("ERROR: Direction8.getRot == NULL");
/*     */     
/*  84 */     return null;
/*     */   }
/*     */   
/**
 * Returns the rot for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public int getRot() {
/*  88 */     return this.rot;
/*     */   }
/*     */   
/**
 * Executes rotate direction180 degrees for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public Direction8 rotateDirection180Degrees() {
/*  92 */     return getDirectionAtRot(4);
/*     */   }
/*     */   
/**
 * Executes rotate direction45 degrees for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public Direction8 rotateDirection45Degrees(boolean _arg1) {
/*  96 */     return getDirectionAtRot(_arg1 ? 1 : -1);
/*     */   }
/*     */   
/**
 * Executes rotate direction90 degrees for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public Direction8 rotateDirection90Degrees(boolean _arg1) {
/* 100 */     return getDirectionAtRot(_arg1 ? 2 : -2);
/*     */   }
/*     */ 
/*     */   
/**
 * Executes ac for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public boolean _AC() {
/* 105 */     return (this.rot % 2 == 0);
/*     */   }
/*     */ 
/*     */   
/**
 * Executes 3f4 for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public int _3f4() {
/* 110 */     return this.rot;
/*     */   }
/*     */   
/**
 * Returns the direction at rot for this Snow War game contract.
 *
 * @param diff Diff supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public Direction8 getDirectionAtRot(int diff) {
/* 114 */     return DIRECTIONS[validateDirection8Value(this.rot + diff)];
/*     */   }
/*     */ 
/*     */   
/**
 * Executes to string for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public String toString() {
/* 119 */     return this.rotName + "(" + Integer.toString(this.rot) + ")";
/*     */   }
/*     */   
/**
 * Returns the rot name for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public String getRotName() {
/* 123 */     return this.rotName;
/*     */   }
/*     */   
/**
 * Returns the diff x for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public int getDiffX() {
/* 127 */     return this.xDiff;
/*     */   }
/*     */   
/**
 * Returns the diff y for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public int getDiffY() {
/* 131 */     return this.yDiff;
/*     */   }
/*     */   
/**
 * Executes have direction for this Snow War game contract.
 *
 * @param find Find supplied by the caller.
 * @param directions Directions supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static boolean haveDirection(Direction8 find, Direction8... directions) {
/* 135 */     for (Direction8 val : directions) {
/* 136 */       if (find == val) {
/* 137 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\Direction8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */