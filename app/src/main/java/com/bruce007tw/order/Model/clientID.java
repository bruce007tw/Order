package com.bruce007tw.order.Model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class clientID {
    @Exclude
    public String id;

    public <T extends clientID> T withId(@NonNull final String clientID) {
        this.id = clientID;
        return (T) this;
    }
}