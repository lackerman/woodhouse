package io.bibliotheca.woodhouse.api

import java.net.URLEncoder

import org.apache.http.HttpEntity
import org.apache.http.client.methods.{HttpGet, HttpPost, HttpRequestBase}
import org.apache.http.impl.client.HttpClientBuilder

import scala.io.Source

object HttpClient {

  /**
    * Thanks to ryryguy @see <a href="http://goo.gl/617sGR">Stack Overflow</a>
    *
    * @param params A map of query parameters
    * @return A string of encoded query params
    */
  def buildEncodedQueryString(params: Map[String, Any]): String = {
    val encoded = for {
      (name, value) <- params if value != None
      encodedValue = value match {
        case Some(x: String) => URLEncoder.encode(x, "UTF8")
        case x => URLEncoder.encode(x.toString, "UTF8")
      }
    } yield name + "=" + encodedValue
    encoded.mkString("?", "&", "")
  }

  /**
    * Returns the text content from a REST URL. Returns None if there
    * was a problem serializing the content
    *
    * @param url The URL to request content from
    * @return An option containing the content or None
    */
  def get(url: String,
          headers: Map[String, String],
          query: Map[String, String]): Option[String] = {
    // Create the query params
    val request = new HttpGet(url + buildEncodedQueryString(query))
    // Create the headers
    headers.foreach { case (key, value) => request.setHeader(key, value) }
    // Perform the actual request
    performRequest(request)
  }

  /**
    * Returns the text content from a REST URL. Returns None if there
    * was a problem serializing the content
    *
    * @param url The URL to request content from
    * @return An option containing the content or None
    */
  def post(url: String,
           headers: Map[String, String],
           query: Map[String, String],
           body: HttpEntity): Option[String] = {
    // Create the query params
    val request = new HttpPost(url + buildEncodedQueryString(query))
    // Create the headers
    headers.foreach { case (key, value) => request.setHeader(key, value) }
    // Create the body
    request.setEntity(body)
    // Perform the actual request
    performRequest(request)
  }

  private def performRequest(request: HttpRequestBase): Option[String] = {
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    val results = Option(response.getEntity) match {
      case Some(e) =>
        val inputStream = e.getContent
        val content = Source.fromInputStream(inputStream).mkString
        inputStream.close()
        Some(content)
      case _ => None
    }
    client.close()
    results
  }
}
