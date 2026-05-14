/*    */ package com.cometproject.games.snowwar.thread;
/*    */ 
/*    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/**
 * Describes worker tasks behavior for the Snow War game subsystem.
 */
/*    */ public class WorkerTasks
/*    */ {
/*    */   public static int serverType;
/*    */   public static final int SERVER_TINY = 0;
/*    */   public static final int SERVER_SMALL = 1;
/*    */   public static final int SERVER_NORMAL = 2;
/*    */   public static final int SERVER_LARGE = 3;
/*    */   public static final int SERVER_EXTRALARGE = 4;
/*    */   public static final int SERVER_TURBO = 5;
/*    */   public static ScheduledThreadPoolExecutor SnowWarTasks;
/*    */   
/**
 * Executes init workers for this Snow War game contract.
 */
/*    */   public static void initWorkers() {
/* 22 */     SnowWarTasks = new ScheduledThreadPoolExecutor(1);
/*    */   }
/*    */   
/**
 * Executes add task for this Snow War game contract.
 *
 * @param task Task supplied by the caller.
 * @param initDelay Init delay supplied by the caller.
 * @param repeatRate Repeat rate supplied by the caller.
 * @param worker Worker supplied by the caller.
 */
/*    */   public static void addTask(GameTask task, int initDelay, int repeatRate, ScheduledThreadPoolExecutor worker) {
/* 26 */     if (repeatRate > 0) {
/* 27 */       task.future = worker.scheduleAtFixedRate(task, initDelay, repeatRate, TimeUnit.MILLISECONDS);
/*    */     } else {
/* 29 */       task.future = worker.schedule(task, initDelay, TimeUnit.MILLISECONDS);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\thread\WorkerTasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */