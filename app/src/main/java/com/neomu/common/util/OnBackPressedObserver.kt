package com.neomu.common.util

interface OnBackPressedObserver {
    fun addOnBackPressedListener(listener: OnBackPressedListener)
    fun removeOnBackPressedListener(listener: OnBackPressedListener)
}