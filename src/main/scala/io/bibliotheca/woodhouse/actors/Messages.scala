package io.bibliotheca.woodhouse.actors

object Messages {

  case object Start

  case object Stop

  case class PlayRecording(filename: String)

  case class Translate(filename: String)

  case class DetermineIntention(query: String)

}
