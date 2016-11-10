package com.transparent.automationfactory.base.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transparent.automationfactory.R;


public class DialogUtils {

	public static Dialog createLoadingDialog(Context context) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);

		loadingDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		loadingDialog.setCancelable(true);
		loadingDialog.setContentView(layout);
		return loadingDialog;
	}

	public static Dialog createDialog(Context context) {
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCancelable(true);

		loadingDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		return loadingDialog;
	}

	/**
	 * @param context
	 *            上下文
	 * @param ImgRes
	 *            图片资源
	 * @param message
	 *            对话框提示信息
	 * @param btnTtitle
	 *            按钮文字
	 * @param listener
	 *            回调事件
	 */
	public static void showDialog(Context context, int ImgRes, String message, String btnTtitle, final ISingleButtonListener listener) {
		final Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCancelable(false);

		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.dialog_hint, null);
		ImageView im = (ImageView) layout.findViewById(R.id.img);
		im.setImageResource(ImgRes);
		TextView tx = (TextView) layout.findViewById(R.id.text);
		tx.setText(message);
		Button b = (Button) layout.findViewById(R.id.button);
		b.setText(btnTtitle);
		b.setTextColor(context.getResources().getColor(R.color.white));
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onBtnClickListener(loadingDialog);
				}
			}
		});
		loadingDialog.setContentView(layout);

		loadingDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		loadingDialog.show();
	}

	public static void showDialog(Context context, int ImgRes, String message, String btnCancelTtitle, String btnOkTtitle, boolean bCancelable, final ITwoButtonListener listener) {
		final Dialog loadingDialog = createCustomDialog(context, R.layout.dialog_with_ok_and_cancel_layout, ImgRes, message, btnCancelTtitle, btnOkTtitle, bCancelable, listener);
		loadingDialog.show();
	}

	public static Dialog createCustomDialog(Context context, int layoutId, int ImgRes, String message, String btnCancelTtitle, String btnOkTtitle, boolean bCancelable,
			final ITwoButtonListener listener) {
		final Dialog dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setCancelable(bCancelable);
		dialog.setCanceledOnTouchOutside(bCancelable);
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(layoutId, null);
		ImageView im = (ImageView) layout.findViewById(R.id.info_imageview);
		if(ImgRes > 0){
			im.setImageResource(ImgRes);
		} else {
			im.setVisibility(View.GONE);
		}

		TextView tx = (TextView) layout.findViewById(R.id.title_textview);
		tx.setText(message);

		Button btnOk = (Button) layout.findViewById(R.id.right_button);
		btnOk.setText(btnOkTtitle);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onBtnOkClickListener(dialog);
				}
			}
		});

		Button btnCancel = (Button) layout.findViewById(R.id.left_button);
		btnCancel.setText(btnCancelTtitle);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onBtnCancelClickListener(dialog);
				}
			}
		});

		dialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		dialog.setContentView(layout);
		return dialog;
	}

	public static Dialog createProgressLoadingDialog(Context context, final String message) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading_with_title, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);

		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		TextView messageTV = (TextView) v.findViewById(R.id.message_tv);
		if (!TextUtils.isEmpty(message)) {
			messageTV.setText(message);
		}

		Dialog pbarDialog;
		pbarDialog = new Dialog(context, R.style.loading_dialog);
		pbarDialog.setContentView(layout);

		pbarDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		pbarDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		return pbarDialog;
	}

	/**
	 *
	 * @param context
	 * @param title 对话框标题
	 * @param msg 信息内容文案
	 * @param lBtnStr 左按钮文案
	 * @param rBtnStr 右按钮文案
	 * @param cancelable
     * @param listener
     */
	public static void showDialogLeftRightButton(Context context, String title, String msg, String lBtnStr, String rBtnStr, boolean cancelable, final ITwoButtonListener listener) {
		final Dialog dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setCancelable(cancelable);
		dialog.setCanceledOnTouchOutside(cancelable);
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.dialog_left_right_button_layout, null);
		TextView titleTv = (TextView) layout.findViewById(R.id.titleTv);
		if(TextUtils.isEmpty(title)){
			titleTv.setVisibility(View.GONE);
		} else {
			titleTv.setVisibility(View.VISIBLE);
			titleTv.setText(title);
		}

		TextView msgTv = (TextView) layout.findViewById(R.id.msgTv);
		msgTv.setText(msg);

		TextView lBtn = (TextView) layout.findViewById(R.id.lBtn);
		View botmidline = layout.findViewById(R.id.botmidline);
		if(TextUtils.isEmpty(lBtnStr)){
			lBtn.setVisibility(View.GONE);
			botmidline.setVisibility(View.GONE);
		} else {
			lBtn.setVisibility(View.VISIBLE);
			botmidline.setVisibility(View.VISIBLE);
			lBtn.setText(lBtnStr);
			lBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (listener != null) {
						listener.onBtnCancelClickListener(dialog);
					}
				}
			});
		}

		TextView rBtn = (TextView) layout.findViewById(R.id.rBtn);
		rBtn.setText(rBtnStr);
		rBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onBtnOkClickListener(dialog);
				}
			}
		});

		dialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// 提示加载时，关闭Toast提示
				ToastUtil.cancel();
			}
		});

		dialog.setContentView(layout);
		dialog.show();

	}

	public interface ISingleButtonListener {
		public void onBtnClickListener(Dialog dialog);
	}

	public interface ITwoButtonListener {
		public void onBtnOkClickListener(Dialog dialog);

		public void onBtnCancelClickListener(Dialog dialog);
	}

}
