package com.example.bettercharactervote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.bettercharactervote.ui.main.TodorokiFragment;
import com.example.bettercharactervote.ui.main.BakugoFragment;

public class MainActivity extends AppCompatActivity {

    ImageButton todoroki_imagebutt, bakugo_imagebutt;
    View baku_fragment, todo_fragment;
    Button close_frag, vote_todo, vote_baku;
    TextView todo_score, baku_score, textview_element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        todo_fragment = findViewById( R.id.todo_fragment );
        baku_fragment = findViewById( R.id.baku_fragment );
        close_frag = findViewById( R.id.close_frag );
        vote_baku = findViewById( R.id.vote_bakugo );
        vote_todo = findViewById( R.id.vote_todoroki);
        todo_score = findViewById( R.id.score_todoroki );
        baku_score = findViewById( R.id.score_bakugo );

        todo_fragment.setVisibility( View.INVISIBLE );
        baku_fragment.setVisibility( View.INVISIBLE );
        close_frag.setVisibility( View.INVISIBLE );

        todoroki_imagebutt = findViewById( R.id.todoroki_imagebutt );
        bakugo_imagebutt = findViewById( R.id.bakugo_imagebutt);

        todoroki_imagebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote_baku.setVisibility( View.INVISIBLE);
                vote_todo.setVisibility( View.INVISIBLE);
                todo_fragment.setVisibility( View.VISIBLE );
                close_frag.setVisibility( View.VISIBLE );
            }
            });

        bakugo_imagebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote_baku.setVisibility( View.INVISIBLE);
                vote_todo.setVisibility( View.INVISIBLE);
                baku_fragment.setVisibility( View.VISIBLE );
                close_frag.setVisibility( View.VISIBLE );
            }
            });

        close_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baku_fragment.setVisibility( View.INVISIBLE );
                todo_fragment.setVisibility( View.INVISIBLE );
                close_frag.setVisibility( View.INVISIBLE );
                vote_baku.setVisibility( View.VISIBLE);
                vote_todo.setVisibility( View.VISIBLE);

            }
        });

        vote_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setScoreElement("todoroki" );
                putVote("todoroki" );
            }
        });


        vote_baku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println( "VOTE BAKUGO" );
//                String url ="https://user.tjhsst.edu/2020ryoung?name=bakugo";
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                System.out.println( "SCORE IS: " + response );
//                                baku_score.setText("VOTES: "+ response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        baku_score.setText("That didn't work!");
//                    }
//                });
//
//                RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext());
//                requestQueue.add(stringRequest);

                putVote("bakugo" );

            }
        });

        setScoreElement("todoroki" );
        setScoreElement("bakugo" );
    }

    void setScoreElement ( String name ) {
        System.out.println( "MainActivity.setScoreElement     name: " + name );
        String url ="https://user.tjhsst.edu/2020ryoung?name=" + name;
        String element_name = "score_" + name;
        System.out.println( "element_name: " + element_name );
        setScore( element_name, url );
    }


    void setElementText ( final String element_name, String text ) {
        final TextView textview_element = ( TextView ) findViewById( getResources( ).getIdentifier( element_name, "id", getPackageName( ) ) );
        System.out.println( "textview_element: " + textview_element);

        textview_element.setText( text );

    }

    void setScore( final String element_name, String url ) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println( "SCORE IS: " + response );
                        setElementText( element_name, "VOTES: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setElementText( element_name,"Error connecting with backend");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext());
        requestQueue.add(stringRequest);
    }


    void putVote( String name ) {
        System.out.println( "MainActivity.setScoreElement     name: " + name );
        String url ="https://user.tjhsst.edu/2020ryoung?name=" + name;
        String element_name = "score_" + name;
        System.out.println( "element_name: " + element_name );
        final TextView textview_element = ( TextView ) findViewById( getResources( ).getIdentifier( element_name, "id", getPackageName( ) ) );
        System.out.println( "textview_element: " + textview_element);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println( "SCORE IS: " + response );
                    setElementText( element_name, "VOTES: " + response);
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    textview_element.setText("Error connecting with backend");
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext());
        requestQueue.add(stringRequest);
    }
}

