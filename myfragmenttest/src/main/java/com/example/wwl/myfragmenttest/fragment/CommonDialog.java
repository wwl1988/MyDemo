package com.example.wwl.myfragmenttest.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by wwl on 2017/1/12.
 */

public class CommonDialog extends BaseDialogFragment {

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String POSITIVE = "positive";
    private static final String NEGATIVE = "negative";

    private static onDialogInteraction mListener;

    public CommonDialog() {
    }

    public static CommonDialog newInstance(String title, String message, String positive, String negative,
                                           onDialogInteraction listener){
        CommonDialog commonDialog = new CommonDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        args.putString(POSITIVE, positive);
        args.putString(NEGATIVE, negative);
        mListener = listener;
        commonDialog.setArguments(args);
        return commonDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String title = arguments.getString(TITLE);
        final String message = arguments.getString(MESSAGE);
        String positive = arguments.getString(POSITIVE);
        String negative = arguments.getString(NEGATIVE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener != null){
                    mListener.onConfirm(dialog, which);
                }
            }
        })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener != null){
                    mListener.onCancel(dialog, which);
                }
            }
        });
        return builder.create();
    }
}
