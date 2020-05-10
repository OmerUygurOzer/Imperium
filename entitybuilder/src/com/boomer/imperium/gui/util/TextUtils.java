package com.boomer.imperium.gui.util;

import com.google.common.base.Strings;

import javax.swing.*;

public final class TextUtils {

    public static boolean isEmpty(JTextField textField){
        return Strings.isNullOrEmpty(textField.getText());
    }

    private TextUtils(){}
}
