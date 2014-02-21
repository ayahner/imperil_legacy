package com.dynamix.hibernate

import org.apache.commons.logging.LogFactory
import org.joda.time.LocalDate

import com.dynamix.util.DomainUtils

class HibernateUtils {
	private static final log = LogFactory.getLog(this)

	def grailsApplication;

	static def handleDynamicParameters(builder,domainClass,paramMap,params){
		builder.buildCriteria {
			for(key in paramMap.keySet()){
				def value = paramMap.get(key);
				if(domainClass.hasPersistentProperty(key)){
					if(value instanceof List){
						like(key, "%"+value.get(value.size()-1)+"%");
					}else if(DomainUtils.isDate(domainClass,key)){
						Date currentDate =  params.getDate(key);
						if(DomainUtils.isJodaDate(domainClass,key)){
							def localDate = new LocalDate(currentDate);
							log.debug("Adding eq as LocalDate:["+localDate+"]")
							eq(key, localDate);
						}else if(DomainUtils.isSqlDate(domainClass,key)){
							def sqlDate = new java.sql.Date(currentDate.getTime());
							log.debug("Adding eq as sql date:["+sqlDate+"]")
							eq(key, sqlDate);
						}else{
							log.debug("Adding ge start value:["+currentDate+"], epoch:"+currentDate.getTime())
							eq(key, currentDate);
						}
					}else{
						like(key, "%"+value+"%");
					}
				}else {
					String noStartKey = key.replace("start-","");
					String noEndKey = key.replace("end-","");

					if(domainClass.hasPersistentProperty(noStartKey)){
						if(DomainUtils.isDate(domainClass,noStartKey)){
							Date currentDate =  params.getDate(key);
							if(DomainUtils.isJodaDate(domainClass,noStartKey)){
								def localDate = new LocalDate(currentDate);
								log.debug("Adding ge as LocalDate:["+localDate+"]")
								ge(noStartKey, localDate);
							}else if(DomainUtils.isSqlDate(domainClass,noStartKey)){
								def sqlDate = new java.sql.Date(currentDate.getTime());
								log.debug("Adding ge as sql date:["+sqlDate+"]")
								ge(noStartKey, sqlDate);
							}else{
								log.debug("Adding ge start value:["+currentDate+"], epoch:"+currentDate.getTime())
								ge(noStartKey, currentDate);
							}
						}else{
							ge(noStartKey, value);
						}
					}else if(domainClass.hasPersistentProperty(noEndKey)){
						if(DomainUtils.isDate(domainClass,noEndKey)){
							Date currentDate =  params.getDate(key);
							if(DomainUtils.isJodaDate(domainClass,noEndKey)){
								def localDate = new LocalDate(currentDate);
								log.debug("Adding le as LocalDate:["+localDate+"]")
								le(noEndKey, localDate);
							}else if(DomainUtils.isSqlDate(domainClass,noEndKey)){
								def sqlDate = new java.sql.Date(currentDate.getTime());
								log.debug("Adding le as sql date:["+sqlDate+"]")
								le(noEndKey, sqlDate);
							}else{
								log.debug("Adding le end value:["+currentDate+"], epoch:"+currentDate.getTime())
								le(noEndKey, currentDate);
							}
						}else{
							le(noEndKey, value);
						}
					}
				}
			}
		}
	}
}
