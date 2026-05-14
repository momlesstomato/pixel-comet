package com.cometproject.games.snowwar;
import com.cometproject.games.snowwar.gameobjects.GameItemObject;
/**
 * Describes collision detection behavior for the Snow War game subsystem.
 */
public class CollisionDetection {
    /**
     * Executes p u for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     * @param _arg2 Arg2 supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean _pU(GameItemObject _arg1, GameItemObject _arg2) {
        if (_arg2 == _arg1) {
            return false;
        }
        return _s(_arg1, _arg2);
    }

    private static boolean _s(GameItemObject _arg1, GameItemObject _arg2) {
        return _arg1.location3D()._0Dk(_arg2.location3D(), _arg1.boundingData()[0] + _arg2.boundingData()[0]);
    }
}