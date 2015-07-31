/**
 * 悠播HD Android客户端
 *
 * 本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目的 ，
 * 如有疑问，请联系http://www.sxmobi.com。
 * 2011 深讯和公司，所有版权保留. 
 */
package com.hxyr.gocarapp.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hxyr.gocarapp.application.GoCarApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.WindowManager;


public class BitmapUtil {

	public static Bitmap getBitmap(String path) {
		return BitmapFactory.decodeFile(path);
	}

	public static String saveBitmap(Bitmap bitmap, String path) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(path));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static String saveBitmap(Bitmap bitmap, String path,
			Bitmap.CompressFormat format) {
		if (path == null || bitmap == null)
			return null;

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(path));
			bitmap.compress(format, 100, bos);
			bos.flush();
			bos.close();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;

	}

	public static int saveBitmap(String strDir, String bitName, Bitmap mBitmap) {

		File fdir = new File(strDir);
		try {
			if (!fdir.exists()) {
				fdir.mkdirs();
			}
		} catch (Exception e) {
			return 1;
		}

		File f = new File(strDir + File.separator + bitName + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			return 1;

		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}

		try {
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

			fOut.flush();

		} catch (Exception e) {
			e.printStackTrace();
			if (null != fOut) {
				try {
					fOut.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return 1;
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		if (f.length() <= 0) {
			try {
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 2;
		}

		return 0;
	}
	
	public static int calcSampleSize(BitmapFactory.Options options,
			int dstWidth, int dstHeight) {
		int height = options.outHeight;
		int width = options.outWidth;

		int inSampleSize = 1;

		if (height > dstHeight || width > dstWidth) {
			int samplesize = Math.round((float) height / (float) dstHeight);
			inSampleSize = Math.round((float) width / (float) dstWidth);
			inSampleSize = Math.max(samplesize, inSampleSize);
		}
		return inSampleSize;
	}

    public static Bitmap convertToBitmap(String path, float scale) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        opts.inJustDecodeBounds = false;
        
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), (int)(width/scale), (int)(height/scale), true);
    }
	
    /**
     * 压缩图片
     * @param path
     * @return
     */
    public static void compressPic(String path, String newPath) {
    	try {
        	File targetFile = new File(newPath);
        	File orginFile = new File(path);
        	if(!targetFile.exists()) {
        		targetFile.createNewFile();
        	}
        	long length = orginFile.length();
        	long targetLength = (long) (200f*1024f);
        	float scale = 1f;
        	if(length > targetLength) {
        		scale = length/targetLength;//压缩到200k
        	}
        	
        	Bitmap bitmap = convertToBitmap(path, scale);
        	FileOutputStream fOut = null;
                    fOut = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public static void savaCutPic(String path, Bitmap btimap) {
    	
    }
    
	public static Bitmap loadBitmapFromFile(String path, int dstWidth,
			int dstHeight) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);

			options.inSampleSize = calcSampleSize(options, dstWidth, dstHeight);

			// Log.i("___________dstWidth, dstHeight",""+dstWidth+"--"+dstHeight);
			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			int degree = readPictureDegree(path);
			if (degree != 0) {
				return rotaingBitmap(degree, bm);
			}

			return bm;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {

		// 旋转图片 动作
		Matrix matrix = new Matrix();

		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 剪裁图片
	 * @param uri
	 */
    public static String startPhotoZoom(Activity context, Uri uri, String newPath) {   
    	Cursor cursor = context.getContentResolver().query(uri,null,null,null,null);
    	cursor.moveToFirst();  
    	String path = cursor.getString(1); 
    	BitmapUtil.compressPic(path, newPath);
    	Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, "image/*");  
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪  
        intent.putExtra("crop", "true");  
        // aspectX aspectY 是宽高的比例  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        // outputX outputY 是裁剪图片宽高  
        intent.putExtra("outputX", 300);  
        intent.putExtra("outputY", 300);  
        intent.putExtra("return-data", true);   
        context.startActivityForResult(intent, 3); 
    	return path;
        
    } 
	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	public static Bitmap loadBitmapFromFile(String path) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			int mbsize = options.outWidth * options.outHeight * 24 / 8 / 1024
					/ 1024;
			if (mbsize >= 3) {
				return loadBitmapFromFileByScreen(GoCarApplication.getInstance(), path);
			}
			return BitmapFactory.decodeFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap loadBitmapFromFileByScreen(Context cn, String path) {

		try {
			WindowManager wm = (WindowManager) cn
					.getSystemService(Context.WINDOW_SERVICE);

			int SW = wm.getDefaultDisplay().getWidth();
			int SH = wm.getDefaultDisplay().getHeight();

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);

			if (options.outWidth > SW || options.outHeight > SH) {
				return loadBitmapFromFile(path, SW, SH);
			}

			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			int degree = readPictureDegree(path);
			if (degree != 0) {
				return rotaingBitmap(degree, bm);
			}
			return bm;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap Rotate(Bitmap src, int nScaleAngle) {
		Bitmap bitampdest = null;

		try {
			if (null != src && src.getWidth() > 0 && src.getHeight() > 0) {

				int scaleWidth = src.getWidth();
				int scaleHeight = src.getHeight();

				int ScaleAngle = nScaleAngle;
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				matrix.setRotate(5 * ScaleAngle);

				bitampdest = Bitmap.createBitmap(src, 0, 0, scaleWidth,
						scaleHeight, matrix, true);

			}
		} catch (Exception e) {
			e.printStackTrace();

			bitampdest = null;
		}
		return bitampdest;
	}
	
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		try {
			if (drawable instanceof BitmapDrawable) {
				return ((BitmapDrawable) drawable).getBitmap();
			} else if (drawable instanceof NinePatchDrawable) {
				Bitmap bitmap = Bitmap
						.createBitmap(
								drawable.getIntrinsicWidth(),
								drawable.getIntrinsicHeight(),
								drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
										: Bitmap.Config.RGB_565);
				Canvas canvas = new Canvas(bitmap);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				drawable.draw(canvas);
				return bitmap;
			} else {
				return null;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Bitmap getBitmapFromUrl(String url) {
		InputStream is = null;
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				conn.disconnect();
				return bitmap;
			} else
				conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (is != null)
					is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
		}
		return null;

	}
	
	public static Bitmap getBitmapFromUrl(String url, int width, int height) {
		try {
			Bitmap bitmap = getBitmapFromUrl(url);
			return scaleFixedBitmap(bitmap, width, height);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 按比例缩放
	 * 
	 * @param bitmap
	 * @param dstWidth
	 * @param dstHeight
	 * @return
	 */
	public static Bitmap scaleFixedBitmap(Bitmap bitmap, int dstWidth,
			int dstHeight) {
		if (bitmap == null)
			return null;
		int h = bitmap.getHeight();
		int w = bitmap.getWidth();

		if (w > h) {
			if (w > dstWidth || h > dstHeight) {
				float scaleWidth = ((float) dstWidth) / w;
				float scaleHeight = ((float) dstHeight) / h;
				float scale = Math.min(scaleWidth, scaleHeight);
				Matrix matrix = new Matrix();
				matrix.postScale(scale, scale);
				Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h,
						matrix, true);
				if (bitmap != newBitmap) {
					bitmap.recycle();
					bitmap = null;
				}
				return newBitmap;
			} else {
				return bitmap;
			}
		} else {
			if (w > dstWidth || h > dstHeight) {
				float scaleWidth = ((float) dstHeight) / w;
				float scaleHeight = ((float) dstWidth) / h;
				float scale = Math.min(scaleWidth, scaleHeight);
				Matrix matrix = new Matrix();
				matrix.postScale(scale, scale);
				Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h,
						matrix, true);
				if (bitmap != newBitmap) {
					bitmap.recycle();
					bitmap = null;
				}
				return newBitmap;
			} else {
				return bitmap;
			}
		}

	}
	
	public static Bitmap toRoundBitmap(Bitmap bitmap) {  
        //圆形图片宽高  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        //正方形的边长  
        int r = 0;  
        //取最短边做边长  
        if(width > height) {  
            r = height;  
        } else {  
            r = width;  
        }  
        //构建一个bitmap  
        Bitmap backgroundBmp = Bitmap.createBitmap(width,  
                 height, Config.ARGB_8888);  
        //new一个Canvas，在backgroundBmp上画图  
        Canvas canvas = new Canvas(backgroundBmp);  
        Paint paint = new Paint();  
        //设置边缘光滑，去掉锯齿  
        paint.setAntiAlias(true);  
        //宽高相等，即正方形  
        RectF rect = new RectF(0, 0, r, r);  
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，  
        //且都等于r/2时，画出来的圆角矩形就是圆形  
        canvas.drawRoundRect(rect, r/2, r/2, paint);  
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        //canvas将bitmap画在backgroundBmp上  
        canvas.drawBitmap(bitmap, null, rect, paint);  
        //返回已经绘画好的backgroundBmp  
        return backgroundBmp;  
    }
}
