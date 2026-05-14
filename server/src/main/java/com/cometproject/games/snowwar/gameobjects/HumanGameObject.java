package com.cometproject.games.snowwar.gameobjects;

import com.cometproject.games.snowwar.*;
import com.cometproject.games.snowwar.data.SnowWarPlayerData;
import com.cometproject.games.snowwar.gameevents.PickBallFromGameItem;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.utilities.RandomUtil;

import java.util.List;

/**
 * Describes human game object behavior for the Snow War game subsystem.
 */
public class HumanGameObject extends GameItemObject {
    public static final int _302 = 534;
    public static final int MAX_HEALTH = 5;
    public static final int MAX_SNOWBALLS = 5;
    public static final int PICKUPBALL_TIME = 20;
    public static final int RESPAWNING_TIME = 100;
    public static final int NODAMAGE_TIME = 60;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_PICKUPBALL = 1;
    public static final int STATUS_RESPAWNING = 2;
    public static final int STATUS_NODAMAGE = 3;
    public static final int BALLTHROW_FIRERATE = 5;
    public static final int[] boundingData = new int[]{1600};
    public static final int collisionHeight = 5000;
    private static final int SCORE_KILL = 5;
    private static final int SCORE_HIT = 1;
    public SnowWarRoom currentSnowWar;
    public SnowWarPlayerData snowWarPlayer;
    public Session cn;
    public int userId;
    public String userName;
    public String look;
    public String motto;
    public String sex;
    public int hits;
    public int kills;
    public int score;
    public int status;
    public int team;
    private final PlayerTile currentLocation;
    private final PlayerTile moveTarget;
    public Direction8 humanDir;
    public Tile currentTile;
    private Tile nextTile;
    private int health;
    private int snowBalls;
    protected int unused;
    private int taskTime;
    private int currentStatus;
    private int fireRateLimiter;
    private int pickUpLimiter;
    public boolean stageLoaded;

    /**
     * Creates a human game object instance for the Snow War game subsystem.
     *
     * @param room Room participating in the operation.
     * @param teamId Team id supplied by the caller.
     */
    public HumanGameObject(SnowWarRoom room, int teamId) {
        super(19);
        SpawnPoint spawn;
        this.health = 5;
        this.snowBalls = 5;
        this.currentSnowWar = room;
        this.currentLocation = new PlayerTile(0, 0, 0);
        this.moveTarget = new PlayerTile(0, 0, 0);
        this.humanDir = Direction8.SE;
        this.team = teamId;

        if (teamId == 1) {
            int i = RandomUtil.getRandomInt(0, this.currentSnowWar.ArenaType.spawnsBLUE.size() - 1);
            spawn = this.currentSnowWar.ArenaType.spawnsBLUE.get(i);
        } else {
            int i = RandomUtil.getRandomInt(0, this.currentSnowWar.ArenaType.spawnsRED.size() - 1);
            spawn = this.currentSnowWar.ArenaType.spawnsRED.get(i);
        }

        this.currentTile = this.currentSnowWar.map.getTile(spawn.x, spawn.y);

        if(this.currentTile != null) {
            this.currentTile._1tH(this);
            this.currentLocation.setXYZ(this.currentTile.location());
            this.moveTarget.setXYZ(this.currentTile.location());
        }
    }

    /**
     * Executes clean data for this Snow War game contract.
     */
    public void cleanData() {
        this.snowWarPlayer.setRoom(null);
        this.snowWarPlayer.setHumanObject(null);
    }

    /**
     * Updates the cur location for this Snow War game contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public void setCurLocation(int x, int y) {
        this.currentSnowWar.checksum += (x * 3) - (getVariable(2) * 3);
        this.currentSnowWar.checksum += (y * 4) - (getVariable(3) * 4);
        this.currentLocation.setXY(x, y);
    }

    /**
     * Updates the cur location for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setCurLocation(PlayerTile val) {
        this.currentSnowWar.checksum += (val.x() * 3) - (getVariable(2) * 3);
        this.currentSnowWar.checksum += (val.y() * 4) - (getVariable(3) * 4);
        this.currentLocation.setXYZ(val);
    }

    /**
     * Updates the current tile for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setCurrentTile(Tile val) {
        this.currentSnowWar.checksum += (val._4gH[0] * 5) - (getVariable(4) * 5);
        this.currentSnowWar.checksum += (val._4gH[1] * 6) - (getVariable(5) * 6);
        if (this.nextTile == null) {
            this.currentSnowWar.checksum += (val._4gH[0] * 13) - (getVariable(12) * 13);
            this.currentSnowWar.checksum += (val._4gH[1] * 14) - (getVariable(13) * 14);
        }
        this.currentTile = val;
    }

    /**
     * Updates the task time for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setTaskTime(int val) {
        this.currentSnowWar.checksum += (val * 11) - (getVariable(10) * 11);
        this.taskTime = val;
    }

    /**
     * Updates the next tile for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setNextTile(Tile val) {
        if (this.nextTile != null) {
            if (val == null) {
                this.currentSnowWar.checksum -= this.currentTile._4gH[0] * 13 - getVariable(12) * 13;
                this.currentSnowWar.checksum -= this.currentTile._4gH[1] * 14 - getVariable(13) * 14;
            } else {
                this.currentSnowWar.checksum += val._4gH[0] * 13 - getVariable(12) * 13;
                this.currentSnowWar.checksum += val._4gH[1] * 14 - getVariable(13) * 14;
            }
        } else if (val != null) {
            this.currentSnowWar.checksum += val._4gH[0] * 13 - getVariable(12) * 13;
            this.currentSnowWar.checksum += val._4gH[1] * 14 - getVariable(13) * 14;
        }
        this.nextTile = val;
    }

    /**
     * Updates the rot for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setRot(Direction8 val) {
        this.currentSnowWar.checksum += val.getRot() * 7 - getVariable(6) * 7;
        this.humanDir = val;
    }

    /**
     * Updates the health for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setHealth(int val) {
        this.currentSnowWar.checksum += val * 8 - getVariable(7) * 8;
        this.health = val;
    }

    /**
     * Updates the snow balls for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setSnowBalls(int val) {
        this.currentSnowWar.checksum += val * 9 - getVariable(8) * 9;
        this.snowBalls = val;
    }

    /**
     * Updates the current status for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setCurrentStatus(int val) {
        this.currentSnowWar.checksum += val * 12 - getVariable(11) * 12;
        this.currentStatus = val;
    }

    /**
     * Updates the move target for this Snow War game contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public void setMoveTarget(int x, int y) {
        this.currentSnowWar.checksum += x * 15 - getVariable(14) * 15;
        this.currentSnowWar.checksum += y * 16 - getVariable(15) * 16;
        this.moveTarget.setXY(x, y);
    }

    /**
     * Updates the move target for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setMoveTarget(PlayerTile val) {
        this.currentSnowWar.checksum += val.x() * 15 - getVariable(14) * 15;
        this.currentSnowWar.checksum += val.y() * 16 - getVariable(15) * 16;
        this.moveTarget.setXYZ(val);
    }

    /**
     * Updates the score for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setScore(int val) {
        this.currentSnowWar.checksum += val * 17 - getVariable(16) * 17;
        this.score = val;
    }

    /**
     * Updates the team for this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void setTeam(int val) {
        this.currentSnowWar.checksum += val * 18 - getVariable(17) * 18;
        this.team = val;
    }

    /**
     * Returns the variable for this Snow War game contract.
     *
     * @param var Var supplied by the caller.
     * @return Value exposed by the contract.
     */
    public int getVariable(int var) {
        if (var == 0) {
            return 5;
        }
        if (var == 1) {
            return this.objectId;
        }
        if (var == 2) {
            return this.currentLocation.x();
        }
        if (var == 3) {
            return this.currentLocation.y();
        }
        if (var == 4) {
            return this.currentTile._4gH[0];
        }
        if (var == 5) {
            return this.currentTile._4gH[1];
        }
        if (var == 6) {
            return this.humanDir.getRot();
        }
        if (var == 7) {
            return this.health;
        }
        if (var == 8) {
            return this.snowBalls;
        }
        if (var == 9) {
            return this.unused;
        }
        if (var == 10) {
            return this.taskTime;
        }
        if (var == 11) {
            return this.currentStatus;
        }
        if (var == 12) {
            return (this.nextTile != null) ? this.nextTile._4gH[0] : this.currentTile._4gH[0];
        }
        if (var == 13) {
            return (this.nextTile != null) ? this.nextTile._4gH[1] : this.currentTile._4gH[1];
        }
        if (var == 14) {
            return this.moveTarget.x();
        }
        if (var == 15) {
            return this.moveTarget.y();
        }
        if (var == 16) {
            return this.score;
        }
        if (var == 17) {
            return this.team;
        }

        if (var == 18) {
            return userId;
        }

        return 0;
    }

    /**
     * Executes bounding data for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public int[] boundingData() {
        return boundingData;
    }

    /**
     * Executes location3 d for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public PlayerTile location3D() {
        return this.currentLocation;
    }

    /**
     * Executes direction360 for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public Direction360 direction360() {
        return null;
    }

    /**
     * Executes do current task for this Snow War game contract.
     */
    public void doCurrentTask() {
        if (this.currentStatus == 2) {
            setHealth(5);
            setCurrentStatus(3);
            setTaskTime(60);
            return;
        }
        if (this.currentStatus == 1) {
            setSnowBalls(this.snowBalls + 1);
        }
        setCurrentStatus(0);
    }

    /**
     * Executes subturn for this Snow War game contract.
     *
     * @param unused Unused supplied by the caller.
     */
    public void subturn(SynchronizedGameStage unused) {
        if (this.taskTime > 0) {
            if (this.taskTime == 1) {
                doCurrentTask();
            }
            setTaskTime(this.taskTime - 1);
        }
        if (this.fireRateLimiter > 0) {
            this.fireRateLimiter--;
        }

        if (this.pickUpLimiter > 0) {
            this.pickUpLimiter--;
        }

        if (canWalk() && this.currentTile != null) {
            if (this.nextTile != null) {
                doWalkSteep();
            } else {
                if (this.pickUpLimiter < 1 && this.currentTile.pickBallsItem != null && this.currentTile.pickBallsItem.canPickUpFromHere()) {
                    this.currentTile.pickBallsItem.concurrentUses++;
                    this.pickUpLimiter = 20;
                    synchronized (this.currentSnowWar.gameEvents) {
                        this.currentSnowWar.gameEvents.add(new PickBallFromGameItem(this, this.currentTile.pickBallsItem));
                    }
                }

                if (!this.currentTile.isTooAway(this.moveTarget)) {
                    int rot = Direction360.getRot(this.moveTarget.x() - this.currentTile.location().x(), this.moveTarget.y() - this.currentTile.location().y());
                    Direction8 direction = Direction360.direction360ValueToDirection8(rot);
                    setNextTile(this.currentTile.getNextTileAtRot(direction));

                    if (this.nextTile == null || !this.nextTile.isOpen(this)) {
                        if (this.nextTile != null && !this.nextTile.isOpen(this) && this.moveTarget.isSamePosition(this.nextTile.location())) {
                            setNextTile(null);
                            stopWalk();
                            return;
                        }
                        direction = direction.getDirectionAtRot(-1);
                        setNextTile(this.currentTile.getNextTileAtRot(direction));
                        if (this.nextTile == null || !this.nextTile.isOpen(this)) {
                            direction = direction.getDirectionAtRot(2);
                            setNextTile(this.currentTile.getNextTileAtRot(direction));
                            if (this.nextTile == null || !this.nextTile.isOpen(this)) {
                                setNextTile(null);
                                stopWalk();
                                return;
                            }
                        }
                    }
                    if (this.nextTile != null) {
                        this.currentTile._40T();
                        this.nextTile._1tH(this);
                        setRot(direction);
                        doWalkSteep();
                    }
                }
            }
        }
    }

    /**
     * Indicates whether this Snow War game contract can walk to.
     *
     * @param walkX Walk x supplied by the caller.
     * @param walkY Walk y supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canWalkTo(int walkX, int walkY) {
        if (!canWalk() || this.currentTile == null) {
            return false;
        }

        Tile localCurrent = this.currentTile;
        PlayerTile localTarget = new PlayerTile(walkX, walkY, 0);
        for (int i = 0; i < 50; i++) {
            int rot = Direction360.getRot(localTarget.x() - localCurrent.location().x(), localTarget.y() - localCurrent.location().y());
            Direction8 direction = Direction360.direction360ValueToDirection8(rot);
            Tile localNext = localCurrent.getNextTileAtRot(direction);
            if (localNext == null || !localNext.isOpen(this)) {
                if (localNext != null && !localNext.isOpen(this)) {
                    if (localTarget.isSamePosition(localNext.location())) {
                        return true;
                    }
                }
                direction = direction.getDirectionAtRot(-1);
                localNext = localCurrent.getNextTileAtRot(direction);
                if (localNext == null || !localNext.isOpen(this)) {
                    direction = direction.getDirectionAtRot(2);
                    localNext = localCurrent.getNextTileAtRot(direction);
                    if (localNext == null || !localNext.isOpen(this)) {
                        return false;
                    }
                }
            }

            localCurrent = localNext;
            localNext = null;
            if (localCurrent.isTooAway(localTarget)) {
                return true;
            }
        }
        return false;
    }

    private void doFire() {
        int pow = 15;
        Tile tile = this.currentTile.getNextTileAtRot(this.humanDir);

        if (this.humanDir == Direction8.N || this.humanDir == Direction8.W) {
            tile = tile.getNextTileAtRot(this.humanDir);
        }

        if (tile == null) {
            return;
        }

        List<GamefuseObject> items = tile.fuseObjects();
        if (items.size() == 1) {
            GamefuseObject item = items.get(0);
            Direction8 dir = Direction8.getDirection(item.Rot);
            if (dir == this.humanDir && item.baseItem.Name.equals("ads_igorraygun")) {
                this.snowWarPlayer.throwSnowballFlood(item.X + dir.getDiffX() * pow, item.Y + dir.getDiffY() * pow);
            }
        }
    }

    /**
     * Executes clean tiles for this Snow War game contract.
     */
    public void cleanTiles() {
        if (this.currentTile != null && this.currentTile._05Z() == this) {
            this.currentTile._40T();
        }

        if (this.nextTile != null && this.nextTile._05Z() == this) {
            this.nextTile._40T();
        }
    }

    /**
     * Handles the remove callback for this Snow War game contract.
     */
    public void onRemove() {
        cleanTiles();
        if (this.snowWarPlayer != null) {
            cleanData();
        }
    }

    private void doWalkSteep() {
        int local0 = this.nextTile.location().x();
        int local1 = this.currentLocation.x();
        int local2 = local1 - local0;

        if (local2 != 0) {
            if (local2 < 0) {
                if (local2 > -534) {
                    local1 = local0;
                } else {
                    local1 += 534;
                }
            } else if (local2 < 534) {
                local1 = local0;
            } else {
                local1 -= 534;
            }
        }

        int local3 = this.nextTile.location().y();
        int local4 = this.currentLocation.y();
        int local5 = local4 - local3;

        if (local5 != 0) {
            if (local5 < 0) {
                if (local5 > -534) {
                    local4 = local3;
                } else {
                    local4 += 534;
                }
            } else if (local5 < 534) {
                local4 = local3;
            } else {
                local4 -= 534;
            }
        }

        setCurLocation(local1, local4);

        if (this.currentLocation.distanceTo(this.nextTile.location()) < MathUtil._43Z(267.0D)) {
            if (this.moveTarget.isSamePosition(this.nextTile.location())) {
                doFire();
            }

            setCurrentTile(this.nextTile);
            setNextTile(null);
        }
    }

    /**
     * Updates the move for this Snow War game contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public void setMove(int x, int y) {
        if (this.currentStatus == 1) {
            setCurrentStatus(0);
            setTaskTime(0);
        }
        if (this.currentStatus == 0 || this.currentStatus == 3) {
            setMoveTarget(x, y);
        }
    }

    /**
     * Executes decrement health for this Snow War game contract.
     *
     * @param attacker Attacker supplied by the caller.
     * @param rot Rot supplied by the caller.
     */
    public void decrementHealth(HumanGameObject attacker, int rot) {
        if (this.team == attacker.team) {
            return;
        }

        if (this.health > 0) {
            if (this.health == 1) {
                doKill(rot);
                attacker.giveScorePerKill(this);
            }
            setHealth(this.health - 1);
        }
    }

    /**
     * Executes give score per hit for this Snow War game contract.
     *
     * @param _arg2 Arg2 supplied by the caller.
     */
    public void giveScorePerHit(HumanGameObject _arg2) {
        if (this.team != _arg2.team) {
            this.hits++;
            giveScore(1);
        }
    }

    /**
     * Executes give score per kill for this Snow War game contract.
     *
     * @param _arg2 Arg2 supplied by the caller.
     */
    public void giveScorePerKill(HumanGameObject _arg2) {
        if (this.team != _arg2.team) {
            this.kills++;
            giveScore(5);
        }
    }

    /**
     * Executes give score for this Snow War game contract.
     *
     * @param _arg2 Arg2 supplied by the caller.
     */
    public void giveScore(int _arg2) {
        setScore(this.score + _arg2);
        this.currentSnowWar.teamScore[this.team - 1] = this.currentSnowWar.teamScore[this.team - 1] + _arg2;
    }

    /**
     * Executes do kill for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     */
    public void doKill(int _arg1) {
        setCurrentStatus(2);
        setTaskTime(100);
        setRot(Direction360.direction360ValueToDirection8(_arg1).rotateDirection180Degrees());
        stopWalk();
    }

    /**
     * Executes stop walk for this Snow War game contract.
     */
    public void stopWalk() {
        if (this.nextTile == null) {
            setMoveTarget(this.currentTile.location());
            setCurLocation(this.currentTile.location());
        } else {
            setCurrentTile(this.nextTile);
            setCurLocation(this.nextTile.location());
            setMoveTarget(this.nextTile.location());
            setNextTile(null);
        }
    }

    /**
     * Indicates whether this Snow War game contract can throw snow ball.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canThrowSnowBall() {
        return (this.snowBalls > 0 && this.fireRateLimiter < 1 && (this.currentStatus == 0 || this.currentStatus == 3));
    }

    /**
     * Executes increase fire limiter for this Snow War game contract.
     */
    public void increaseFireLimiter() {
        this.fireRateLimiter = 5;
    }

    /**
     * Executes vs for this Snow War game contract.
     *
     * @param victimX Victim x supplied by the caller.
     * @param victimY Victim y supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean _vs(int victimX, int victimY) {
        if (this.snowBalls < 1) {
            return false;
        }
        stopWalk();
        int local2 = Direction360.getRot(victimX - this.currentLocation.x(), victimY - this.currentLocation.y());
        int local3 = Direction360.direction360ValueToDirection8(local2).getRot();
        setRot(Direction8.getDirection(local3));

        setSnowBalls(this.snowBalls - 1);
        return true;
    }

    /**
     * Indicates whether this Snow War game contract can walk.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canWalk() {
        return (this.currentStatus == 0 || this.currentStatus == 3);
    }

    /**
     * Indicates whether this Snow War game contract can pick snow balls.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canPickSnowBalls() {
        return ((this.currentStatus == 0 || this.currentStatus == 3) && this.snowBalls < 5);
    }

    /**
     * Executes make snow ball for this Snow War game contract.
     */
    public void makeSnowBall() {
        if (canPickSnowBalls()) {
            setCurrentStatus(1);
            setTaskTime(20);
            stopWalk();
        }
    }

    /**
     * Executes available snow ball slots for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public int availableSnowBallSlots() {
        return 5 - this.snowBalls;
    }

    /**
     * Adds snow balls to this Snow War game contract.
     *
     * @param val Val supplied by the caller.
     */
    public void addSnowBalls(int val) {
        setSnowBalls(this.snowBalls + val);
    }

    /**
     * Executes test snow ball collision for this Snow War game contract.
     *
     * @param snowBall Snow ball supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean testSnowBallCollision(SnowBallGameObject snowBall) {
        if (this.currentStatus == 2 || this.currentStatus == 3 || snowBall.getAttacker() == this) {
            return false;
        }
        return super.testSnowBallCollision(snowBall);
    }

    /**
     * Handles the snow ball hit callback for this Snow War game contract.
     *
     * @param snowBall Snow ball supplied by the caller.
     */
    public void onSnowBallHit(SnowBallGameObject snowBall) {
        HumanGameObject attacker = snowBall.getAttacker();
        decrementHealth(attacker, snowBall.direction360()._2Hq());
        attacker.giveScorePerHit(this);
    }

    /**
     * Executes collision height for this Snow War game contract.
     *
     * @return Result produced by the operation.
     */
    public int collisionHeight() {
        return 5000;
    }
}