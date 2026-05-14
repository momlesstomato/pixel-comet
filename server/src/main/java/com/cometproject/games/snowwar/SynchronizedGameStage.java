package com.cometproject.games.snowwar;
import com.cometproject.games.snowwar.gameobjects.GameItemObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes synchronized game stage behavior for the Snow War game subsystem.
 */
public class SynchronizedGameStage {
    public final Map<Integer, GameItemObject> gameObjects = new LinkedHashMap<>();
    private final List<GameItemObject> _2xj = new ArrayList<>();
    public int objectIdCounter;

    /**
     * Adds game object to this Snow War game contract.
     *
     * @param obj Obj supplied by the caller.
     */
    public void addGameObject(GameItemObject obj) {
        if (obj.objectId == 0) {
            obj.objectId = this.objectIdCounter++;
        }
        this.gameObjects.put(obj.objectId, obj);
        obj._active = true;
    }

    /**
     * Removes game object from this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     */
    public void removeGameObject(int _arg1) {
        GameItemObject local1 = this.gameObjects.remove(_arg1);
        if (local1 != null) {
            local1.onRemove();
        }
    }

    /**
     * Executes queue delete object for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     */
    public void queueDeleteObject(GameItemObject _arg1) {
        if (_arg1 == null) {
            return;
        }
        this._2xj.add(_arg1);
        _arg1._active = false;
        _arg1.GenerateCHECKSUM((SnowWarRoom) this, -1);
    }

    /**
     * Executes 3 pl for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     * @return Result produced by the operation.
     */
    public GameItemObject _3Pl(int _arg1) {
        return this.gameObjects.get(_arg1);
    }

    /**
     * Executes subturn for this Snow War game contract.
     */
    public void subturn() {
        if(!this.gameObjects.isEmpty() && this.gameObjects != null) {
            for (GameItemObject local0 : this.gameObjects.values()) {
                if (local0 != null)
                    local0.subturn(this);
            }
        }

        if (!this._2xj.isEmpty()) {
            for (GameItemObject local1 : this._2xj) {
                removeGameObject(local1.objectId);
            }
            this._2xj.clear();
        }
    }
}