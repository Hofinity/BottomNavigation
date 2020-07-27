package com.hofinity.bottomNavigationViewExample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hofinity.bottomNavigation.BottomNavigationView;
import com.hofinity.bottomNavigation.interfaces.BmOnClickListener;
import com.hofinity.bottomNavigation.interfaces.BmOnLongClickListener;
import com.hofinity.bottomNavigation.models.BmItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bnv);
        bnv.initWithSaveInstanceState(savedInstanceState);
//        bnv.setCenterButtonIcon(R.drawable.ic_home);
        bnv.addBmItem(new BmItem("profile", R.drawable.ic_user));
        bnv.addBmItem(new BmItem("support", R.drawable.ic_support));
        bnv.addBmItem(new BmItem("search", R.drawable.ic_search));
        bnv.addBmItem(new BmItem("setting", R.drawable.ic_settings));

        bnv.showBadgeAtIndex(0,1000);
        bnv.showFullBadgeText(true);
        bnv.setBorderType(BottomNavigationView.CENTER_HORIZONTAL);

//        bnv.setCenterButtonSelected();
        //bnv.showIconOnly();


        bnv.setBmOnClickListener(new BmOnClickListener() {
            @Override
            public void onCenterButtonClick() {

            }

            @Override
            public void onItemClick(int itemIndex, String itemTitle) {

                switch (itemIndex) {
                    case 0:

                        break;

                    case 1:

                        break;

                    case 2:

                        break;

                    case 3:

                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemTitle) {

            }

        });

        bnv.setBmOnLongClickListener(new BmOnLongClickListener() {
            @Override
            public void onCenterButtonLongClick() {

            }

            @Override
            public void onItemLongClick(int index, String title) {

            }
        });

    }
}