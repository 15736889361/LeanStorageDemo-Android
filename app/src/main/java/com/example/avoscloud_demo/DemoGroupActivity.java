package com.example.avoscloud_demo;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.avos.avoscloud.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoGroupActivity extends ListActivity {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    setupAVOSCloud(false);
    setupGroupAdapter();
  }

  private void setupAVOSCloud(boolean config) {
    if (!config) {
      AVOSCloud.initialize(getApplication(),
          Config.APP_ID, Config.APP_KEY);
      return;
    }
    final Dialog dialog = new Dialog(this);
    dialog.setContentView(R.layout.cloud);
    dialog.setTitle("Setup AVOS Cloud");

    // set the custom dialog components - text, image and button
    Button dialogButton = (Button) dialog.findViewById(R.id.btn_ok);
    dialogButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
        EditText appId = (EditText) dialog.findViewById(R.id.editViewClientKey);
        EditText clientKey = (EditText) dialog.findViewById(R.id.editTextClientKey);
        if (appId.getText().length() <= 0 || clientKey.getText().length() <= 0) {
          Toast.makeText(DemoGroupActivity.this, "Empty key.", Toast.LENGTH_LONG).show();
          return;
        }
        AVOSCloud.initialize(getApplication(),
            appId.getText().toString(),
            clientKey.getText().toString());
      }
    });

    dialogButton = (Button) dialog.findViewById(R.id.btn_cancel);
    dialogButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });

    dialog.show();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_config) {
      setupAVOSCloud(true);
    }
    return super.onOptionsItemSelected(item);
  }

  public void setupGroupAdapter() {
    List<String> array = myDemoArray();
    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
        android.R.layout.simple_list_item_1,
        array);
    setListAdapter(adapter);
  }

  private List<String> myDemoArray() {
    List<String> array = new ArrayList<String>();
    array.add("ObjectDemoActivity");
    array.add("QueryDemoActivity");
    array.add("UserDemoActivity");
    array.add("FileDemoActivity");
    array.add("PointerDemoActivity");
    array.add("AVRelationDemoActivity");
    array.add("SubclassDemoActivity");
    return array;
  }

  private String getActivityClassName(String activity) {
    return "com.example.avoscloud_demo.demo." + activity;
  }

  private void startActivityByName(final String className) {
    try {
      Intent intent = new Intent(this, Class.forName(className));
      startActivity(intent);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  protected void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
    List<String> array = myDemoArray();
    String name = array.get(position);
    String value = getActivityClassName(name);
    startActivityByName(value);
  }
}
