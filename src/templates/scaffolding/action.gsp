<% import grails.persistence.Event;
import com.dynamix.util.NameUtils;
import com.dynamix.util.DomainUtils;
import com.dynamix.util.GSPUtils;
import com.dynamix.util.Action;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;%>
<%=packageName%>

${'<'+'%'+'@'} page import="grails.persistence.Event"${'%'+'>'}
${'<'+'%'+'@'} page import="com.dynamix.util.NameUtils"${'%'+'>'}
${'<'+'%'+'@'} page import="com.dynamix.util.DomainUtils"${'%'+'>'}
${'<'+'%'+'@'} page import="com.dynamix.util.GSPUtils"${'%'+'>'}
${'<'+'%'+'@'} page import="com.dynamix.util.Action"${'%'+'>'}
${'<'+'%'+'@'} page import="java.util.Collection"${'%'+'>'}
${'<'+'%'+'@'} page import="org.apache.commons.lang.StringUtils"${'%'+'>'}

${'<'+'%'}
domainClass = grailsApplication.getArtefact('Domain', '${domainClass.fullName}');
controllerClass = grailsApplication.getArtefact('Controller', '${domainClass.fullName}Controller');
isSubmitForm = request.getParameter("submit") == 'true';
actionIndex = Integer.valueOf(request.getParameter("actionIndex"));
actions = domainClass.getStaticPropertyValue('actions',Collection.class);
currentAction = actions[actionIndex];
submitUrl = domainClass.propertyName + currentAction.path

props = GSPUtils.getProps(domainClass,new org.codehaus.groovy.grails.scaffolding.DomainClassPropertyComparator(domainClass));
primaryFieldProps = GSPUtils.getPrimaryProps(domainClass,props); 
secondaryFieldProps = GSPUtils.getSecondaryProps(domainClass,props,primaryFieldProps);
primaryThenSecondaryFieldProps = new ArrayList(primaryFieldProps);
primaryThenSecondaryFieldProps.addAll(secondaryFieldProps);

modalProps = new ArrayList();
if(currentAction.includeFields!=null){
	for (def fieldProp in primaryThenSecondaryFieldProps) {
		if(currentAction.includeFields.contains(fieldProp.name)) {
	    	modalProps.add(fieldProp);
	    }
	}
}else if (currentAction.excludeFields!=null) {
	for (def fieldProp in primaryThenSecondaryFieldProps) {
		if(!currentAction.excludeFields.contains(fieldProp.name)) {
	    	modalProps.add(fieldProp);
	    }
	}
}else{
	modalProps.addAll(primaryThenSecondaryFieldProps);
}

${'%'+'>'}
									<div class="modal-header">
										<h3><g:message code="\${currentAction.messageCode}"/> - <g:message code="nav.header.${domainClass.name}"/></h3>
									</div>
									<div class="modal-body">
										<form role="form">
										<table>
											<tbody>	
											${'<'+'%'}
												modalProps.eachWithIndex { modalProp, i2 ->
											${'%'+'>'}
													<tr>
														<td><g:message code="${domainClass.propertyName}.\${modalProp.name}.label" default="\${modalProp.naturalName}" />:</td>
												${'<'+'%'}	if (modalProp.isAssociation()) { ${'%'+'>'}
														<td ng-controller="TypeAheadCtrl" ng-init="init('/\${modalProp.referencedDomainClass.propertyName}/all','\${modalProp.referencedDomainClass.getStaticPropertyValue('defaultLabelProp',String.class)}','\${modalProp.referencedDomainClass.propertyName}s')">
															<input type="text" ng-model="model.\${modalProp.name}" placeholder="Search..." typeahead="model for model in getLocation(\$viewValue) | filter:\$viewValue" typeahead-loading="loadingLocations" class="form-control">
    														<i ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>
														</td>
												${'<'+'%'}	} else if (modalProp.type == Boolean || modalProp.type == boolean) { ${'%'+'>'}
														<td><input type="checkbox" ng-model="model.\${modalProp.name}"></input></td>
												${'<'+'%'}  } else if (modalProp.type == org.joda.time.LocalDate) { ${'%'+'>'}
														<td><input type="date" ng-model="model.\${modalProp.name}"></td>
												${'<'+'%'}  } else if (modalProp.type == Date || modalProp.type == java.sql.Date || modalProp.type == java.sql.Time || modalProp.type == Calendar) { ${'%'+'>'}
														<td><input type="datetime" ng-model="model.\${modalProp.name}"></td>
												${'<'+'%'}  } else { ${'%'+'>'}
														<td><input ng-model="model.\${modalProp.name}"></input></td>
												${'<'+'%'}  } ${'%'+'>'}
												</tr>
											${'<'+'%'} } ${'%'+'>'}
											</tbody>
										</table>
										</form>
									</div>
									<pre>form = {{model | json}}</pre>
									<div class="modal-footer">
											${'<'+'%'} if(!isSubmitForm) {${'%'+'>'}
										<button class="btn btn-primary" ng-click="ok()">OK</button>
											${'<'+'%'} } else { ${'%'+'>'}
										<button class="btn btn-primary" ng-click="submit('\${submitUrl}')">Submit</button>
										<button class="btn btn-warning" ng-click="cancel()">Cancel</button>
											${'<'+'%'} } ${'%'+'>'}
									</div>