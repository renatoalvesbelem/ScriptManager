package br.com.bdChange;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DbChangeControl {
    private File diretorioScrips;

    public DbChangeControl(File diretorioScrips) {
        this.diretorioScrips = diretorioScrips;
    }

    public void criarArquivoDbChange() throws IOException {
        DbChangeModel dbChangeModel = new DbChangeModel();

        Writer gravarArq = new OutputStreamWriter(new FileOutputStream(diretorioScrips + "/dbChange.xml"), StandardCharsets.UTF_8);

        gravarArq.write(dbChangeModel.getCabecalho());
        gravarArq.write("\n");
        gravarArq.write(dbChangeModel.getInicioScripts());
        gravarArq.write("\n");
        for (String script : diretorioScrips.list()) {
            if (script.contains(".DH4")) {
                gravarArq.write(dbChangeModel.getScript(script, ""));
                gravarArq.write("\n");
            }

        }
        gravarArq.write(dbChangeModel.getfimScripts());
        gravarArq.close();
    }

}
