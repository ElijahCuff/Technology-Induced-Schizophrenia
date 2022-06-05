package empireofe.induced;

import android.content.*;
import android.preference.*;

public class Settings {
    protected static SharedPreferences pref;
    protected Settings(SharedPreferences preferences)
    {
        pref = preferences;
	  }

    public static Settings get(Context context)
    {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return new Settings(preferences);
	  }
    public Boolean whispers()
    {
        return pref.getBoolean("whispers", false);
	  }
    
    
    public Boolean hideApp()
    {
        return pref.getBoolean("useHide", false);
	  }
    
    public String selSound()
    {
        return pref.getString("commands","danger");
    }
}
