package empireofe.induced;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.media.*;
import android.os.*;
import android.preference.*;
import android.widget.*;
import java.util.*;
import android.content.res.*;

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

        
        if(key.equals("commands"))
        {
            String sel = settings.selSound();  
            String[] sounds = getResources().getStringArray(R.array.command_keys);
            if(sel.equals(sounds[0]))
            {
                selSound = R.raw.whisper_1;
            }
            else if(sel.equals(sounds[1]))
            {
                selSound = R.raw.whisper_2;
            }
            else if(sel.equals(sounds[2]))
            {
                selSound = R.raw.whisper_3;
            }
            
          }
          

        if (key.equals("whispers")) {
            if (settings.whispers()) {     
                whisper();           
            }
            else {
                if (mp != null) {
                    mp.stop();
                }
            }
        }
	  }
    
    int selSound = 0;
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

        
        String sel = settings.selSound();

        String[] sounds = getResources().getStringArray(R.array.command_keys);
        if(sel.equals(sounds[0]))
        {
            selSound = R.raw.whisper_1;
        }
        else if(sel.equals(sounds[1]))
        {
            selSound = R.raw.whisper_2;
        }
        else if(sel.equals(sounds[2]))
        {
            selSound = R.raw.whisper_3;
        }
        
        if (settings.whispers()) {
            if (settings.whispers()) {     
            if(mp == null)
            {
                whisper();     
                }
            }
            else {
                if (mp != null) {
                    mp.stop();
                }
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

    public void whisper()
    {
        new Thread(new Runnable(){
                @Override
                public void run()
                {
                    while (settings.whispers()) {
                        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
                        mp = MediaPlayer.create(Preferences.this, selSound);
                        mp.setVolume(0.02f, 0.02f);
                        mp.start();
                        Random rand = new Random();
                        int amount = rand.nextInt(80000);
                        SystemClock.sleep(amount + 25000);
                        mp = MediaPlayer.create(Preferences.this, selSound);
                        mp.setVolume(0.02f, 0.02f);
                        mp.start();
                        SystemClock.sleep(amount + 25000);
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
