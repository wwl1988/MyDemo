package com.example.wwl.myfragmenttest.fragment;


import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

/**
 * Created by wwl on 2017/1/12.
 */

public class BaseDialogFragment extends DialogFragment {

    public interface onDialogInteraction extends DialogInterface.OnClickListener {

        void onConfirm(DialogInterface dialog, int which);

        void onCancel(DialogInterface dialog, int which);
    }

    protected onDialogInteraction mListener;

}
