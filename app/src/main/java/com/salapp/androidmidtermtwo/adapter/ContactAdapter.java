package com.salapp.androidmidtermtwo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.salapp.androidmidtermtwo.ModifyContactActivity;
import com.salapp.androidmidtermtwo.R;
import com.salapp.androidmidtermtwo.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts;

    private List<Contact> contactsTemp = new ArrayList<>();
    private Context context;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
        this.contactsTemp.addAll(contacts);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View contactView = layoutInflater.inflate(R.layout.contact_row, parent, false);
        RecyclerView.ViewHolder viewHolder = new ContactViewHolder(contactView);
        return (ContactViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts.size() > 0) {
            holder.contactTxtView.setText(contacts.get(position).getName());
            holder.contactPhoneView.setText(contacts.get(position).getPhoneNumber());

            holder.contactCardView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), ModifyContactActivity.class);
                intent.putExtra("id", contacts.get(position).getId());
                intent.putExtra("name", contacts.get(position).getName());
                intent.putExtra("phone", contacts.get(position).getPhoneNumber());
                intent.putExtra("email", contacts.get(position).getEmail());
                intent.putExtra("address", contacts.get(position).getAddress());

                ((Activity) context).startActivityForResult(intent, 1);

            });
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void filter(String text) {
        text = text.toLowerCase();
        contacts.clear();

        if (text.length() == 0) {
            contacts.addAll(contactsTemp);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(text);
            contacts = contactsTemp.stream().filter(contact -> contact.getName().toLowerCase().contains(sb))
                    .collect(Collectors.toList());

        }
        notifyDataSetChanged();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView contactTxtView;
        private TextView contactPhoneView;
        private CardView contactCardView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactCardView = itemView.findViewById(R.id.contactCard);
            contactTxtView = itemView.findViewById(R.id.contactNameCard);
            contactPhoneView = itemView.findViewById(R.id.contactPhoneCard);
        }
    }
}
