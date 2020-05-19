package com.boomer.imperium.model.io;

import com.boomer.imperium.Context;

public interface ContextIOListener {
    void contextWritten(Context context);
    void contextRead(Context context);
    void contextGenerated(Context context);
}
