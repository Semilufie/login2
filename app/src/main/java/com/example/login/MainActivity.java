package com.example.login;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.login.Fragment.CarFragment;
import com.example.login.Fragment.HomeFragment;
import com.example.login.Fragment.MeFragment;
import com.example.login.Fragment.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private CarFragment carFragment;
    private HomeFragment homeFragment;
    private MeFragment meFragment;
    private OrderFragment orderFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化控件
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.home) {
                    seletedDefault(0);
                } else if (menuItem.getItemId() == R.id.car) {
                    seletedDefault(1);
                } else if (menuItem.getItemId() == R.id.order) {
                    seletedDefault(2);
                } else {
                    seletedDefault(3);
                }
                return true;
            }
        });

        // 默认首页选中
        seletedDefault(0);
    }

    private void seletedDefault(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_container, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (carFragment == null) {
                    carFragment = new CarFragment();
                    transaction.add(R.id.fragment_container, carFragment);
                } else {
                    transaction.show(carFragment);
                    carFragment.loadData();
                }
                break;
            case 2:
                if (orderFragment == null) {
                    orderFragment = new OrderFragment();
                    transaction.add(R.id.fragment_container, orderFragment);
                } else {
                    transaction.show(orderFragment);
                    orderFragment.loadData();
                }
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fragment_container, meFragment);
                } else {
                    transaction.show(meFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();

    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (carFragment != null) {
            transaction.hide(carFragment);
        }
        if (orderFragment != null) {
            transaction.hide(orderFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
//        transaction.commit();
    }
}