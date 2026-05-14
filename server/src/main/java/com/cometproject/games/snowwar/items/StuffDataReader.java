/*    */ package com.cometproject.games.snowwar.items;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes stuff data reader behavior for the Snow War game subsystem.
 */
/*    */ public class StuffDataReader
/*    */ {
/*    */   public int type;
/*    */   public byte[] bytes;
/*    */   public int reader;
/*    */   
/**
 * Creates a stuff data reader instance for the Snow War game subsystem.
 *
 * @param arr Arr supplied by the caller.
 */
/*    */   public StuffDataReader(byte[] arr) {
/* 13 */     if (arr == null) {
/*    */       
/* 15 */       this.bytes = new byte[2];
/*    */       return;
/*    */     } 
/* 18 */     this.bytes = arr;
/* 19 */     this.type = readInt8();
/*    */   }
/*    */   
/**
 * Executes read int32 for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int readInt32() {
/* 23 */     return ((this.bytes[this.reader++] & 0xFF) << 24) + ((this.bytes[this.reader++] & 0xFF) << 16) + ((this.bytes[this.reader++] & 0xFF) << 8) + (this.bytes[this.reader++] & 0xFF);
/*    */   }
/*    */   
/**
 * Executes read int16 for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int readInt16() {
/* 27 */     return ((this.bytes[this.reader++] & 0xFF) << 8) + (this.bytes[this.reader++] & 0xFF);
/*    */   }
/*    */   
/**
 * Executes read int8 for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public int readInt8() {
/* 31 */     return this.bytes[this.reader++] & 0xFF;
/*    */   }
/*    */   
/**
 * Executes read string for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*    */   public String readString() {
/* 35 */     int len = readInt16();
/* 36 */     byte[] text = new byte[len];
/* 37 */     System.arraycopy(this.bytes, this.reader, text, 0, len);
/* 38 */     this.reader += len;
/* 39 */     return new String(text);
/*    */   }
/*    */   
/**
 * Executes can read for this Snow War game contract.
 *
 * @param len Len supplied by the caller.
 * @return Value exposed by the contract.
 */
/*    */   public boolean canRead(int len) {
/* 43 */     return (this.bytes.length - this.reader >= len);
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\items\StuffDataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */