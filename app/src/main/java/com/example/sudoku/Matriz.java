package com.example.sudoku;

public class Matriz {
    private Celula[][] celulas = new Celula[9][9];

    int varias = 50;

    public Matriz(){
        for(int x = 0; x<9; x++){
            for(int y = 0; y<9;y++){
                celulas[x][y] = new Celula();
            }
        }
        preencheJogo();
    }

    public Celula getCelulaMatriz(int x, int y){
        return celulas[x][y];
    }

    public void printJogo(){
        for (int i = 0; i<9; i++)
        {
            for (int j = 0; j<9; j++)
                System.out.print(celulas[i][j].getValor() + " ");
            System.out.println();
        }
        System.out.println();
    }


    public void preencheDiagonal(){
        for(int i = 0; i< 9; i=i+3){
            preencheBloco(i, i);
        }
    }

    public void preencheBloco(int linha, int coluna){
        int nm;
        for(int x = 0; x<3; x++){
            for(int y = 0; y<3; y++){
                do{
                    nm = geraRandom(9);
                }
                while (!naoRepetido(linha,coluna,nm));

                celulas[linha+x][coluna+y].setValor(nm);
            }
        }
    }

    public boolean naoRepetido(int linhainicial, int colunainicial, int num)
    {
        for (int i = 0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                if (celulas[linhainicial + i][colunainicial + j].getValor() == num)
                    return false;
            }
        }
        return true;
    }

    public int geraRandom(int x){
        return (int) Math.floor((Math.random()*x+1));
    }

    public void preencheJogo()
    {

        preencheDiagonal();


        preencheRestante(0, 3);


        desativaNCelulas();
    }

    public void desativaNCelulas(){
        int count = varias;
        while (count != 0) {
            int cellId = geraRandom(9*9)-1;

            int i = (cellId/9);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            if (celulas[i][j].isAtivado()) {
                count--;
                celulas[i][j].setAtivado(false);
            }
        }
    }

    public boolean checaSeguro(int i, int j, int num){
        return (naoUsadaNaLinha(i, num) &&
                naoUsadaNaColuna(j, num) &&
                naoUsadaNoBloco(i-i%3, j-j%3, num));
    }

    public boolean naoUsadaNaLinha(int i, int num){
        for (int j = 0; j<9; j++)
            if (celulas[i][j].getValor() == num)
                return false;
        return true;
    }

    public boolean naoUsadaNaColuna(int j, int num){
        for (int i = 0; i<9; i++)
            if (celulas[i][j].getValor() == num)
                return false;
        return true;
    }

    public boolean naoUsadaNoBloco(int linhainicial, int colunainicial, int num){
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++)
                if (celulas[linhainicial+i][colunainicial+j].getValor()==num)
                    return false;

        return true;
    }



    public boolean preencheRestante(int i, int j){
        if (j>=9 && i<9-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=9 && j>=9)
            return true;

        if (i < 3)
        {
            if (j < 3)
                j = 3;
        }
        else if (i < 9-3)
        {
            if (j==(int)(i/3)*3)
                j =  j + 3;
        }
        else
        {
            if (j == 9-3)
            {
                i = i + 1;
                j = 0;
                if (i>=9)
                    return true;
            }
        }

        for (int num = 1; num<=9; num++)
        {
            if (checaSeguro(i, j, num))
            {
                celulas[i][j].setValor(num);
                if (preencheRestante(i, j+1))
                    return true;

                celulas[i][j].setValor(0);
            }
        }
        return false;
    }


}
