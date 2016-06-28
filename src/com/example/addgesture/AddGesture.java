package com.example.addgesture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddGesture extends Activity {
	
	EditText editText;
	GestureOverlayView gestureView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //editText = (EditText)findViewById(R.id.gesture_name);  //获取文本编辑框
        gestureView = (GestureOverlayView)findViewById(R.id.gesture); //获取手势编辑视图
        gestureView.setGestureColor(Color.RED); //设置手势的绘制颜色
        gestureView.setGestureStrokeWidth(4); //设置手势的绘制宽度
        //为gesture的手势完成事件绑定事件监听器
        gestureView.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			
			@Override
			public void onGesturePerformed(GestureOverlayView overlay, final Gesture gesture) {
				// TODO Auto-generated method stub
				View saveDialog = getLayoutInflater().inflate(R.layout.save, null); //加载save.xml界面布局代表的视图
				editText = (EditText)findViewById(R.id.gesture_name);  //获取文本编辑框
				ImageView imageView = (ImageView)saveDialog.findViewById(R.id.show); //获取saveDialog里的show组件
				final EditText gestureName = (EditText)saveDialog.findViewById(R.id.gesture_name); //获取saveDialog里的gesture_name组件
				//根据gesture包含的手势创建一个位图
				Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xFFF000); 
				imageView.setImageBitmap(bitmap);
				//使用对话框显示saveDialog组件
				new AlertDialog.Builder(AddGesture.this)
				.setView(saveDialog)
				.setPositiveButton("保存", new OnClickListener() {
					
					@SuppressLint("SdCardPath") @Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						GestureLibrary gestureLib = GestureLibraries.fromFile("/sdcard/mygestures"); //获取指定文件对应的手势库
						gestureLib.addGesture(gestureName.getText().toString(), gesture); //添加手势
						gestureLib.save();  //保存手势库
					}
				})
				.setNegativeButton("取消", null).show();
			}
		});
        
    }
}
