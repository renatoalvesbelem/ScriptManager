package br.com.script;

import br.com.bdChange.DbChangeControl;
import br.com.view.PrincipalView;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static br.com.properties.Properties.*;

public class DiretorioControl {
    private File diretrioDestino;

    public DiretorioControl(Map<String, List<Set>> listaScripts, String diretrioDestino) {
        this.diretrioDestino = new File(diretrioDestino);
        for (String diretorio : listaScripts.keySet()) {
            criarDiretorio(diretorio);
            criarPastasBancos(diretorio);
            criarCopiarDiretoriosInstancias(listaScripts, diretorio);
        }
    }

    private void criarSubDiretorio(String nomeDiretorio) {
        File diretorio = new File(nomeDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    private void criarDiretorio(String nomeDiretorio) {
        String diretorioDestino = retornarTipoDoDiretorio(this.diretrioDestino);

        File diretorio = new File(diretorioDestino + "/" + nomeDiretorio);
        if (diretorio.exists()) {
            removerDiretorioEConteudo(diretorio);
        }
        diretorio.mkdirs();

    }

    private void removerDiretorioEConteudo(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; ++i) {
                removerDiretorioEConteudo(files[i]);
            }
        }
        f.delete();
    }

    private void criarPastasBancos(String nomeDiretorio) {
        String diretorioDestino = retornarTipoDoDiretorio(this.diretrioDestino);

        for (String banco : PASTAS_BANCOS) {
            criarSubDiretorio(diretorioDestino + "/" + nomeDiretorio + "/" + banco);
        }
    }

    private void criarCopiarDiretoriosInstancias(Map<String, List<Set>> listaScripts, String diretrioDestino) {
        for (Set<String> scripts : listaScripts.get(diretrioDestino)) {
            for (String script : scripts) {
                String instancia = instanciaE(script);
                criarDiretorioInstancia(instancia, diretrioDestino);
                copiarScripts(instancia, diretrioDestino, script);
            }

            criarDBChange(diretrioDestino);

        }
    }

    private void criarDBChange(String nomeDiretorio) {
        String diretorioDestino = retornarTipoDoDiretorio(this.diretrioDestino);

        for (String banco : PASTAS_BANCOS) {
            File diretoriosIntancia = new File(diretorioDestino + "/" + nomeDiretorio + "/" + banco);
            for (File direrorio : diretoriosIntancia.listFiles()) {
                criarArquivoDbChange(direrorio);
            }
        }
    }

    private void criarArquivoDbChange(File absolutePath) {
        DbChangeControl dbChangeControl = new DbChangeControl(absolutePath);
        try {
            dbChangeControl.criarArquivoDbChange();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarDiretorioInstancia(String nomeInstancia, String nomeDiretorio) {
        String diretorioDestino = retornarTipoDoDiretorio(this.diretrioDestino);
        for (String banco : PASTAS_BANCOS) {
            criarSubDiretorio(diretorioDestino + "/" + nomeDiretorio + "/" + banco + "/" + nomeInstancia);
        }
    }

    private String instanciaE(String script) {
        if (script.contains(NET)) {
            return NET;
        } else if (script.contains(PG)) {
            return PG;
        } else if (script.contains(SG)) {
            return SG;
        } else if (script.contains(IND)) {
            return IND;
        } else if (script.contains(SAJ)) {
            return PG;
        }
        return "NONE";
    }

    private void copiarScripts(String nomeInstancia, String nomeDiretorio, String nomeScript) {
        String diretorioDestino = retornarTipoDoDiretorio(this.diretrioDestino);

        for (String banco : PASTAS_BANCOS) {
            String destino = diretorioDestino + "/" + nomeDiretorio + "/" + banco + "/" + nomeInstancia + "/" + nomeScript;
            String origem = PASTA_GIT + nomeInstancia + "/" + banco + "/" + nomeScript;
            copiar(origem, destino);
        }
    }

    private void copiar(String scriptOrigem, String scriptDestino) {
        File origem = new File(scriptOrigem);
        File destino = new File(scriptDestino);
        {
            if (destino.exists())
                destino.delete();
            FileChannel sourceChannel = null;
            FileChannel destinationChannel = null;
            try {
                sourceChannel = new FileInputStream(origem).getChannel();
                destinationChannel = new FileOutputStream(destino).getChannel();
                sourceChannel.transferTo(0, sourceChannel.size(),
                        destinationChannel);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                PrincipalView.adicionarLinhaAreaLog(destino.getName() + " não foi encontrado em: " + destino.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (sourceChannel != null && sourceChannel.isOpen()) {
                    try {
                        sourceChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (destinationChannel != null && destinationChannel.isOpen()) {
                    try {
                        destinationChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private  String retornarTipoDoDiretorio(File diretorio){
        if (!diretorio.isDirectory()) {
            return diretorio.getParent();
        } else {
            return diretorio.getAbsolutePath();
        }
    }

}
