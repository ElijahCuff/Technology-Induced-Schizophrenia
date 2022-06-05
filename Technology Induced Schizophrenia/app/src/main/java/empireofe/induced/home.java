package empireofe.induced;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;

public class home extends Activity 
{
    SharedPreferences preferences;
    static Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ctx = this;
         startActivity(new Intent(this, Preferences.class).setFlags(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS));      
    }
}
