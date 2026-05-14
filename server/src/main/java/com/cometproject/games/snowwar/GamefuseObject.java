package com.cometproject.games.snowwar;

import com.cometproject.games.snowwar.items.Item;
import com.cometproject.games.snowwar.items.StringStuffData;

/**
 * Describes gamefuse object behavior for the Snow War game subsystem.
 */
public class GamefuseObject extends Item {
  public int X;
  public int Y;
  public int Rot;
  public int Z;

    /**
     * Creates a gamefuse object instance for the Snow War game subsystem.
     */
    public GamefuseObject() {
        extraData = new StringStuffData(null);
    }

}