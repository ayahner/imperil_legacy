package com.dynamix.util

import grails.persistence.Event


class GSPUtils {

	static def getProps(domainClass,comparator){
		def excludedProps = Event.allEvents.toList() << 'id' << 'version'
		def allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
		def props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
		Collections.sort(props, comparator);
		return props;
	}
	
	
	static def getPrimaryProps(domainClass,props){
		return getPrimaryProps(domainClass,props,Long.MAX_VALUE);
	}
	
	static def getPrimaryProps(domainClass,props,maxProps){
		def controllerClass = domainClass.grailsApplication.getArtefact('Controller', domainClass.fullName+'Controller')
		def primaryFields = domainClass.getStaticPropertyValue('primaryFields',Collection.class);

		def primaryFieldProps = new ArrayList()
		for (prop in props){
			if(primaryFields == null){
				primaryFieldProps.add(prop);
			}else if(primaryFields.contains(prop.name)){
				def currentIndex = primaryFields.indexOf(prop.name);
				def addIndex = currentIndex>primaryFieldProps.size()?primaryFieldProps.size():currentIndex;
				primaryFieldProps.add(addIndex, prop);
			}

			if(primaryFieldProps.size() == maxProps){
				break;
			}
		}
		return primaryFieldProps;
	}
	
	static def getSecondaryProps(domainClass,props,primaryFieldProps){
		return getSecondaryProps(domainClass,props,primaryFieldProps,Long.MAX_VALUE);
	}

	static def getSecondaryProps(domainClass,props,primaryFieldProps,maxProps){
		def controllerClass = domainClass.grailsApplication.getArtefact('Controller', domainClass.fullName+'Controller')

		def secondaryFields = controllerClass.getStaticPropertyValue('secondaryFields',Collection.class)
		def secondaryFieldProps = new ArrayList()
		for (prop in props){
			if(!primaryFieldProps.contains(prop)){
				if(secondaryFields == null){
					secondaryFieldProps.add(prop);
				}else if(secondaryFields.contains(prop.name)){
					def currentIndex = secondaryFields.indexOf(prop.name)
					def addIndex = currentIndex>secondaryFieldProps.size()?secondaryFieldProps.size():currentIndex;
					secondaryFieldProps.add(addIndex, prop);
				}
				if(secondaryFieldProps.size() == maxProps){
					break;
				}
			}
		}
		return secondaryFieldProps;
	}
}
