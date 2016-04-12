package io.bibliotheca.woodhouse.api


case class Header(status: String)

case class Result(name: String,
                  lexical: String,
                  confidence: Double)

case class TextTranslation(header: Header,
                           results: Option[java.util.List[Result]])