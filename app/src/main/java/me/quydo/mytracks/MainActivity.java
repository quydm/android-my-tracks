package me.quydo.mytracks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import me.quydo.mytracks.databinding.MainActivityBinding;
import me.quydo.mytracks.model.Track;

public class MainActivity extends Activity {

    private MainActivityBinding binding;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        pref = getSharedPreferences("my_track", MODE_PRIVATE);
        long currentTrack = pref.getLong("current_track_id", 0);
        if (currentTrack > 0)
            showBtnStop();
        else
            showBtnStart();
    }

    public void startTracking(View view) {
        String trackName = binding.trackName.getText().toString();
        if (TextUtils.isEmpty(trackName)) {
            Toast.makeText(this, R.string.track_name_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        Track track = new Track();
        track.trackName = trackName;
        track.createdTime = String.valueOf(System.currentTimeMillis() / 1000);
        track.save();

        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("current_track_id", track.getId());
        editor.apply();

        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra("track_id", track.getId());
        startService(intent);

        showBtnStop();
    }

    public void stopTracking(View view) {
        stopService(new Intent(this, LocationService.class));

        showBtnStart();
    }

    private void showBtnStop() {
        binding.btnStart.setVisibility(View.GONE);
        binding.btnStop.setVisibility(View.VISIBLE);
    }

    private void showBtnStart() {
        binding.btnStart.setVisibility(View.VISIBLE);
        binding.btnStop.setVisibility(View.GONE);
    }

}
