package com.justice.sendmessageusingfirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout contactInput;
    private Button sendBtn;
    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setOnClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.instructionItem) {

            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(textView);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        }

        return true;
    }

    private void setOnClickListeners() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBtnTapped();
            }
        });
    }

    private void sendBtnTapped() {

        String contact = contactInput.getEditText().getText().toString().trim();
        if (contact.isEmpty()) {
            contactInput.setError("Please fill Field");
            return;
        }
        sendMessage("+" + contact);

/////////////////
    }

    private void sendMessage(String contact) {

        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(contact, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Snackbar.make(coordinatorLayout, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Snackbar.make(coordinatorLayout, "Message Send", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });


    }

    private void initWidgets() {
        contactInput = findViewById(R.id.contactInput);
        sendBtn = findViewById(R.id.sendBtn);
        progressDialog = new ProgressDialog(this);
        coordinatorLayout = findViewById(R.id.coordinator);
        textView = findViewById(R.id.textView);


    }
}
