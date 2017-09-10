package org.saungit.bakingapp;

/**
 * Created by muhtar on 9/10/17.
 */

public enum CustomPagerEnum {
    RED(R.string.app_name, R.layout.view_red),
    BLUE(R.string.app_name, R.layout.activity_steps),
    ORANGE(R.string.exo_controls_fastforward_description, R.layout.activity_main);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
