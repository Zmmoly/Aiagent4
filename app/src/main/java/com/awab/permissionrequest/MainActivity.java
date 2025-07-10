
package com.awab.permissionrequest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String[] permissions15 = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    for (String permission : result.keySet()) {
                        boolean granted = Boolean.TRUE.equals(result.get(permission));
                        Toast.makeText(this, permission + ": " + (granted ? "GRANTED" : "DENIED"), Toast.LENGTH_SHORT).show();
                    }
                });

        requestAllPermissions();
    }

    private void requestAllPermissions() {
        List<String> toRequest = new ArrayList<>();

        for (String perm : permissions15) {
            if (ActivityCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                toRequest.add(perm);
            }
        }

        if (!toRequest.isEmpty()) {
            permissionLauncher.launch(toRequest.toArray(new String[0]));
        } else {
            Toast.makeText(this, "All permissions already granted!", Toast.LENGTH_SHORT).show();
        }
    }
}
