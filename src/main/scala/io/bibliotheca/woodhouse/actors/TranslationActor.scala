package io.bibliotheca.woodhouse.actors

import java.io.File
import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef}
import io.bibliotheca.woodhouse.Settings
import io.bibliotheca.woodhouse.actors.Messages.{DetermineIntention, PlayRecording, Translate}
import io.bibliotheca.woodhouse.api.{AuthToken, HttpClient, Serializer, TextTranslation}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.entity.FileEntity
import org.apache.http.message.BasicNameValuePair

import scala.concurrent.ExecutionContext

class TranslationActor(speaker: ActorRef, luis: ActorRef) extends Actor with ActorLogging {
  implicit val ec = ExecutionContext.global

  val settings = Settings(context.system)
  val instanceId = UUID.randomUUID.toString

  override def receive: Receive = {
    case Translate(filename) =>
      speaker ! PlayRecording(filename)
      for {
        token <- getAccessToken
        payload <- translate(token, filename)
        translation <- Serializer.deserialize(payload, classOf[TextTranslation])
        results <- translation.results
      } yield luis ! DetermineIntention(results.get(0).lexical)
  }

  private def getAccessToken: Option[AuthToken] = {
    val body = new java.util.ArrayList[BasicNameValuePair]()
    body.add(new BasicNameValuePair("grant_type", "credentials"))
    body.add(new BasicNameValuePair("client_id", settings.clientId))
    body.add(new BasicNameValuePair("client_secret", settings.clientSecret))
    body.add(new BasicNameValuePair("scope", settings.clientScope))

    val results = HttpClient.post("https://oxford-speech.cloudapp.net/token/issueToken",
      Map.empty, Map.empty, new UrlEncodedFormEntity(body))

    results match {
      case Some(payload) => Serializer.deserialize(payload, classOf[AuthToken])
      case None => None
    }
  }

  private def translate(authToken: AuthToken, filename: String): Option[String] = {
    val params = Map(
      "version" -> "3.0",
      "requestid" -> UUID.randomUUID.toString,
      "appID" -> settings.queryAppId,
      "format" -> "json",
      "locale" -> settings.queryLocale,
      "device.os" -> settings.systemOS,
      "scenarios" -> "ulm",
      "instanceid" -> settings.queryInstanceId)

    val headers = Map(
      "Authorization" -> s"Bearer ${authToken.accessToken}",
      "Content-Type" -> "audio/wav; samplerate=16000")

    HttpClient.post("https://speech.platform.bing.com/recognize/query",
      headers, params, new FileEntity(new File(filename)))
  }
}
