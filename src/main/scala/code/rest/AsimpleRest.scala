package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.json._

object AsimpleRest extends RestHelper {

  serve {
    case XmlGet("simpleapi" :: "static" :: _, _) => <b>Static Return XML</b>
    case JsonGet("simpleapi" :: "static" :: _, _) => JString("Static Return JSON")
  }
}