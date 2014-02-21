<%=packageName ? "package ${packageName}\n\n" : ''%>

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON;
import grails.orm.HibernateCriteriaBuilder;
import grails.transaction.Transactional
import com.dynamix.hibernate.HibernateUtils;
import com.dynamix.util.Report;
import com.dynamix.ui.TreeNode;
import org.codehaus.groovy.grails.commons.GrailsDomainClass;

@Transactional(readOnly = true)
class ${className}Controller {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def grailsApplication

	<%
	// System.out.println("this:"+org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this))
	// System.out.println("binding:"+org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(binding))
	//	log.debug("className:"+className)
	//	log.debug("propertyName:"+propertyName)
	//	log.debug("domainClass:"+domainClass)
	//	log.debug("domainClass.owners:"+domainClass.owners)
	//	log.debug("domainClass.mappedBy:"+domainClass.mappedBy)
	def staticBelongsTo = domainClass.getStaticPropertyValue('belongsTo',java.util.Map.class)
	//	log.debug("staticBelongsTo:"+staticBelongsTo)
	if(staticBelongsTo !=null && !staticBelongsTo.isEmpty()){
		for (Object key : staticBelongsTo.keySet()){%>

			def by${org.apache.commons.lang.StringUtils.capitalize(key)} = {
				params.max = params.max==null && params.limit!=null?Math.min(Integer.valueOf(params.limit) ?: 10, 100):25;
				if(params.offset == null){
					params.offset = params.start;
					if(params.offset == null) {
						params.offset = 0;
					}
				}
				log.info("Getting by${org.apache.commons.lang.StringUtils.capitalize(key)} with params:"+params)
				def ${key}Object = ${org.apache.commons.lang.StringUtils.capitalize(key)}.get(params.parentId);
				def results = ${className}.createCriteria().list(max: params.max, offset: params.offset){
					eq("${key}", ${key}Object)
					if(params.sort){
						def sortAsObject = JSON.parse(params.sort)
						for (sortObject in sortAsObject){
							def sortObjectProp =sortObject.get('property');
							def sortObjectDirection =sortObject.get('direction')?.toLowerCase();
							order(sortObjectProp, sortObjectDirection);
						}
					}
				};
				def listResult = [ totalCount: results.totalCount, ${org.apache.commons.lang.StringUtils.uncapitalize(className)}s: results]
				def jsonListResult = listResult as JSON
				log.debug(jsonListResult);
				render jsonListResult
			}

			<% 	   }
	} %>

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond ${className}.list(params), model:[${propertyName}Count: ${className}.count()]
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count()]
	}

	def report(Integer max) {
		list(max)
	}
	
	def show(${className} ${propertyName}) {
		respond ${propertyName}
	}

	def action() {
		render view: "action"
	}

	def all = {
		params.max = params.max==null && params.limit!=null?Math.min(Integer.valueOf(params.limit) ?: 10, 100):25;
		if(params.offset == null){
			params.offset = params.start;
		}
		log.debug("params:"+params)

		GrailsDomainClass domainClass = grailsApplication.getArtefact('Domain', '${domainClass.fullName}');
		HibernateCriteriaBuilder builder =  ${className}.createCriteria();
		def results = builder.list(max: params.max, offset: params.offset){
			HibernateUtils.handleDynamicParameters(builder,domainClass,params,params);
			if(params.sort){
				def sortAsObject = JSON.parse(params.sort)
				for (sortObject in sortAsObject){
					def sortObjectProp =sortObject.get('property');
					def sortObjectDirection =sortObject.get('direction')?.toLowerCase();
					order(sortObjectProp, sortObjectDirection);
				}
			}
		};
	
		def listResult = [ totalCount: results.totalCount, ${org.apache.commons.lang.StringUtils.uncapitalize(className)}s: results]
		def jsonListResult = listResult as JSON
		log.debug(jsonListResult);
		render jsonListResult
	}

	def countImpl = {
		GrailsDomainClass domainClass = grailsApplication.getArtefact('Domain', '${domainClass.fullName}');
		HibernateCriteriaBuilder builder =  ${className}.createCriteria();
		def results = builder.count(){
			HibernateUtils.handleDynamicParameters(builder,domainClass,params,params);
		};
		return results;
	}

	def count = {
		log.debug("Params:"+params)
		def listResult = [ totalCount: countImpl()]
		def jsonListResult = listResult as JSON
		log.debug(jsonListResult);
		render jsonListResult
	}
	
	@Transactional
	def save(${className} ${propertyName}) {
		log.error("saveImpl called with ${propertyName}:"+${propertyName})
		
		if (${propertyName} == null) {
			log.error("Returning notFound b/c ${propertyName} is null!:"+${propertyName})
			notFound()
			return
		}

		if (${propertyName}.hasErrors()) {
			log.error("Returning 400 b/c ${propertyName} hasErrors:"+${propertyName}.errors)
			response.status = 400
			flash.message = "Something's broken:"+${propertyName}.errors
			return
		}

		${propertyName}.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: '${propertyName}.label', default: '${className}'), ${propertyName}.id])
				redirect ${propertyName}
			}
			'*' { respond ${propertyName} as JSON, [status: CREATED] }
		}
	}

	def edit(${className} ${propertyName}) {
		respond ${propertyName}
	}
	
	@Transactional
	def update(Long id, Long version) {
		${className} ${propertyName} = ${className}.get(id);
		params.keySet().each {
			${propertyName}[it] = params.get(it);
		}
		updateImpl(${propertyName})
	}

	@Transactional
	def updateImpl(${className} ${propertyName}) {
		if (${propertyName} == null) {
			notFound()
			return
		}

		if (${propertyName}.hasErrors()) {
			respond ${propertyName}.errors, view:'edit'
			return
		}

		${propertyName}.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: '${className}.label', default: '${className}'), ${propertyName}.id])
				redirect ${propertyName}
			}
			'*'{ respond ${propertyName}, [status: OK] }
		}
	}

	@Transactional
	def delete(${className} ${propertyName}) {

		if (${propertyName} == null) {
			notFound()
			return
		}

		${propertyName}.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: '${className}.label', default: '${className}'), ${propertyName}.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}



	def handleStateArray(arrayList, states, currentParams) {
		def lastStateAdded = null;
		def lastStateAddedNodeParams = null;
		for (state in states) {
			if (state instanceof Collection) {
				lastStateAdded.tree = new ArrayList();
				lastStateAdded.leaf = false;
				lastStateAdded.expanded = lastStateAdded.state == 'All';
				handleStateArray(lastStateAdded.tree, state,lastStateAddedNodeParams);
			} else {
				lastStateAddedNodeParams = currentParams+(state.urlParams?:"");
				def myParams = org.codehaus.groovy.grails.web.util.WebUtils.fromQueryString(lastStateAddedNodeParams);
				GrailsDomainClass domainClass = grailsApplication.getArtefact('Domain', '${domainClass.fullName}');

				HibernateCriteriaBuilder builder =  ${className}.createCriteria();
				def results = builder.count(){
					HibernateUtils.handleDynamicParameters(builder,domainClass,params,params);
					HibernateUtils.handleDynamicParameters(builder,domainClass,myParams,params);
				};

				lastStateAdded = new TreeNode(text : state.label + ' ('+results+')',
				label : state.label,
				state : state.name,
				name : '${com.dynamix.util.NameUtils.getNaturalName(className)}',
				urlParams : state.urlParams,
				path : grailsApplication.getArtefact('Controller', '${domainClass.fullName}Controller').controllerPath.substring(1) + "list",
				leaf : true);
				arrayList.add(lastStateAdded);
			}
		}
	}

	def tree(){
		ArrayList test = new ArrayList();
		handleStateArray(test, ${domainClass.name}.navStates, org.codehaus.groovy.grails.web.util.WebUtils.toQueryString(params));
		def listResult = [ tree: test];
		def jsonListResult = listResult as JSON
		log.debug(jsonListResult);
		render jsonListResult
	}

	def reportData(){
		if(params.reportIndex){
			Report report = ${className}.reports.get(Integer.valueOf(params.reportIndex));
			String reportLabelField = report.reportLabelFields[params.reportLabelFieldIndex?Integer.valueOf(params.reportLabelFieldIndex):0];
			GrailsDomainClass domainClass = grailsApplication.getArtefact('Domain', '${domainClass.fullName}');
			HibernateCriteriaBuilder builder =  ${className}.createCriteria();
			def results = builder.list(){
				HibernateUtils.handleDynamicParameters(builder,domainClass,params,params);
				projections {
					groupProperty(reportLabelField)
					countDistinct("id")
				}
			};

			List formattedResults = new ArrayList();
			for(result in results){
				formattedResults.add([
					name:result.getAt(0),
					//x:result.getAt(0),
					y:result.getAt(1)
				]);
			}

			log.debug(results);
			//			def listResult = [ totalCount: results.size(), results: formattedResults];
			def jsonListResult = formattedResults as JSON
			log.debug(jsonListResult);
			render jsonListResult
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label', default: '${className}'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
