package com.example.tic_tac_toe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //2D Array
    private Button[][] buttons = new Button[3][3];

    //Input_variables
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView Player_1;
    private TextView Player_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference variables from xml variables
        Player_1 = findViewById(R.id.Player_1_text);
        Player_2 = findViewById(R.id.Player_2_text);

        //Assigning Button array with the main buttons
        for(int i = 0; i < 3; i++){
            for( int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        //Assigns reset button with a code.
        Button buttonReset = findViewById(R.id.Reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    //
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        //Players inputs
        if(player1Turn){
            ((Button)v).setText(("X"));
        } else{
            ((Button)v).setText(("O"));
        }

        //next turn
        roundCount++;

        //Check who won the match
        if(checkForWin()) {
            if(player1Turn){
                player1Wins();
            }else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        }else {
            player1Turn = !player1Turn;
        }
    }

    //
    private  boolean checkForWin(){
        String[][] field = new String[3][3];

        
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //Row Checker
        for(int i = 0; i < 3 ; i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        //Columns Checker
        for(int i = 0; i < 3 ; i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        //Left - Right Diagonal Checker
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        //Right - Left Diagonal Checker
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    //Cheers player 1 and add win points
    private void player1Wins(){
        player1Points++;
        Toast.makeText(this,"Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoards();
    }

    //Cheers player 2 and add win points
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this,"Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoards();
    }


    private void draw(){
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        resetBoards();
    }

    //Updates players points in the screen
    private void updatePointsText(){
        Player_1.setText("Player 1: " + player1Points);
        Player_2.setText("Player 2: " + player2Points);
    }

    //reset the tiles
    private void resetBoards(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    //Restart the game
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        resetBoards();
        updatePointsText();
    }

    //Save data into Save Instances
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1turn",player1Turn);
    }

    //Get data from Save Instances
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1turn");
    }
}