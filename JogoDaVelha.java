import java.util.Scanner;

public class JogoDaVelha{
    protected static final int X = 1, O = -1;
    protected static final int VAZIO = 0;
    protected int[][] tabuleiro;
    protected int jogador;
protected int dimensao;

    public JogoDaVelha() {
        setDimensao(3);
    }

    public JogoDaVelha(int dimensao) {
        setDimensao(dimensao);
    }

    public void setDimensao(int dimensao) {
        if (dimensao < 3) {
            throw new IllegalArgumentException("A dimensão deve ser pelo menos 3");
        }
        this.dimensao = dimensao;
        this.tabuleiro = new int[dimensao][dimensao];
        limpaTabuleiro();
    }

    public void limpaTabuleiro() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                tabuleiro[i][j] = VAZIO;
            }
        }
        jogador = X;
    }

    public void poePeca(int i, int j) {
        if (i < 0 || i >= dimensao || j < 0 || j >= dimensao) {
            throw new IllegalArgumentException("Posição Inválida");
        }
        if (tabuleiro[i][j] != VAZIO) throw new IllegalArgumentException("Posição Ocupada");
        tabuleiro[i][j] = jogador;
        jogador = -jogador;
        System.out.println("continua\n");
    }

    public String eVencedor(int marca) {
        for (int i = 0; i < dimensao; i++) {
            boolean venceuLinha = true;
            for (int j = 0; j < dimensao; j++) {
                if (tabuleiro[i][j] != marca) {
                    venceuLinha = false;
                    break;
                }
            }
            if (venceuLinha) return "linha ";
        }

        for (int j = 0; j < dimensao; j++) {
            boolean venceuColuna = true;
            for (int i = 0; i < dimensao; i++) {
                if (tabuleiro[i][j] != marca) {
                    venceuColuna = false;
                    break;
                }
            }
            if (venceuColuna) return "coluna ";
        }

        boolean venceuDiagonal1 = true;
        for (int i = 0; i < dimensao; i++) {
            if (tabuleiro[i][i] != marca) {
                venceuDiagonal1 = false;
                break;
            }
        }
        if (venceuDiagonal1) return "diagonal";

        boolean venceuDiagonal2 = true;
        for (int i = 0; i < dimensao; i++) {
            if (tabuleiro[i][dimensao - 1 - i] != marca) {
                venceuDiagonal2 = false;
                break;
            }
        }
        if (venceuDiagonal2) return "diagonal";

        return null;
    }

    public String venceuUsando() {
        String resultadoX = eVencedor(X);
        if (resultadoX != null) {
            return "X venceu por " + resultadoX;
        }

        String resultadoO = eVencedor(O);
        if (resultadoO != null) {
            return "O venceu por " + resultadoO;
        }

        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                if (tabuleiro[i][j] == VAZIO) {
                    return null;
                }
            }
        }
        return "Empate!";
    }

    public void jogarContraSiMesma() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            limpaTabuleiro();
            while (venceuUsando() == null) {
                boolean jogadaValida = false;
                while (!jogadaValida) {
                    int i = (int) (Math.random() * dimensao);
                    int j = (int) (Math.random() * dimensao);
                    if (tabuleiro[i][j] == VAZIO) {
                        poePeca(i, j);
                        jogadaValida = true;
                    }
                }
                System.out.println(toString());
            }

            String resultado = venceuUsando();
            System.out.println(resultado);

            System.out.println("Deseja jogar novamente? (s/n)");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("s")) {
                continuar = false;
            }
        }

        scanner.close();
    }

    public String toString() {
        StringBuilder retorno = new StringBuilder();
        for (int i = 0; i < dimensao; i++) {
            retorno.append("| ");
            for (int j = 0; j < dimensao; j++) {
                if (tabuleiro[i][j] == X) {
                    retorno.append("X | ");
                } else if (tabuleiro[i][j] == O) {
                    retorno.append("O | ");
                } else {
                    retorno.append("  | ");
                }
            }
            retorno.append("\n");
        }
        return retorno.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Jogo da Velha com dimensão variável!");

        int dimensao = 0; // Inicializa com valor inválido
        while (dimensao < 3) {
            System.out.println("Escolha a dimensão do tabuleiro (mínimo 3 para 3x3, 5 para 5x5, etc.):");
            try {
                dimensao = Integer.parseInt(scanner.nextLine());
                if (dimensao < 3) {
                    System.out.println("Dimensão inválida. A dimensão deve ser pelo menos 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }

        System.out.println("Iniciando jogo com tabuleiro " + dimensao + "x" + dimensao);

        JogoDaVelha jogo = new JogoDaVelha(dimensao);
        jogo.jogarContraSiMesma();
    }
}