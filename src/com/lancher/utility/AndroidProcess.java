package com.lancher.utility;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

public class AndroidProcess extends Activity{
    @SuppressLint("NewApi")
	protected  void doStartApplicationWithPackageName(String packagename) {  
        
        // ͨ��������ȡ��APP��ϸ��Ϣ������Activities��services��versioncode��name�ȵ�  
        PackageInfo packageinfo = null;  
        try {  
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
        if (packageinfo == null) {  
            return;  
        }  
      
        // ����һ�����ΪCATEGORY_LAUNCHER�ĸð�����Intent  
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);  
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
        resolveIntent.setPackage(packageinfo.packageName);  
      
        // ͨ��getPackageManager()��queryIntentActivities��������  
        List<ResolveInfo> resolveinfoList = getPackageManager()  
                .queryIntentActivities(resolveIntent, 0);  
      
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();  
        if (resolveinfo != null) {  
            // packagename = ����packname  
            String packageName = resolveinfo.activityInfo.packageName;  
            // �����������Ҫ�ҵĸ�APP��LAUNCHER��Activity[��֯��ʽ��packagename.mainActivityname]  
            String className = resolveinfo.activityInfo.name;  
            // LAUNCHER Intent  
            Intent intent = new Intent(Intent.ACTION_MAIN);  
            intent.addCategory(Intent.CATEGORY_LAUNCHER);  
      
            // ����ComponentName����1:packagename����2:MainActivity·��  
            ComponentName cn = new ComponentName(packageName, className);  
      
            intent.setComponent(cn);  
            startActivity(intent);  
        }  
    }  
}
