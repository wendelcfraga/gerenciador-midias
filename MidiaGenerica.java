public class MidiaGenerica extends Midia{

    public MidiaGenerica(String nome, String descricao, String imagemCapa, int avaliacao, boolean consumido) {
        super(nome, descricao, imagemCapa, avaliacao, consumido);
        
    }

    public MidiaGenerica(int id, String nome, String descricao, String imagemCapa, int avaliacao, boolean consumido) {
        super(id, nome, descricao, imagemCapa, avaliacao, consumido);
        
    }
    
}
