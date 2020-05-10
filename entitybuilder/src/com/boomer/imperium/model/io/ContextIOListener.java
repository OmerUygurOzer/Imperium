package com.boomer.imperium.model.io;

import com.boomer.imperium.model.Context;

public interface ContextIOListener {
    void contextWritten(Context context);
    void contextRead(Context context);
}
