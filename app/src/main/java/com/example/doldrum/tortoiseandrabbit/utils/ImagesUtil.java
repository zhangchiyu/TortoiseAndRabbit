package com.example.doldrum.tortoiseandrabbit.utils;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;


/**
 * Created by ywt on 2015/8/25 0025.
 */
public class ImagesUtil {

    public static final int PHOTO_REQUEST_CAMERA = 7;   // 拍照
    public static final int PHOTO_REQUEST_GALLERY = 8;  // 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 9;      // 结果
    public static final int CROP = 10;                  // 裁剪上传
    public static final int UNCROP = 11;                // 不裁剪上传
    public static final int ENCRPT = 12;                // 加密上传
    public static final int UNENCRPT = 13;              // 不加密上传
    public static final int FLAG_FRONT = 1;             // 正面
    public static final int FLAG_BACK = 2;              // 反面
    public static final int FLAG_HAND = 3;              // 手持证件
    public static final int FLAG_EXTRA = 4;             // 额外的证件（如公司营业证）
    public static final String CAMERA_P = "camera_path";// 拍照存储位置
    public static final String CROP_P = "crop_path";    // 裁剪存储位置





    /**
     * 显示头像信息--圆形显示 （加载资源文件中的图片）
     *
     * @param ivDisplay 显示控件
     */
    public static void displayCircleImage(int resource, ImageView ivDisplay) {
        Glide.with(App.getInstance()).load(resource)
                .placeholder(R.drawable.ic_user_head)
                .error(R.drawable.ic_user_head)
                .transform(new GlideCircleTransform(App.getInstance()))
                .into(ivDisplay);
    }

    //图片处理
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI(Uri contentUri) {
        String res = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(App.getInstance(), contentUri)) {
            String wholeID = DocumentsContract.getDocumentId(contentUri);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = App.getInstance().getContentResolver().query(MediaStore.Images.Media
                            .EXTERNAL_CONTENT_URI, column,
                    sel, new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                res = cursor.getString(columnIndex);
            }
            cursor.close();
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = App.getInstance().getContentResolver().query(contentUri, projection, null,
                    null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            res = cursor.getString(column_index);
        }
        return res;
    }



}
