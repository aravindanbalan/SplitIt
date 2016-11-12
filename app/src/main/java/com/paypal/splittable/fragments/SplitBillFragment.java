package com.paypal.splittable.fragments;

import com.paypal.splittable.R;
import com.paypal.splittable.utils.IConstants;
import com.paypal.splittable.utils.SharedPreferenceUtils;
import com.paypal.splittable.widgets.PaymentCodeCardController;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by arbalan on 3/21/16.
 */
public class SplitBillFragment extends Fragment {
    private static final String LOG_TAG = SplitBillFragment.class.getName();
    private RelativeLayout mSplitBillView;
    private RelativeLayout mPaycodeContainerView;
    private PaymentCodeCardController mCardController;
    private EditText mBillAmount;
    private EditText mShareCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_split_bill, container, false);

        mSplitBillView = (RelativeLayout) top.findViewById(R.id.split_bill_info);
        mPaycodeContainerView = (RelativeLayout) top.findViewById(R.id.paycode_container);
        mCardController = (PaymentCodeCardController) top.findViewById(R.id.view_paycode_container);
        mBillAmount = (EditText) top.findViewById(R.id.bill_amount);
        mShareCount = (EditText) top.findViewById(R.id.share_count);
        Button generate = (Button) top.findViewById(R.id.generate_code);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    generateCode();
                }
            }
        });

        Button back = (Button) top.findViewById(R.id.paycode_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePaycodeContainer();
            }
        });
        return top;
    }

    private void generateCode() {
        if (mBillAmount != null && mCardController != null) {

            String amount = mBillAmount.getText().toString();
            String count = mShareCount.getText().toString();

            if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(count)) {
                Double bill_amount = Double.parseDouble(amount);
                Integer share_count = Integer.parseInt(count);

                if (bill_amount != null && share_count > 0) {
                    Double split_amount = bill_amount / share_count;
                    String userName = readUserNameFromSharedPref();
                    if (!TextUtils.isEmpty(userName)) {
                        String paycodeValue = userName + "/" + split_amount;
                        Log.i(LOG_TAG, "************ Generate code" + paycodeValue);
                        mCardController.renderPayCode(paycodeValue);
                        showPaycodeContainer();
                    }
                }
            }
        }
    }

    private boolean validateFields() {
        //TODO show hint saying this field is required
        return true;
    }

    private void showPaycodeContainer() {
        if (mPaycodeContainerView != null && mSplitBillView != null) {
            mPaycodeContainerView.setVisibility(View.VISIBLE);
            mSplitBillView.setVisibility(View.GONE);
        }
    }

    private void hidePaycodeContainer() {
        if (mPaycodeContainerView != null && mSplitBillView != null) {
            mPaycodeContainerView.setVisibility(View.GONE);
            mSplitBillView.setVisibility(View.VISIBLE);
        }
    }

    private String readUserNameFromSharedPref() {
        SharedPreferences preferences = SharedPreferenceUtils.getSharedPreference(getActivity());
        return preferences.getString(IConstants.PAYPAL_ME_USER_NAME, "");
    }
}