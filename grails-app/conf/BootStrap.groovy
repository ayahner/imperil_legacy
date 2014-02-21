
import org.apache.commons.lang.BooleanUtils
import org.apache.commons.lang.RandomStringUtils
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.example.InterestStatement
import org.example.MarginAgreement
import org.example.MarginCall
import org.example.Party
import org.example.Pledge
import org.example.RecallItem
import org.example.Substitution
import org.example.ext.InterestStatementBusinessState
import org.example.ext.MarginAgreementBusinessState
import org.example.ext.MarginCallBusinessState
import org.example.ext.SubstitutionBusinessState

import com.dynamix.auth.Requestmap
import com.dynamix.bootstrap.BootStrapUtils
import com.dynamix.notification.Notification
import com.dynamix.organization.Organization
import com.dynamix.role.Role
import com.dynamix.user.AppUser
import com.dynamix.usertorole.AppUserToRole


class BootStrap {
	def springSecurityService
	GrailsApplication grailsApplication

	def init = { servletContext ->
		// Check whether the test data already exists.
		int numberOfAgreementsToGenerate = 20

		Random random = new Random();
		long now =System.currentTimeMillis();

		def adminOrganization = Organization.findByLabel('Admin')?:new Organization(label: 'Admin').save(failOnError: true);

		// Dummy organizations
		def barclaysOrganization = Organization.findByLabel('Barclays')?:new Organization(label: 'Barclays').save(failOnError: true);
		def xyOrganization = Organization.findByLabel('XY Investments')?:new Organization(label: 'XY Investments').save(failOnError: true);
		def jpmOrganization = Organization.findByLabel('JPM')?:new Organization(label: 'JPM').save(failOnError: true);
		def hsbcOrganization = Organization.findByLabel('HSBC')?:new Organization(label: 'HSBC').save(failOnError: true);
		// END Dummy organizations

		// User definitions
		def viewUsers = new ArrayList();
		def appUsers = new ArrayList();
		def orgAdminUsers = new ArrayList();
		def adminUsers = new ArrayList();

		viewUsers.add(AppUser.findByUsername('xy.view')?:new AppUser(organization:xyOrganization, username: 'xy.view',password:'password',enabled:true).save(failOnError: true));
		appUsers.add(AppUser.findByUsername('xy.user')?:new AppUser(organization:xyOrganization, username: 'xy.user',password:'password',enabled:true).save(failOnError: true));
		orgAdminUsers.add(AppUser.findByUsername('xy.admin')?:new AppUser(organization:xyOrganization, username: 'xy.admin',password:'password',enabled:true).save(failOnError: true));

		viewUsers.add(AppUser.findByUsername('b.view')?:new AppUser(organization:barclaysOrganization, username: 'b.view',password:'password',enabled:true).save(failOnError: true));
		appUsers.add(AppUser.findByUsername('b.user')?:new AppUser(organization:barclaysOrganization, username: 'b.user',password:'password',enabled:true).save(failOnError: true));
		orgAdminUsers.add(AppUser.findByUsername('b.admin')?:new AppUser(organization:barclaysOrganization, username: 'b.admin',password:'password',enabled:true).save(failOnError: true));

		adminUsers.add(AppUser.findByUsername('joe')?:new AppUser(organization:adminOrganization, username: 'joe',password:'joe',enabled:true).save(failOnError: true));
		adminUsers.add(AppUser.findByUsername('andrew')?:new AppUser(organization:adminOrganization, username: 'andrew',password:'andrew',enabled:true).save(failOnError: true));
		// END User definitions

		// Dynamic role creation/mapping
		for (GrailsControllerClass clazz : grailsApplication.getControllerClasses()){
			Boolean noRole = clazz.getStaticPropertyValue('noRole',Boolean.class);
			if(BooleanUtils.isNotTrue(noRole)){
				Boolean isAdmin = clazz.getStaticPropertyValue('isAdmin',Boolean.class);
				Boolean isSuperAdmin = clazz.getStaticPropertyValue('isSuperAdmin',Boolean.class);

				//Has to match index.gsp - line 33
				String domainNaturalName = clazz.naturalName.replace(" Controller", "");
				String readRoleName = domainNaturalName+" Read"
				String writeRoleName = domainNaturalName+" Write"

				def readRole = Role.findByAuthority(readRoleName) ?: new Role(authority: readRoleName).save(failOnError: true);
				def writeRole = Role.findByAuthority(writeRoleName) ?: new Role(authority: writeRoleName).save(failOnError: true);

				if(BooleanUtils.isNotTrue(isAdmin) && BooleanUtils.isNotTrue(isSuperAdmin) ){
					for (def viewUser : viewUsers) {
						if (!viewUser.authorities.contains(readRole)){
							new AppUserToRole(appUser: viewUser, role: readRole).save(failOnError: true);
						}
					}
					for (def appUser : appUsers) {
						if (!appUser.authorities.contains(readRole)){
							new AppUserToRole(appUser: appUser, role: readRole).save(failOnError: true);
							new AppUserToRole(appUser: appUser, role: writeRole).save(failOnError: true);
						}
					}
				}
				if(BooleanUtils.isNotTrue(isSuperAdmin)){
					for (def orgAdminUser : orgAdminUsers) {
						if (!orgAdminUser.authorities.contains(readRole)){
							new AppUserToRole(appUser: orgAdminUser, role: readRole).save(failOnError: true);
							new AppUserToRole(appUser: orgAdminUser, role: writeRole).save(failOnError: true);
						}
					}
				}
				for (def adminUser : adminUsers) {
					if (!adminUser.authorities.contains(readRole)){
						new AppUserToRole(appUser: adminUser, role: readRole).save(failOnError: true);
						new AppUserToRole(appUser: adminUser, role: writeRole).save(failOnError: true);
					}
				}
			}
		}
		// END Dynamic role creation/mapping

		// Requestmap
		new Requestmap(url: '/js/**', configAttribute: 'permitAll').save(failOnError:true)
		new Requestmap(url: '/css/*', configAttribute: 'permitAll').save(failOnError:true)
		new Requestmap(url: '/images/**', configAttribute: 'permitAll').save(failOnError:true)
		new Requestmap(url: '/login/**', configAttribute: 'permitAll').save(failOnError:true)
		new Requestmap(url: '/oauth/**', configAttribute: 'permitAll').save(failOnError:true)
		new Requestmap(url: '/**', configAttribute: 'IS_AUTHENTICATED_FULLY').save(failOnError:true)
		// END Requestmap

		// Dummy notifications
		for (int i =0; i<numberOfAgreementsToGenerate/4;i++){
			new Notification(user: appUsers[0],level:'Info',message:'Something was done').save(failOnError:true);
			new Notification(user: appUsers[1],level:'Warning',message:'Something was poorly done').save(failOnError:true);
			new Notification(user: orgAdminUsers[0],level:'Error',message:'Something broke').save(failOnError:true);
			new Notification(user: adminUsers[0],level:'Info',message:'Something was done').save(failOnError:true);
		}
		// END Dummy notifications

		// Dummy parties
		List parties = new ArrayList();
		for (int i =0; i<numberOfAgreementsToGenerate;i++){
			parties.add(Party.findByLabel('Barclays Capital-'+i)?:new Party(label: 'Barclays Capital-'+i,organization: barclaysOrganization).save(failOnError: true));
			parties.add(Party.findByLabel('XY Ventures-'+i)?:new Party(label: 'XY Ventures-'+i,organization: xyOrganization).save(failOnError: true));
			parties.add(Party.findByLabel('JPM Investments-'+i)?:new Party(label: 'JPM Investments-'+i,organization: jpmOrganization).save(failOnError: true));
			parties.add(Party.findByLabel('HSBC Bank-'+i)?:new Party(label: 'HSBC Bank-'+i,organization: hsbcOrganization).save(failOnError: true));
		}
		// END Dummy parties

		// Dummy agreements

		List agreements = new ArrayList();
		for (int i =0; i<numberOfAgreementsToGenerate;i++){
			def shortName = 'F '+ (random.nextInt(8999)+1000)
			def partyA = parties.get(random.nextInt(parties.size()))
			List otherParties = new ArrayList(parties)
			otherParties.remove(partyA)
			def partyB = otherParties.get(random.nextInt(otherParties.size()))

			def longName = partyA.label +' vs. '+partyB.label
			def nextBusinessState = MarginAgreementBusinessState.values()[random.nextInt(MarginAgreementBusinessState.values().length)].state;
			agreements.add(MarginAgreement.findByLongName(longName)?:new MarginAgreement(
					shortName: shortName,
					longName: longName,
					type: random.nextBoolean()?'ISDA':random.nextBoolean()?'Repo':random.nextBoolean()?'SCSA':'CCP',
					businessState: nextBusinessState,
					direction: nextBusinessState == 'Active' || nextBusinessState == 'Cancelled'?'':random.nextBoolean()?'Incoming':'Outgoing',
					timeZone: random.nextBoolean()?'EST':random.nextBoolean()?'GMT':random.nextBoolean()?'JST':'CET',
					partyA: partyA,
					partyB: partyB,
					activeDate : BootStrapUtils.getRandomLocalDate(),
					).save(failOnError: true));
		}
		// END Dummy agreements

		// Dummy calls
		List calls = new ArrayList();
		for (int i =0; i<agreements.size();i++){
			def agreement = agreements.get(random.nextInt(agreements.size()));
			def nextBusinessState = MarginCallBusinessState.values()[random.nextInt(MarginCallBusinessState.values().length)].state;

			calls.add(new MarginCall(totalCallAmount: random.nextInt(9999)*1000,
			marginAgreement: agreement,
			businessState: nextBusinessState,
			direction:  nextBusinessState == 'Cancelled'?'':random.nextBoolean()?'Incoming':'Outgoing',
			settlementDate : BootStrapUtils.getRandomLocalDate(),
			type :random.nextBoolean()?'Netted':random.nextBoolean()?'Initial':'Variation',
			valuationDate : BootStrapUtils.getRandomLocalDate()
			).save(failOnError:true));
		}
		// END Dummy calls

		// Dummy pledges
		for (int i =0; i<calls.size()*5;i++){
			new Pledge(marginCall: calls.get(random.nextInt(calls.size())),
			adjustedCollateralValue : random.nextInt(100)*1000,
			currentMarketValue : random.nextInt(100)*1000     ,
			finalTransferAmount : random.nextInt(100)*1000    ,
			fxRate : random.nextInt(100)*1000                 ,
			haircut : random.nextInt(100)*1000                ,
			price : random.nextInt(100)*1000                  ,
			settlementDate : BootStrapUtils.getRandomLocalDate(),
			securityId : RandomStringUtils.randomAlphanumeric(10),
			securityIdType :random.nextBoolean()?'CUSIP':random.nextBoolean()?'RIC':'ISIN',
			quantity : random.nextInt(100)*1000).save(failOnError:true);
		}
		// END Dummy pledges

		// Dummy recallItems
		for (int i =0; i<calls.size()*5;i++){
			new RecallItem(marginCall: calls.get(random.nextInt(calls.size())),
			adjustedCollateralValue : random.nextInt(100)*1000,
			currentMarketValue : random.nextInt(100)*1000     ,
			finalTransferAmount : random.nextInt(100)*1000    ,
			fxRate : random.nextInt(100)*1000                 ,
			haircut : random.nextInt(100)*1000                ,
			price : random.nextInt(100)*1000                  ,
			quantity : random.nextInt(100)*1000,
			settlementDate : BootStrapUtils.getRandomLocalDate(),
			securityId : RandomStringUtils.randomAlphanumeric(10),
			securityIdType :random.nextBoolean()?'CUSIP':random.nextBoolean()?'RIC':'ISIN',).save(failOnError:true);
		}
		// END Dummy recallItems

		// Dummy subs
		for (int i =0; i<agreements.size();i++){
			def agreement = agreements.get(random.nextInt(agreements.size()));
			def nextBusinessState = SubstitutionBusinessState.values()[random.nextInt(SubstitutionBusinessState.values().length)].state;

			new Substitution(totalCallAmount: random.nextInt(9999)*1000,
			marginAgreement: agreement,
			businessState: nextBusinessState,
			direction: nextBusinessState == 'Cancelled'?'':random.nextBoolean()?'Incoming':'Outgoing',
			settlementDate : BootStrapUtils.getRandomLocalDate(),
			type :random.nextBoolean()?'Netted':random.nextBoolean()?'Initial':'Variation',
			valuationDate : BootStrapUtils.getRandomLocalDate()
			).save(failOnError:true);
		}
		// END Dummy subs

		// Dummy Interest statements
		for (int i =0; i<agreements.size();i++){
			def agreement = agreements.get(random.nextInt(agreements.size()));
			def nextBusinessState = InterestStatementBusinessState.values()[random.nextInt(InterestStatementBusinessState.values().length)].state;

			new InterestStatement(totalCallAmount: random.nextInt(9999)*1000,
			marginAgreement: agreement,
			businessState: nextBusinessState,
			direction: nextBusinessState =='Cancelled'?'':random.nextBoolean()?'Incoming':'Outgoing',
			settlementDate : BootStrapUtils.getRandomLocalDate(),
			type :random.nextBoolean()?'Netted':random.nextBoolean()?'Initial':'Variation',
			valuationDate : BootStrapUtils.getRandomLocalDate()
			).save(failOnError:true);
		}
		// END Dummy InterestStatement

	}
	def destroy = {
	}
}
