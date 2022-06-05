package empireofe.induced;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.media.*;
import android.os.*;
import android.preference.*;
import android.widget.*;
import java.util.*;

public class Preferences extends PreferenceActivity implements
SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
    {
        // load new changes
        settings = Settings.get(this);
        // Check if the activity is hidden now
        if (key.equals("useHide")) {
            if (settings.hideApp()) {
                new AlertDialog.Builder(ctx)
                    .setCancelable(false)
                    .setTitle("WARNING !!!")
                    .setMessage("You are about to hide the application on this device... \n\nto open again, navigate to unlock.com and press Qualify Now, then select System Framework.")
                    .setNeutralButton(" HIDE FOREVER ", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {

                            updateAlias("home-show", false);
                            updateAlias("home-hide", true);
                            setTitle(getString(R.string.app_name_hidden));

                        }
                    })
                    .setNegativeButton("CANCEL / EXIT", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            SwitchPreference switcher =  (SwitchPreference) getPreferenceScreen().findPreference("useHide");
                            switcher.setChecked(false);
                        }
                    })
                    .show();
            }
            else {
                updateAlias("home-show", true);
                updateAlias("home-hide", false);
            }
        } 

        if (key.equals("whispers")) {
            if (settings.whispers()) {     
                whisper(R.raw.whisper_1);     
            }
            else {
                if (mp != null) {
                    mp.stop();
                }
            }
        }
	  }
    Settings settings;
    MediaPlayer mp ;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // load settings 
        settings = Settings.get(getBaseContext().getApplicationContext());
        setTitle(getString(R.string.app_name_hidden));
        // register the preference listener
        SharedPreferences preferences = settings.pref;
        preferences.registerOnSharedPreferenceChangeListener(Preferences.this);
        addPreferencesFromResource(R.xml.settings);
        ctx = this;

        // do some changes for incompatible firmwares
        if (Build.VERSION.SDK_INT < 11) {
            // disable by key
            // getPreferenceScreen().findPreference("pref_key").setEnabled(false);
        }

        if (settings.whispers()) {
            if (mp == null) {
                whisper(R.raw.whisper_1);
            }
        }

	  }

    // custom function to reload activity-alias
    public void updateAlias(String activityRefference, boolean enabledState)
    {
        int state = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        if (enabledState) {
            state = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        }
        getPackageManager().setComponentEnabledSetting(
            new ComponentName(getPackageName(), getPackageName() + "." + activityRefference), 
            state, PackageManager.DONT_KILL_APP);
	  }

    public void whisper(final int sound)
    {
        new Thread(new Runnable(){
                @Override
                public void run()
                {
                    while (settings.whispers()) {
                        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
                        mp = MediaPlayer.create(Preferences.this, sound);
                        mp.setVolume(0.03f, 0.03f);
                        mp.start();
                        Random rand = new Random();
                        int amount = rand.nextInt(10000);
                        SystemClock.sleep(amount + 5000);
                        mp = MediaPlayer.create(Preferences.this, sound);
                        mp.setVolume(0.03f, 0.03f);
                        mp.start();
                        SystemClock.sleep(amount + 5000);
                    }
                }
            }).start();
    }

    // stop user from exiting on accident
    private static long back_pressed;
    @Override
    public void onBackPressed()
    {
        if (back_pressed + 400 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Double Tap To Close !!!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
	  }

}
