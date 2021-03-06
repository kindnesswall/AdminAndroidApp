package ir.hamedgh.adminkindnesswall.helper;

import android.content.Context;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class MaterialDialogBuilder {

	public static MaterialDialog.Builder create(Context ctx) {
		boolean HAS_RTL = DeviceInfo.hasSupportRTL();

		GravityEnum dir, btnDir;

		if (HAS_RTL) {
			if (DeviceInfo.isRTL()) {
				dir = GravityEnum.START;
				btnDir = GravityEnum.START;
			} else {
				dir = GravityEnum.START;
				btnDir = GravityEnum.END;
			}
		} else {
			dir = GravityEnum.END;
			btnDir = GravityEnum.END;
		}

		return new MaterialDialog.Builder(ctx)
				.titleGravity(dir)
				.contentGravity(dir)
				.buttonsGravity(btnDir)
				.typeface("divar_mehrabani.ttf", "divar_mehrabani.ttf");
	}

}
