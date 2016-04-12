package io.bibliotheca.woodhouse.audio

import java.io.File
import java.util.UUID
import javax.sound.sampled._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Open the MIC channel and start writing to the Speakers
  *
  * @see TargetDataLine - Target actually refers to the Audio Input
  * @see SourceDataLine - Source actually refers to the Audio output target
  */
class Mic {
  implicit val ec = ExecutionContext.global

  private val format = new AudioFormat(16000, 8, 2, true, true)
  private val target = new DataLine.Info(classOf[TargetDataLine], format)
  private val input = AudioSystem.getLine(target).asInstanceOf[TargetDataLine]

  def read(): Future[String] =
    Future {
      input.open(format)
      input.start()
      val filename = s"${UUID.randomUUID().toString}.wav"
      val inputStream = new AudioInputStream(input)
      AudioSystem.write(inputStream, AudioFileFormat.Type.WAVE, new File(filename))
      filename
    }

  def stop(): Unit = {
    input.stop()
    input.close()
  }
}
