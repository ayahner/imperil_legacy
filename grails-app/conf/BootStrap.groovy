
import org.codehaus.groovy.grails.commons.GrailsApplication

import com.dynamix.auth.Requestmap
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

    Role playRole = Role.findByAuthority('play')?:new Role(authority: 'play').save(failOnError: true);
    Role modRole = Role.findByAuthority('mod')?:new Role(authority: 'mod').save(failOnError: true);
    Role adminRole = Role.findByAuthority('admin')?:new Role(authority: 'admin').save(failOnError: true);


    // User definitions

    def player1 = AppUser.findByUsername('player1')?:new AppUser(username: 'player1',password:'password',enabled:true).save(failOnError: true);
    def player2 = AppUser.findByUsername('player2')?:new AppUser(username: 'player2',password:'password',enabled:true).save(failOnError: true);

    def mod1 = AppUser.findByUsername('mod1')?:new AppUser(username: 'mod1',password:'password',enabled:true).save(failOnError: true);
    def mod2 = AppUser.findByUsername('mod2')?:new AppUser(username: 'mod2',password:'password',enabled:true).save(failOnError: true);

    def joe = AppUser.findByUsername('joe')?:new AppUser(username: 'joe',password:'joe',enabled:true).save(failOnError: true)
    def andrew = AppUser.findByUsername('andrew')?:new AppUser(username: 'andrew',password:'andrew',enabled:true).save(failOnError: true)

    // END User definitions

    // Default role creation/mapping
    Map<Role, List<AppUser>> roleMap = [:]
    roleMap.put(playRole, [
      player1,
      player2,
      joe,
      andrew
    ])
    roleMap.put(modRole, [mod1, mod2, joe, andrew])
    roleMap.put(adminRole, [joe, andrew])

    roleMap.each() { Role role, List<AppUser> users ->
      for (AppUser user : users) {
        if (!user.authorities.contains(role)){
          new AppUserToRole(appUser: user, role: role).save(failOnError: true);
        }
      }
    }
    // END Default role creation/mapping

    // Requestmap
    new Requestmap(url: '/js/**', configAttribute: 'permitAll').save(failOnError:true)
    new Requestmap(url: '/css/*', configAttribute: 'permitAll').save(failOnError:true)
    new Requestmap(url: '/images/**', configAttribute: 'permitAll').save(failOnError:true)
    new Requestmap(url: '/login/**', configAttribute: 'permitAll').save(failOnError:true)
    new Requestmap(url: '/oauth/**', configAttribute: 'permitAll').save(failOnError:true)
    new Requestmap(url: '/**', configAttribute: 'IS_AUTHENTICATED_FULLY').save(failOnError:true)
    // END Requestmap

  }
  def destroy = {
  }
}
