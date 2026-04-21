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