package com.example.bakingbella;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.CachedHashCodeArrayMap;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class Checkout extends AppCompatActivity implements CreditFragment.CreditFragmentListener, DebitFragment.DebitFragmentListener {
    EditText editTextFullName, editTextStreet, editTextCity, editTextProvince, editTextPostalAddress;
    TextView txtProductName, txtProductQuantity, txtAmount, txtTax, txtTotalAmount;
    Switch switchSaveCard;
    Button btnPlaceOrder;
    RadioGroup radioGroup;
    String username;
    Fragment fragment;
    private CreditFragment creditFragment;
    private DebitFragment debitFragment;
    public Integer flagValue;
    Integer radioFlag = 0;
    DatabaseReference mReference;
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextCity = findViewById(R.id.editTextCity);
        editTextProvince = findViewById(R.id.editTextProvince);
        editTextPostalAddress = findViewById(R.id.editTextPostalAddress);
        radioGroup = findViewById(R.id.radioGroup);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

//        txtProductName = findViewById(R.id.txtProductName);
//        txtProductQuantity = findViewById(R.id.txtProductQuantity);
        txtAmount = findViewById(R.id.txtAmount);
        txtTax = findViewById(R.id.txtTax);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        Intent checkoutIntent = getIntent();
        Integer total = checkoutIntent.getIntExtra("total",0);
        username  = checkoutIntent.getStringExtra("username");
        txtAmount.setText("$"+total);
//        Intent myIntent = getIntent();
//        String title = myIntent.getStringExtra("title");
//        String quantity = myIntent.getStringExtra("quantity");
//        Float amount = myIntent.getFloatExtra("price", 0);
//        Integer image = myIntent.getIntExtra("image", -1);
//
//        Float amountSum = total * (Integer.parseInt(quantity));
        Float tax =Float.parseFloat( total.toString()) * 13 / 100;
        Float totalAmount = total + tax;
//
//        txtProductName.setText(title);
//        txtProductQuantity.setText(quantity);
//        txtAmount.setText("$" + String.valueOf(amountSum));
        txtTax.setText("$" + df.format(tax));
        txtTotalAmount.setText("$" + df.format(totalAmount));

        // getting which radio button is clicked
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioBtnCredit:
                        radioFlag = 1;
                        creditFragment = new CreditFragment();
                        fragment = creditFragment;
                        break;
                    case R.id.radioBtnDebit:
                        radioFlag = 2;
                        debitFragment = new DebitFragment();
                        fragment = debitFragment;
                        break;
                    case R.id.radioBtnCash:
                        fragment = new CashFragment();
                        radioFlag = 0;
                        break;
                    default:
                        Log.i("default", "nothing");
                }
                // displaying appropriate fragment according to radio button selection
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        Intent intent = new Intent(Checkout.this, OrderDetails.class);
        try {
            btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    String fullName = editTextFullName.getEditableText().toString().trim();
                    String streetAddress = editTextStreet.getEditableText().toString().trim();
                    String city = editTextCity.getEditableText().toString().trim();
                    String province = editTextProvince.getEditableText().toString().trim();
                    String postalCode = editTextPostalAddress.getEditableText().toString().trim();

                    // checking if input field is empty or not
                    boolean name = isEmptyMethod(fullName, editTextFullName);
                    boolean street = isEmptyMethod(streetAddress, editTextStreet);
                    boolean citi = isEmptyMethod(city, editTextCity);
                    boolean state = isEmptyMethod(province, editTextProvince);
                    boolean postal = isEmptyMethod(postalCode, editTextPostalAddress);

                    if (name && street && citi && state && postal) {
//                        intent.putExtra("title", title);
//                        intent.putExtra("quantity", quantity);
//                        intent.putExtra("price", total);
//                        intent.putExtra("image", image);
                        if (radioFlag == 0 || (radioFlag == 1 && flagValue == 4) || (radioFlag == 2 && flagValue == 3)) {
//                            startActivity(intent);
                            Log.i("place order clickeddd","true");
                            AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
                            builder.setMessage("Your Order has confirmed. Thank you!");
                            builder.setTitle("Order Confirmation");
                            builder.setCancelable(false);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    emptyCart();
                                    Intent intentOk = new Intent(Checkout.this, MainActivity.class);
                                    intentOk.putExtra("username",username);
                                    startActivity(intentOk);
                                }
                            });
                            AlertDialog alertDialog = builder.create();

                            // Show the Alert Dialog box
                            alertDialog.show();
                        }
                    } else {
                    }
                }
            });
        } catch (Exception exception) {
            Log.i("Exception", exception + "");
            return;
        }
    }

    private void emptyCart() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CartList").child("UserView").child(username).child("Products");
        Query checkFood = ref.orderByChild("foodName");
        checkFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                    foodSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.e("onCancelled", databaseError.toException());
            }
        });
    }

    // Custom method
    private boolean isEmptyMethod(String input, EditText editText) {
        if (input.isEmpty()) {
            editText.setError("Field can't be empty!");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    // Implementing methods of both interfaces and calling methods
    @Override
    public void validateCredit(String name, String cardNum, String expDate, String cvv) {
        flagValue = creditFragment.catchFlag(name, cardNum, expDate, cvv);
    }

    @Override
    public void validateDebit(String cardNum, String expDate, String cvv) {
        flagValue = debitFragment.catchFlagDebit(cardNum, expDate, cvv);
    }
}
