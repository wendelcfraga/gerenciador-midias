public abstract class Midia {
    private static int contadorId = 0;
    protected int id;
    protected String nome;
    protected String descricao;
    protected String imagemCapa;
    protected int avaliacao;
    protected boolean consumido;
    private int idCarregado;

    public Midia(String nome, String descricao, String imagemCapa, int avaliacao, boolean consumido) {
        this.id = contadorId++;
        this.nome = nome;
        this.descricao = descricao;
        this.imagemCapa = imagemCapa;
        this.avaliacao = avaliacao;
        this.consumido = consumido;
    }

    public Midia(int id, String nome, String descricao, String imagemCapa, int avaliacao, boolean consumido) {
        this.id = contadorId++;
        this.nome = nome;
        this.descricao = descricao;
        this.imagemCapa = imagemCapa;
        this.avaliacao = avaliacao;
        this.consumido = consumido;
        this.idCarregado = id;
    }

    public int getId() {
        return id;
    }

    public int getIdCarregado() {
        return idCarregado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(String imagemCapa) {
        this.imagemCapa = imagemCapa;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public boolean isConsumido() {
        return consumido;
    }

    public void setConsumido(boolean consumido) {
        this.consumido = consumido;
    }
}
