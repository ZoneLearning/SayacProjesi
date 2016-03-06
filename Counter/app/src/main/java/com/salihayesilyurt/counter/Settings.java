package com.salihayesilyurt.counter;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by User on 1.2.2016.
 */
public class Settings extends PreferenceActivity {
 /*   Videoda anlatılanda ayarlar ekranı gelmedi. Bu linkteki fragment olusturmyı denedim
    http://alvinalexander.com/android/android-tutorial-preferencescreen-preferenceactivity-preferencefragment
    http://developer.android.com/guide/topics/ui/settings.html
    Yanlış onCreate ( onCreate(Bundle savedInstanceState, PersistableBundle persistentState))  metodunu seçmişim. O yüzden
    ayarlar ekranı gozukmuyor.  Dogru metod olunca videodakide calisti ama fragment ile yapmak onerildigi icin boyle bıraktım.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.settings);
       getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }


    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }
}
