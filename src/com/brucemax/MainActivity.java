package com.brucemax;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost(); // очень полезный метод инициализирует активити для работы с вкладками
        
        TabHost.TabSpec tabSpec; // Сущность служащая для настройки вкладки (картинка, текст, интент(цель))
        
        tabSpec = tabHost.newTabSpec("tag1");
        View v = getLayoutInflater().inflate(R.layout.tab_header, null); // Делаем View из лайаута
        tabSpec.setIndicator(v);
        tabSpec.setContent(new Intent (this,OneActivity.class)); //this указывает на Контекст пакета прикладных программ реализации этого класса.
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Макс!", getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(new Intent(this, TwoActivity.class));
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Киря!!!", getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(new Intent(this, ThreeActivity.class));
        tabHost.addTab(tabSpec);
        
                
       // при переключениях между вкладками одна уходит в onPause, другая возвращается в onResume. 
    }
}