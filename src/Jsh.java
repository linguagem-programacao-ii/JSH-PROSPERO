import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public final class Jsh {

    private static String dir;
    private static String name;
    private static String UID;
    private static String barra;

    public static void promptTerminal() {
        name = System.getProperty("user.name");
        UID = Utilidades.getUID(name);
        barra = System.getProperty("file.separator");

        while(true) {
            exibirPrompt();
            ComandoPrompt comandoEntrado = lerComando();
            System.out.println();
            executarComando(comandoEntrado);
            System.out.println();
        }
    }

    public static void exibirPrompt() {
        dir = System.getProperty("user.dir");
        System.out.print(name + "#" + UID + ":" + dir + "% ");
    }

    public static ComandoPrompt lerComando() {
        Scanner scn = new Scanner(System.in);
        String comando = scn.nextLine();
        return new ComandoPrompt(comando);
    }

    public static void executarComando(ComandoPrompt comando) {
        int out = 1;
        switch (comando.getNome()){
            case "encerrar":
                System.exit(0);
                break;
            case "relogio":
                out = ComandosInternos.exibirRelogio();
                break;
            case "la":
                out = ComandosInternos.escreverListaArquivos(Optional.of(dir));
                break;
            case "cd":
                out = ComandosInternos.criarNovoDiretorio(comando.getArgumentos().get(1), dir);
                break;
            case "ad":
                out = ComandosInternos.apagarDiretorio((String)comando.getArgumentos().get(1), dir);
                break;
            case "mdt":
                out = ComandosInternos.mudarDiretorioTrabalho((String)comando.getArgumentos().get(1));
                break;
            default:
                out = executarPrograma(comando);
        }

        if (out != 0)
            System.out.println("Ocorreu um erro");
    }

    public static int executarPrograma(ComandoPrompt comando) {
        int saida = 1;
        File dirA = new File(dir);
        List<File> listArq = Arrays.asList(Objects.requireNonNull(dirA.listFiles()));
        boolean executar = false;

        for(int x = 1; x < listArq.size() && !executar; ++x) {
            executar = ((File)listArq.get(x)).getName().equals(comando.getNome());
        }

        System.out.println(executar);

        return saida;
    }

    public static void main(String... args) {
        promptTerminal();
    }

    private Jsh() {
    }
}
