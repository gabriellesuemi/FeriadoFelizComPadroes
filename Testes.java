import java.util.List;

public class Testes {

    public static void main(String[] args) {
        System.out.println("TESTES");
        testarFactoryMusica();
        testarFactoryPodcast();
        testarSingleton();
        testarProxy();
        testarAdapter();
        testarObserver();
        testarVisitor();
    }

    private static void testarFactoryMusica() {
        Conteudo c = FabricaConteudo.criarConteudo("musica", "Halo", "Beyonce");

        if (c instanceof Musica) {
            System.out.println("Teste Factory Música: PASSOU");
        } else {
            System.out.println("Teste Factory Música: FALHOU");
        }
    }

    private static void testarFactoryPodcast() {
        Conteudo c = FabricaConteudo.criarConteudo("podcast", "Notícias", "Flávio");

        if (c instanceof Podcast) {
            System.out.println("Teste Factory Podcast: PASSOU");
        } else {
            System.out.println("Teste Factory Podcast: FALHOU");
        }
    }

    private static void testarSingleton() {
        ConfiguracaoStreaming c1 = ConfiguracaoStreaming.getInstancia();
        ConfiguracaoStreaming c2 = ConfiguracaoStreaming.getInstancia();

        if (c1 == c2) {
            System.out.println("Teste Singleton: PASSOU");
        } else {
            System.out.println("Teste Singleton: FALHOU");
        }
    }

    private static void testarProxy() {
        ProxyCatalogo catalogo = new ProxyCatalogo(false);
        catalogo.adicionarConteudo(new Musica("Believer", "Imagine Dragons"));
        List<Conteudo> lista = catalogo.listarConteudos();

        if (lista.size() == 1) {
            System.out.println("Teste Proxy: PASSOU");
        } else {
            System.out.println("Teste Proxy: FALHOU");
        }
    }

    private static void testarAdapter() {
        Reprodutor reprodutor = new AdaptadorArquivoLocal(new ArquivoLocalPlayer());

        try {
            reprodutor.reproduzir("teste.mp3");
            System.out.println("Teste Adapter: PASSOU");
        } catch (Exception e) {
            System.out.println("Teste Adapter: FALHOU");
        }
    }

    private static void testarObserver() {
        SistemaNotificacao sistema = new SistemaNotificacao();
        UsuarioObservador usuario = new UsuarioObservador("Maria");
        sistema.adicionarObservador(usuario);

        Conteudo conteudo = new Musica("Shape of You", "Ed Sheeran");
        sistema.novoConteudoAdicionado(conteudo);

        if (usuario.getUltimoRecebido() != null &&
                usuario.getUltimoRecebido().getTitulo().equals("Shape of You")) {
            System.out.println("Teste Observer: PASSOU");
        } else {
            System.out.println("Teste Observer: FALHOU");
        }
    }

    private static void testarVisitor() {
        Conteudo musica = new Musica("Thriller", "Michael Jackson");
        Conteudo podcast = new Podcast("Ciência ", "Fernanda");
        Visitor visitor = new RelatorioVisitor();

        try {
            musica.aceitar(visitor);
            podcast.aceitar(visitor);
            System.out.println("Teste Visitor: PASSOU");
        } catch (Exception e) {
            System.out.println("Teste Visitor: FALHOU");
        }
    }
}