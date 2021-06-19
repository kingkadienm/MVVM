package com.wangzs.app_base.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.wangzs.app_base.AppApplicationContext;
import com.wangzs.app_base.R;




@SuppressLint("InflateParams")
public class ToastUtils {
    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    @ColorInt
    private static int ERROR_COLOR = Color.parseColor("#353A3E");
    @ColorInt
    private static int INFO_COLOR = Color.parseColor("#353A3E");
    @ColorInt
    private static int SUCCESS_COLOR = Color.parseColor("#353A3E");
    @ColorInt
    private static int WARNING_COLOR = Color.parseColor("#353A3E");
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");

    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 16; // in SP

    private static boolean tintIcon = false;
    private static Toast currentToast;

    private ToastUtils() {
        // avoiding instantiation
    }

    public static void normal(@NonNull CharSequence message) {
        normal(message, Toast.LENGTH_SHORT, null, false);
    }

    public static void normal(@NonNull CharSequence message, Drawable icon) {
        normal(message, Toast.LENGTH_SHORT, icon, false);
    }

    public static void normal(@NonNull CharSequence message, int duration) {
        normal(message, duration, null, false);
    }

    public static void normal(@NonNull CharSequence message, int duration,
                              Drawable icon) {
        normal(message, duration, icon, false);
    }

    public static void normal(@NonNull CharSequence message, int duration,
                              Drawable icon, boolean withIcon) {
        custom(message, icon, NORMAL_COLOR, duration, withIcon, false);
    }

    public static void warning(@NonNull CharSequence message) {
        warning(message, Toast.LENGTH_SHORT, false);
    }

    public static void warning(@NonNull CharSequence message, int duration) {
        warning(message, duration, false);
    }

    public static void warning(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, getDrawable(R.drawable.app_base_toasy_error_outline_white),
                WARNING_COLOR, duration, withIcon, false);
    }

    public static void info(@NonNull CharSequence message) {
        info(message, Toast.LENGTH_SHORT, false);
    }


    public static void info(@StringRes int message) {
        info(AppApplicationContext.getContext().getString(message), Toast.LENGTH_SHORT, false);
    }

    public static void info(@NonNull CharSequence message, int duration) {
        info(message, duration, false);
    }

    public static void info(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, getDrawable(R.drawable.app_base_toasy_info_outline_white),
                INFO_COLOR, duration, withIcon, false);
    }

    public static void success(@NonNull CharSequence message) {
        success(message, Toast.LENGTH_SHORT, false);
    }

    public static void success(@NonNull CharSequence message, int duration) {
        success(message, duration, false);
    }

    public static void success(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, getDrawable(R.drawable.app_base_toasy_check_white),
                SUCCESS_COLOR, duration, withIcon, false);
    }

    public static void error(@NonNull CharSequence message) {
        error(message, Toast.LENGTH_SHORT, false);
    }

    public static void error(@NonNull CharSequence message, int duration) {
        error(message, duration, false);
    }

    public static void error(@NonNull CharSequence message, int duration, boolean withIcon) {
        custom(message, getDrawable(R.drawable.app_base_error_emoj),
                ERROR_COLOR, duration, withIcon, false);
    }

    public static void custom(@NonNull CharSequence message, Drawable icon,
                              int duration, boolean withIcon) {
        custom(message, icon, -1, duration, withIcon, false);
    }

    public static void custom(@NonNull CharSequence message, @DrawableRes int iconRes,
                              @ColorInt int tintColor, int duration,
                              boolean withIcon, boolean shouldTint) {
        custom(message, getDrawable(iconRes),
                tintColor, duration, withIcon, shouldTint);
    }

    public static void custom(@NonNull CharSequence message, Drawable icon,
                              @ColorInt int tintColor, int duration,
                              boolean withIcon, boolean shouldTint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N /*&& !notificationEnabled*/) {
            BaseToast.show(message);
            return;
        }
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = new Toast(AppApplicationContext.getContext());
        currentToast.setGravity(Gravity.CENTER,0,0);
        final View toastLayout = ((LayoutInflater) AppApplicationContext.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.app_base_toast_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        toastLayout.setBackgroundResource(R.drawable.app_base_toast_bg);

        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            }
            if (tintIcon) {
                icon = tintIcon(icon, DEFAULT_TEXT_COLOR);
            }
            setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        toastTextView.setText(message);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        toastLayout.setPadding(35, 35, 35, 35);

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        currentToast.show();
    }

    public static class Config {
        @ColorInt
        private int DEFAULT_TEXT_COLOR = ToastUtils.DEFAULT_TEXT_COLOR;
        @ColorInt
        private int ERROR_COLOR = ToastUtils.ERROR_COLOR;
        @ColorInt
        private int INFO_COLOR = ToastUtils.INFO_COLOR;
        @ColorInt
        private int SUCCESS_COLOR = ToastUtils.SUCCESS_COLOR;
        @ColorInt
        private int WARNING_COLOR = ToastUtils.WARNING_COLOR;

        private Typeface typeface = ToastUtils.currentTypeface;
        private int textSize = ToastUtils.textSize;

        private boolean tintIcon = ToastUtils.tintIcon;

        private Config() {
            // avoiding instantiation
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            ToastUtils.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
            ToastUtils.ERROR_COLOR = Color.parseColor("#353A3E");
            ToastUtils.INFO_COLOR = Color.parseColor("#353A3E");
            ToastUtils.SUCCESS_COLOR = Color.parseColor("#353A3E");
            ToastUtils.WARNING_COLOR = Color.parseColor("#353A3E");
            ToastUtils.currentTypeface = LOADED_TOAST_TYPEFACE;
            ToastUtils.textSize = 16;
            ToastUtils.tintIcon = false;
        }

        @CheckResult
        public Config setTextColor(@ColorInt int textColor) {
            DEFAULT_TEXT_COLOR = textColor;
            return this;
        }

        @CheckResult
        public Config setErrorColor(@ColorInt int errorColor) {
            ERROR_COLOR = errorColor;
            return this;
        }

        @CheckResult
        public Config setInfoColor(@ColorInt int infoColor) {
            INFO_COLOR = infoColor;
            return this;
        }

        @CheckResult
        public Config setSuccessColor(@ColorInt int successColor) {
            SUCCESS_COLOR = successColor;
            return this;
        }

        @CheckResult
        public Config setWarningColor(@ColorInt int warningColor) {
            WARNING_COLOR = warningColor;
            return this;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        public void apply() {
            ToastUtils.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR;
            ToastUtils.ERROR_COLOR = ERROR_COLOR;
            ToastUtils.INFO_COLOR = INFO_COLOR;
            ToastUtils.SUCCESS_COLOR = SUCCESS_COLOR;
            ToastUtils.WARNING_COLOR = WARNING_COLOR;
            ToastUtils.currentTypeface = typeface;
            ToastUtils.textSize = textSize;
            ToastUtils.tintIcon = tintIcon;
        }
    }
    static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
        drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    private static Drawable tint9PatchDrawableFrame(@ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(R.drawable.app_base_toast_frame);
        return tintIcon(toastDrawable, tintColor);
    }

    static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return AppApplicationContext.getContext().getDrawable(id);
        } else {
            return AppApplicationContext.getContext().getResources().getDrawable(id);
        }
    }
}
