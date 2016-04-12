package io.bibliotheca.woodhouse.actors

import akka.actor.{Actor, ActorLogging}
import io.bibliotheca.woodhouse.Settings
import io.bibliotheca.woodhouse.actors.Messages.DetermineIntention
import io.bibliotheca.woodhouse.api.HttpClient

class IntentionActor extends Actor with ActorLogging {
  val settings = Settings(context.system)

  override def receive: Receive = {
    case DetermineIntention(query) =>
      derive(query) match {
        case Some(results) => log.debug(s"Intention:\n$results")
        case None => log.debug(s"Failed to determine the intention")
      }
  }

  private def derive(query: String) = {
    val queryParams = Map(
      "q" -> query,
      "id" -> settings.luisId,
      "subscription-key" -> settings.luisSubscriptionKey)

    HttpClient.get("https://api.projectoxford.ai/luis/v1/application", Map.empty, queryParams)
  }
}
