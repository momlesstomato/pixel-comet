package com.cometproject.games.snowwar;

/**
 * Describes math util behavior for the Snow War game subsystem.
 */
public class MathUtil {
    /**
     * Executes 43 z for this Snow War game contract.
     *
     * @param _arg1 Arg1 supplied by the caller.
     * @return Result produced by the operation.
     */
    public static int _43Z(double _arg1) {
        if (_arg1 >= 0) {
            return (int) Math.floor(_arg1);
        }
        return (int) Math.ceil(_arg1);
    }
}