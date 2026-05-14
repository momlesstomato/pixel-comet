/*    */ package com.cometproject.games.snowwar.items;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes stuff data writer behavior for the Snow War game subsystem.
 */
/*    */ public class StuffDataWriter
/*    */ {
/* 10 */   public List<Integer> savedPositions = new ArrayList<>();
/*    */   
/*    */   private byte[] bytes;
/*    */   public int writer;
/*    */   
/**
 * Creates a stuff data writer instance for the Snow War game subsystem.
 *
 * @param type Type supplied by the caller.
 * @param Size Size supplied by the caller.
 */
/*    */   public StuffDataWriter(int type, int Size) {
/* 16 */     this.bytes = new byte[Size];
/* 17 */     writeInt8(type);
/*    */   }
/*    */   
/**
 * Creates a stuff data writer instance for the Snow War game subsystem.
 *
 * @param type Type supplied by the caller.
 */
/*    */   public StuffDataWriter(int type) {
/* 21 */     this(type, 1000);
/*    */   }
/*    */   
/**
 * Returns the data for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public byte[] getData() {
/* 25 */     if (this.writer == this.bytes.length) {
/* 26 */       return this.bytes;
/*    */     }
/*    */     
/* 29 */     byte[] rtn = new byte[this.writer];
/* 30 */     for (int i = 0; i < this.writer; i++) {
/* 31 */       rtn[i] = this.bytes[i];
/*    */     }
/* 33 */     this.bytes = rtn;
/*    */     
/* 35 */     return this.bytes;
/*    */   }
/*    */   
/**
 * Executes write int32 for this Snow War game contract.
 *
 * @param in In supplied by the caller.
 */
/*    */   public void writeInt32(int in) {
/* 39 */     this.bytes[this.writer++] = (byte)(in >>> 24 & 0xFF);
/* 40 */     this.bytes[this.writer++] = (byte)(in >>> 16 & 0xFF);
/* 41 */     this.bytes[this.writer++] = (byte)(in >>> 8 & 0xFF);
/* 42 */     this.bytes[this.writer++] = (byte)(in >>> 0 & 0xFF);
/*    */   }
/*    */   
/**
 * Executes write int16 for this Snow War game contract.
 *
 * @param in In supplied by the caller.
 */
/*    */   public void writeInt16(int in) {
/* 46 */     this.bytes[this.writer++] = (byte)(in >>> 8 & 0xFF);
/* 47 */     this.bytes[this.writer++] = (byte)(in >>> 0 & 0xFF);
/*    */   }
/*    */   
/**
 * Executes write int8 for this Snow War game contract.
 *
 * @param in In supplied by the caller.
 */
/*    */   public void writeInt8(int in) {
/* 51 */     this.bytes[this.writer++] = (byte)(in >>> 0 & 0xFF);
/*    */   }
/*    */   
/**
 * Executes write string for this Snow War game contract.
 *
 * @param in In supplied by the caller.
 */
/*    */   public void writeString(String in) {
/* 55 */     int len = in.length();
/* 56 */     writeInt16(len);
/* 57 */     for (int i = 0; i < len; i++) {
/* 58 */       this.bytes[this.writer++] = (byte)(in.charAt(i) & 0xFF);
/*    */     }
/*    */   }
/*    */   
/**
 * Executes write bytes for this Snow War game contract.
 *
 * @param in In supplied by the caller.
 */
/*    */   public void writeBytes(byte[] in) {
/* 63 */     int len = in.length;
/* 64 */     writeInt16(len);
/* 65 */     for (int i = 0; i < len; i++) {
/* 66 */       this.bytes[this.writer++] = in[i];
/*    */     }
/*    */   }
/*    */ 
/*    */   
/**
 * Updates the saved for this Snow War game contract.
 *
 * @param add Add supplied by the caller.
 * @return Value exposed by the contract.
 */
/*    */   public Object setSaved(Object add) {
/* 72 */     this.savedPositions.add(Integer.valueOf(this.writer));
/* 73 */     return add;
/*    */   }
/*    */   
/**
 * Executes write saved for this Snow War game contract.
 *
 * @param add Add supplied by the caller.
 */
/*    */   public void writeSaved(Object add) {
/* 77 */     if (add instanceof Integer) {
/* 78 */       int tmp = this.writer;
/* 79 */       this.writer = ((Integer)this.savedPositions.remove(this.savedPositions.size() - 1)).intValue();
/* 80 */       writeInt32(((Integer)add).intValue());
/* 81 */       this.writer = tmp;
/*    */     } else {
/* 83 */       throw new UnsupportedOperationException("Bad Param in SetWriter " + add.getClass());
/*    */     } 
/*    */   }
/*    */   
/**
 * Executes write saved int8 for this Snow War game contract.
 *
 * @param add Add supplied by the caller.
 */
/*    */   public void writeSavedInt8(int add) {
/* 88 */     int tmp = this.writer;
/* 89 */     this.writer = ((Integer)this.savedPositions.remove(this.savedPositions.size() - 1)).intValue();
/* 90 */     writeInt8(add);
/* 91 */     this.writer = tmp;
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\items\StuffDataWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */