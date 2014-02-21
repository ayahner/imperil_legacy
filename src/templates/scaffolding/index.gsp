<% import grails.persistence.Event;
import com.dynamix.util.NameUtils;
import com.dynamix.util.DomainUtils;
import com.dynamix.util.GSPUtils;
import com.dynamix.util.Action;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;%>
<%=packageName%>
			<% 
props = GSPUtils.getProps(domainClass,comparator.constructors[0].newInstance([domainClass] as Object[]));
actions = domainClass.getStaticPropertyValue('actions',Collection.class);
excludedDisplayProps = domainClass.getStaticPropertyValue('displayIgnore',Collection.class)?:new java.util.ArrayList();
primaryFieldProps = GSPUtils.getPrimaryProps(domainClass,props); 
secondaryFieldProps = GSPUtils.getSecondaryProps(domainClass,props,primaryFieldProps);
primaryThenSecondaryFieldProps = new ArrayList(primaryFieldProps);
primaryThenSecondaryFieldProps.addAll(secondaryFieldProps);
%>
<script>
var primaryColumns = [
<%
primaryFieldProps.eachWithIndex { p, i ->
	if(i!=0){
		%>,<%	
	}
	if(p.isAssociation()){
	%>
		{field:'${p.name}${StringUtils.capitalize(p.referencedDomainClass.getStaticPropertyValue("defaultLabelProp",String.class)?:"label")}',displayName:'<g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" />'}
	<% }else{ %>
		{field:'${p.name}',displayName:'<g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" />'}
	<% } } %>
];

function ColumnCtrl(\$scope, \$rootScope) {
	\$rootScope.columnDefs = primaryColumns;
}
</script>
		<div ng-controller="ColumnCtrl"></div>
		<div id="list-${domainClass.propertyName}" class="content scaffold-list" role="main" ng-class="reveal" ng-controller="GridCtrl" ng-init="setObjectName('${domainClass.propertyName}');">
		   <h2>\${message(code: 'nav.header.${domainClass.name}')}</h2>
			<g:if test="\${flash.message}">
				<div class="message" role="status">\${flash.message}</div>
			</g:if>
			<div class="status" style="padding:5px">
				<%  int actionIndex = 0;
					 for (Action action : actions) { %>
					<button type="button" class="btn btn-primary action-button slide-reveal-show" ng-controller="ModalCtrl" ng-init="init(true,'/'+objectName+'/action?actionIndex=${actionIndex}',null)" ng-click="open()"
					<%
						if(action.showMap !=null){
							String showConditions = 'hasSelection';
							for (Object key : action.showMap.keySet()){
   								def value = action.showMap.get(key);
								showConditions += ' && (';
								if(value instanceof Collection){
									boolean first = true;
									for ( x in value){
										if(!first){
											showConditions += ' || ';
										}
										showConditions += 'currentSelection.'+key+' == \''+x+'\'';
										first = false;
									}
								}else{
									showConditions += 'currentSelection.'+key+' == \''+value+'\'';
								}
								showConditions += ')'
							}
							%> ng-show="${showConditions}" <%
						}
					%>
					>
						<div  class="action-image-container">
							<img src="\${resource(file: '${action.imagePath}')}"/>
						</div>
						<g:message code="${action.messageCode}"/>
					</button>
				<%  actionIndex++;
					} %>			
			</div>
			
            <div class="grid" ng-grid="gridOptions"></div>
		</div>