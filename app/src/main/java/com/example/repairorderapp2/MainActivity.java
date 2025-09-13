package com.example.repairorderapp2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {



    EditText orderNumberET; // create edit text

    EditText paintET;

    EditText partsET;

    EditText laborET;

    EditText inspectET;

    TextView subTotalTV; //initialize text view

    TextView taxTV;

    TextView totalTV;

    Button submitB; // create button

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            computeAndDisplay();


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //Output TextViews
        subTotalTV = findViewById(R.id.subTV);

        taxTV = findViewById(R.id.taxValueTV);

        totalTV = findViewById(R.id.totalValueTV);



        //Input EditTexts
        orderNumberET = findViewById(R.id.orderTE);

        paintET = findViewById(R.id.paintTE);

        partsET = findViewById(R.id.partTE);

        laborET = findViewById(R.id.laborTE);

        inspectET = findViewById(R.id.inspectionTE);

        //Submit Button
        submitB = findViewById(R.id.submitButton);

        submitB.setOnClickListener(buttonListener);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    //formats double to money string
    private double parseMoney(EditText et)
    {
        if (et == null) return 0.0;
        String r = et.getText().toString();
        if(r.startsWith("$")) r = r.substring(1).trim();
        if(r.isEmpty()) return 0.0;
        try{ return Double.parseDouble(r);}
        catch (NumberFormatException e) { return 0.0; }

    }
    private String formatMoney(double d)
    {
        return String.format("$%.2f", d);
    }

    private void computeAndDisplay()
    {
        double pv = parseMoney(partsET);
        double lv = parseMoney(laborET);
        double iv = parseMoney(inspectET);
        double pt = parseMoney(paintET);

        double subtotal = pt + iv + pv + lv;
        double tax = subtotal * 0.13;
        double total = subtotal + tax;

        ((TextView) findViewById(R.id.valueTV)).setText(formatMoney(subtotal));
        ((TextView) findViewById(R.id.taxValueTV)).setText(formatMoney(tax));
        ((TextView) findViewById(R.id.totalValueTV)).setText(formatMoney(total));
    }





}