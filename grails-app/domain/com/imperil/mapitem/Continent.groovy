package com.imperil.mapitem

import org.aspectj.weaver.ast.HasAnnotation;

class Continent {
  static hasMany = [territories:Territory]
  String name, description
  static constraints = { name blank: false }
}
