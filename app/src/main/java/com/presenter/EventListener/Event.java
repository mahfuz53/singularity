package com.presenter.EventListener;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.Singularity.store.R;
import com.presenter.view.dialog;


/**
 * Created by msi on 1/2/2018.
 */
public class Event {
    OnEventListener ml;

    // constructor
/*    Event(OnEventListener ml) {
        this.ml = ml;
    }

    public void MyLogicToIntimateOthere() {
        ml.callback("success");
    }*/

    public void setOnEventListener(OnEventListener listener) {
        ml = listener;
    }

    public void doEvent() {
        /*
         * code code code
         */

        // and in the end

        if (ml != null)
            ml.callback_("success"); // event result object :)
    }

    public void alertBoxCredit(final Context appContx,final Context con,String title,String msg,String ok,boolean onlyOk, int width, int height)
    {
        final Dialog testShow_popup = dialog.showBottomDialog(con, R.layout.alert_box_show,width-(width/7)  , height-(height/3), -1, -1);
        testShow_popup.setCancelable(false);

        TextView cancelBtn              = (TextView) testShow_popup.findViewById(R.id.textcancle);
        TextView okBtn                  = (TextView) testShow_popup.findViewById(R.id.textOK);
        TextView msgText                = (TextView) testShow_popup.findViewById(R.id.textMsg);
        TextView titleText              = (TextView) testShow_popup.findViewById(R.id.textTitle);

        titleText.setText(title);
        msgText.setLinkTextColor(Color.RED);
        msgText.setMovementMethod(LinkMovementMethod.getInstance());
        msgText.setText(Html.fromHtml(msg));
        okBtn.setText(ok);

        if(onlyOk)
        {
            cancelBtn.setVisibility(View.GONE);
        }
        else {
            cancelBtn.setVisibility(View.VISIBLE);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ml != null) ml.callback_("success");
                testShow_popup.cancel();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ml != null)ml.callback_("failed");
                testShow_popup.cancel();
            }
        });
    }
}
