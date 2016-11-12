package com.paypal.splittable.fragments;

import com.paypal.splittable.R;
import com.paypal.splittable.adapters.ContactPickerAdapter;
import com.paypal.splittable.utils.SmsUtil;
import com.paypal.splittable.widgets.CustomMultiAutoCompleteTextView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by arbalan on 3/21/16.
 */
public class MessageFragment extends Fragment {
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    private CustomMultiAutoCompleteTextView mContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_message, container, false);

        checkForPermissions();
        mContact = (CustomMultiAutoCompleteTextView) top.findViewById(R.id.editText);
        ContactPickerAdapter contactPickerAdapter = new ContactPickerAdapter(getContext(),
                android.R.layout.simple_list_item_1, SmsUtil.getContacts(
                getContext(), false));
        mContact.setAdapter(contactPickerAdapter);

        return top;
    }

    private void checkForPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[] { Manifest.permission.READ_CONTACTS },
                        READ_CONTACTS_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
