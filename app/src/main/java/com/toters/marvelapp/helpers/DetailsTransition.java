package com.toters.marvelapp.helpers;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

public class DetailsTransition extends TransitionSet {
    public DetailsTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeBounds()).
                addTransition(new ChangeImageTransform()).
                addTransition(new ChangeTransform());

    }
}
