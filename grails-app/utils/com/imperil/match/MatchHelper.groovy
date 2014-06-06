package com.imperil.match

import com.imperil.player.Player

class MatchHelper {
  static def Match nextTurn(Match match) {
    Player currentPlayer = match.currentPlayer
    List players = match.players

    Integer index = players.indexOf(match.currentPlayer) + 1
    Player nextPlayer = players[index>=match.players.size()?0:index]
    match.currentPlayer=nextPlayer
    match.save()
    return match
  }
}
