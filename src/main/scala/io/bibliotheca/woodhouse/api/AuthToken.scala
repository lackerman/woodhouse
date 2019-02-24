package io.bibliotheca.woodhouse.api

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class AuthToken(accessToken: String,
                     tokenType: String,
                     expiresIn: String,
                     scope: String)

object AuthTokenJsonProtocol extends DefaultJsonProtocol {
  implicit val authTokenFormat: RootJsonFormat[AuthToken] = jsonFormat4(AuthToken)
}