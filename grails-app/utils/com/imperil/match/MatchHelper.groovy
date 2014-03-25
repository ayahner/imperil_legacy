package com.imperil.match

import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Continent
import com.imperil.mapitem.Territory
import com.imperil.player.Player

class MatchHelper {
  static def Match nextTurn(Match match) {
    Player currentPlayer = match.currentPlayer
    Integer index = match.players.indexOf(match.currentPlayer) + 1
    Player nextPlayer = match.players[index>=match.players.size()?0:index]
    match.currentPlayer=nextPlayer
    currentPlayer = match.currentPlayer
    match.save()
    return match
  }

  static def Match loadMatch(Long matchId) {
    Match match = Match.findById(matchId)
    BoardMap boardMap = match.boardMap
    Set<Continent> continents = boardMap.continents
    Set<Territory> territories = continents.collect { Continent continent ->
      continent.territories.each { Territory it ->
        it.garrison = Garrison.findByTerritoryAndMatch(it, match)
      }
    }
    return match
  }
}
