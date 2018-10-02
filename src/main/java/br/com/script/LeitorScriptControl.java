package br.com.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class LeitorScriptControl {
    private File caminhoArquivo;

    public LeitorScriptControl(String caminhoArquivo) {
        this.caminhoArquivo = new File(caminhoArquivo);

    }

    public Map<String, List<Set<String>>> ler() {
        Map<String, List<Set<String>>> listaScripts = new HashMap<>();

        if (!caminhoArquivo.isDirectory()) {
            List<Set<String>> scripts = new ArrayList<>();
            scripts.add(lerArquivo(caminhoArquivo.getAbsolutePath()));
            listaScripts.put(caminhoArquivo.getName().substring(0, caminhoArquivo.getName().lastIndexOf(".")), scripts);
        } else {
            for (File file : Objects.requireNonNull(caminhoArquivo.listFiles())) {
                ArrayList<Set<String>> scripts = new ArrayList<>();
                if (!file.isDirectory()) {
                    scripts.add(lerArquivo(file.getAbsolutePath()));
                    listaScripts.put(file.getName().substring(0, file.getName().lastIndexOf(".")), scripts);
                }
            }

        }
        return listaScripts;
    }

    private Set<String> lerArquivo(String caminhoArquivo) {
        Set<String> scriptsDH4 = new TreeSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
            while (br.ready()) {
                String script = br.readLine();
                if (!script.isEmpty()) {
                    scriptsDH4.add(script.trim());
                }
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return scriptsDH4;
    }
}
