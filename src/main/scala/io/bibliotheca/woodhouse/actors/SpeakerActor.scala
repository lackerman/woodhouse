package io.bibliotheca.woodhouse.actors

import akka.actor.{Actor, ActorLogging}
import io.bibliotheca.woodhouse.actors.Messages.PlayRecording
import io.bibliotheca.woodhouse.audio.Speaker

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class SpeakerActor extends Actor with ActorLogging {
  implicit val ec = ExecutionContext.global

  override def receive: Receive = {
    case PlayRecording(filename) =>
      log.debug("Start playing audio")
      Speaker.play(filename).onComplete {
        case Success(numBytesWritten) =>
          log.debug(s"Wrote $numBytesWritten bytes")
        case Failure(ex) =>
          log.debug("Failed to play the audio data")
      }
  }
}
