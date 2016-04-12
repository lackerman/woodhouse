package io.bibliotheca.woodhouse.api

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, PropertyNamingStrategy}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object Serializer {
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def deserialize[T](payload: String, cls: Class[T]): Option[T] =
    try {
      Some(mapper.readValue(payload, cls))
    } catch {
      case e: Exception => None
    }

  def serialize[T](obj: T): String = mapper.writeValueAsString(obj)
}
