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

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    // Use jQuery 1.4
    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    LiftRules.dispatch.append(Arest) // stateful -- associated with a servlet container session
    LiftRules.statelessDispatchTable.append(Arest) // stateless -- no session created

    LiftRules.dispatch.append(AsimpleRest) // stateful -- associated with a servlet container session
    LiftRules.statelessDispatchTable.append(AsimpleRest) // stateless -- no session created
    
    LiftRules.dispatch.append(XmlRest.dispatch)
    LiftRules.statelessDispatchTable.append(XmlRest.dispatch) // stateless -- no session created
     LiftRules.httpAuthProtectedResource.append(XmlRest.protection)

     LiftRules.authentication = HttpDigestAuthentication("realm") {
      case (username, req, authfunc) => {
        val pw = "pw" //fake lookup username -> pw
        logger.info("Digest auth request with " + username + " :lookup pw :" + pw + ": . " + req.toString() )
        if ( authfunc(pw) ) {
        	userRoles(List(AuthRole("authenticated")))
            logger.info(" user authenticated with digest " )
            true
        }
        else
        {
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
