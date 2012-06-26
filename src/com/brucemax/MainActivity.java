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
        
        TabHost tabHost = getTabHost(); // ����� �������� ����� �������������� �������� ��� ������ � ���������
        
        TabHost.TabSpec tabSpec; // �������� �������� ��� ��������� ������� (��������, �����, ������(����))
        
        tabSpec = tabHost.newTabSpec("tag1");
        View v = getLayoutInflater().inflate(R.layout.tab_header, null); // ������ View �� �������
        tabSpec.setIndicator(v);
        tabSpec.setContent(new Intent (this,OneActivity.class)); //this ��������� �� �������� ������ ���������� �������� ���������� ����� ������.
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("����!", getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(new Intent(this, TwoActivity.class));
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("����!!!", getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(new Intent(this, ThreeActivity.class));
        tabHost.addTab(tabSpec);
        
                
       // ��� ������������� ����� ��������� ���� ������ � onPause, ������ ������������ � onResume. 
    }
}