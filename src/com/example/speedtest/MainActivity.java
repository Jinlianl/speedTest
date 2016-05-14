package com.example.speedtest;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speedtest.DownloadManager;

//import android.view.Window;

public class MainActivity extends Activity {

	private long netSpeed;
	private long curSpeed;
	private long total;
	protected long preSize;
	protected long previousTime;
	protected long currentTime;
	public long timeSpend;

	protected String outputDir;

	protected DownloadManager downloadFile;
	protected NetworkUtils networkUtils = new NetworkUtils();
	public DrawAnimation draw;
	private TextView op;
	private TextView tp;
	private Button start = null;
	private TextView tv = null;

	private boolean isTesting = false;

	protected String carrier;
	protected String type = "unknown";

	// Access Network Environment
	private void getStatus() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		// Network Operator Name
		carrier = tm.getNetworkOperatorName();
		/**
		 * Access Mobile Network Type * NETWORK_TYPE_CDMA----CDMA
		 * NETWORK_TYPE_EDGE ----------EDGE NETWORK_TYPE_EVDO_0--EVDO0
		 * NETWORK_TYPE_EVDO_A--------EVDOA NETWORK_TYPE_GPRS----GPRS
		 * NETWORK_TYPE_HSDPA----------HSDPA NETWORK_TYPE_HSPA----HSPA
		 * NETWORK_TYPE_HSUPA----------HSUPA NETWORK_TYPE_UMTS----UMTS
		 */
		if (NetworkUtils.isNetworkAvailable(MainActivity.this) == true) {
			// Indicate WiFi or Mobile Network
			if (NetworkUtils.isWifi(MainActivity.this) == true) {
				type = "WiFi";
			} else {
				networkUtils.netType(tm, type);
			}
		} else {
			Toast.makeText(this, "当前无网络\n请检查网络设置", Toast.LENGTH_LONG).show();
		}
	}

	// set up a handler
	Handler handler = new Handler();

	// set up runnable programs controlling by handler
	// start downloading
	Runnable startTest = new Runnable() {
		public void run() {
			preSize = 0;
			downloadFile
					.execute("http://ftp.sjtu.edu.cn/ubuntu-cd//quantal/ubuntu-12.10-desktop-i386.iso");
			previousTime = System.currentTimeMillis();
		}
	};

	// printing current download speed
	Runnable speedCount = new Runnable() {
		public void run() {
			total = downloadFile.getSize();
			curSpeed = (total - preSize) / 1000;
			draw.passValue(curSpeed);
			tv.setText("速度:\n"+Long.toString(curSpeed) + "KB/s");
			preSize = total;
			handler.postDelayed(speedCount, 1000);

		}
	};
	// end task and print result
	Runnable endTest = new Runnable() {

		@Override
		public void run() {
			handler.removeCallbacks(speedCount);
			currentTime = System.currentTimeMillis();
			timeSpend = currentTime - previousTime;
			total = downloadFile.getSize();
			netSpeed = total / timeSpend;
			// calculate avg download speed
			tv.setText("平均速度\n" + Long.toString(netSpeed) + " KB/sec");
			downloadFile.stopDownload();
			draw.passValue(netSpeed);
			handler.postDelayed(delTemp, 100);
		}

	};
	// set up a way to delete temporary files
	Runnable delTemp = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			outputDir = downloadFile.getODir();
			File temp = new File(outputDir + "Temp.tp");
			temp.delete();
			isTesting = false;
			start.setText("Start");
		}
	};

	public void speedtestAct() {
		downloadFile = new DownloadManager();
		isTesting = true;
		handler.post(startTest);
		handler.postDelayed(speedCount, 1000);
		handler.postDelayed(endTest, 10000);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		draw = (DrawAnimation) findViewById(R.id.imageView);
		start = (Button) findViewById(R.id.button_start);
		tv = (TextView) findViewById(R.id.textView_result);
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isTesting == false) {
					speedtestAct();// setting speed test timer
					start.setText("Cancel");// - total 10 seconds

				} else {
					handler.removeCallbacks(endTest);
					handler.post(endTest);
					isTesting = false;
				}

			}

		});
		op = (TextView) findViewById(R.id.textView_op);
		tp = (TextView) findViewById(R.id.textView_tp);
		getStatus();
		op.setText("	运营商：" + carrier);
		tp.setText("	网络类型：" + type);

	}

}
