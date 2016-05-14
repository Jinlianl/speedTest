package com.example.speedtest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.os.AsyncTask;

public class DownloadManager extends AsyncTask<String, Integer, String> {

	private InputStream input;
	private String outputDir;
	private OutputStream output;
	private long total;

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		// mProgressDialog.show();
	}

	@Override
	protected String doInBackground(String... url1) {
		int count; // counter for DL file size

		try {

			URL url = new URL(url1[0]);
			// download the file
			input = new BufferedInputStream(url.openStream()); // loading
																// download
			outputDir = "/sdcard/speedTest/"; // address
			File destDir = new File(outputDir);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			output = new FileOutputStream(outputDir + "Temp.tp"); // download
			// directory

			byte data[] = new byte[1024];

			total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();

		}

		catch (Exception e) {
		}
		return null;
	}

	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// mProgressDialog.dismiss();
	}
	
	public long getSize(){
		return total;
	}
	public String getODir(){
		return outputDir;
	}

	public void stopDownload() {
		if (input != null) {
			try {    
				output.flush();
				output.close();
				input.close();
				// handler.postDelayed(delTemp, 100);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}