package jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import static jogo.TipoDeItem.VAZIO;

public class BotaoTabuleiro extends JButton {
    private int posX;
    private int posY;
    private boolean escondido;
    private Personagem personagem = null;
    private TipoDeItem item = VAZIO;
    
    
    public BotaoTabuleiro(int i, int j) {
        this.posX = i;
        this.posY = j;
        int x = i * 42;
        int y = (14 - j) * 42; // Ajuste para inverter a ordem das linhas
        this.setBounds(x, y, 42, 42);
        this.setOpaque(true);
        this.setBorderPainted(true);
        this.setBackground(Color.gray);
        this.escondido = true;
        this.addActionListener(e -> {
            BotaoTabuleiro botao = (BotaoTabuleiro) e.getSource();
            System.out.println("\n");
            System.out.println("-----------------------");
            System.out.println("Posicao X: " + botao.getPosX());
            System.out.println("Posicao Y: " + botao.getPosY());
             System.out.println("Escondido: " + botao.escondido);
            System.out.println("Tem Alguem Aqui: " + botao.temAlguemAqui());
//            System.out.println("Tipo de Item: " + botao.());
            System.out.println("------------------------------------");
        });
        
//        if(i+j == 0){
//            escondido = false;
//        }else {
//            escondido = false;
//        }
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public boolean temAlguemAqui() {
       if (this.personagem != null){
           return true;
       }else if(this.item != VAZIO){
           return true;
       }
       return false;
      
    }
    
//    public void adicionarDestaque(Color color) {
//        this.setBackground(color);
//      //  this.temAlguemAqui = true;
//    }
//
//    public void adcionarDestaqueItem(Color color) {
//        this.setBackground(color);
//      //  this.temAlguemAqui = false;
//    }
//
//    public void removerDestaque() {
//        this.setBackground(Color.white);
//        //this.temAlguemAqui = false;
//    }

    
//    public TipoDeItem getTipoDeItem() {
//        return tipo;
//    }
//
//    public void setTipoDeItem(TipoDeItem tipo) {
//        this.tipo = tipo;
//    }
        
    public void setTemAlguemAqui(boolean temAlguemAqui) {
      //  this.temAlguemAqui = temAlguemAqui;
    }
    
    public boolean getEscondido(){
        return escondido;
    }
    
    public void setEscondido(boolean escondido){
        this.escondido = escondido;
    }
    
    public void adicionarPersonagem(Personagem personagem, Color color){
       this.personagem = personagem;
       
       if(personagem.getClass().isInstance(new Jogador(0))){
           this.setBackground(color);
           this.escondido = false;
       }else if(this.escondido = false){
           this.setBackground(color);
       }
     
    }
    
    public void removerPersonagem(){
       if(personagem.getClass().isInstance(new Jogador(0))){
           this.setBackground(Color.WHITE);
           this.escondido = false;
           System.out.println("Personagem: " + Personagem.class);
       }else if(escondido == false){
           this.setBackground(Color.WHITE);
       } 
       this.personagem = null;
    }
    
    public Personagem retornarPersonagem (){
       return this.personagem;
    }
    
    public void adicionarItem(TipoDeItem item, Color color){
        this.item = item;
        
        if(escondido == false){
            this.setBackground(color);
        }
        
    }
    
    public void removerItem(){
        this.item = null;
        setBackground(Color.WHITE);
    }
    
    public TipoDeItem retornarItem (){
        return this.item;
    }
}
