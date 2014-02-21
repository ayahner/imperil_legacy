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
primaryFieldProps = GSPUtils.getPrimaryProps(domainClass,props); 
secondaryFieldProps = GSPUtils.getSecondaryProps(domainClass,props,primaryFieldProps);
primaryThenSecondaryFieldProps = new ArrayList(primaryFieldProps);
primaryThenSecondaryFieldProps.addAll(secondaryFieldProps);
			%>
		<g:set var="entityName" value="\${message(code: 'nav.header.${domainClass.name}')}" />
			
									<div class="modal-header">
										<h3><g:message code="default.show.label" args="[entityName]" /> - \${${propertyName}.id}</h3>
									</div>
									<div class="modal-body">
										<table>
											<tbody>
											<%  
												primaryThenSecondaryFieldProps.eachWithIndex { modalProp, i2 -> %>
													<tr>
														<td><g:message code="${domainClass.propertyName}.${modalProp.name}.label" default="${modalProp.naturalName}" />:</td>
												<%	if (modalProp.isAssociation()){%>
														<td>\${fieldValue(bean: ${propertyName}.${modalProp.name}, field: "${modalProp.referencedDomainClass.getStaticPropertyValue("defaultLabelProp",String.class)?:"label"}")}</td>
												<%	} else if (modalProp.type == Boolean || modalProp.type == boolean) { %>
														<td><g:formatBoolean boolean="\${${propertyName}.${modalProp.name}}" /></td>
												<%  } else if (modalProp.type == Date || modalProp.type == java.sql.Date || modalProp.type == java.sql.Time || modalProp.type == Calendar) { %>
														<td><g:formatDate date="\${${propertyName}.${modalProp.name}}" /></td>
												<%  } else { %>
														<td>\${fieldValue(bean: ${propertyName}, field: "${modalProp.name}")}</td>
												<%  } %>
												</tr>
											<%  } %>
											</tbody>
										</table>
									</div>
									<div class="modal-footer">
										<button class="btn btn-primary" ng-click="ok()">OK</button>
									</div>