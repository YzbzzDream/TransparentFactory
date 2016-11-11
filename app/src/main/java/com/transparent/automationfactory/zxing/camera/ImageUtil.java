/*
 * @项目名称: ETCP停车
 * @文件名称: ImageUtil.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.transparent.automationfactory.zxing.camera;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.math.BigDecimal;

/*
 * @项目名称: ETCP停车
 * @文件名称: ImageUtil.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

public class ImageUtil {
	
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);

		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0,b.getWidth(),b.getHeight(), matrix, false);
		return rotaBitmap;
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,  
	        int reqWidth, int reqHeight) { 
		
		final BitmapFactory.Options mOptions = new BitmapFactory.Options();
		mOptions.inJustDecodeBounds = true;
		
		BitmapFactory.decodeResource(res, resId, mOptions);
		
		final int inSamplesize = calculateInSampleSize(mOptions, reqWidth, reqHeight);
		
		mOptions.inJustDecodeBounds = false;
		mOptions.inSampleSize = inSamplesize;
		
		return BitmapFactory.decodeResource(res, resId,mOptions);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,  
	        int reqWidth, int reqHeight) {  

		final int height = options.outHeight;
		final int width = options.outWidth;
		
		int inSampleSize = 1;
		
		if(height>reqHeight||width>reqWidth){
			final int widthRatio = Math.round((float)width/(float)reqWidth);
			final int heightRatio = Math.round((float)height/(float)reqHeight);
			
			inSampleSize = heightRatio<widthRatio?heightRatio:widthRatio;
		}
		
		return inSampleSize;
	}
	
	public static Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {  
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图  
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {return bitmap;}  
        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode);  
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃  
        float sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN).floatValue();  
        float sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN).floatValue();  
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸  
            sx = (sx < sy ? sx : sy);sy = sx;// 哪个比例小一点，就用哪个比例  
        }  
        Matrix matrix = new Matrix();  
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了  
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
    }  
	
}
