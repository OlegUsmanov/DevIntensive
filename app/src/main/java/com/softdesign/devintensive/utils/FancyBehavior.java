package com.softdesign.devintensive.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;


public class FancyBehavior <V extends View>
        extends CoordinatorLayout.Behavior<V> {

    /**
     * Конструктор для создания экземпляра FancyBehavior через код.
     */
    public FancyBehavior() {
    }

    /**
     * Конструктор для создания экземпляра FancyBehavior через разметку.
     *
     * @param context The {@link Context}.
     * @param attrs The {@link AttributeSet}.
     */
    public FancyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Извлекаем любые пользовательские атрибуты
        // в идеале с префиксом behavior_
        // чтобы обозначить принадлежность атрибута к Behavior
    }
}

