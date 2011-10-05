package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import code.rest._
import net.liftweb.http.auth.HttpDigestAuthentication
import net.liftweb.http.auth.userRoles
import net.liftweb.http.auth.AuthRole

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {

  val logger = Logger(classOf[Boot])

  def boot {
    // where to search snippet
    LiftRules.addToPackages("code")


    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    LiftRules.dispatch.append(XmlRest.dispatch)
    LiftRules.statelessDispatchTable.append(XmlRest.dispatch) // stateless -- no session created
    LiftRules.httpAuthProtectedResource.append(XmlRest.protection)

    LiftRules.authentication = HttpDigestAuthentication("realm") {
      case (username, req, authfunc) => {
        val pw = "pw" //fake lookup username -> pw
        logger.info("Digest auth request with " + username + " :lookup pw :" + pw + ": . " + req.toString())
        if (authfunc(pw)) {
          userRoles(List(AuthRole("authenticated")))
          logger.info(" user authenticated with digest ")
          true
        } else {
          logger.info(" user not authenticated with digest")
          false
        }
      }
      case _ => {
        logger.debug(" bad client call for digest authentication")
        false
      }
    }

  }

}
