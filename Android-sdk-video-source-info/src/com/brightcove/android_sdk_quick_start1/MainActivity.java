package com.brightcove.android_sdk_quick_start1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import com.brightcove.player.media.Catalog;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.media.VideoFields;
import com.brightcove.player.media.VideoListener;
import com.brightcove.player.model.Source;
import com.brightcove.player.model.SourceCollection;
import com.brightcove.player.model.Video;
import com.brightcove.player.util.StringUtil;
import com.brightcove.player.view.BrightcoveVideoView;

public class MainActivity extends Activity {
  public static final String TAG = "**VIDEO INFO**";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final BrightcoveVideoView bcVideoView = (BrightcoveVideoView) findViewById(R.id.bc_video_view);
    final Catalog catalog = new Catalog("h9Ne4RpQO6O-1IuekDkyR874wALDyZFa63r4pSQQr3ESNL9HANIazA..");

    MediaController controller = new MediaController(this);
    bcVideoView.setMediaController(controller);

    HashMap<String, String> options = new HashMap<String, String>();
    
    List<String> values = new ArrayList<String>(Arrays.asList(VideoFields.DEFAULT_FIELDS));
    options.put("video_fields", StringUtil.join(values, ",")); 
    Log.d(TAG,"options: " + options);
    
    catalog.findVideoByReferenceID("bird-for-android-sdk", options, new VideoListener() {
     //catalog.findVideoByReferenceID("bird-for-android-sdk", new VideoListener() {
      @Override
      public void onVideo(Video video) {
        Log.d(TAG, "video: " + video);
        Log.d(TAG, "video properties: " + video.getProperties());
        Log.d(TAG, "default fields: " + Arrays.toString(VideoFields.DEFAULT_FIELDS));
        Log.d(TAG, "cuepoints: " + video.getCuePoints());
        Log.d(TAG, "duration: " + video.getDuration());
        Log.d(TAG, "id: " + video.getId());
        Log.d(TAG, "source collections: " + video.getSourceCollections());
        Log.d(TAG, "renditions: " + video.getProperties().get(VideoFields.WVM_RENDITIONS));        
        
        bcVideoView.add(video);
        bcVideoView.start();
        
        Map<DeliveryType, SourceCollection> sourcesCollections = video.getSourceCollections();
        Log.d(TAG, "source collections: " + sourcesCollections.values());
        SourceCollection collection = sourcesCollections.get(DeliveryType.MP4);
        //SourceCollection collection = sourcesCollections.get(DeliveryType.HLS);
        Log.d(TAG, "collection: " + collection);
        Set<Source> sources = collection.getSources();
        Log.d(TAG, "sources: " + sources);
        Log.d(TAG, "collection properties: " + collection.getProperties());
        for (Source source:sources){
          Log.d(TAG, "source properties: " + source.getProperties());
        }
      }

      @Override
      public void onError(String error) {
        //Insert error handling here
        Log.e(TAG, error);
      }
    });

  }
}