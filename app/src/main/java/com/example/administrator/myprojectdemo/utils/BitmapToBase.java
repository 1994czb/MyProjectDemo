package com.example.administrator.myprojectdemo.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapToBase {
	
	/**
	 * Bitmap  转成Base64编码的工具类
	 * */
	
	public static void recycleBitmap(Bitmap bitmap){
		if(bitmap!=null){
			if(!bitmap.isRecycled()){ 
				bitmap.recycle();  
			} 
		}
	}
	
	/**
	 * �ϴ�ͷ��ʱ��bitmapת����Base64�ַ�����
	 * */
	
	public static String bitmapToBase64(Bitmap bitmap) {
		String result = "";
		ByteArrayOutputStream bos = null;
		try {
			if (null != bitmap) {
				bos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);// ��bitmap�����ֽ���������
				bos.flush();// ��bos���������ڴ��е�����ȫ���������ջ���
				bos.close();
				byte[] bitmapByte = bos.toByteArray();
				result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
