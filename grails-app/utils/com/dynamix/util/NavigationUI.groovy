package com.dynamix.util

import grails.converters.JSON;
import org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib

class NavigationUI {

	String messageCode
	String name
	String urlParams

	def g = new ValidationTagLib()

	static def navByBusinessState(newBusinessState) {
		return new NavigationUI(name:newBusinessState.state,messageCode:"nav.state."+newBusinessState,urlParams:'&businessState='+newBusinessState.state)
	}

	static NavigationUI ALL =new NavigationUI(name:'All',messageCode:"nav.state.All")
	static NavigationUI INCOMING =new NavigationUI(name:'Incoming',messageCode:"nav.state.Incoming",urlParams:'&direction=Incoming')
	static NavigationUI OUTGOING =new NavigationUI(name:'Outgoing',messageCode:"nav.state.Outgoing",urlParams:'&direction=Outgoing')
	
	String getLabel(){
		String label = g.message(code:messageCode);
		return label;
	}
	
	String toString(){
		String result ='{"messageCode":"'+messageCode+'","label":"'+getLabel()+'","name":"'+name+'","urlParams":"'+(urlParams!=null?urlParams:'')+'"}';
		return result;
	}
}
