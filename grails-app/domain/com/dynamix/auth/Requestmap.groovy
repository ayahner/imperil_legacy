package com.dynamix.auth

class Requestmap {

	String url
	String configAttribute

	static mapping = {
		cache true
	}

	static constraints = {
		url blank: false, unique: true
		configAttribute blank: false
	}
	
	String toString(){
		return url +'-'+configAttribute
	}
}
