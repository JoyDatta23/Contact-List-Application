package edu.ewubd.mycontacts;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private List<Contact> contactList;
    private ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        databaseHelper = new DatabaseHelper(this);

        ListView listViewContacts = findViewById(R.id.listViewContacts);

        contactList = databaseHelper.getAllContacts();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);

        listViewContacts.setAdapter(adapter);

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = contactList.get(position);
                openContactDetailsActivity(selectedContact.getId());
            }
        });
        findViewById(R.id.btnAddContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactFormActivity();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    private void openContactDetailsActivity(long contactId) {
        Intent intent = new Intent(this, ContactDetailsActivity.class);
        intent.putExtra("CONTACT_ID", contactId);
        startActivity(intent);
    }

    private void openContactFormActivity() {
        Intent intent = new Intent(this, ContactFormActivity.class);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            contactList.clear();
            contactList.addAll(databaseHelper.getAllContacts());
            adapter.notifyDataSetChanged();
        }
    }
}
