/**
 * Imperil Application
 */
var imperilApp = angular.module('imperilApp', [ 'ngRoute' ]);

function toString(obj) {
  var str = '';
  for ( var p in obj) {
    if (obj.hasOwnProperty(p)) {
      str += p + '::' + obj[p] + '\n';
    }
  }
  return str;
}
console.log('imperil.js complete')

Array.prototype.contains = function(obj) {
  var i = this.length;
  while (i--) {
    if (this[i] == obj) {
      return true;
    }
  }
  return false;
}