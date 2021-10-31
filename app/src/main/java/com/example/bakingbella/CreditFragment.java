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


public class CreditFragment extends Fragment {
    private  CreditFragmentListener listener;
    EditText editTextCardNumber, editTextCardName,editTextExpiryDate,editTextSecurityNumber;
    Button btnPayment;
    int fragFlag;

    // defining interface
    public interface CreditFragmentListener{
        void validateCredit(String name, String cardNum, String expDate, String cvv);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.credit_fragment, container, false);
        editTextCardNumber = view.findViewById(R.id.editTextCardNumber);
        editTextCardName = view.findViewById(R.id.editTextCardName);
        editTextExpiryDate = view.findViewById(R.id.editTextExpiryDate);
        editTextSecurityNumber = view.findViewById(R.id.editTextSecurityNumber);
        btnPayment = view.findViewById(R.id.btnPayment);

        try {
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String card = editTextCardNumber.getEditableText().toString().trim();
                    String name = editTextCardName.getEditableText().toString().trim();
                    String expiry = editTextExpiryDate.getEditableText().toString().trim();
                    String securityCode = editTextSecurityNumber.getEditableText().toString().trim();

                    // getting inputs and passing to method using interface concept
                    listener.validateCredit(name, card, expiry,securityCode);
                }
            });
        }
        catch (Exception e ){}
        return view;
    }
    // Method for validating credit card details
    public Integer catchFlag (String name, String cardNum, String expDate, String cvv){
         fragFlag =0;
         if(cardNum.isEmpty()){
             editTextCardNumber.setError("Can't be empty");
             fragFlag = 0;
         }
         else if (cardNum.length() == 16) {
             editTextCardNumber.setError(null);
             fragFlag= fragFlag+1;
         }
         else {
            editTextCardNumber.setError("Invalid card number!");
            fragFlag = 0;
         }

        if(name.isEmpty()){
            editTextCardName.setError("Can't be empty");
            fragFlag = 0;
        }
        else if (name.length() >= 3) {
            editTextCardName.setError(null);
            fragFlag = fragFlag+1;
        }
        else {
            editTextCardName.setError("Too short name");
        }

        if(expDate.isEmpty()){
            editTextExpiryDate.setError("Can't be empty");
            fragFlag = 0;
        }
        else if (expDate.length() == 7) {
            editTextExpiryDate.setError(null);
            fragFlag = fragFlag+1;
        }
        else {
            editTextExpiryDate.setError("Invalid date. Must use / ");
        }

        if(cvv.isEmpty()){
            editTextSecurityNumber.setError("Can't be empty");
            fragFlag = 0;
        }
        else if (cvv.length() == 3) {
            editTextSecurityNumber.setError(null);
            fragFlag = fragFlag+1;
        }
        else {
            editTextSecurityNumber.setError("Invalid security code");
        }

        if(fragFlag == 4){
             btnPayment.setText("Payment done");
             btnPayment.setBackgroundColor(Color.rgb(255, 99, 49));
             btnPayment.setEnabled(false);
        }
        return fragFlag;
    }

    // Two fragment lifecycle methods to sync up input data with fragments
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreditFragmentListener){
            listener = (CreditFragmentListener) context;

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
