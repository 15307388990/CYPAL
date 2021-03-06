package com.cypal.ming.cypal.service;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cypal.ming.cypal.R;

public class NotificationUpdateActivity extends Activity {
	private DownloadService.DownloadBinder binder;
	private boolean isBinded;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量
	private boolean isDestroy = true;
	private TextView progress_count;

	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.update);
		showDownloadDialog();
	}

	private void showDownloadDialog() {
		// 构造软件下载对话框
		Builder builder = new Builder(this,AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		progress_count = (TextView) v.findViewById(R.id.progress_count);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置取消状态
				if (binder != null) {
					binder.cancel();
					binder.cancelNotification();
				}
				finish();
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		// downloadApk();
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (DownloadService.DownloadBinder) service;
			System.out.println("服务启动!!!");
			// 开始下载
			isBinded = true;
			binder.addCallback(callback);
			binder.start();

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isDestroy) {
			Intent it = new Intent(NotificationUpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
		System.out.println(" notification  onresume");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (isDestroy ) {
			Intent it = new Intent(NotificationUpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
		System.out.println(" notification  onNewIntent");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println(" notification  onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isDestroy = false;
		System.out.println(" notification  onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isBinded) {
			System.out.println(" onDestroy   unbindservice");
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			System.out.println(" onDestroy  stopservice");
			Intent it = new Intent(this, DownloadService.class);
			stopService(it);
		}
	}

	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			// TODO Auto-generated method stub
			if ("finish".equals(result)) {
				finish();
				return;
			}
			int i = (Integer) result;
			mProgress.setProgress(i);

			mHandler.sendEmptyMessage(i);
		}

	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progress_count.setText("当前进度 ： " + msg.what + "%");
		};
	};

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}
}
