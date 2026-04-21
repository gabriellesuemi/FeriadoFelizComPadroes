import java.util.ArrayList;
import java.util.List;

interface Conteudo {
    String getTitulo();
    void aceitar(Visitor visitor);
}

class Musica implements Conteudo {
    private String titulo;
    private String artista;

    public Musica(String titulo, String artista) {
        this.titulo = titulo;
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    @Override
    public void aceitar(Visitor visitor) {
        visitor.visitarMusica(this);
    }
}

class Podcast implements Conteudo {
    private String titulo;
    private String apresentador;

    public Podcast(String titulo, String apresentador) {
        this.titulo = titulo;
        this.apresentador = apresentador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getApresentador() {
        return apresentador;
    }

    @Override
    public void aceitar(Visitor visitor) {
        visitor.visitarPodcast(this);
    }
}


// FACTORY

class FabricaConteudo {
    public static Conteudo criarConteudo(String tipo, String titulo, String autor) {
        if (tipo.equalsIgnoreCase("musica")) {
            return new Musica(titulo, autor);
        } else if (tipo.equalsIgnoreCase("podcast")) {
            return new Podcast(titulo, autor);
        }

        throw new IllegalArgumentException("Tipo de conteúdo inválido.");
    }
}


// SINGLETON

class ConfiguracaoStreaming {
    private static ConfiguracaoStreaming instancia;
    private String nomePlataforma;

    private ConfiguracaoStreaming() {
        this.nomePlataforma = "StreamMusic";
    }

    public static ConfiguracaoStreaming getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoStreaming();
        }
        return instancia;
    }

    public String getNomePlataforma() {
        return nomePlataforma;
    }

    public void setNomePlataforma(String nomePlataforma) {
        this.nomePlataforma = nomePlataforma;
    }
}


// PROXY

interface Catalogo {
    void adicionarConteudo(Conteudo conteudo);
    List<Conteudo> listarConteudos();
}

class CatalogoReal implements Catalogo {
    private List<Conteudo> conteudos = new ArrayList<>();

    @Override
    public void adicionarConteudo(Conteudo conteudo) {
        conteudos.add(conteudo);
    }

    @Override
    public List<Conteudo> listarConteudos() {
        return conteudos;
    }
}

class ProxyCatalogo implements Catalogo {
    private CatalogoReal catalogoReal;
    private boolean usuarioPremium;

    public ProxyCatalogo(boolean usuarioPremium) {
        this.catalogoReal = new CatalogoReal();
        this.usuarioPremium = usuarioPremium;
    }

    @Override
    public void adicionarConteudo(Conteudo conteudo) {
        catalogoReal.adicionarConteudo(conteudo);
    }

    @Override
    public List<Conteudo> listarConteudos() {
        if (usuarioPremium) {
            System.out.println("Proxy: acesso liberado para usuário premium.");
        } else {
            System.out.println("Proxy: acesso limitado para usuário comum.");
        }
        return catalogoReal.listarConteudos();
    }
}


// ADAPTER

interface Reprodutor {
    void reproduzir(String titulo);
}

class ArquivoLocalPlayer {
    public void playFile(String nomeArquivo) {
        System.out.println("Reproduzindo arquivo local: " + nomeArquivo);
    }
}

// Adaptador
class AdaptadorArquivoLocal implements Reprodutor {
    private ArquivoLocalPlayer playerLocal;

    public AdaptadorArquivoLocal(ArquivoLocalPlayer playerLocal) {
        this.playerLocal = playerLocal;
    }

    @Override
    public void reproduzir(String titulo) {
        playerLocal.playFile(titulo);
    }
}


// FACADE

class PlataformaStreamingFacade {
    private Catalogo catalogo;
    private SistemaNotificacao sistemaNotificacao;
    private Reprodutor reprodutor;

    public PlataformaStreamingFacade(Catalogo catalogo, SistemaNotificacao sistemaNotificacao, Reprodutor reprodutor) {
        this.catalogo = catalogo;
        this.sistemaNotificacao = sistemaNotificacao;
        this.reprodutor = reprodutor;
    }

    public void cadastrarConteudo(String tipo, String titulo, String autor) {
        Conteudo conteudo = FabricaConteudo.criarConteudo(tipo, titulo, autor);
        catalogo.adicionarConteudo(conteudo);
        sistemaNotificacao.novoConteudoAdicionado(conteudo);
    }

    public void listarCatalogo() {
        for (Conteudo c : catalogo.listarConteudos()) {
            System.out.println("- " + c.getTitulo());
        }
    }

    public void tocarConteudo(String titulo) {
        reprodutor.reproduzir(titulo);
    }
}


// DECORATOR

interface Tocador {
    void tocar(String titulo);
}

// Componente concreto
class TocadorSimples implements Tocador {
    @Override
    public void tocar(String titulo) {
        System.out.println("Tocando: " + titulo);
    }
}

// Decorator base
abstract class TocadorDecorator implements Tocador {
    protected Tocador tocador;

    public TocadorDecorator(Tocador tocador) {
        this.tocador = tocador;
    }

    @Override
    public void tocar(String titulo) {
        tocador.tocar(titulo);
    }
}

// Decorator concreto: equalizador
class EqualizadorDecorator extends TocadorDecorator {
    public EqualizadorDecorator(Tocador tocador) {
        super(tocador);
    }

    @Override
    public void tocar(String titulo) {
        super.tocar(titulo);
        System.out.println("Equalizador ativado.");
    }
}

// Decorator concreto: modo premium
class ModoPremiumDecorator extends TocadorDecorator {
    public ModoPremiumDecorator(Tocador tocador) {
        super(tocador);
    }

    @Override
    public void tocar(String titulo) {
        super.tocar(titulo);
        System.out.println("Modo premium: áudio sem anúncios.");
    }
}


// OBSERVER

interface Observador {
    void atualizar(Conteudo conteudo);
}

interface Sujeito {
    void adicionarObservador(Observador o);
    void removerObservador(Observador o);
    void notificarObservadores(Conteudo conteudo);
}

// Observador concreto
class UsuarioObservador implements Observador {
    private String nome;
    private Conteudo ultimoRecebido;

    public UsuarioObservador(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(Conteudo conteudo) {
        ultimoRecebido = conteudo;
        System.out.println(nome + " recebeu notificação: novo conteúdo -> " + conteudo.getTitulo());
    }

    public Conteudo getUltimoRecebido() {
        return ultimoRecebido;
    }
}

// Sujeito concreto
class SistemaNotificacao implements Sujeito {
    private List<Observador> observadores = new ArrayList<>();

    @Override
    public void adicionarObservador(Observador o) {
        observadores.add(o);
    }

    @Override
    public void removerObservador(Observador o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores(Conteudo conteudo) {
        for (Observador o : observadores) {
            o.atualizar(conteudo);
        }
    }

    public void novoConteudoAdicionado(Conteudo conteudo) {
        notificarObservadores(conteudo);
    }
}



interface Visitor {
    void visitarMusica(Musica musica);
    void visitarPodcast(Podcast podcast);
}

public class SistemaPadroes {
    public static void main(String[] args) {
        Musica musica = new Musica("Eduardo e Mônica", "Legião Urbana");
        Podcast podcast = new Podcast("Tecnologia", "Camila");

        System.out.println(musica.getTitulo());
        System.out.println(podcast.getTitulo());
    }
}