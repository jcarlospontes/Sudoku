package com.example.sudoku.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudoku.Celula;
import com.example.sudoku.Matriz;
import com.example.sudoku.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button botaopause, botaosair;

    private Button botaoapp1, botaoapp2, botaoapp3, botaoapp4,botaoapp5, botaoapp6,botaoapp7, botaoapp8,botaoapp9, botaoapplimp, selecionado;

    private boolean perdeu = false;

    private View decorView, layout;

    public boolean pausado = false;

    Matriz matriz = new Matriz();

    Button botaomatriz[][] = new Button[9][9];

    String tempo;



    int min, seg = 0;

    Thread tempoJogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startGameThread();
        ligarJogo();

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        layout = (GridLayout) findViewById(R.id.LayoutSudoku);
        botaopause = (Button) findViewById(R.id.BotaoAlterna);
        botaosair = (Button) findViewById(R.id.BotaoSair);
        botaopause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!pausado){
                    botaopause.setBackgroundResource(R.drawable.retomar);
                    pausado = true;
                    layout.setVisibility(View.INVISIBLE);
                }
                else{
                    botaopause.setBackgroundResource(R.drawable.pausar);
                    pausado = false;
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });
        botaosair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                botaopause.callOnClick();
                AlertDialog.Builder confirmasair = new AlertDialog.Builder(MainActivity.this);
                confirmasair.setTitle("Atenção");
                confirmasair.setMessage("Deseja sair do jogo atual?");
                confirmasair.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                        System.exit(0);
                    }
                });
                confirmasair.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        botaopause.callOnClick();
                    }
                });
                confirmasair.show();
            }
        });


    }

    public void startGameThread() {
        tempoJogo = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        while(true) {
                            wait(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!pausado) {
                                        seg += 1;
                                        if (seg == 61) {
                                            min += 1;
                                            seg = 0;
                                        }
                                    }
                                    TextView text = (TextView) findViewById(R.id.StringRelogio);
                                    tempo = String.format("%02d:%02d",min,seg);
                                    text.setText(tempo);
                                }
                            });
                        }
                    }
                } catch (Exception e) {

                }
            };
        };
        tempoJogo.start();
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    public void abrirActivityMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }


    public void ligarJogo(){
        botaomatriz[0][0] = (Button) findViewById(R.id.button00);
        botaomatriz[0][1] = (Button) findViewById(R.id.button01);
        botaomatriz[0][2] = (Button) findViewById(R.id.button02);
        botaomatriz[0][3] = (Button) findViewById(R.id.button03);
        botaomatriz[0][4] = (Button) findViewById(R.id.button04);
        botaomatriz[0][5] = (Button) findViewById(R.id.button05);
        botaomatriz[0][6] = (Button) findViewById(R.id.button06);
        botaomatriz[0][7] = (Button) findViewById(R.id.button07);
        botaomatriz[0][8] = (Button) findViewById(R.id.button08);

        botaomatriz[1][0] = (Button) findViewById(R.id.button10);
        botaomatriz[1][1] = (Button) findViewById(R.id.button11);
        botaomatriz[1][2] = (Button) findViewById(R.id.button12);
        botaomatriz[1][3] = (Button) findViewById(R.id.button13);
        botaomatriz[1][4] = (Button) findViewById(R.id.button14);
        botaomatriz[1][5] = (Button) findViewById(R.id.button15);
        botaomatriz[1][6] = (Button) findViewById(R.id.button16);
        botaomatriz[1][7] = (Button) findViewById(R.id.button17);
        botaomatriz[1][8] = (Button) findViewById(R.id.button18);

        botaomatriz[2][0] = (Button) findViewById(R.id.button20);
        botaomatriz[2][1] = (Button) findViewById(R.id.button21);
        botaomatriz[2][2] = (Button) findViewById(R.id.button22);
        botaomatriz[2][3] = (Button) findViewById(R.id.button23);
        botaomatriz[2][4] = (Button) findViewById(R.id.button24);
        botaomatriz[2][5] = (Button) findViewById(R.id.button25);
        botaomatriz[2][6] = (Button) findViewById(R.id.button26);
        botaomatriz[2][7] = (Button) findViewById(R.id.button27);
        botaomatriz[2][8] = (Button) findViewById(R.id.button28);

        botaomatriz[3][0] = (Button) findViewById(R.id.button30);
        botaomatriz[3][1] = (Button) findViewById(R.id.button31);
        botaomatriz[3][2] = (Button) findViewById(R.id.button32);
        botaomatriz[3][3] = (Button) findViewById(R.id.button33);
        botaomatriz[3][4] = (Button) findViewById(R.id.button34);
        botaomatriz[3][5] = (Button) findViewById(R.id.button35);
        botaomatriz[3][6] = (Button) findViewById(R.id.button36);
        botaomatriz[3][7] = (Button) findViewById(R.id.button37);
        botaomatriz[3][8] = (Button) findViewById(R.id.button38);

        botaomatriz[4][0] = (Button) findViewById(R.id.button40);
        botaomatriz[4][1] = (Button) findViewById(R.id.button41);
        botaomatriz[4][2] = (Button) findViewById(R.id.button42);
        botaomatriz[4][3] = (Button) findViewById(R.id.button43);
        botaomatriz[4][4] = (Button) findViewById(R.id.button44);
        botaomatriz[4][5] = (Button) findViewById(R.id.button45);
        botaomatriz[4][6] = (Button) findViewById(R.id.button46);
        botaomatriz[4][7] = (Button) findViewById(R.id.button47);
        botaomatriz[4][8] = (Button) findViewById(R.id.button48);

        botaomatriz[5][0] = (Button) findViewById(R.id.button50);
        botaomatriz[5][1] = (Button) findViewById(R.id.button51);
        botaomatriz[5][2] = (Button) findViewById(R.id.button52);
        botaomatriz[5][3] = (Button) findViewById(R.id.button53);
        botaomatriz[5][4] = (Button) findViewById(R.id.button54);
        botaomatriz[5][5] = (Button) findViewById(R.id.button55);
        botaomatriz[5][6] = (Button) findViewById(R.id.button56);
        botaomatriz[5][7] = (Button) findViewById(R.id.button57);
        botaomatriz[5][8] = (Button) findViewById(R.id.button58);

        botaomatriz[6][0] = (Button) findViewById(R.id.button60);
        botaomatriz[6][1] = (Button) findViewById(R.id.button61);
        botaomatriz[6][2] = (Button) findViewById(R.id.button62);
        botaomatriz[6][3] = (Button) findViewById(R.id.button63);
        botaomatriz[6][4] = (Button) findViewById(R.id.button64);
        botaomatriz[6][5] = (Button) findViewById(R.id.button65);
        botaomatriz[6][6] = (Button) findViewById(R.id.button66);
        botaomatriz[6][7] = (Button) findViewById(R.id.button67);
        botaomatriz[6][8] = (Button) findViewById(R.id.button68);

        botaomatriz[7][0] = (Button) findViewById(R.id.button70);
        botaomatriz[7][1] = (Button) findViewById(R.id.button71);
        botaomatriz[7][2] = (Button) findViewById(R.id.button72);
        botaomatriz[7][3] = (Button) findViewById(R.id.button73);
        botaomatriz[7][4] = (Button) findViewById(R.id.button74);
        botaomatriz[7][5] = (Button) findViewById(R.id.button75);
        botaomatriz[7][6] = (Button) findViewById(R.id.button76);
        botaomatriz[7][7] = (Button) findViewById(R.id.button77);
        botaomatriz[7][8] = (Button) findViewById(R.id.button78);

        botaomatriz[8][0] = (Button) findViewById(R.id.button80);
        botaomatriz[8][1] = (Button) findViewById(R.id.button81);
        botaomatriz[8][2] = (Button) findViewById(R.id.button82);
        botaomatriz[8][3] = (Button) findViewById(R.id.button83);
        botaomatriz[8][4] = (Button) findViewById(R.id.button84);
        botaomatriz[8][5] = (Button) findViewById(R.id.button85);
        botaomatriz[8][6] = (Button) findViewById(R.id.button86);
        botaomatriz[8][7] = (Button) findViewById(R.id.button87);
        botaomatriz[8][8] = (Button) findViewById(R.id.button88);

        botaoapp1 = (Button) findViewById(R.id.BotaoUm);
        botaoapp1.setOnClickListener(this);
        botaoapp2 = (Button) findViewById(R.id.BotaoDois);
        botaoapp2.setOnClickListener(this);
        botaoapp3 = (Button) findViewById(R.id.BotaoTres);
        botaoapp3.setOnClickListener(this);
        botaoapp4 = (Button) findViewById(R.id.BotaoQuatro);
        botaoapp4.setOnClickListener(this);
        botaoapp5 = (Button) findViewById(R.id.BotaoCinco);
        botaoapp5.setOnClickListener(this);
        botaoapp6 = (Button) findViewById(R.id.BotaoSeis);
        botaoapp6.setOnClickListener(this);
        botaoapp7 = (Button) findViewById(R.id.BotaoSete);
        botaoapp7.setOnClickListener(this);
        botaoapp8 = (Button) findViewById(R.id.BotaoOito);
        botaoapp8.setOnClickListener(this);
        botaoapp9 = (Button) findViewById(R.id.BotaoNove);
        botaoapp9.setOnClickListener(this);
        botaoapplimp = (Button) findViewById(R.id.BotaoLimpar);
        botaoapplimp.setOnClickListener(this);




        for(int x = 0; x<9; x++){
            for(int y = 0; y<9;y++){
                botaomatriz[x][y].setOnClickListener(this);
                if(!matriz.getCelulaMatriz(x, y).isAtivado()){
                    switch (matriz.getCelulaMatriz(x,y).getValor()){
                        case 1:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis1);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 2:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis2);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 3:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis3);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 4:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis4);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 5:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis5);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 6:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis6);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 7:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis7);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 8:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis8);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                        case 9:
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btdis9);
                            botaomatriz[x][y].setBackgroundTintMode(null);
                            break;
                    }
                }
                else{
                    botaomatriz[x][y].setBackgroundResource(R.drawable.vazio);
                    switch (matriz.getCelulaMatriz(x,y).getValor()){
                        case 1:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt1));
                            break;
                        case 2:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt2));
                            break;
                        case 3:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt3));
                            break;
                        case 4:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt4));
                            break;
                        case 5:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt5));
                            break;
                        case 6:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt6));
                            break;
                        case 7:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt7));
                            break;
                        case 8:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt8));
                            break;
                        case 9:
                            matriz.getCelulaMatriz(x,y).setBack(getDrawable(R.drawable.bt9));
                            break;
                    }
                }
            }
        }
    }

    public Button retornaSelecionado() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (matriz.getCelulaMatriz(x, y).isSelecionado()) {
                    return botaomatriz[x][y];
                }
            }
        }
        return null;
    }

    public void gameOver(boolean ganhou){
        if(ganhou){
            AlertDialog.Builder mensagemganhou = new AlertDialog.Builder(MainActivity.this);
            findViewById(R.id.BotaoAlterna).callOnClick();
            mensagemganhou.setTitle("Parabêns!!");
            mensagemganhou.setMessage("Você ganhou o jogo em "+tempo+", deseja jogar novamente?");
            mensagemganhou.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            mensagemganhou.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                    System.exit(0);
                }
            });
            mensagemganhou.show();
        }
        else{
            AlertDialog.Builder mensagemperdeu = new AlertDialog.Builder(MainActivity.this);
            mensagemperdeu.setTitle("Que pena");
            mensagemperdeu.setMessage("O seu resultado está errado");
            mensagemperdeu.show();
            selecionado.setBackgroundResource(R.drawable.vazio);
        }
    }

    public boolean verificaMatriz(){
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if(matriz.getCelulaMatriz(x,y).isAtivado()){
                    if(!areDrawablesIdentical(botaomatriz[x][y].getBackground(), matriz.getCelulaMatriz(x,y).getBack())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void verificaFim(){
        boolean cabo = true;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getResources().getDrawable(R.drawable.vazio)) || areDrawablesIdentical(botaomatriz[x][y].getBackground(),getResources().getDrawable(R.drawable.vaziosel))){
                    cabo = false;
                }
            }
        }
        if(cabo){
            selecionado = retornaSelecionado();
            desativaSelecionado();
            gameOver(verificaMatriz());
        }
    }

    public void desativaSelecionado(){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9;j++){
                if(matriz.getCelulaMatriz(i,j).isSelecionado()){
                    matriz.getCelulaMatriz(i,j).setSelecionado(false);
                    if (areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel1))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt1);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel2))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt2);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel3))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt3);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel4))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt4);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel5))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt5);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel6))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt6);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel7))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt7);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel8))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt8);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.btsel9))) {
                        botaomatriz[i][j].setBackgroundResource(R.drawable.bt9);
                    }
                    else if(areDrawablesIdentical(botaomatriz[i][j].getBackground(),getResources().getDrawable(R.drawable.vaziosel))){
                        botaomatriz[i][j].setBackgroundResource(R.drawable.vazio);
                    }
                }
            }
        }
    }


    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        for(int x = 0; x<9; x++){
            for(int y = 0; y<9;y++){
                if(view.getId() == botaomatriz[x][y].getId()){
                    if(matriz.getCelulaMatriz(x,y).isAtivado()){
                        desativaSelecionado();
                        matriz.getCelulaMatriz(x,y).setSelecionado(true);
                        if (areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt1))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel1);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt2))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel2);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt3))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel3);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt4))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel4);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt5))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel5);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt6))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel6);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt7))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel7);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt8))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel8);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.bt9))) {
                            botaomatriz[x][y].setBackgroundResource(R.drawable.btsel9);
                        }
                        else if(areDrawablesIdentical(botaomatriz[x][y].getBackground(),getDrawable(R.drawable.vazio))){
                            botaomatriz[x][y].setBackgroundResource(R.drawable.vaziosel);
                        }
                    }
                }
            }
        }

        if(view.getId() == R.id.BotaoUm){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel1);

        }
        else if(view.getId() == R.id.BotaoDois){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel2);

        }
        else if(view.getId() == R.id.BotaoTres){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel3);

        }
        else if(view.getId() == R.id.BotaoQuatro){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel4);

        }
        else if(view.getId() == R.id.BotaoCinco){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel5);

        }
        else if(view.getId() == R.id.BotaoSeis){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel6);

        }
        else if(view.getId() == R.id.BotaoSete){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel7);

        }
        else if(view.getId() == R.id.BotaoOito){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel8);

        }
        else if(view.getId() == R.id.BotaoNove){
            retornaSelecionado().setBackgroundResource(R.drawable.btsel9);

        }
        else if(view.getId() == R.id.BotaoLimpar){
            retornaSelecionado().setBackgroundResource(R.drawable.vaziosel);

        }
            verificaFim();

    }




}