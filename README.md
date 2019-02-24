# Scala Speech Understanding playground

Playing with Luis whilst learning about AKKA
- [akka actors](https://doc.akka.io/docs/akka/2.5/actors.html)
- Java Sound API
- [spray-json](https://github.com/spray/spray-json)
- [Scalatest](http://www.scalatest.org/)
- [JUnit 4](https://junit.org/junit4/)
- [Apache HttpClient](https://hc.apache.org/httpcomponents-client-4.5.x/index.html)
- [sbt](https://www.scala-sbt.org/)

1. Tell the application what to do
    - `1 <ENTER>` Start recording from Mic
    - `2 <ENTER>` Stop recording from Mic
2. Use Project Oxford (Microsoft's Speech-to-Text service) to translate Speech to Text
3. Send the text to Luis (Microsoft's Language Understanding Intelligent Service) to derive the intent of the text
4. Let's see