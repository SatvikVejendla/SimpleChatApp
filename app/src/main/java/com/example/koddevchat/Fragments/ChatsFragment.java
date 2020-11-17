package com.example.koddevchat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.koddevchat.Adapter.UserAdapter;
import com.example.koddevchat.Model.Chat;
import com.example.koddevchat.Model.User;
import com.example.koddevchat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    FloatingActionButton addfriend, newchat, addgroup;

    private List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        newchat = view.findViewById(R.id.newchat);
        addgroup = view.findViewById(R.id.addgroup);
        addfriend = view.findViewById(R.id.addfriend);
        newchat = view.findViewById(R.id.newchat);
        addgroup.setVisibility(View.GONE);
        addfriend.setVisibility(View.GONE);

        newchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addgroup.getVisibility() == View.GONE) {
                    Animation animation = new TranslateAnimation(0, 0,420, 0);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    addgroup.startAnimation(animation);
                    addgroup.setVisibility(View.VISIBLE);

                    Animation animation2 = new TranslateAnimation(0, 0,210, 0);
                    animation2.setDuration(200);
                    animation2.setFillAfter(true);
                    addfriend.startAnimation(animation2);
                    addfriend.setVisibility(View.VISIBLE);
                } else {
                    Animation animation = new TranslateAnimation(0, 0,0, 420);
                    animation.setDuration(300);
                    animation.setFillAfter(true);
                    addgroup.startAnimation(animation);
                    addgroup.setVisibility(View.GONE);

                    Animation animation2 = new TranslateAnimation(0, 0,0, 210);
                    animation2.setDuration(300);
                    animation2.setFillAfter(true);
                    addfriend.startAnimation(animation2);
                    addfriend.setVisibility(View.GONE);
                }
            }
        });

        usersList = new ArrayList<>();///////////create animation for add friend/group popping up on the screen when you click the plus

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for(DataSnapshot sh: snapshot.getChildren()){
                    Chat chat = sh.getValue(Chat.class);

                    if(chat.getSender().equals(fuser.getUid())){
                        usersList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(fuser.getUid())){
                        usersList.add(chat.getSender());
                    }

                    readChats();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    private void readChats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for(DataSnapshot sh: snapshot.getChildren()){
                    User user = sh.getValue(User.class);

                    for(String id : usersList){
                        if(user.getId().equals(id)){
                            if(mUsers.size() != 0){
                                for(int i = 0; i < mUsers.size(); i++){
                                    User user1 = mUsers.get(i);
                                    if(!user.getId().equals(user1.getId())){
                                        mUsers.add(user);
                                    }
                                }
                            } else {
                                mUsers.add(user);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}