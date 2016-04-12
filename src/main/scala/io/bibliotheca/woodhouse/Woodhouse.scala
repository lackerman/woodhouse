package io.bibliotheca.woodhouse

import akka.actor.{ActorSystem, Props}
import io.bibliotheca.woodhouse.actors.Messages.{Start, Stop}
import io.bibliotheca.woodhouse.actors.{IntentionActor, MicActor, SpeakerActor, TranslationActor}

import scala.io.StdIn

object Woodhouse {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Woodhouse")

    val speakerActor = system.actorOf(Props[SpeakerActor], "Speaker")
    val intentionActor = system.actorOf(Props[IntentionActor], "Intentioner")
    val translator = system.actorOf(Props(new TranslationActor(speakerActor, intentionActor)), "Translator")
    val audioReader = system.actorOf(Props(new MicActor(translator)), "Mic")

    while (true) {
      val request = StdIn.readLine("\nEnter Option:\n1. Start Recording\n2. Stop Recording\n\n")
      request match {
        case "1" => audioReader ! Start
        case "2" => audioReader ! Stop
        case _ => println("--> Ignoring")
      }
    }
  }
}
