package com.example.sewakos.PencariKos;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sewakos.Communication.Pesan;
import com.example.sewakos.PemilikKos.BerandaPemilikKos;
import com.example.sewakos.Profile.Profile;
import com.example.sewakos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavbarPencariKos extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private BerandaPencariKos berandaPencariKos = new BerandaPencariKos();
    private Pesan pesan = new Pesan();
    private Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bottom_navbar_pencari_kos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navBeranda);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.navBeranda) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, berandaPencariKos).commit();
            return true;
        } else if (menuItem.getItemId() == R.id.navPesan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, pesan).commit();
            return true;
        } else if (menuItem.getItemId() == R.id.navProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, profile).commit();
            return true;
        }
        return false;
    }
}