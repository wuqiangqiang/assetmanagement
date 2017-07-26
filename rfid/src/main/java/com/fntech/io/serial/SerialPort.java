package com.fntech.io.serial;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*class ByteData
{
 public byte[] bytedata; 
}*/

public class SerialPort {
	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method
	 * close();
	 */
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;

	public SerialPort(File device, int baudrate, int databit,int stopbit,int parity)
			throws SecurityException, IOException {

		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			try {
				/* Missing read/write permission, trying to chmod the file */
				Process su;
				su = Runtime.getRuntime().exec("/system/bin/su");
				String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
						+ "exit\n";
				su.getOutputStream().write(cmd.getBytes());
				if ((su.waitFor() != 0) || !device.canRead()
						|| !device.canWrite()) {
					throw new SecurityException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SecurityException();
			}
		}

		mFd = open(device.getAbsolutePath(), baudrate, databit,stopbit,parity);
		if (mFd == null) {
			//Log.e(TAG, "native open returns null");
			throw new IOException();
		}
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
	}

	// Getters and setters
	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}

	// JNI 
	/*
	  path 锟斤拷锟斤拷锟借备锟侥硷拷路锟斤拷
	  baudrate 锟斤拷锟斤拷锟斤拷 1200 2400 4800 9600 ... 57600 115200
	  databit 锟斤拷锟轿� 8
	  stopbit 停止位 0 1 2
	  parity 校锟斤拷位 0 1
	*/
	private native static FileDescriptor open(String path, int baudrate,int databit,int stopbit,int parity);

	public native void close();
	
	public native int cleardata();
	
	/*
	 jni 锟斤拷锟斤拷指锟斤拷锟斤拷锟斤拷锟斤拷锟�
	*/
	public native int senddata(byte[] sendbyte,int len,int timeout);
	
	/*
	  jni 锟斤拷锟斤拷指锟斤拷锟斤拷锟斤拷锟斤拷锟�
	*/
	public native int recvdata(ByteData recvdata,int len,int timeout); 

	static {
		System.loadLibrary("fntech_serial");
	}
}
