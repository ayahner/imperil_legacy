package com.imperil.rules
import com.imperil.match.Match
import com.imperil.player.Player


public class RuleHelper {

  public static void initMatch(Match match) {
    switch (match.players.size()) {
      case 3:
        match.players.each { Player player ->
          player.armyCount = 35
        }
        break
      case 4:
        match.players.each { Player player ->
          player.armyCount = 30
        }
        break
      case 5:
        match.players.each { Player player ->
          player.armyCount = 25
        }
        break
      case 6:
        match.players.each { Player player ->
          player.armyCount = 20
        }
        break
      default:
        break
    }
    match.players.each { Player player ->
      player.save(failOnError:true)
    }
  }
}
