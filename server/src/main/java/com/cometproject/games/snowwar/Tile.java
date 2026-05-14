/*     */ package com.cometproject.games.snowwar;
/*     */ 
/*     */ import com.cometproject.games.snowwar.gameobjects.GameItemObject;
/*     */ import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
/*     */ import com.cometproject.games.snowwar.gameobjects.PickBallsGameItemObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/**
 * Describes tile behavior for the Snow War game subsystem.
 */
/*     */ public class Tile
/*     */ {
/*  13 */   public static int TILE_SIZE = 3200;
/*  14 */   public static int _37G = MathUtil._43Z((TILE_SIZE / 2));
/*  15 */   public static int _03y = TILE_SIZE + _37G;
/*  16 */   public static int _1L = (int)Math.sqrt((TILE_SIZE * TILE_SIZE + TILE_SIZE * TILE_SIZE));
/*     */   
/*     */   private final PlayerTile _location;
/*     */   
/*     */   private final Tile[] _3ob;
/*     */   
/*     */   private GameItemObject _0Zr;
/*     */   public int[] _4gH;
/*     */   private boolean blocked;
/*     */   private int _height;
/*     */   public PickBallsGameItemObject pickBallsItem;
/*     */   private final List<GamefuseObject> _0E8;
/*     */   
/**
 * Creates a tile instance for the Snow War game subsystem.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 */
/*     */   public Tile(int _arg1, int _arg2) {
/*  30 */     this._3ob = new Tile[8];
/*  31 */     this._4gH = new int[] { _arg1, _arg2, 0 };
/*  32 */     this._location = new PlayerTile(_arg1 * TILE_SIZE, _arg2 * TILE_SIZE, 0);
/*  33 */     this._0E8 = new ArrayList<>();
/*     */   }
/*     */   
/**
 * Executes 4m c for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static int _4mC(int _arg1) {
/*  37 */     return MathUtil._43Z(((_arg1 + _37G) / TILE_SIZE));
/*     */   }
/*     */   
/**
 * Executes 3 fs for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static int _3FS(int _arg1) {
/*  41 */     return MathUtil._43Z(((_arg1 + _37G) / TILE_SIZE));
/*     */   }
/*     */   
/**
 * Executes 3b for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static int _3b(int _arg1) {
/*  45 */     return _arg1 * TILE_SIZE;
/*     */   }
/*     */   
/**
 * Executes 3 qo for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public static int _3Qo(int _arg1) {
/*  49 */     return _arg1 * TILE_SIZE;
/*     */   }
/*     */ 
/*     */   
/**
 * Executes fuse objects for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public List<GamefuseObject> fuseObjects() {
/*  54 */     return this._0E8;
/*     */   }
/*     */ 
/*     */   
/**
 * Executes l r for this Snow War game contract.
 *
 * @param fuseItem Fuse item supplied by the caller.
 */
/*     */   public void _lR(GamefuseObject fuseItem) {
/*  59 */     this._0E8.add(fuseItem);
/*  60 */     _4AO((int)(fuseItem.baseItem.Height * TILE_SIZE));
/*     */   }
/*     */   
/**
 * Executes 4 ao for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 */
/*     */   public void _4AO(int _arg1) {
/*  64 */     this._height += _arg1;
/*  65 */     if (this._height < 0) {
/*  66 */       this._height = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/**
 * Executes 4i l for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public int[] _4iL() {
/*  72 */     return this._4gH;
/*     */   }
/*     */   
/**
 * Executes location for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public PlayerTile location() {
/*  76 */     return this._location;
/*     */   }
/*     */   
/**
 * Indicates whether too away applies to this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public boolean isTooAway(PlayerTile _arg1) {
/*  80 */     int local1 = this._location.x() - _arg1.x();
/*  81 */     if (local1 < 0) {
/*  82 */       local1 = -local1;
/*     */     }
/*  84 */     int local2 = this._location.y() - _arg1.y();
/*  85 */     if (local2 < 0) {
/*  86 */       local2 = -local2;
/*     */     }
/*  88 */     return (local1 < _37G && local2 < _37G);
/*     */   }
/*     */   
/**
 * Executes 3i t for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 */
/*     */   public void _3iT(Tile _arg1, Direction8 _arg2) {
/*  92 */     _2UR(_arg1, _arg2);
/*  93 */     _arg1._2UR(this, _arg2.rotateDirection180Degrees());
/*     */   }
/*     */   
/*     */   private void _2UR(Tile _arg1, Direction8 _arg2) {
/*  97 */     this._3ob[_arg2.getRot()] = _arg1;
/*     */   }
/*     */   
/**
 * Returns the next tile at rot for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public Tile getNextTileAtRot(Direction8 _arg1) {
/* 101 */     return this._3ob[_arg1.getRot()];
/*     */   }
/*     */ 
/*     */   
/**
 * Indicates whether open applies to this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public boolean isOpen(GameItemObject _arg1) {
/* 106 */     boolean isBlocked = false;
/* 107 */     if (this._0E8.size() == 1) {
/* 108 */       isBlocked = !((GamefuseObject)this._0E8.get(0)).baseItem.allowWalk;
/*     */     }
/* 110 */     else if (!this._0E8.isEmpty()) {
/* 111 */       isBlocked = true;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     return (!isBlocked && this._0Zr == null && !this.blocked);
/*     */   }
/*     */ 
/*     */   
/**
 * Executes 1t h for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public boolean _1tH(GameItemObject _arg1) {
/* 120 */     boolean _arg2 = false;
/* 121 */     if (this._0Zr == null) {
/* 122 */       this._0Zr = _arg1;
/* 123 */       _arg2 = true;
/*     */     } 
/* 125 */     return _arg2;
/*     */   }
/*     */ 
/*     */   
/**
 * Executes remove game object for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public GameItemObject removeGameObject() {
/* 130 */     GameItemObject local0 = null;
/* 131 */     if (this._0Zr != null) {
/* 132 */       local0 = this._0Zr;
/* 133 */       this._0Zr = null;
/*     */     } 
/* 135 */     return local0;
/*     */   }
/*     */   
/**
 * Executes 4fe for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public GameItemObject _4fe() {
/* 139 */     return this._0Zr;
/*     */   }
/*     */   
/**
 * Executes 05 z for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public HumanGameObject _05Z() {
/* 143 */     if (this._0Zr != null && this._0Zr instanceof HumanGameObject) {
/* 144 */       return (HumanGameObject)this._0Zr;
/*     */     }
/* 146 */     return null;
/*     */   }
/*     */   
/**
 * Executes 40 t for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public HumanGameObject _40T() {
/* 150 */     HumanGameObject local0 = _05Z();
/* 151 */     if (local0 != null) {
/* 152 */       this._0Zr = null;
/*     */     }
/* 154 */     return local0;
/*     */   }
/*     */ 
/*     */   
/**
 * Executes distance to for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public int distanceTo(Tile _arg1) {
/* 159 */     return this._location.distanceTo(_arg1.location());
/*     */   }
/*     */ 
/*     */   
/**
 * Executes direction to for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public Direction8 directionTo(Tile _arg1) {
/* 164 */     return this._location.directionTo(_arg1.location());
/*     */   }
/*     */ 
/*     */   
/**
 * Returns the node at for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public Tile getNodeAt(Direction8 _arg1) {
/* 169 */     return this._3ob[_arg1.getRot()];
/*     */   }
/*     */ 
/*     */   
/**
 * Executes direction is blocked for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public boolean directionIsBlocked(Direction8 _arg1, GameItemObject _arg2) {
/* 174 */     return isOpen(_arg2);
/*     */   }
/*     */ 
/*     */   
/**
 * Returns the path cost for this Snow War game contract.
 *
 * @param _arg1 Arg1 supplied by the caller.
 * @param _arg2 Arg2 supplied by the caller.
 * @return Value exposed by the contract.
 */
/*     */   public int getPathCost(Direction8 _arg1, GameItemObject _arg2) {
/* 179 */     if (_arg1._AC()) {
/* 180 */       return TILE_SIZE;
/*     */     }
/*     */     
/* 183 */     return _1L;
/*     */   }
/*     */   
/**
 * Executes height for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public int height() {
/* 187 */     return this._height;
/*     */   }
/*     */ 
/*     */   
/**
 * Executes to string for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
/*     */   public String toString() {
/* 192 */     return " X:" + this._location.x() + " Y:" + this._location.y() + " Z:" + this._location.z();
/*     */   }
/*     */   
/**
 * Updates the blocked for this Snow War game contract.
 *
 * @param block Block supplied by the caller.
 */
/*     */   public void setBlocked(boolean block) {
/* 196 */     this.blocked = block;
/*     */   }
/*     */ }


/* Location:              C:\Users\Custom\Documents\SWFs Habbis\CMS\app\client\socket\jd-gui-windows-1.6.6\tambaleo.jar!\com\cometproject\games\snowwar\Tile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */