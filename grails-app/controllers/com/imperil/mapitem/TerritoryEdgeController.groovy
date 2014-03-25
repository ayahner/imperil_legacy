package com.imperil.mapitem

import org.hibernate.criterion.CriteriaSpecification;

import grails.converters.XML

class TerritoryEdgeController {

  def index() {
    log.debug("index hit")
  }

  def edgeMap() {
    log.debug("list hit")
    def boardMapId = params.boardMapId
    BoardMap boardMap = BoardMap.get(boardMapId)
    List<TerritoryEdge> edges = boardMap.territoryEdges
    List<Territory> territories = Territory.withCriteria {
    }


    def result = [
      territories:Territory.findAllByBoardMap(boardMap),
      edges:edges
    ]

    render result as XML
  }
}
