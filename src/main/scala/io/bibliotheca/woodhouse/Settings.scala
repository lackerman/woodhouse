package io.bibliotheca.woodhouse

import akka.actor.{ActorSystem, ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider}
import com.typesafe.config.Config

class SettingsImpl(config: Config) extends Extension {
  val clientId: String = config.getString("speech-to-text.accessToken.clientId")
  val clientSecret: String = config.getString("speech-to-text.accessToken.clientSecret")
  val clientScope: String = config.getString("speech-to-text.accessToken.clientScope")

  val queryInstanceId: String = config.getString("speech-to-text.query.instanceId")
  val queryAppId: String = config.getString("speech-to-text.query.appId")
  val queryLocale: String = config.getString("speech-to-text.query.locale")

  val luisId: String = config.getString("luis.id")
  val luisSubscriptionKey: String = config.getString("luis.subscriptionKey")

  val systemOS = System.getProperty("os.name")
}

object Settings extends ExtensionId[SettingsImpl] with ExtensionIdProvider {
  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) = new SettingsImpl(system.settings.config)

  // Java API: retrieve the Settings extension for the given system.
  override def get(system: ActorSystem): SettingsImpl = super.get(system)
}
