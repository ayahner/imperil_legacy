package com.imperil.mapitem

import org.hibernate.criterion.CriteriaSpecification;

import grails.converters.XML

class TerritoryEdgeController {

  def index() {
    log.debug("index hit")
  }

  def list() {
    log.debug("list hit")

    def result = TerritoryEdge.findAll()
    render result as XML
  }
}
