package com.example.koddevchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.koddevchat.Adapter.MessageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.List;

public class GameActivity extends AppCompatActivity   {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, leavegame;
    boolean chose1, chose2, chose3, chose4, chose5, chose6, chose7, chose8, chose9;
    int turn = 1;
    boolean creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn1 = (Button) findViewById(R.id.button_00);
        btn2 = (Button) findViewById(R.id.button_01);
        btn3 = (Button) findViewById(R.id.button_02);
        btn4 = (Button) findViewById(R.id.button_10);
        btn5 = (Button) findViewById(R.id.button_11);
        btn6 = (Button) findViewById(R.id.button_12);
        btn7 = (Button) findViewById(R.id.button_20);
        btn8 = (Button) findViewById(R.id.button_21);
        btn9 = (Button) findViewById(R.id.button_22);
        leavegame = (Button) findViewById(R.id.leavegame);




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn == 1){

                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        leavegame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Leave Game");
                builder.setMessage("Are you sure you want to leave the game? You may not be able to join back.");

                builder.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Chats");
                        if(creator){
                            reff.child(MessageActivity.gameid).removeValue();
                            MessageAdapter.gamecreated = false;
                            MessageActivity.gameid="";
                        }
                        Intent i2 = new Intent(GameActivity.this, MessageActivity.class);
                        i2.putExtra("userid", MessageActivity.userid);
                        startActivity(i2);
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}