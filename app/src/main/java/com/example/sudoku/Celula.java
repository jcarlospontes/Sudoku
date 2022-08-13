package com.example.sudoku;

import android.graphics.drawable.Drawable;

public class Celula {
    private int valor;
    private boolean ativado;
    private boolean selecionado;
    private Drawable back;

    public Celula(){
        this.valor = 0;
        this.ativado = true;
        this.selecionado = false;
        this.back = null;
    }

    public void setBack(Drawable back){
        this.back = back;
    }
    public Drawable getBack(){
        return this.back;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isAtivado() {
        return ativado;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }

    public void setSelecionado(boolean selecionar){
        this.selecionado = selecionar;
    }

    public boolean isSelecionado(){
        return this.selecionado;
    }

    public void setAtivar(){
        this.ativado = true;
    }
}
