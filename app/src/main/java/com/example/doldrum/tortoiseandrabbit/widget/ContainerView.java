package com.example.doldrum.tortoiseandrabbit.widget;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.bean.PointBitmap;
import java.util.ArrayList;
import java.util.HashMap;

public class ContainerView extends View {
    private Paint paint;
    private ArrayList<PointBitmap> list = new ArrayList<>();
    private Resources resources;
    private int raduis;
    private HashMap<Integer,Bitmap> map = new HashMap<>();
    private int [] resid = {R.mipmap.qian,R.mipmap.wan,R.mipmap.tenwan,R.mipmap.ttwan,R.mipmap.fwan};


    public ContainerView(Context context) {
        this(context,null);
    }

    public ContainerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ContainerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resources = context.getResources();
        init();
    }

    private void init() {
        raduis = 80;
        paint = new Paint();
        paint.setAntiAlias(true);
        for (int i = 0 ; i< resid.length ;i++){
            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(resources, resid[i]), raduis,raduis);
            map.put(resid[i],bitmap);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0 ; i< list.size();i++){
            PointBitmap pointBitmap = list.get(i);
            int bitmaprseId = pointBitmap.getBitmaprseId();
            Bitmap bitmap = map.get(bitmaprseId);
            canvas.drawBitmap(bitmap,pointBitmap.getPoint().x,pointBitmap.getPoint().y,paint);
        }


    }

    public ArrayList<PointBitmap> getList() {
        return list;
    }

    public void setList(ArrayList<PointBitmap> list) {
        this.list = list;
        invalidate();
    }
}
