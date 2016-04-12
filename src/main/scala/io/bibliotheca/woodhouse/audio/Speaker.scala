package io.bibliotheca.woodhouse.audio

import java.io.File
import javax.sound.sampled._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Open the Speaker channel for writing output
  *
  * @see TargetDataLine - Target actually refers to the Audio Input
  * @see SourceDataLine - Source actually refers to the Audio output target
  */
object Speaker {
  implicit val ec = ExecutionContext.global

  def play(filename: String): Future[Int] =
    Future {
      val stream = AudioSystem.getAudioInputStream(new File(filename))
      val format = stream.getFormat
      val info = new DataLine.Info(classOf[SourceDataLine], format)

      val output = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]
      output.open(format)
      output.start()

      val data = new Array[Byte](1024)
      def read(): Int = {
        val bytesRead = stream.read(data, 0, data.length)
        if (bytesRead != -1) {
          val bytesWritten = output.write(data, 0, bytesRead)
          bytesWritten + read()
        } else {
          0
        }
      }
      val bytesWritten = read()

      output.drain()
      output.close()
      bytesWritten
    }
}