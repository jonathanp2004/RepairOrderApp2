package com.example.repairorderapp2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    // create edit text

    EditText paintET;
    EditText partsET;
    EditText laborET;
    EditText inspectET;
    TextView subTotalTV; //initialize text view
    TextView taxTV;
    TextView totalTV;
    Button submitB; // create button
    Spinner orderSpinner; // create spinner
    String selectedOrderType; // variable to hold selected order type
    ArrayList<String> orderTypes;


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


        paintET = findViewById(R.id.paintTE);

        partsET = findViewById(R.id.partTE);

        laborET = findViewById(R.id.laborTE);

        inspectET = findViewById(R.id.inspectionTE);

        orderSpinner = findViewById(R.id.orderSpinner);

        orderTypes = new ArrayList<>(Arrays.asList(
                "Maintenance", "Repair", "Inspection", "Restock", "Installation", "Deinstallation"
        ));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, orderTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);

        //Spinner setup
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.ot_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        orderSpinner.setAdapter(adapter);*/

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                selectedOrderType = parent.getItemAtPosition(position).toString();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
            });


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