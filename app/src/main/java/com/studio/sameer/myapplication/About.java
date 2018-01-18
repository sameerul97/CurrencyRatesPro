package com.studio.sameer.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class About extends AppCompatActivity {
    private ImageView gitHub,share,mail,review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        gitHub = (ImageView)findViewById(R.id.gitHubLogo);
        share = (ImageView)findViewById(R.id.shareLogo);
        mail = (ImageView)findViewById(R.id.mailLogo);
        review = (ImageView)findViewById(R.id.reviewLogo);

        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/sameerul97?tab=repositories";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out this App!! called Currency Rates PRO";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent Email = new Intent(Intent.ACTION_SEND);
//                Email.setType("text/email");
//                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@hotmail.com" });
//                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
//                Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
//                startActivity(Intent.createChooser(Email, "Send Feedback:"));

                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "sameerul97@gmail.com" });
                startActivity(intent);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.sameer.hameed.learnprogramming";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}
