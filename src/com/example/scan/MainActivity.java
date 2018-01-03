package com.example.scan;

import java.io.ByteArrayOutputStream;

import com.example.zxing.activity.CaptureActivity;
import com.example.zxing.encoding.EncodingHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView zxing_content;
	private ImageView create_img;
	private Button create_btn;
	private Button scan_btn;
	private EditText create_edit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        initEvent();
        
    }
    
    private void initEvent() {
        create_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 switch (v.getId()){
			        case R.id.create_btn:
			          String content = "" ;
			          if (create_edit.getText().toString().equals("")){
			            //Toast.makeText(this,"请输入二维码信息",Toast.LENGTH_SHORT).show();
			            return;
			          }
			          content = create_edit.getText().toString();
			          try {
			            //生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
			            Bitmap qrcodeBitmap = EncodingHandler.createQRCode(content, 800);
			            if (qrcodeBitmap!=null) {
			              create_img.setImageBitmap(qrcodeBitmap);
			            }else{
			              //Toast.makeText(this,"生成二维码失败",Toast.LENGTH_SHORT).show();
			            }
			          } catch (Exception e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			          }
			          break;
			      }
			}
		});
        
        scan_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent startScan = new Intent(MainActivity.this,CaptureActivity.class);
			     startActivityForResult(startScan, 0);
			}
		});
      }

    private void initView() {
    	zxing_content = (TextView) findViewById(R.id.create_edit);
        create_img = (ImageView) findViewById(R.id.create_img);
        create_btn = (Button) findViewById(R.id.create_btn);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        create_edit = (EditText) findViewById(R.id.create_edit);
      }
    
    
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	if( resultCode == -1){
    		String result = data.getExtras().getString("result");
    		zxing_content.setText(result);
    	}
    }
    
}