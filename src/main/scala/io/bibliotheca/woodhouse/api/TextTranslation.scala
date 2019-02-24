package io.bibliotheca.woodhouse.api

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Header(status: String)

case class Result(name: String,
                  lexical: String,
                  confidence: Double)

case class TextTranslation(header: Header,
                           results: Option[List[Result]])

object TextTranslationJsonProtocol extends DefaultJsonProtocol {
  implicit val headerFormat: RootJsonFormat[Header] = jsonFormat1(Header)
  implicit val resultFormat: RootJsonFormat[Result] = jsonFormat3(Result)
  implicit val textFormat: RootJsonFormat[TextTranslation] = jsonFormat2(TextTranslation)
}