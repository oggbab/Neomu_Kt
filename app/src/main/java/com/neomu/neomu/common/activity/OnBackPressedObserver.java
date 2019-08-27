package com.neomu.neomu.common.activity;

public interface OnBackPressedObserver {
    void addOnBackPressedListener(OnBackPressedListener listener);
    void removeOnBackPressedListener(OnBackPressedListener listener);
}
