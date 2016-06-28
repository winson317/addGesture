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

        //editText = (EditText)findViewById(R.id.gesture_name);  //��ȡ�ı��༭��
        gestureView = (GestureOverlayView)findViewById(R.id.gesture); //��ȡ���Ʊ༭��ͼ
        gestureView.setGestureColor(Color.RED); //�������ƵĻ�����ɫ
        gestureView.setGestureStrokeWidth(4); //�������ƵĻ��ƿ��
        //Ϊgesture����������¼����¼�������
        gestureView.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			
			@Override
			public void onGesturePerformed(GestureOverlayView overlay, final Gesture gesture) {
				// TODO Auto-generated method stub
				View saveDialog = getLayoutInflater().inflate(R.layout.save, null); //����save.xml���沼�ִ������ͼ
				editText = (EditText)findViewById(R.id.gesture_name);  //��ȡ�ı��༭��
				ImageView imageView = (ImageView)saveDialog.findViewById(R.id.show); //��ȡsaveDialog���show���
				final EditText gestureName = (EditText)saveDialog.findViewById(R.id.gesture_name); //��ȡsaveDialog���gesture_name���
				//����gesture���������ƴ���һ��λͼ
				Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xFFF000); 
				imageView.setImageBitmap(bitmap);
				//ʹ�öԻ�����ʾsaveDialog���
				new AlertDialog.Builder(AddGesture.this)
				.setView(saveDialog)
				.setPositiveButton("����", new OnClickListener() {
					
					@SuppressLint("SdCardPath") @Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						GestureLibrary gestureLib = GestureLibraries.fromFile("/sdcard/mygestures"); //��ȡָ���ļ���Ӧ�����ƿ�
						gestureLib.addGesture(gestureName.getText().toString(), gesture); //�������
						gestureLib.save();  //�������ƿ�
					}
				})
				.setNegativeButton("ȡ��", null).show();
			}
		});
        
    }
}
