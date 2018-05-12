package com.dl.dlexerciseandroid.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by logicmelody on 2016/5/27.
 */
public class MusicUtils {

    public static String getAudioFilePath(Context context, Uri uri) {
        String filePath = null;

        if(uri != null) {
            Cursor cursor = null;

            try {
                // 傳進來的是Audio的uri，我們去query MediaStore.Audio.AudioColumns.DATA這個欄位，就可以得到完整的file path
                cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Audio.AudioColumns.DATA},
                        null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    filePath = cursor.getString(0);

                    if (filePath == null || filePath.trim().isEmpty()) {
                        filePath = uri.toString();
                    }
                }

            } catch(Exception e) {
                e.printStackTrace();
                filePath = uri.toString();

            } finally {
                if(cursor != null) {
                    cursor.close();
                }
            }

        } else {
            filePath = uri.toString();
        }

        return filePath;
    }

    public static boolean isMusicFile(String musicFilePath) {
        String extension = Utils.getExtensionFrom(musicFilePath.trim());

        Log.d("danny", "Music file path = " + musicFilePath);
        Log.d("danny", "Music file extension = " + extension);

        if (TextUtils.isEmpty(extension)) {
            return false;
        }

        extension = extension.toLowerCase();

        if (extension.equals("mp3")) {
            return true;

        } else {
            return false;
        }
    }
}
