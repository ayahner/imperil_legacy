package com.dynamix.util

import grails.converters.JSON;
import org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib

class Action {
	
	static String CREATE_PATH = '/create';
	static String UPDATE_PATH = '/update';
	static String DELETE_PATH = '/delete';
	
	static String CREATE_LABEL = 'default.button.create.label';
	static String EDIT_LABEL = 'default.button.edit.label';
	static String UPDATE_LABEL = 'default.button.update.label';
	static String DELETE_LABEL = 'default.button.delete.label';
	
	static String DEFAULT_CREATE_IMAGE_PATH = '/images/app/default/action/create.png';
	static String DEFAULT_EDIT_IMAGE_PATH = '/images/app/default/action/edit.png';
	static String DEFAULT_DELETE_IMAGE_PATH = '/images/app/default/action/delete.png';
	
	static def DEFAULT_CREATE = new Action(messageCode:CREATE_LABEL,path:CREATE_PATH,imagePath:DEFAULT_CREATE_IMAGE_PATH);
	static def DEFAULT_EDIT = new Action(messageCode:EDIT_LABEL,path:UPDATE_PATH,imagePath:DEFAULT_EDIT_IMAGE_PATH,showMap:[:]);
	static def DEFAULT_UPDATE = new Action(messageCode:UPDATE_LABEL,path:UPDATE_PATH,imagePath:DEFAULT_EDIT_IMAGE_PATH,showMap:[:]);
	static def DEFAULT_DELETE = new Action(messageCode:DELETE_LABEL,path:DELETE_PATH,imagePath:DEFAULT_DELETE_IMAGE_PATH,includeFields:[],showMap:[:]);
	
	def g = new ValidationTagLib()

	String messageCode
	
	String path
	String imagePath
	def showMap
	def includeFields
	def excludeFields=['dateCreated','lastUpdated']
	
	String getPrefix(){
		return path.replaceAll("/","");
	}
	
	String getLabel(){
		String label = g.message(code:messageCode);
		return label;
	}
}
