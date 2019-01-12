package com.example.doldrum.tortoiseandrabbit.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

public class ChipView extends View {

    //定义相关变量,依次是妹子显示位置的X,Y坐标
    public float bitmapX;
    public float bitmapY;
    private float pWidth,pHeight;
    public int number;
    public int res;
    public boolean isOther;
    private float ratio;
    public ChipView(Context context,int res,float width,float high) {
        super(context);
        //设置妹子的起始坐标
        this.res = res;
        Random rand = new Random();
        bitmapX = rand.nextInt((int) width);
        bitmapY = rand.nextInt((int)high);
        pWidth = width;
        pHeight = high;
        this.ratio = 0.5f;
    }


    public ChipView(Context context,int res,float width,float high,float ratio) {
        super(context);
        //设置妹子的起始坐标
        this.res = res;
        Random rand = new Random();
        bitmapX = rand.nextInt((int) width);
        bitmapY = rand.nextInt((int)high);
        pWidth = width;
        pHeight = high;
        this.ratio = ratio;
    }

    //重写View类的onDraw()方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建,并且实例化Paint的对象
        Paint paint = new Paint();
        //根据图片生成位图对象
        //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), res);
        Bitmap bitmap = scaleBitmap(BitmapFactory.decodeResource(this.getResources(), res), ratio);
        int width = bitmap.getWidth(); //图片的宽高
        int height = bitmap.getHeight();

        //绘制萌妹子
        if (bitmapX<width){
            bitmapX += width;
        }
        if (bitmapX>(pWidth-width)){
            if (ratio == 0.25){
                bitmapX = bitmapX - width;
            }else {
                bitmapX -= width;
            }
        }
        if (bitmapY < height){
            bitmapY+= height;
        }
        if (bitmapY > (pHeight - height)){
            bitmapY-= height;
        }

        bitmapX = bitmapX - 20;
        bitmapY = bitmapY - 20;
        canvas.drawBitmap(bitmap, bitmapX, bitmapY,paint);
        //判断图片是否回收,木有回收的话强制收回图片
        if(bitmap.isRecycled())
        {
            bitmap.recycle();
        }
    }


    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}
