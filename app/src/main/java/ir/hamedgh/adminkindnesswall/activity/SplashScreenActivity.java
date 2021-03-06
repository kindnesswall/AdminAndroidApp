package ir.hamedgh.adminkindnesswall.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.hamedgh.adminkindnesswall.MainActivity;
import ir.hamedgh.adminkindnesswall.R;
import ir.hamedgh.adminkindnesswall.app.AppController;
import ir.hamedgh.adminkindnesswall.constants.Constants;
import ir.hamedgh.adminkindnesswall.helper.ApiRequest;
import ir.hamedgh.adminkindnesswall.helper.DeviceInfo;
import ir.hamedgh.adminkindnesswall.helper.UpdateChecker;
import ir.hamedgh.adminkindnesswall.interfaces.UpdateCheckerInterface;
import ir.hamedgh.adminkindnesswall.model.api.output.AppInfoOutput;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */

public class SplashScreenActivity extends AppCompatActivity implements ApiRequest.Listener, UpdateCheckerInterface { //,ChoosePlaceCallback {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /**
     * Called when the activity is first created.
     */

    Context context;
    private int REQUEST_CODE_INTRO = 321;
    private ApiRequest apiRequest;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        context = this;


//        apiRequest = new ApiRequest(this, this);

        if (AppController.getStoredString(Constants.Authorization)!=null) {
//            apiRequest.getAppInfo();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else {
//            startActivityForResult(AppIntro.createIntent(), REQUEST_CODE_INTRO);
            Intent mainIntent = new Intent(this, LoginActivity.class);
            startActivity(mainIntent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INTRO) {
            if (resultCode == RESULT_OK) {
                // Finished the intro
            } else {
                // Cancelled the intro. You can then e.g. finish this activity too.
            }
            apiRequest.getAppInfo();
        }
    }

//    @Override
//    public void onCitySelected(Place city) {
//        AppController.storeString(Constants.MY_LOCATION_ID, city.id);
//        AppController.storeString(Constants.MY_LOCATION_NAME, city.name);
//
//        Intent mainIntent = new Intent(this, BottomBarActivity.class);
//        startActivity(mainIntent);
//
//        finish();
//    }

//    @Override
//    public void onRegionSelected(Place region) {
//
//    }

    private void onUpdateVersionResponse(AppInfoOutput appInfoOutput) {

        AppController.storeString(Constants.SMS_CENTER, appInfoOutput.smsCenter);
        boolean isForcedUpdate;

        if (appInfoOutput.updateInfo.force_update != null && appInfoOutput.updateInfo.force_update.equalsIgnoreCase("true")) {
            isForcedUpdate = true;
        } else {
            isForcedUpdate = false;//todo use this
        }

        UpdateChecker updateChecker = new UpdateChecker(
                this,
                getResources().getString(R.string.app_name),
                appInfoOutput.updateInfo.version,
                appInfoOutput.updateInfo.apk_url,
                null,
                appInfoOutput.updateInfo.changes);

        if (Integer.valueOf(appInfoOutput.updateInfo.version) > DeviceInfo.getAppVersionCode()
        && (isForcedUpdate ||
                DeviceInfo.getAppVersionCode() < Integer.valueOf(updateChecker.mUpdateDetail.latestVersion))
                ) {

            //Notify Update
//            Intent[] intents = new Intent[1];
//            intents[0] = Intent.makeMainActivity(new ComponentName(AppController.getAppContext(),
//                    BottomBarActivity.class));
            // intents[1] = new Intent(AppController.getAppContext(), HomeActivity.class);
//            updateChecker.showUpdaterDialog(
//                    context,
//                    getString(R.string.update_to_new_version),
//                    getString(R.string.exist_new_version),
//                    appInfoOutput.updateInfo.changes,
//                    appInfoOutput.updateInfo.version,
//                    intents,
//                    isForcedUpdate);
//
//            AppController.getInstance().setIsCheckedUpdate(true);

        } else {

            afterAll();

        }

    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.body() instanceof AppInfoOutput) {
            onUpdateVersionResponse((AppInfoOutput) response.body());
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }

    @Override
    public void onNotNowBtnClicked() {
        afterAll();
    }

    @Override
    public void onNeverBtnClicked() {
        afterAll();
    }

    private void afterAll(){
//        if (AppController.getStoredString(Constants.MY_LOCATION_ID) != null) {

//            startActivity(BottomBarActivity.createIntent());
//            finish();

//        } else {
//
//            FragmentManager fm = getSupportFragmentManager();
//            ChoosePlaceDialogFragment.newInstance(false)
//                    .show(fm, ChoosePlaceDialogFragment.class.getName());
//        }
    }
}
