package io.bibliotheca.woodhouse.api

import io.bibliotheca.woodhouse.api.TextTranslationJsonProtocol._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import spray.json.enrichString

@RunWith(classOf[JUnitRunner])
class SerializerTest extends FunSuite {

  test("should deserialize unsuccessfully translated speech JSON into a TextTranslation object") {
    val json =
      """
      {
        "version": "3.0",
        "header": {
          "status": "error",
          "properties": { "requestid": "request id", "NOSPEECH": "1" }
        }
      }

      """

    val translated = TextTranslation(Header("error"), None)

    val jsonAst = json.parseJson
    val actual = jsonAst.convertTo[TextTranslation]
    assert(actual == translated)
  }

  test("should deserialize successfully translated speech JSON into a TextTranslation object") {
    val json =
      """
        {
          "version": "3.0",
          "header": {
            "status": "success",
            "scenario": "ulm",
            "name": "start playing music",
            "lexical": "start playing music",
            "properties": { "requestid": "request id", "HIGHCONF": "1" }
          },
          "results": [{
            "scenario": "ulm",
            "name": "start playing music",
            "lexical": "start playing music",
            "confidence": "0.8484803",
            "properties": { "HIGHCONF": "1" }
          }]
        }
      """

    val results = List(Result("start playing music", "start playing music", 0.8484803))
    val translated = TextTranslation(Header("success"), Some(results))

    val jsonAst = json.parseJson
    val actual = jsonAst.convertTo[TextTranslation]
    assert(actual == translated)
  }
}
