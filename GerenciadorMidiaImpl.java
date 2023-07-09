import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorMidiaImpl implements GerenciadorMidia {
    private List<Midia> midias;
    private static final String ARQUIVO_MIDIAS = "midias.txt";

    public GerenciadorMidiaImpl() {
        this.midias = new ArrayList<>();
        verificarECriarArquivoSeNaoExistir();
        carregarMidiasDoArquivo();
    }

    @Override
    public void adicionarMidia(Midia midia) {
        midias.add(midia);
        gravarMidiaEmArquivo(midia);
    }

    private void gravarMidiaEmArquivo(Midia midia) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_MIDIAS, true))) {
            writer.write(midia.getId() + ";");
            writer.write(midia.getNome() + ";");
            writer.write(midia.getDescricao() + ";");
            writer.write(midia.getImagemCapa() + ";");
            writer.write(midia.getAvaliacao() + ";");
            writer.write(midia.isConsumido() ? "1" : "0");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verificarECriarArquivoSeNaoExistir() {
        File arquivo = new File(ARQUIVO_MIDIAS);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void carregarMidiasDoArquivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_MIDIAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                int id = Integer.parseInt(campos[0]);
                String nome = campos[1];
                String descricao = campos[2];
                String imagemCapa = campos[3];
                int avaliacao = Integer.parseInt(campos[4]);
                boolean consumido = campos[5].equals("1");

                Midia midia = new MidiaGenerica(id, nome, descricao, imagemCapa, avaliacao, consumido);
                
                midias.add(midia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerMidia(int id) {
        midias.removeIf(midia -> midia.getId() == id);
    }

    @Override
    public Midia buscarMidiaPorNome(String nome) {
        for (Midia midia : midias) {
            if (midia.getNome().toLowerCase().contains(nome.toLowerCase())) {
                return midia;
            }
        }
        return null;
    }

    @Override
    public List<Midia> listarTodasMidias() {
        return midias;
    }
}

