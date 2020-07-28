# BottomNavigation

![Alt text](screenshots/center_curved_border.jpg?raw=true "Curved")

![Alt text](screenshots/linear_border.jpg?raw=true "Linear")


Gradle :

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Hofinity:BottomNavigation:0.0.1'
	}
	
  -------------------------------------------------------------------------
  
  Maven :
  
  Step 1. 
  
  	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
  Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.Hofinity</groupId>
	    <artifactId>BottomNavigation</artifactId>
	    <version>0.0.1</version>
	</dependency>

  -------------------------------------------------------------------------


Getting started

  Adding a custom widget in xml :

    <com.hofinity.bottomNavigation.BottomNavigationView
        android:id="@+id/bnv"
        android:layout_width="match_parent"
        android:layout_height="90dp"
        android:layout_gravity="bottom"
        app:bm_activeItemColor="@color/main_red3"
        android:background="@color/white"
        app:bm_inactiveCenterButtonBackgroundColor="@color/white"
        app:bm_inactiveItemColor="@color/click_gray"
        app:bm_backgroundColor="@color/white"
        app:bm_activeCenterButtonBackgroundColor="@color/main_red3"
        app:bm_inactiveCenterButtonIconColor="@color/black"
        app:layout_behavior="bottomNavigationView.behaviors.BmBehaviour"
        app:bm_centerButtonIcon="@drawable/ic_home"
        android:layout_alignParentBottom="true"
        app:bm_itemIconSize="@dimen/bm_item_icon_default_size"
        app:bm_itemIconOnlySize="@dimen/bm_item_icon_only_size"
        app:bm_itemTextSize="@dimen/bm_item_text_default_size"
        app:bm_centerPartLinear="true"
        app:bm_itemsMode="normal"
        app:bm_show_border="true"
        app:bm_border_color="@color/light_gray"
        app:bm_badgeBackgroundColor="@color/main_red3"
        app:bm_badgeTextColor="@color/white"
        app:bm_badgeShapeType="filledOval"
        app:bm_borderType="linear"
        app:layout_constraintBottom_toBottomOf="parent"/>


  Binding view in Activity:
  
	BottomNavigationView bnv = findViewById(R.id.bnv);
	
        bnv.initWithSaveInstanceState(savedInstanceState);
	
  Add items:
  
        bnv.addBmItem(new BmItem("profile", R.drawable.ic_user));
        bnv.addBmItem(new BmItem("support", R.drawable.ic_support));
        bnv.addBmItem(new BmItem("search", R.drawable.ic_search));
        bnv.addBmItem(new BmItem("setting", R.drawable.ic_settings));

  Add badge view:
  
        bnv.showBadgeAtIndex(0,1000);
        bnv.showFullBadgeText(true);
	
  Items click listener:

        bnv.setBmOnClickListener(new BmOnClickListener() {
            @Override
            public void onCenterButtonClick() {

            }

            @Override
            public void onItemClick(int itemIndex, String itemTitle) {
                
            }

            @Override
            public void onItemReselected(int itemIndex, String itemTitle) {

            }

        });
	
  Items long click listener:

        bnv.setBmOnLongClickListener(new BmOnLongClickListener() {
            @Override
            public void onCenterButtonLongClick() {

            }

            @Override
            public void onItemLongClick(int index, String title) {

            }
        });
