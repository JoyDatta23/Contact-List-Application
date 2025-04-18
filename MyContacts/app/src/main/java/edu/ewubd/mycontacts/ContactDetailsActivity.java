package edu.ewubd.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private long contactId;

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewHomePhone;
    private TextView textViewOfficePhone;
    private Button btnEditContact;
    private Button btnDeleteContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CONTACT_ID")) {
            contactId = intent.getLongExtra("CONTACT_ID", -1);
        } else {
            finish();
        }

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewHomePhone = findViewById(R.id.textViewHomePhone);
        textViewOfficePhone = findViewById(R.id.textViewOfficePhone);
        btnEditContact = findViewById(R.id.btnEditContact);
        btnDeleteContact = findViewById(R.id.btnDeleteContact);

        loadContactDetails();

        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditContactActivity();
            }
        });

        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });
    }

    private void loadContactDetails() {
        Contact contact = databaseHelper.getContactById(contactId);

        if (contact != null) {
            textViewName.setText(contact.getName());
            textViewEmail.setText(contact.getEmail());
            textViewHomePhone.setText(contact.getHomePhone());
            textViewOfficePhone.setText(contact.getOfficePhone());
        } else {
            finish();
        }
    }

    private void openEditContactActivity() {
        Intent intent = new Intent(this, ContactFormActivity.class);
        intent.putExtra("CONTACT_ID", contactId);
        startActivityForResult(intent, 2);
    }

    private void deleteContact() {
        databaseHelper.deleteContact(contactId);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            loadContactDetails();
        }
    }
}
