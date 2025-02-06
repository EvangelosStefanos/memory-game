package org.memory.game.gamemode;

import org.memory.game.gamemode.duel.DuelLogic;
import org.memory.game.gamemode.fourkind.FourKindLogic;
import org.memory.game.gamemode.threekind.ThreeKindLogic;
import org.memory.game.gamemode.twokind.TwoKindLogic;
import org.memory.game.logic.Settings;

/**
 * Factory for the creation of a specific Game Mode Logic.
 * @author EvanStefan
 */
public class GameModeLogicFactory {
  private GameModeLogicFactory() {}
  
  /**
   * Create and return a logic object for a specified game mode
   * 
   * @param settings - Settings defined by the user
   * @return - Logic object for the game mode specified in the settings
   */
  public static AbstractGameModeLogic createGameModeLogic(Settings settings){
    AbstractGameModeLogic logic = null;
    switch (settings.gt) {
      case 1:
        logic = new TwoKindLogic(settings);
        break;
      case 2:
        logic = new TwoKindLogic(settings);
        break;
      case 3:
        logic = new ThreeKindLogic(settings);
        break;
      case 4:
        logic = new FourKindLogic(settings);
        break;
      case 5:
        logic = new DuelLogic(settings);
        break;
      default:
        System.out.println("Error in StartGame button.");
        break;
    }
    return logic;
  }
}
