package com.hofinity.bottomNavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hofinity.bottomNavigation.Views.BezierView;
import com.hofinity.bottomNavigation.Views.CenterButton;
import com.hofinity.bottomNavigation.helpers.BadgeHelper;
import com.hofinity.bottomNavigation.interfaces.BmOnClickListener;
import com.hofinity.bottomNavigation.interfaces.BmOnLongClickListener;
import com.hofinity.bottomNavigation.models.BadgeItem;
import com.hofinity.bottomNavigation.models.BmItem;
import com.hofinity.bottomNavigation.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Developed with love by Smooke on 07/24/2020.
 */

public class BottomNavigationView extends RelativeLayout {

    boolean isCenterButtonSelectable = true;
    boolean showBorder = true;
    boolean useCustomFont = false;
    boolean isCenterBtnIconColorFilterEnabled = true;
    boolean show99plusSign = true;

    static final int NOT_DEFINED = -918; //random number, not -1 because it is color.bm_WHITE
    static final int MAX_ITEM_SIZE = 4;
    static final int MIN_ITEM_SIZE = 2;

    public static final int NORMAL = 0;
    public static final int TEXT_ONLY = 1;
    public static final int ICON_ONLY = 2;
    public static final int HIDDEN = 3;
    public static final int FILLED_OVAL = 4;
    public static final int NOT_FILLED_OVAL = 5;
    public static final int FILLED_RECTANGLE = 6;
    public static final int NOT_FILLED_RECTANGLE = 7;
    public static final int LINEAR = 8;
    public static final int CENTER_CURVED = 9;

    int itemsMode = NORMAL;
    int badgeShapeType = FILLED_OVAL;
    int borderType = CENTER_CURVED;
    int borderThickness = 5;
    int bmItemIconSize = NOT_DEFINED;
    int bmItemIconOnlySize = NOT_DEFINED;
    int bmItemTextSize = NOT_DEFINED;
    int bmBackgroundColor = NOT_DEFINED;
    int centerButtonId = NOT_DEFINED;
    int activeCenterButtonIconColor = NOT_DEFINED;
    int inactiveCenterButtonIconColor = NOT_DEFINED;
    int activeCenterBtnBgColor = NOT_DEFINED;
    int inactiveCenterBtnBgColor = NOT_DEFINED;
    int centerButtonIcon = NOT_DEFINED;
    int activeItemColor = NOT_DEFINED;
    int inactiveItemColor = NOT_DEFINED;
    int activeBmItemTextColor = NOT_DEFINED;
    int inactiveBmItemTextColor = NOT_DEFINED;
    int centerButtonRippleColor = NOT_DEFINED;
    int borderColor = NOT_DEFINED;
    int badgeTextColor = NOT_DEFINED;
    int badgeBackgroundColor = NOT_DEFINED;
    int currentSelectedItem = 0;
    int contentWidth;

    final int bmNavigationHeight = (int) getResources().getDimension(R.dimen.bm_navigation_height);
    final int mainContentHeight = (int) getResources().getDimension(R.dimen.main_content_height);
    final int mainContentWidth = (int) getResources().getDimension(R.dimen.item_content_width);
    final int centerContentWidth = (int) getResources().getDimension(R.dimen.center_content_width);
    final int centerButtonSize = (int) getResources().getDimension(R.dimen.bm_center_button_default_size);

    static final String TAG = "BottomNavigationView";
    static final String CURRENT_SELECTED_ITEM_BUNDLE_KEY = "currentItem";
    static final String BADGES_ITEM_BUNDLE_KEY = "badgeItem";
    static final String CHANGED_ICON_AND_TEXT_BUNDLE_KEY = "changedIconAndText";
    static final String center_BUTTON_ICON_KEY = "centerButtonIconKey";
    static final String center_BUTTON_COLOR_KEY = "centerButtonColorKey";
    static final String bm_backgroundColor_KEY = "backgroundColorKey";
    static final String BADGE_FULL_TEXT_KEY = "badgeFullTextKey";
    static final String VISIBILITY_KEY = "visibilityKey";

    List<BmItem> bmItems = new ArrayList<>();
    List<View> bmItemList = new ArrayList<>();
    List<RelativeLayout> badgeList = new ArrayList<>();

    HashMap<Integer, Object> badgeSaveInstanceHashMap = new HashMap<>();
    HashMap<Integer, BmItem> changedItemAndIconHashMap = new HashMap<>();

    Bundle savedInstanceState;
    Context context;
    BmOnClickListener bmOnClickListener;
    BmOnLongClickListener bmOnLongClickListener;
    Typeface customFont;
    TypedValue typedValue;

    BezierView centerContent;
    CenterButton centerButton;
    LinearLayout startContentView;
    LinearLayout endContentView;
    RelativeLayout centerBackgroundView;
    View topBorderView;

    /**
     * Constructors
     */
    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    /**
     * Init custom attributes
     *
     * @param attrs attributes
     */
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            Resources resources = getResources();

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationView);

            // main params
            bmBackgroundColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_backgroundColor, resources.getColor(R.color.bm_background_color));
            
            // Item params
            itemsMode = typedArray.getInt(R.styleable.BottomNavigationView_bm_itemsMode, -1);
            bmItemIconSize = typedArray.getDimensionPixelSize(R.styleable.BottomNavigationView_bm_itemIconSize, resources.getDimensionPixelSize(R.dimen.bm_item_icon_default_size));
            bmItemIconOnlySize = typedArray.getDimensionPixelSize(R.styleable.BottomNavigationView_bm_itemIconOnlySize, resources.getDimensionPixelSize(R.dimen.bm_item_icon_only_size));
            bmItemTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomNavigationView_bm_itemTextSize, resources.getDimensionPixelSize(R.dimen.bm_item_text_default_size));
            activeItemColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_activeItemColor, resources.getColor(R.color.bm_default_active_item_color));
            inactiveItemColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_inactiveItemColor, resources.getColor(R.color.bm_default_inactive_item_color));
            activeBmItemTextColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_activeItemTextColor, activeItemColor);
            inactiveBmItemTextColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_inactiveItemTextColor, inactiveItemColor);
//            activeBmItemTextColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_activeItemTextColor, resources.getColor(R.color.bm_default_active_item_text_color));
//            inactiveBmItemTextColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_inactiveItemTextColor, resources.getColor(R.color.bm_default_inactive_item_text_color));
            
            // center button params
            inactiveCenterBtnBgColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_inactiveCenterButtonBackgroundColor, resources.getColor(R.color.bm_center_button_color));
            centerButtonIcon = typedArray.getResourceId(R.styleable.BottomNavigationView_bm_centerButtonIcon, R.drawable.ic_home);
            activeCenterButtonIconColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_activeCenterButtonIconColor, resources.getColor(R.color.bm_white));
            inactiveCenterButtonIconColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_inactiveCenterButtonIconColor, resources.getColor(R.color.bm_default_inactive_item_color));
            activeCenterBtnBgColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_activeCenterButtonBackgroundColor, resources.getColor(R.color.bm_center_button_color));
            
            //border params
            borderType = typedArray.getInt(R.styleable.BottomNavigationView_bm_borderType, CENTER_CURVED);
            showBorder = typedArray.getBoolean(R.styleable.BottomNavigationView_bm_show_border, false);
            borderColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_border_color, resources.getColor(R.color.bm_default_border_color));

            //badge params
            badgeShapeType = typedArray.getInt(R.styleable.BottomNavigationView_bm_badgeShapeType, FILLED_OVAL);
            badgeTextColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_badgeTextColor, resources.getColor(R.color.bm_white));
            badgeBackgroundColor = typedArray.getColor(R.styleable.BottomNavigationView_bm_badgeBackgroundColor, resources.getColor(R.color.bm_badge_background_color));

            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Set default values

        if (bmBackgroundColor == NOT_DEFINED)
            bmBackgroundColor = ContextCompat.getColor(context, R.color.bm_background_color);

        if (inactiveCenterBtnBgColor == NOT_DEFINED)
            inactiveCenterBtnBgColor = ContextCompat.getColor(context, R.color.bm_center_button_color);

        if (centerButtonIcon == NOT_DEFINED)
            centerButtonIcon = R.drawable.ic_home;

        if (activeItemColor == NOT_DEFINED)
            activeItemColor = ContextCompat.getColor(context, R.color.bm_default_active_item_color);

        if (inactiveItemColor == NOT_DEFINED)
            inactiveItemColor = ContextCompat.getColor(context, R.color.bm_default_inactive_item_color);

        if (activeBmItemTextColor == NOT_DEFINED)
            activeBmItemTextColor = activeItemColor;
//            activeBmItemTextColor = ContextCompat.getColor(context, R.color.bm_default_active_item_text_color);

        if (inactiveBmItemTextColor == NOT_DEFINED)
            inactiveBmItemTextColor = inactiveItemColor;
//            inactiveBmItemTextColor = ContextCompat.getColor(context, R.color.bm_default_inactive_item_text_color);

        if (bmItemTextSize == NOT_DEFINED)
            bmItemTextSize = (int) getResources().getDimension(R.dimen.bm_item_text_default_size);

        if (bmItemIconSize == NOT_DEFINED)
            bmItemIconSize = (int) getResources().getDimension(R.dimen.bm_item_icon_default_size);

        if (bmItemIconOnlySize == NOT_DEFINED)
            bmItemIconOnlySize = (int) getResources().getDimension(R.dimen.bm_item_icon_only_size);

        if (centerButtonRippleColor == NOT_DEFINED)
            centerButtonRippleColor = ContextCompat.getColor(context, R.color.bm_background_highlight_color);

        if (activeCenterButtonIconColor == NOT_DEFINED)
            activeCenterButtonIconColor = ContextCompat.getColor(context, R.color.bm_white);

        if (inactiveCenterButtonIconColor == NOT_DEFINED)
            inactiveCenterButtonIconColor = ContextCompat.getColor(context, R.color.bm_default_inactive_item_color);

        if (badgeTextColor == NOT_DEFINED)
            badgeTextColor = ContextCompat.getColor(context, R.color.bm_white);

        if (badgeBackgroundColor == NOT_DEFINED)
            badgeBackgroundColor = ContextCompat.getColor(context, R.color.bm_badge_background_color);

        if (borderType == NOT_DEFINED)
            borderType = CENTER_CURVED;

        if (badgeShapeType == NOT_DEFINED)
            badgeShapeType = FILLED_OVAL;

        // Set main layout params
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = bmNavigationHeight;
        setBackgroundColor(ContextCompat.getColor(context, R.color.bm_transparent));
        setLayoutParams(params);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, typedValue, true);

        // Restore current item index from savedInstance
        restoreCurrentItem();

        // Trow exceptions if items size is greater than 4 or lesser than 2
        if (bmItems.size() < MIN_ITEM_SIZE && !isInEditMode()) {
            throw new NullPointerException("Your BottomMenu item count must be greater than 1 ," +
                    " your current items count isa : " + bmItems.size());
        }

        if (bmItems.size() > MAX_ITEM_SIZE && !isInEditMode()) {
            throw new IndexOutOfBoundsException("Your items count maximum can be 4," +
                    " your current items count is : " + bmItems.size());
        }

        // Get left or right content width
        contentWidth = (width - bmNavigationHeight) / 2;

        // Removing all view for not being duplicated
        removeAllViews();

        //Views initializations and customizing
        initAndAddViewsToMainView();

        // Redraw main view to make subviews visible
        postRequestLayout();

        // Restore Translation height
        restoreTranslation();
    }

    /**
     * Views initializations and customizing
     */
    private void initAndAddViewsToMainView() {

        RelativeLayout mainContent = new RelativeLayout(context);
        centerBackgroundView = new RelativeLayout(context);

        topBorderView = new View(context);
        startContentView = new LinearLayout(context);
        endContentView = new LinearLayout(context);
        centerButton = new CenterButton(context);
        centerContent = buildBezierView();

        startContentView.setId(View.generateViewId());

        topBorderView.setBackgroundColor(borderColor);

        if(showBorder) {
            borderThickness = 5;
            topBorderView.setVisibility(View.VISIBLE);
        } else {
            borderThickness = 0;
            topBorderView.setVisibility(View.GONE);
        }

        if (centerButtonId != NOT_DEFINED) centerButton.setId(centerButtonId);

        centerButton.setSize(FloatingActionButton.SIZE_NORMAL);
        centerButton.setUseCompatPadding(false);
        centerButton.setRippleColor(centerButtonRippleColor);
//        centerButton.setBackgroundTintList(ColorStateList.valueOf(inactiveCenterBtnBgColor));
        centerButton.setBackgroundTintList(ColorStateList.valueOf(activeCenterBtnBgColor));
        centerButton.setSupportImageTintList(ColorStateList.valueOf(activeCenterButtonIconColor));
        centerButton.setImageResource(centerButtonIcon);

        if (isCenterBtnIconColorFilterEnabled || isCenterButtonSelectable)
            centerButton.setSupportImageTintList(ColorStateList.valueOf(activeCenterButtonIconColor));

        centerButton.setOnClickListener(view -> {
            if (bmOnClickListener != null) bmOnClickListener.onCenterButtonClick();
            if (isCenterButtonSelectable) updateBmItems(-1);
        });
        centerButton.setOnLongClickListener(v -> {
            if (bmOnLongClickListener != null) bmOnLongClickListener.onCenterButtonLongClick();
            return true;
        });
        
        // Top border params
        LayoutParams topBorderParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, borderThickness);
        topBorderParams.addRule(RelativeLayout.ABOVE, startContentView.getId());

        // FAB layout params
        LayoutParams fabParams = new LayoutParams(centerButtonSize, centerButtonSize);
        fabParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        // Main content  params
        LayoutParams mainContentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mainContentHeight);
        mainContentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Center content params
        LayoutParams centerContentParams = new LayoutParams(centerContentWidth, bmNavigationHeight);
        centerContentParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        centerContentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Center Background View content params
        LayoutParams centerBackgroundViewParams = new LayoutParams(mainContentWidth, mainContentHeight);
        centerBackgroundViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        centerBackgroundViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Start content params
        LayoutParams startContentParams = new LayoutParams(contentWidth,mainContentHeight-borderThickness);
        startContentParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        startContentParams.addRule(LinearLayout.HORIZONTAL);
        startContentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // End content params
        LayoutParams endContentParams = new LayoutParams(contentWidth, mainContentHeight-borderThickness);
        endContentParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        endContentParams.addRule(LinearLayout.HORIZONTAL);
        endContentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Adding views background colors
        setBackgroundColors();

        // Adding view to centerContent
        centerContent.addView(centerButton, fabParams);

        // Adding views to mainContent
        addView(topBorderView, topBorderParams);
        addView(startContentView, startContentParams);
        addView(endContentView, endContentParams);

        // Adding views to mainView
        addView(centerBackgroundView, centerBackgroundViewParams);
        addView(centerContent, centerContentParams);
        addView(mainContent, mainContentParams);

        // Restore changed icons and texts from savedInstance
        restoreChangedIconsAndTexts();

        // Adding current items to start and end of content
        addBmItems(startContentView, endContentView);

        setCenterButtonSelected();
    }

    /**
     * Adding given items to content
     *
     * @param startContentView  to left content
     * @param endContentView    and right content
     */
    private void addBmItems(LinearLayout startContentView, LinearLayout endContentView) {

        // Removing all views for not being duplicated
        if (startContentView.getChildCount() > 0 || endContentView.getChildCount() > 0) {
            startContentView.removeAllViews();
            endContentView.removeAllViews();
        }

        // Clear bmItemList for not being duplicated
        bmItemList.clear();
        // Clear badgeList for not being duplicated
        badgeList.clear();

        // Getting LayoutInflater to inflate item view from XML
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < bmItems.size(); i++) {
            final int index = i;
            int targetWidth;

            if (bmItems.size() > MIN_ITEM_SIZE) {
                targetWidth = contentWidth / 2;
            } else {
                targetWidth = contentWidth;
            }

            if (bmItems.size() == 3 && i == 2) targetWidth = contentWidth;

            LayoutParams textAndIconContainerParams = new LayoutParams(targetWidth, mainContentHeight);
            RelativeLayout textAndIconContainer = (RelativeLayout) inflater.inflate(R.layout.item_bm, this, false);
            textAndIconContainer.setLayoutParams(textAndIconContainerParams);

            LinearLayout bmItemContentView = textAndIconContainer.findViewById(R.id.contentView);
            RelativeLayout bmItemRootView = textAndIconContainer.findViewById(R.id.rootView);
            ImageView bmItemIcon = textAndIconContainer.findViewById(R.id.iconImageView);
            TextView bmItemText = textAndIconContainer.findViewById(R.id.titleTextView);
            RelativeLayout badgeMainView = textAndIconContainer.findViewById(R.id.badgeMainView);
            TextView badgeTextView = textAndIconContainer.findViewById(R.id.badgeTextView);
            bmItemIcon.setImageResource(bmItems.get(i).getIcon());
            bmItemText.setText(bmItems.get(i).getTitle());
            bmItemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, bmItemTextSize);

//            bmItemRootView.setBackgroundResource(typedValue.resourceId);
//            bmItemRootView.setBackground(Utils.createRippleDrawable(Color.TRANSPARENT,
//                    ContextCompat.getColor(context,R.color.click_gray),new Rect()));

            if(bmItems.size() <= 2) {
                bmItemContentView.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                bmItemContentView.setOrientation(LinearLayout.VERTICAL);
            }

            if (bmItems.size() == 3 && i == 2) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );
                params.setMargins(20, 0, 20, 0);
                bmItemText.setLayoutParams(params);
            }

            // Set a custom id to the item
            if (bmItems.get(i).getId() != -1) textAndIconContainer.setId(bmItems.get(i).getId());

            // Set custom font to item textView
            if (useCustomFont) {
                bmItemText.setTypeface(customFont);
                badgeTextView.setTypeface(customFont);
            }

            ViewGroup.LayoutParams iconParams = bmItemIcon.getLayoutParams();

            switch (itemsMode) {

                // Hide item icon and show only text
                case TEXT_ONLY:
                    iconParams.height = bmItemIconSize;
                    iconParams.width = bmItemIconSize;
                    bmItemIcon.setLayoutParams(iconParams);
                    Utils.changeViewVisibility(bmItemIcon, View.GONE);
                    break;

                // Hide item text and change icon size
                case ICON_ONLY:
                    iconParams.height = bmItemIconOnlySize;
                    iconParams.width = bmItemIconOnlySize;
                    bmItemIcon.setLayoutParams(iconParams);
                    Utils.changeViewVisibility(bmItemText, View.GONE);
                    break;

                case HIDDEN:
                    iconParams.height = bmItemIconSize;
                    iconParams.width = bmItemIconSize;
                    bmItemIcon.setLayoutParams(iconParams);
                    Utils.changeViewVisibility(bmItemText, View.GONE);
                    Utils.changeViewVisibility(bmItemIcon, View.GONE);
                    break;

                case NORMAL:
                default :
                    iconParams.height = bmItemIconSize;
                    iconParams.width = bmItemIconSize;
                    bmItemIcon.setLayoutParams(iconParams);
                    break;
            }

            // Adding items to item list
            bmItemList.add(textAndIconContainer);

            // Adding badge items to badge list
            badgeList.add(badgeMainView);

            // Adding sub views to left and right sides
            if (bmItems.size() == MIN_ITEM_SIZE && startContentView.getChildCount() == 1) {
                endContentView.addView(textAndIconContainer, textAndIconContainerParams);
            } else if (bmItems.size() > MIN_ITEM_SIZE && startContentView.getChildCount() == 2) {
                endContentView.addView(textAndIconContainer, textAndIconContainerParams);
                if(bmItems.size() == 3) bmItemContentView.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                startContentView.addView(textAndIconContainer, textAndIconContainerParams);
            }

            // Changing current selected item tint
            if (i == currentSelectedItem) {
                bmItemText.setTextColor(activeBmItemTextColor);
                Utils.changeImageViewTint(bmItemIcon, activeItemColor);
            } else {
                bmItemText.setTextColor(inactiveBmItemTextColor);
                Utils.changeImageViewTint(bmItemIcon, inactiveItemColor);
            }

            textAndIconContainer.setOnClickListener(view -> updateBmItems(index));

            textAndIconContainer.setOnLongClickListener(v -> {
                if (bmOnLongClickListener != null)
                    bmOnLongClickListener.onItemLongClick(index, bmItems.get(index).getTitle());
                return true;
            });
        }

        // Restore available badges from saveInstance
        restoreBadges();
    }

    /**
     * Update selected item and change it's and non selected item tint
     *
     * @param selectedIndex item in index
     */
    private void updateBmItems(final int selectedIndex) {

        // return if item already selected
        if (currentSelectedItem == selectedIndex) {
            if (bmOnClickListener != null && selectedIndex >= 0)
                bmOnClickListener.onItemReselected(selectedIndex, bmItems.get(selectedIndex).getTitle());
            return;
        }

        if (isCenterButtonSelectable) {
            // Selects the center button as current
            if (selectedIndex == -1) {
                if (centerButton != null) {
                    centerButton.setSupportImageTintList(ColorStateList.valueOf(activeCenterButtonIconColor));

                    if (activeCenterBtnBgColor != NOT_DEFINED) {
                        centerButton.setBackgroundTintList(ColorStateList.valueOf(activeCenterBtnBgColor));
                    }
                }
            }

            // Removes selection from center button
            if (currentSelectedItem == -1) {
                if (centerButton != null) {
                    centerButton.setSupportImageTintList(ColorStateList.valueOf(inactiveCenterButtonIconColor));

                    if (activeCenterBtnBgColor != NOT_DEFINED) {
                        centerButton.setBackgroundTintList(ColorStateList.valueOf(inactiveCenterBtnBgColor));
                    }
                }
            }
        }

        // Change active and inactive icon and text color
        for (int i = 0; i < bmItemList.size(); i++) {
            if (i == selectedIndex) {
                RelativeLayout textAndIconContainer = (RelativeLayout) bmItemList.get(selectedIndex);
                ImageView bmItemIcon = textAndIconContainer.findViewById(R.id.iconImageView);
                TextView bmItemText = textAndIconContainer.findViewById(R.id.titleTextView);
                bmItemText.setTextColor(activeBmItemTextColor);
                Utils.changeImageViewTint(bmItemIcon, activeItemColor);
            } else if (i == currentSelectedItem) {
                RelativeLayout textAndIconContainer = (RelativeLayout) bmItemList.get(i);
                ImageView bmItemIcon = textAndIconContainer.findViewById(R.id.iconImageView);
                TextView bmItemText = textAndIconContainer.findViewById(R.id.titleTextView);
                bmItemText.setTextColor(inactiveBmItemTextColor);
                Utils.changeImageViewTint(bmItemIcon, inactiveItemColor);
            }
        }

        /*
          Set a listener that gets fired when the selected item changes

          @param listener a listener for monitoring changes in item selection
         */
        if (bmOnClickListener != null && selectedIndex >= 0)
            bmOnClickListener.onItemClick(selectedIndex, bmItems.get(selectedIndex).getTitle());

        /*
          Change current selected item index
         */
        currentSelectedItem = selectedIndex;
    }

    /**
     * Set views background colors
     */
    private void setBackgroundColors() {
        startContentView.setBackgroundColor(bmBackgroundColor);
        endContentView.setBackgroundColor(bmBackgroundColor);

        if(borderType == LINEAR) {
            centerBackgroundView.setBackgroundColor(ContextCompat.getColor(context, R.color.bm_transparent));
        } else if(borderType == CENTER_CURVED) {
//            centerBackgroundView.setBackgroundColor(bmBackgroundColor);
            centerBackgroundView.setBackgroundColor(bmBackgroundColor);
        } else {
//            centerBackgroundView.setBackgroundColor(bmBackgroundColor);
            centerBackgroundView.setBackgroundColor(bmBackgroundColor);
        }
    }

    /**
     * Indicate event queue that we have changed the View hierarchy during a layout pass
     */
    private void postRequestLayout() {
        BottomNavigationView.this.getHandler().post(new Runnable() {
            @Override
            public void run() {
                BottomNavigationView.this.requestLayout();
            }
        });
    }

    /**
     * Restore current item index from savedInstance
     */
    private void restoreCurrentItem() {
        Bundle restoredBundle = savedInstanceState;
        if (restoredBundle != null) {
            if (restoredBundle.containsKey(CURRENT_SELECTED_ITEM_BUNDLE_KEY))
                currentSelectedItem = restoredBundle.getInt(CURRENT_SELECTED_ITEM_BUNDLE_KEY, 0);
        }
    }

    /**
     * Restore available badges from saveInstance
     */
    @SuppressWarnings("unchecked")
    private void restoreBadges() {
        Bundle restoredBundle = savedInstanceState;

        if (restoredBundle != null) {
            if (restoredBundle.containsKey(BADGE_FULL_TEXT_KEY)) {
                show99plusSign = restoredBundle.getBoolean(BADGE_FULL_TEXT_KEY);
            }

            if (restoredBundle.containsKey(BADGES_ITEM_BUNDLE_KEY)) {
                badgeSaveInstanceHashMap = (HashMap<Integer, Object>) savedInstanceState.getSerializable(BADGES_ITEM_BUNDLE_KEY);
                if (badgeSaveInstanceHashMap != null) {
                    for (Integer integer : badgeSaveInstanceHashMap.keySet()) {
                        BadgeHelper.forceShowBadge(badgeList.get(integer),
                                (BadgeItem) Objects.requireNonNull(badgeSaveInstanceHashMap.get(integer)),
                                show99plusSign);
                    }
                }
            }
        }
    }

    /**
     * Restore changed icons,colors and texts from saveInstance
     */
    @SuppressWarnings("unchecked")
    private void restoreChangedIconsAndTexts() {
        Bundle restoredBundle = savedInstanceState;
        if (restoredBundle != null) {
            if (restoredBundle.containsKey(CHANGED_ICON_AND_TEXT_BUNDLE_KEY)) {
                changedItemAndIconHashMap = (HashMap<Integer, BmItem>) restoredBundle.getSerializable(CHANGED_ICON_AND_TEXT_BUNDLE_KEY);
                if (changedItemAndIconHashMap != null) {
                    BmItem bmItem;
                    for (int i = 0; i < changedItemAndIconHashMap.size(); i++) {
                        bmItem = changedItemAndIconHashMap.get(i);
                        bmItems.get(i).setIcon(bmItem != null ? bmItem.getIcon() : 0);
                        bmItems.get(i).setTitle(bmItem != null ? bmItem.getTitle() : "");
                    }
                }
            }

            if (restoredBundle.containsKey(center_BUTTON_ICON_KEY)) {
                centerButtonIcon = restoredBundle.getInt(center_BUTTON_ICON_KEY);
                centerButton.setImageResource(centerButtonIcon);
            }

            if (restoredBundle.containsKey(bm_backgroundColor_KEY)) {
                int backgroundColor = restoredBundle.getInt(bm_backgroundColor_KEY);
                changeBackgroundColor(backgroundColor);
            }
        }
    }

    /**
     * Creating bezier view with params
     *
     * @return created bezier view
     */
    private BezierView buildBezierView() {
        BezierView bezierView = new BezierView(context, bmBackgroundColor,borderColor);
        bezierView.build(centerContentWidth, bmNavigationHeight - mainContentHeight,
                borderThickness,showBorder,borderType);
        return bezierView;
    }

    /**
     * Throw Array Index Out Of Bounds Exception
     *
     * @param itemIndex item index to show on logs
     */
    private void throwArrayIndexOutOfBoundsException(int itemIndex) {
        throw new ArrayIndexOutOfBoundsException("Your item index can't be 0 or greater than item size," +
                " your items size is " + bmItems.size() + ", your current index is :" + itemIndex);
    }

    /**
     * Initialization with savedInstanceState to save current selected
     * position and current badges
     *
     * @param savedInstanceState bundle to saveInstance
     */
    public void initWithSaveInstanceState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    /**
     * Save badges and current position
     *
     * @param outState bundle to saveInstance
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_SELECTED_ITEM_BUNDLE_KEY, currentSelectedItem);
        outState.putInt(center_BUTTON_ICON_KEY, centerButtonIcon);
        outState.putInt(bm_backgroundColor_KEY, bmBackgroundColor);
        outState.putBoolean(BADGE_FULL_TEXT_KEY, show99plusSign);
        outState.putFloat(VISIBILITY_KEY, this.getTranslationY());

        if (badgeSaveInstanceHashMap.size() > 0)
            outState.putSerializable(BADGES_ITEM_BUNDLE_KEY, badgeSaveInstanceHashMap);
        if (changedItemAndIconHashMap.size() > 0)
            outState.putSerializable(CHANGED_ICON_AND_TEXT_BUNDLE_KEY, changedItemAndIconHashMap);
    }

    public void setCenterButtonId(@IdRes int id) {
        this.centerButtonId = id;
    }

    /**
     * Set center circle button background color
     *
     * @param inactiveCenterBtnBgColor target color
     */
    public void setCenterButtonColor(@ColorInt int inactiveCenterBtnBgColor) {
        this.inactiveCenterBtnBgColor = inactiveCenterBtnBgColor;
    }

    /**
     * Set main background color
     *
     * @param bmBackgroundColor target color
     */
    public void setBmBackgroundColor(@ColorInt int bmBackgroundColor) {
        this.bmBackgroundColor = bmBackgroundColor;
    }

    /**
     * Set center button icon
     *
     * @param icon target icon
     */
    public void setCenterButtonIcon(int icon) {
        this.centerButtonIcon = icon;
        if (centerButton == null) {
            Log.e(TAG, "You should call setCenterButtonIcon() instead, " +
                    "centerButtonIcon works if navigation already set up");
        } else {
            centerButton.setImageResource(icon);
        }
    }

    /**
     * Set active & inactive center button background color
     *
     * @param activeColor   center button background active color
     * @param inactiveColor center button background inactive color
     */
    public void setCenterButtonBackgroundColor(@ColorInt int activeColor,@ColorInt int inactiveColor) {
        this.activeCenterBtnBgColor = activeColor;
        this.inactiveCenterBtnBgColor = inactiveColor;
    }

    /**
     * Set inactive center button background color
     *
     * @param inactiveColor center button background inactive color
     */
    public void setInactiveCenterButtonBackgroundColor(@ColorInt int inactiveColor) {
        this.inactiveCenterBtnBgColor = inactiveColor;
    }

    /**
     * Set active center button background color
     *
     * @param activeColor center button background active color
     */
    public void setActiveCenterButtonBackgroundColor(@ColorInt int activeColor) {
        this.activeCenterBtnBgColor = activeColor;
    }

    /**
     * Set active item text color
     *
     * @param activeColor   active item color
     * @param inactiveColor inactive item color
     */
    public void setItemColor(@ColorInt int activeColor,@ColorInt int inactiveColor) {
        this.activeItemColor = activeColor;
        this.inactiveItemColor = inactiveColor;
    }

    /**
     * Set active item text color
     *
     * @param activeItemColor color to change
     */
    public void setActiveItemColor(@ColorInt int activeItemColor) {
        this.activeItemColor = activeItemColor;
    }

    /**
     * Set inactive item text color
     *
     * @param inactiveItemColor color to change
     */
    public void setInActiveItemColor(@ColorInt int inactiveItemColor) {
        this.inactiveItemColor = inactiveItemColor;
    }

    /**
     * Set item icon size
     *
     * @param bmItemIconSize target size
     */
    public void setBmItemIconSize(int bmItemIconSize) {
        this.bmItemIconSize = bmItemIconSize;
    }

    /**
     * Set item icon size if showIconOnly() called
     *
     * @param bmItemIconOnlySize target size
     */
    public void setBmItemIconSizeInOnlyIconMode(int bmItemIconOnlySize) {
        this.bmItemIconOnlySize = bmItemIconOnlySize;
    }

    /**
     * Set item text size
     *
     * @param bmItemTextSize target size
     */
    public void setBmItemTextSize(int bmItemTextSize) {
        this.bmItemTextSize = bmItemTextSize;
    }

    /**
     * Set center button pressed state color
     *
     * @param centerButtonRippleColor Target color
     */
    public void setCenterButtonRippleColor(int centerButtonRippleColor) {
        this.centerButtonRippleColor = centerButtonRippleColor;
    }

//    /**
//     * Makes center button selectable
//     */
//    public void setCenterButtonSelectable(boolean isSelectable) {
//        this.isCenterButtonSelectable = isSelectable;
//    }

    /**
     * set type of items
     *
     * @param itemsMode type of items
     */
    public void setItemsMode(int itemsMode) {
        this.itemsMode = itemsMode;
    }

    /**
     * Add item to navigation
     *
     * @param bmItem item to add
     */
    public void addBmItem(BmItem bmItem) {
        bmItems.add(bmItem);
    }

    /**
     * Change current selected item to center button
     */
    public void setCenterButtonSelected() {
        if (!isCenterButtonSelectable)
            throw new ArrayIndexOutOfBoundsException("Please be more careful, you must set the center button selectable");
        else
            updateBmItems(-1);
    }

    /**
     * Set item and center click
     *
     * @param bmOnClickListener click listener
     */
    public void setBmOnClickListener(BmOnClickListener bmOnClickListener) {
        this.bmOnClickListener = bmOnClickListener;
    }

    /**
     * Set item and center button long click
     *
     * @param bmOnLongClickListener long click listener
     */
    public void setBmOnLongClickListener(BmOnLongClickListener bmOnLongClickListener) {
        this.bmOnLongClickListener = bmOnLongClickListener;
    }

    /**
     * Change current selected item to given index
     * Note: -1 represents the center button
     *
     * @param indexToChange given index
     */
    public void changeCurrentItem(int indexToChange) {
        if (indexToChange < -1 || indexToChange > bmItems.size())
            throw new ArrayIndexOutOfBoundsException("Please be more careful, we do't have such item : " + indexToChange);
        else {
            updateBmItems(indexToChange);
        }
    }

    /**
     * Restore translation height from saveInstance
     */
    @SuppressWarnings("unchecked")
    private void restoreTranslation() {
        Bundle restoredBundle = savedInstanceState;

        if (restoredBundle != null) {
            if (restoredBundle.containsKey(VISIBILITY_KEY)) {
                this.setTranslationY(restoredBundle.getFloat(VISIBILITY_KEY));
            }

        }
    }

    /**
     * show badge at item
     *
     * @param itemIndex index
     * @param badgeText badge count text
     */
    public void showBadgeAtIndex(int itemIndex, int badgeText) {
        if (itemIndex < 0 || itemIndex > bmItems.size()) {
            throwArrayIndexOutOfBoundsException(itemIndex);
        } else {
            new Handler().postDelayed(() -> {

                RelativeLayout badgeView = badgeList.get(itemIndex);

                // Set circle background to badge view
                badgeView.setBackground(BadgeHelper.createShapeDrawable(badgeBackgroundColor,badgeShapeType));

                BadgeItem badgeItem = new BadgeItem(itemIndex, badgeText,badgeBackgroundColor);
                BadgeHelper.showBadge(badgeView, badgeItem,badgeTextColor, show99plusSign);
                badgeSaveInstanceHashMap.put(itemIndex, badgeItem);
            }, 1000);
        }
    }

    /**
     * show badge at item
     *
     * @param itemIndex  index
     * @param badgeText  badge count text
     * @param badgeColor badge background color
     */
    public void showBadgeAtIndex(int itemIndex, int badgeText, @ColorInt int badgeColor) {
        if (itemIndex < 0 || itemIndex > bmItems.size()) {
            throwArrayIndexOutOfBoundsException(itemIndex);
        } else {
            new Handler().postDelayed(() -> {

                RelativeLayout badgeView = badgeList.get(itemIndex);

                // Set circle background to badge view
                badgeView.setBackground(BadgeHelper.createShapeDrawable(badgeColor,badgeShapeType));

                BadgeItem badgeItem = new BadgeItem(itemIndex, badgeText, badgeColor);
                BadgeHelper.showBadge(badgeView, badgeItem,badgeTextColor, show99plusSign);
                badgeSaveInstanceHashMap.put(itemIndex, badgeItem);
            }, 1000);
        }
    }

    /**
     * show badge at item
     *
     * @param itemIndex      index
     * @param badgeText      badge count text
     * @param badgeColor     badge background color
     * @param badgeTextColor badge text color
     */
    public void showBadgeAtIndex(int itemIndex, int badgeText, @ColorInt int badgeColor, @ColorInt int badgeTextColor) {
        if (itemIndex < 0 || itemIndex > bmItems.size()) {
            throwArrayIndexOutOfBoundsException(itemIndex);
        } else {
            new Handler().postDelayed(() -> {

                RelativeLayout badgeView = badgeList.get(itemIndex);

                // Set circle background to badge view
                badgeView.setBackground(BadgeHelper.createShapeDrawable(badgeColor,badgeShapeType));

                BadgeItem badgeItem = new BadgeItem(itemIndex, badgeText, badgeColor);
                BadgeHelper.showBadge(badgeView, badgeItem,badgeTextColor, show99plusSign);
                badgeSaveInstanceHashMap.put(itemIndex, badgeItem);
            }, 1000);
        }
    }

    /**
     * show badge at item
     *
     * @param itemIndex      index
     * @param badgeText      badge count text
     * @param badgeColor     badge background color
     * @param badgeTextColor badge text color
     * @param badgeShapeType badge shape type
     */
    public void showBadgeAtIndex(int itemIndex, int badgeText, @ColorInt int badgeColor,
                                 @ColorInt int badgeTextColor, int badgeShapeType) {
        if (itemIndex < 0 || itemIndex > bmItems.size()) {
            throwArrayIndexOutOfBoundsException(itemIndex);
        } else {
            new Handler().postDelayed(() -> {

                RelativeLayout badgeView = badgeList.get(itemIndex);

                // Set circle background to badge view
                badgeView.setBackground(BadgeHelper.createShapeDrawable(badgeColor,badgeShapeType));

                BadgeItem badgeItem = new BadgeItem(itemIndex, badgeText, badgeColor);
                BadgeHelper.showBadge(badgeView, badgeItem,badgeTextColor, show99plusSign);
                badgeSaveInstanceHashMap.put(itemIndex, badgeItem);
            }, 1000);
        }
    }

    /**
     * Hide badge at index
     *
     * @param index badge index
     */
    public void hideBadgeAtIndex(final int index) {
        if (badgeList.get(index).getVisibility() == GONE) {
            Log.e(TAG, "Badge at index: " + index + " already is hidden");
        } else {
            BadgeHelper.hideBadge(badgeList.get(index));
            badgeSaveInstanceHashMap.remove(index);
        }
    }

    /**
     * Hiding all available badges
     */
    public void hideAllBadges() {
        for (RelativeLayout badge : badgeList) {
            if (badge.getVisibility() == VISIBLE) BadgeHelper.hideBadge(badge);
        }
        badgeSaveInstanceHashMap.clear();
    }

    /**
     * Change badge text at index
     *
     * @param badgeIndex target index
     * @param badgeText  badge count text to change
     */
    public void changeBadgeTextAtIndex(Integer badgeIndex, int badgeText) {
        if (badgeSaveInstanceHashMap.get(badgeIndex) != null &&
                (((BadgeItem) Objects.requireNonNull(badgeSaveInstanceHashMap.get(badgeIndex))).getIntBadgeText() != badgeText)) {

            BadgeItem currentBadgeItem = (BadgeItem) badgeSaveInstanceHashMap.get(badgeIndex);
            if(currentBadgeItem != null) {
                BadgeItem badgeItemForSave = new BadgeItem(badgeIndex, badgeText, currentBadgeItem.getBadgeColor());
                BadgeHelper.forceShowBadge(badgeList.get(badgeIndex), badgeItemForSave, show99plusSign);
                badgeSaveInstanceHashMap.put(badgeIndex, badgeItemForSave);
            }
        }
    }

    /**
     * Set custom font to item textView
     *
     * @param font custom font
     */
    public void setFont(Typeface font) {
        useCustomFont = true;
        this.customFont = font;
    }

    public void setCenterButtonIconColorFilterEnabled(boolean enabled) {
        isCenterBtnIconColorFilterEnabled = enabled;
    }

    /**
     * Change item icon if navigation already set up
     *
     * @param itemIndex Target position
     * @param newIcon   Icon to change
     */
    public void changeItemIconAtPosition(int itemIndex, int newIcon) {
        if (itemIndex < 0 || itemIndex > bmItems.size()) {
            throwArrayIndexOutOfBoundsException(itemIndex);
        } else {
            BmItem bmItem = bmItems.get(itemIndex);
            RelativeLayout textAndIconContainer = (RelativeLayout) bmItemList.get(itemIndex);
            ImageView bmItemIcon = textAndIconContainer.findViewById(R.id.iconImageView);
            bmItemIcon.setImageResource(newIcon);
            bmItem.setIcon(newIcon);
            changedItemAndIconHashMap.put(itemIndex, bmItem);
        }
    }

    /**
     * Change item text if navigation already set up
     *
     * @param itemIndex Target position
     * @param newText   Text to change
     */
    public void changeItemTextAtPosition(int itemIndex, String newText) {
        if (itemIndex < 0 || itemIndex > bmItems.size()) {
            throwArrayIndexOutOfBoundsException(itemIndex);
        } else {
            BmItem bmItem = bmItems.get(itemIndex);
            RelativeLayout textAndIconContainer = (RelativeLayout) bmItemList.get(itemIndex);
            TextView bmItemIcon = textAndIconContainer.findViewById(R.id.titleTextView);
            bmItemIcon.setText(newText);
            bmItem.setTitle(newText);
            changedItemAndIconHashMap.put(itemIndex, bmItem);
        }
    }

    /**
     * Change background color if view already set up
     *
     * @param color Target color to change
     */
    public void changeBackgroundColor(@ColorInt int color) {
        if (color == bmBackgroundColor) {
            Log.d(TAG, "changeBackgroundColor : color already changed");
            return;
        }

        bmBackgroundColor = color;
        setBackgroundColors();
        centerContent.changeBackgroundColor(color);
    }


    /**
     * Show full badge text or show 99+ for numbers greater than 99
     *
     * @param show99plusSign false for full text
     */
    public void showFullBadgeText(boolean show99plusSign) {
        this.show99plusSign = show99plusSign;
    }

    /**
     * set active center button color
     *
     * @param color target color
     */
    public void setActiveCenterButtonIconColor(@ColorInt int color) {
        activeCenterButtonIconColor = color;
    }

    /**
     * set inactive center button color
     *
     * @param color target color
     */
    public void setInActiveCenterButtonIconColor(@ColorInt int color) {
        inactiveCenterButtonIconColor = color;
    }

    /**
     * Set type of border
     *
     * @param borderType type of border
     */
    public void setBorderType(int borderType) {
        this.borderType = borderType;
    }
}