package example.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExampleUtils {
  public static void writePcmToWavFile(byte[] pcmData, String filepath) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(filepath)) {
      int channels = 1; // 单声道
      int sampleWidth = 2;
      int frameRate = 24000;
      // RIFF header
      writeString(fos, "RIFF");
      writeInt(fos, 36 + pcmData.length);
      writeString(fos, "WAVE");

      // Format chunk
      writeString(fos, "fmt ");
      writeInt(fos, 16); // Subchunk1Size
      writeShort(fos, (short) 1); // AudioFormat (PCM)
      writeShort(fos, (short) channels);
      writeInt(fos, frameRate);
      writeInt(fos, frameRate * channels * sampleWidth); // ByteRate
      writeShort(fos, (short) (channels * sampleWidth)); // BlockAlign
      writeShort(fos, (short) (sampleWidth * 8)); // BitsPerSample

      // Data chunk
      writeString(fos, "data");
      writeInt(fos, pcmData.length);
      fos.write(pcmData);
    }
  }

  private static void writeString(FileOutputStream fos, String value) throws IOException {
    fos.write(value.getBytes(StandardCharsets.US_ASCII));
  }

  private static void writeInt(FileOutputStream fos, int value) throws IOException {
    fos.write(value & 0xFF);
    fos.write((value >> 8) & 0xFF);
    fos.write((value >> 16) & 0xFF);
    fos.write((value >> 24) & 0xFF);
  }

  private static void writeShort(FileOutputStream fos, short value) throws IOException {
    fos.write(value & 0xFF);
    fos.write((value >> 8) & 0xFF);
  }
}
