package fr.kazutoshi.serviceslist;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  protected void onStart() {
    super.onStart();

    EditText editText = (EditText) findViewById(R.id.editText);
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        list(s.toString());
      }
    });

    list("");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }



  private void list(String str) {

    final ActivityManager activityManager =
            (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

    layout.removeAllViews();

    ProgressBar pb = new ProgressBar(this);
    layout.addView(pb);

    List<PackageInfo> pkgs = getPackageManager().getInstalledPackages(PackageManager.GET_SERVICES);

    for (PackageInfo pkg : pkgs) {
      /*if (pkg.services != null)
        for (ServiceInfo service : pkg.services) {
          if (service.packageName.contains("citae")) {
            TextView textView = new TextView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(layoutParams);
            textView.setText(service.processName + " : " + service.packageName + " : " + service.name);
            layout.addView(textView);
          }
        }*/
      /*if (pkg.packageName.contains("citae") && pkg.services != null) {
        for (ServiceInfo service : pkg.services) {
          TextView textView = new TextView(this);
          ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
          textView.setLayoutParams(layoutParams);
          textView.setText(service.processName + " : " + service.packageName + " : " + service.name);
          layout.addView(textView);
        }
      }*/
      final String packageName = pkg.packageName;

      if (packageName.contains(str)) {

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button killButton = new Button(this);
        TextView textView = new TextView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        killButton.setLayoutParams(layoutParams);
        textView.setLayoutParams(layoutParams);
        killButton.setText("X");
        textView.setText(pkg.packageName);

        killButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            activityManager.killBackgroundProcesses(packageName);
          }
        });

        horizontalLayout.addView(killButton);
        horizontalLayout.addView(textView);

        layout.addView(horizontalLayout);
      }
    }
    layout.removeView(pb);
  }
}
