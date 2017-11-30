package com.example.sevillano.proto_2;

import android.content.Context;
import android.view.View;
import android.widget.MediaController;

public class CustomController extends MediaController {
    public CustomController(Context context, View anchor)     {
        super(context);
        super.setAnchorView(anchor);
    }
    @Override
    public void setAnchorView(View view)     {
        // Do nothing
    }
}
