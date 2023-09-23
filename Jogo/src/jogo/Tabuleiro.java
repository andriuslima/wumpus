package jogo;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Tabuleiro extends JFrame {
    private static final Color COR_DESTAQUE_POCO = Color.BLUE;
    private static final Color COR_DESTAQUE_MADEIRA = Color.GREEN;
    private static final Color COR_DESTAQUE_OURO = Color.MAGENTA;
    private static final Color COR_DESTAQUE_JOGADOR = Color.YELLOW;
    private static final Color COR_DESTAQUE_MONSTRO = Color.BLACK;
    private static final Color COR_DESTAQUE_MONSTRO_RAPIDO = Color.RED;
    private final BotaoTabuleiro[][] botoesTabuleiro = new BotaoTabuleiro[15][15];
    private final MonstroLento monstroLento = new MonstroLento();
    private final MonstroRapido monstroRapido = new MonstroRapido();
    private final JButton botaoCima;
    private final JButton botaoBaixo;
    private final JButton botaoEsquerda;
    private final JButton botaoDireita;
    private Jogador player;

    public Tabuleiro() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Wumpus Game");
        setLayout(null);
        setBounds(165, 70, 900, 750);
        setPreferredSize(new Dimension(950, 700));
        setResizable(false);
        pack();

        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < 15; i++) {
                botoesTabuleiro[i][j] = new BotaoTabuleiro(i, j);
                add(botoesTabuleiro[i][j]);
            }
        }
        this.player = new Jogador();
        botoesTabuleiro[0][0].adicionarPersonagem(player, COR_DESTAQUE_JOGADOR);
        criaPocos();
        criaOuro();
        criaMadeira();
        moverMonstroRapido();
        moverMonstroLento();


        botaoCima = new JButton("Cima");
        botaoCima.setBounds(650, 20, 100, 30);
        botaoCima.addActionListener(e -> {
            int novaPosY = player.getPosY() + 1; // Calcula a nova posição Y após o movimento
            if (checkMove(player.getPosX(), novaPosY)) {
                // testa se encontoru o monstro
                coletaItem(player.getPosX(), novaPosY);
                atualizarPersonagem(player, COR_DESTAQUE_JOGADOR, player.getPosX(), player.getPosY(), player.getPosX(), novaPosY); // Atualiza a posição depois de coletar o ouro
                System.out.println("Posicao do Jogador X: " + player.getPosX() + ", Posicao Y: " + player.getPosY());
                moverMonstroRapido();
                moverMonstroLento();
            } else System.out.println("Posicao invalida");
        });
        add(botaoCima);


        botaoBaixo = new JButton("Baixo");
        botaoBaixo.setBounds(650, 60, 100, 30);
        botaoBaixo.addActionListener(e -> {
            int novaPosY = player.getPosY() - 1; // Calcula a nova posição Y após o movimento
            if (checkMove(player.getPosX(), novaPosY)) {
                coletaItem(player.getPosX(), novaPosY);
                atualizarPersonagem(player, COR_DESTAQUE_JOGADOR, player.getPosX(), player.getPosY(), player.getPosX(), novaPosY); // Atualiza a posição depois de coletar o ouro
                System.out.println("Posicao do Jogador X: " + player.getPosX() + ", Posicao Y: " + player.getPosY());
                moverMonstroRapido();
                moverMonstroLento();
            } else System.out.println("Posicao invalida");
        });
        add(botaoBaixo);

        botaoDireita = new JButton("Direita");
        botaoDireita.setBounds(650, 100, 100, 30);
        botaoDireita.addActionListener(e -> {
            int novaPosX = player.getPosX() + 1; // Calcula a nova posição Y após o movimento
            if (checkMove(novaPosX, player.getPosY())) {
                coletaItem(novaPosX, player.getPosY());
                atualizarPersonagem(player, COR_DESTAQUE_JOGADOR, player.getPosX(), player.getPosY(), novaPosX, player.getPosY()); // Atualiza a posição depois de coletar o ouro
                System.out.println("Posicao do Jogador X: " + player.getPosX() + ", Posicao Y: " + player.getPosY());
                moverMonstroRapido();
                moverMonstroLento();
            } else System.out.println("Posicao invalida");
        });
        add(botaoDireita);

        botaoEsquerda = new JButton("Esquerda");
        botaoEsquerda.setBounds(650, 140, 100, 30);
        botaoEsquerda.addActionListener(e -> {
            int novaPosX = player.getPosX() - 1; // Calcula a nova posição Y após o movimento
            if (checkMove(novaPosX, player.getPosY())) {
                coletaItem(novaPosX, player.getPosY());
                atualizarPersonagem(player, COR_DESTAQUE_JOGADOR, player.getPosX(), player.getPosY(), novaPosX, player.getPosY()); // Atualiza a posição depois de coletar o ouro
                System.out.println("Posicao do Jogador X: " + player.getPosX() + ", Posicao Y: " + player.getPosY());
                moverMonstroRapido();
                moverMonstroLento();
            } else System.out.println("Posicao invalida");
        });
        add(botaoEsquerda);

        setVisible(true);
    }


    private void atualizarPersonagem(Personagem p, Color c, int xAntigo, int yAntigo, int xNovo, int yNovo) {
        botoesTabuleiro[xNovo][yNovo].adicionarPersonagem(p, c);
        botoesTabuleiro[xAntigo][yAntigo].removerPersonagem();

        p.setPosX(xNovo);
        p.setPosY(yNovo);

        System.out.println("\n");
        System.out.println("Nova posicao de " + p.getClass().getSimpleName() + " -> x :" + p.getPosX() + " Posicao y: " + p.getPosY());
    }

    private void atualizarMonstro(Personagem m, Color c, int xAntigo, int yAntigo, int xNovo, int yNovo) {
        botoesTabuleiro[xNovo][yNovo].adicionarPersonagem(m, c);

        if (botoesTabuleiro[xAntigo][yAntigo].retornarPersonagem() != null) {
            botoesTabuleiro[xAntigo][yAntigo].removerPersonagem();
        }

        m.setPosX(xNovo);
        m.setPosY(yNovo);
        System.out.println("Nova posicao de " + m.getClass().getSimpleName() + " -> x :" + m.getPosX() + " Posicao y: " + m.getPosY());
    }

    private void moverMonstroLento() {
        Random random = new Random();
        boolean moveu = false;
        int minimo = 0;
        int maximo = 3;
        do {
            int num = random.nextInt(maximo - minimo + 1) + minimo;
            switch (num) {
                case 0 -> {
                    if (checkMoveMonstro(monstroLento.getPosY(), monstroLento.getPosY() + 1)) {
                        atualizarMonstro(monstroLento, COR_DESTAQUE_MONSTRO, monstroLento.posX, monstroLento.posY, monstroLento.posX, monstroLento.posY + 1);
                        moveu = true;
                    }
                }
                case 1 -> {
                    if (checkMoveMonstro(monstroLento.getPosY(), monstroLento.getPosY() - 1)) {
                        atualizarMonstro(monstroLento, COR_DESTAQUE_MONSTRO, monstroLento.posX, monstroLento.posY, monstroLento.posX, monstroLento.posY - 1);
                        moveu = true;
                    }
                }
                case 2 -> {
                    if (checkMoveMonstro(monstroLento.getPosX() - 1, monstroLento.getPosY())) {
                        atualizarMonstro(monstroLento, COR_DESTAQUE_MONSTRO, monstroLento.posX, monstroLento.posY, monstroLento.posX - 1, monstroLento.posY);
                        moveu = true;
                    }
                }
                case 3 -> {
                    if (checkMoveMonstro(monstroLento.getPosX() + 1, monstroLento.getPosY())) {
                        atualizarMonstro(monstroLento, COR_DESTAQUE_MONSTRO, monstroLento.posX, monstroLento.posY, monstroLento.posX + 1, monstroLento.posY);
                        moveu = true;
                    }
                }
            }
        } while (!moveu);

        // testa se encontoru o jogador
    }


    private void moverMonstroRapido() {
        Random random = new Random();
        int num;
        boolean moveu = false;
        int minimo = 0;
        int maximo = 7;

        do {
            num = random.nextInt(maximo - minimo + 1) + minimo;
            switch (num) {
                case 0 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() - 1, monstroRapido.getPosY() + 2)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() - 1, monstroRapido.getPosY() + 2);
                        moveu = true;
                    }
                }
                case 1 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() + 1, monstroRapido.getPosY() + 2)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() + 1, monstroRapido.getPosY() + 2);
                        moveu = true;
                    }
                }
                case 2 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() - 2, monstroRapido.getPosY() + 1)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() - 2, monstroRapido.getPosY() + 1);
                        moveu = true;
                    }
                }
                case 3 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() - 2, monstroRapido.getPosY() - 1)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() - 2, monstroRapido.getPosY() - 1);
                        moveu = true;
                    }
                }
                case 4 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() + 2, monstroRapido.getPosY() + 1)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() + 2, monstroRapido.getPosY() + 1);
                        moveu = true;
                    }
                }
                case 5 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() + 2, monstroRapido.getPosY() - 1)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() + 2, monstroRapido.getPosY() - 1);
                        moveu = true;
                    }
                }
                case 6 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() + 1, monstroRapido.getPosY() - 2)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() + 1, monstroRapido.getPosY() - 2);
                        moveu = true;
                    }
                }
                case 7 -> {
                    if (checkMoveMonstro(monstroRapido.getPosX() - 1, monstroRapido.getPosY() - 2)) {
                        atualizarMonstro(monstroRapido, COR_DESTAQUE_MONSTRO_RAPIDO, monstroRapido.getPosX(), monstroRapido.getPosY(), monstroRapido.getPosX() - 1, monstroRapido.getPosY() - 2);
                        moveu = true;
                    }
                }
            }
        } while (!moveu);

        // testa se encontoru o jogador
    }

    private boolean checkMove(int x, int y) {
        return (x <= 14 && x >= 0 && y <= 14 && y >= 0);
    }

    private boolean checkMoveMonstro(int x, int y) {
        return (x <= 14 && x >= 0 && y <= 14 && y >= 0) && botoesTabuleiro[x][y].retornarItem() != TipoDeItem.POCO;
    }

    public void criaPocos() {
        Random random = new Random();
        int i = 0;
        int min = 0;
        int max = 14;

        while (i < 5) {
            int pocoX = random.nextInt(max - min + 1) + min;
            int pocoY = random.nextInt(max - min + 1) + min;

            if (!botoesTabuleiro[pocoX][pocoY].temAlguemAqui()) {
                botoesTabuleiro[pocoX][pocoY].adicionarItem(TipoDeItem.POCO, COR_DESTAQUE_POCO);
                System.out.println("Poço " + i + ": " + pocoX + " " + pocoY);
                i++;
            }
        }
    }


    public void criaMadeira() {
        Random random = new Random();
        int i = 0;
        int min = 0;
        int max = 14;

        while (i < 2) {

            int madeiraX = random.nextInt(max - min + 1) + min;
            int madeiraY = random.nextInt(max - min + 1) + min;

            if (!botoesTabuleiro[madeiraX][madeiraY].temAlguemAqui()) {
                botoesTabuleiro[madeiraX][madeiraY].adicionarItem(TipoDeItem.MADEIRA, COR_DESTAQUE_MADEIRA);
                i++;
                System.out.println("Madeira " + i + ": " + madeiraX + " " + madeiraY);
            }
        }
    }

    public void criaOuro() {
        Random random = new Random();
        int i = 0;
        int min = 0;
        int max = 14;

        while (i < 1) {
            int ouroX = random.nextInt(max - min + 1) + min;
            int ouroY = random.nextInt(max - min + 1) + min;

            if (!botoesTabuleiro[ouroX][ouroY].temAlguemAqui()) {
                botoesTabuleiro[ouroX][ouroY].adicionarItem(TipoDeItem.OURO, COR_DESTAQUE_OURO);
                System.out.println("Ouro " + i + ": " + ouroX + " " + ouroY);
                i++;
            }
        }
    }

    private void coletaItem(int novoX, int novoY) {
        BotaoTabuleiro destino = botoesTabuleiro[novoX][novoY];
        TipoDeItem tipoDeItem = destino.retornarItem();
        switch (tipoDeItem) {
            case OURO:
                coletarOuro(destino); // Primeiro, coleta o ouro
                break;
            case MADEIRA:
                coletarMadeira(destino);
                break;
            case POCO:
                caiuPoco();
                break;
            default:
                break;
        }
    }

    private void coletarOuro(BotaoTabuleiro botaoOuro) {
        boolean adicionou = player.adicionarOuro();
        if (adicionou) {
            botaoOuro.removerItem();
        }
        System.out.println("\n");
        System.out.println("O jogador coletou ouro. Total de ouro: " + player.getQuantidadeOuro());
    }

    private void coletarMadeira(BotaoTabuleiro botaoMadeira) {
        boolean adicionou = player.adicionarMadeira(); // Incrementa a quantidade de Madeira do jogador
        if (adicionou) {
            botaoMadeira.removerItem(); // Remove o destaque da Madeira no botão
        }
    }

    private void caiuPoco() {
        //botaoPoco.removerDestaque(); // Remove o destaque da Madeira no botão
        //botaoPoco.setTipoDeItem(BotaoTabuleiro.TipoDeItem.VAZIO); // Marca o botão como vazio
        //player.adicionarMadeira(); // Incrementa a quantidade de Madeira do jogador
        System.out.println("\n");
        System.out.println("O jogador caiu num poco.  ");
    }


}

// #############################

//PEDRO e ALEMON
// Movimentacao wumpus Lento
// Num = 0 -> cima, 1 baixo, 2 esquerda, 3 direita

// #############################
// Movimentacao wumpus Rapido
// Num = 0 -> cima, cima, esquerda
// Num = 1 -> cima, cima, direita
// Num = 2 -> esquerda, esquerda, cima
// Num = 3 -> esquerda, esquerda, baixo
// Num = 4 -> direita, direita, cima
// Num = 5 -> direita, direita, baixo
// Num = 6 -> baixo, baixo, direita
// Num = 7 -> baixo, baixo, esquerda
