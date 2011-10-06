package code.rest

import scala.collection.Seq
import net.liftweb.http.LiftResponse
import scala.xml.NodeSeq
import net.liftweb.http.rest.XMLApiHelper
import net.liftweb.common.Box
import net.liftweb.common.Logger
import net.liftweb.http.LiftRules
import net.liftweb.http.Req
import net.liftweb.http.GetRequest
import net.liftweb.common.Full
import net.liftweb.http.auth.AuthRole
import net.liftweb.json._
import net.liftweb.http.JsonResponse
import net.liftweb.http.XmlResponse
import net.liftweb.common.EmptyBox
import net.liftweb.common.Empty

object XmlRest extends XMLApiHelper with Logger {

  info("DispatchRestAPI init")

  // Required override, though not used
  def createTag(contents: NodeSeq) = <api>{ contents }</api>

  def dispatch: LiftRules.DispatchPF = {

    case Req(List("api", "q1"), _, GetRequest) => { () => JsonResponse(JString(" api => q1")) }
    case Req(List("api", "q1", id), _, GetRequest) => { () => XmlResponse(<b> api q1 { id }</b>) }
    case Req(List("api", "q1", id, "cond1"), _, GetRequest) => { () => XmlResponse(<b> api q1 { id } cond1</b>) }
    case Req(List("api", "q1", "reg", "cond1"), _, GetRequest) => { () => XmlResponse(<b> api q1 reg cond1</b>) }
    case Req(List("api", "q1", id, "cond2"), _, GetRequest) => { () => XmlResponse(<b> api q1 { id } cond3</b>) }

  }

  def protection: LiftRules.HttpAuthProtectedResourcePF = {
			//    case Req(List("api", "q1", id, something), _, GetRequest) => {
			//      info("authentication match on: " + something)
			//      something match {
			//        case "cond1" => Full(AuthRole("authenticated"))
			//        //case "cond2" => Empty     // this will still trigger auth - just without role checking
			//        case "cond3" => Full(AuthRole("authenticated"))
			//        case _ => Empty
			//      }
			//    }
    case Req(List("api", "q1", id, "cond1"), _, GetRequest) => Full(AuthRole("authenticated"))
    case Req(List("api", "q1", id, "cond3"), _, GetRequest) => Full(AuthRole("authenticated"))
  }

}