package io.bibliotheca.woodhouse.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import io.bibliotheca.woodhouse.actors.Messages.{Start, Stop, Translate}
import io.bibliotheca.woodhouse.audio.Mic

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class MicActor(translator: ActorRef) extends Actor with ActorLogging {
  implicit val ec = ExecutionContext.global

  private val micReader = new Mic

  override def receive: Receive = {
    case Start =>
      log.debug("Start reading from Mic")
      micReader.read().onComplete {
        case Success(data) =>
          log.debug(s"Future completed. Data ready")
          translator ! Translate(data)
        case Failure(ex) =>
          log.debug("Failed to read data")
          ex.printStackTrace()
      }
    case Stop =>
      log.debug("Stop reading from Mic")
      micReader.stop()
  }
}
