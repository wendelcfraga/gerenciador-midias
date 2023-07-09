import java.util.List;

public interface GerenciadorMidia {
    void adicionarMidia(Midia midia);
    void removerMidia(int id);
    Midia buscarMidiaPorNome(String nome);
    List<Midia> listarTodasMidias();
}

