package com.jangle.ConroyTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javax.swing.text.html.HTMLEditorKit.Parser;

import com.jangle.*;
import com.jangle.client.*;
import com.jangle.communicate.Client_ParseData;
import com.jangle.communicate.CommUtil;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException, LineUnavailableException {

		// Client Cl = new Client();
		// Client_ParseData Parse = null;
		// // TestServer server = new TestServer(9090);
		//
		// try {
		// Parse = new Client_ParseData(Cl, "localhost", 9090);
		// System.out.println("generated client");
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// Thread.sleep(1000);

		// EDIT BELOW HERE

		// set up the TargetDataLine
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		TargetDataLine microphone;
		SourceDataLine speakers;
		try {
			microphone = AudioSystem.getTargetDataLine(format);

			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			microphone = (TargetDataLine) AudioSystem.getLine(info);
			microphone.open(format);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int numBytesRead;
			int CHUNK_SIZE = 1024;
			byte[] data = new byte[microphone.getBufferSize() / 5];
			microphone.start();

			int bytesRead = 0;
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
			speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speakers.open(format);
			speakers.start();
			
			
			
			
			
			while (bytesRead < 100000) {
				numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
				bytesRead += numBytesRead;
				// write the mic data to a stream for use later
				out.write(data, 0, numBytesRead);
				// write mic data to stream for immediate playback
				speakers.write(data, 0, numBytesRead);
			}
			
			//close the various buffers
			speakers.drain();
			speakers.close();
			microphone.close();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

}
