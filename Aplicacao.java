import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Aplicacao extends JFrame {
    private GerenciadorMidia gerenciadorMidia;
    private JPanel painelMidias;
    private JTextField campoBusca;

    public Aplicacao() {
        this.gerenciadorMidia = new GerenciadorMidiaImpl();
    
        this.setTitle("Gerenciador de Mídias");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
    
        JButton botaoAdicionar = new JButton("Adicionar Mídia");
        botaoAdicionar.addActionListener(e -> adicionarMidia());
    
        this.add(botaoAdicionar, BorderLayout.SOUTH);
    
        campoBusca = new JTextField();
        JButton botaoBusca = new JButton("Buscar");
        botaoBusca.addActionListener(e -> {
            painelMidias.removeAll();
            buscarMidia();
            painelMidias.revalidate();
            painelMidias.repaint();
        });
    
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> {
            campoBusca.setText("");
            atualizarListaMidias();
        });
    
        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(campoBusca, BorderLayout.CENTER);
        painelBusca.add(botaoBusca, BorderLayout.EAST);
        painelBusca.add(botaoVoltar, BorderLayout.WEST);
    
        this.add(painelBusca, BorderLayout.NORTH);
    
        painelMidias = new JPanel();
        painelMidias.setLayout(new GridLayout(0, 3));
        JScrollPane scrollPane = new JScrollPane(painelMidias);
    
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
    
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarListaMidias();
            }
        });
    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }    

    private void buscarMidia() {
        String nome = campoBusca.getText();
        Midia midia = gerenciadorMidia.buscarMidiaPorNome(nome);
        if (midia != null) {
            mostrarMidia(midia);
        } else {
            JOptionPane.showMessageDialog(this, "Mídia não encontrada");
        }
    }

    private void adicionarMidia() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome da mídia:");
        String descricao = JOptionPane.showInputDialog(this, "Digite a descrição da mídia:");
        String imagemCapa = selecionarImagemCapa();
        int avaliacao = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a avaliação da mídia (0-10):"));
        boolean consumido = JOptionPane.showInputDialog(this, "A mídia foi consumida? (sim/não):").equalsIgnoreCase("sim");

        Midia midia = new MidiaGenerica(nome, descricao, imagemCapa, avaliacao, consumido);
        gerenciadorMidia.adicionarMidia(midia);

        atualizarListaMidias();
        revalidate();
        repaint();
    }

    private void mostrarMidia(Midia midia) {
        JPanel painelMidia = new JPanel();
        painelMidia.setLayout(new BoxLayout(painelMidia, BoxLayout.Y_AXIS));
        painelMidia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painelMidia.setPreferredSize(new Dimension(300, 300));

        BufferedImage imagemOriginal = null;
        try {
            imagemOriginal = ImageIO.read(new File(midia.getImagemCapa()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image imagemRedimensionada = imagemOriginal.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imagemIcon = new ImageIcon(imagemRedimensionada);
        JLabel labelImagem = new JLabel(imagemIcon);
        labelImagem.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(painelMidia, 
                    "Nome: " + midia.getNome() + "\n" +
                    "Descrição: " + midia.getDescricao() + "\n" +
                    "Avaliação: " + midia.getAvaliacao() + "\n" +
                    "Consumido: " + (midia.isConsumido() ? "Sim" : "Não"));
            }
        });

        JLabel labelNome = new JLabel("Nome: " + midia.getNome());
        labelNome.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JLabel labelDescricao = new JLabel("Descrição: " + midia.getDescricao());
        labelDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JLabel labelAvaliacao = new JLabel("Avaliação: " + midia.getAvaliacao());
        labelAvaliacao.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JLabel labelConsumido = new JLabel("Consumido: " + (midia.isConsumido() ? "Sim" : "Não"));
        labelConsumido.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.addActionListener(e -> {
            gerenciadorMidia.removerMidia(midia.getId());
            atualizarListaMidias();
        });

        JButton botaoAlterar = new JButton("Alterar");
        botaoAlterar.addActionListener(e -> alterarMidia(midia));

        painelMidia.add(botaoExcluir);
        painelMidia.add(botaoAlterar);

        painelMidia.add(labelImagem);
        painelMidia.add(Box.createRigidArea(new Dimension(0, 10)));
        painelMidia.add(labelNome);
        painelMidia.add(labelDescricao);
        painelMidia.add(labelAvaliacao);
        painelMidia.add(labelConsumido);
        painelMidias.add(painelMidia);
    }

    private void alterarMidia(Midia midia) {
        String nome = JOptionPane.showInputDialog(this, "Digite o novo nome da mídia:", midia.getNome());
        String descricao = JOptionPane.showInputDialog(this, "Digite a nova descrição da mídia:", midia.getDescricao());
        String imagemCapa = selecionarImagemCapa();
        int avaliacao = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a nova avaliação da mídia (0-10):", midia.getAvaliacao()));
        boolean consumido = JOptionPane.showInputDialog(this, "A mídia foi consumida? (sim/não):", midia.isConsumido() ? "sim" : "não").equalsIgnoreCase("sim");
    
        midia.setNome(nome);
        midia.setDescricao(descricao);
        midia.setImagemCapa(imagemCapa);
        midia.setAvaliacao(avaliacao);
        midia.setConsumido(consumido);
    
        atualizarListaMidias();
    }
    

    private String selecionarImagemCapa() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    private void atualizarListaMidias() {
        painelMidias.removeAll();

        for (Midia midia : gerenciadorMidia.listarTodasMidias()) {
            mostrarMidia(midia);
        }

        painelMidias.revalidate();
        painelMidias.repaint();
    }

    public static void main(String[] args) {
        new Aplicacao();
    }
}
