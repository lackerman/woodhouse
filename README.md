# Scala Speech Understanding playground

Playing with Luis whilst learning about AKKA
 - Akka Actors
 - Java Sound API
 - Jackson
 - Scalatest & JUnit
 - Apache HttpClient
 - Sbt

1. Tell the application what to do
    `1 <ENTER>` Start recording from Mic
    `2 <ENTER>` Stop recording from Mic

2. Use Project Oxford (Microsoft's Speech-to-Text service) to translate Speech to Text
3. Send the text to Luis (Microsoft's Language Understanding Intelligent Service) to derive the intent of the text
4. Let's see