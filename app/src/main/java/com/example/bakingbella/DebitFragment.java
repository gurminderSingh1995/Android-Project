package com.example.bakingbella;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DebitFragment extends Fragment {
    private DebitFragmentListener listener;
    Button btnPaymentDebit;
    EditText editTextDebitCardNumber, editTextDebitExpiryDate,editTextDebitSecurityNumber;
    int fragFlag;

    // Defining interface
    public interface DebitFragmentListener{
        void validateDebit( String cardNum, String expDate, String cvv);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.debit_fragment, container, false);
        editTextDebitCardNumber = view.findViewById(R.id.editTextDebitCardNumber);
        editTextDebitExpiryDate = view.findViewById(R.id.editTextDebitExpiryDate);
        editTextDebitSecurityNumber = view.findViewById(R.id.editTextDebitSecurityNumber);
        btnPaymentDebit = view.findViewById(R.id.btnPaymentDebit);

        try {
            btnPaymentDebit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cardDebit = editTextDebitCardNumber.getEditableText().toString().trim();
                    String expiryDebit = editTextDebitExpiryDate.getEditableText().toString().trim();
                    String securityCodeDebit = editTextDebitSecurityNumber.getEditableText().toString().trim();

                    // getting inputs and passing to method
                    listener.validateDebit( cardDebit, expiryDebit,securityCodeDebit);
                }
            });
        }
        catch (Exception e ){}
        return view;
    }
    // Method for validating debit card details
    public Integer catchFlagDebit ( String cardNum, String expDate, String cvv){
        fragFlag =0;
        if(cardNum.isEmpty()){
            editTextDebitCardNumber.setError("Can't be empty");
            fragFlag = 0;
        }
        else if (cardNum.length() == 16) {
            editTextDebitCardNumber.setError(null);
            fragFlag= fragFlag+1;
        }
        else {
            editTextDebitCardNumber.setError("Invalid card number!");
            fragFlag = 0;
        }

        if(expDate.isEmpty()){
            editTextDebitExpiryDate.setError("Can't be empty");
            fragFlag = 0;
        }
        else if (expDate.length() == 7) {
            editTextDebitExpiryDate.setError(null);
            fragFlag = fragFlag+1;
        }
        else {
            editTextDebitExpiryDate.setError("Invalid date. Must use / ");
        }

        if(cvv.isEmpty()){
            editTextDebitSecurityNumber.setError("Can't be empty");
            fragFlag = 0;
        }
        else if (cvv.length() == 3) {
            editTextDebitSecurityNumber.setError(null);
            fragFlag = fragFlag+1;
        }
        else {
            editTextDebitSecurityNumber.setError("Invalid security code");
        }

        if(fragFlag == 3){
            btnPaymentDebit.setText("Payment done");
            btnPaymentDebit.setBackgroundColor(Color.rgb(255, 99, 49));
            btnPaymentDebit.setEnabled(false);
        }
        return fragFlag;
    }
    // Two fragment lifecycle methods to sync up input data with fragments
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DebitFragmentListener){
            listener = (DebitFragmentListener) context;

        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
