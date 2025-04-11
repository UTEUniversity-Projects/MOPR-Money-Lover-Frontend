package com.moneylover.data.model.others;

import android.view.Gravity;
import android.widget.Toast;
import android.content.Context;
import es.dmoral.toasty.Toasty;
import lombok.Data;

@Data
public class ToastMessage {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_ERROR = 2;
    public static final int TYPE_WARNING = 3;

    private int type;
    private String message;
    private int gravity;

    public ToastMessage(int type, String message) {
        this(type, message, Gravity.BOTTOM);
    }

    public ToastMessage(int type, String message, int gravity) {
        this.type = type;
        this.message = message;
        this.gravity = gravity;
    }

    public void showMessage(Context context) {
        Toast toast = null;

        switch (type) {
            case TYPE_NORMAL:
                toast = Toasty.normal(context, message);
                break;
            case TYPE_SUCCESS:
                toast = Toasty.success(context, message);
                break;
            case TYPE_WARNING:
                toast = Toasty.warning(context, message);
                break;
            case TYPE_ERROR:
                toast = Toasty.error(context, message);
                break;
            default:
                break;
        }

        if (toast != null) {
            toast.setGravity(gravity, 0, 100);
            toast.show();
        }
    }
}
