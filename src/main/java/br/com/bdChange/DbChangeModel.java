package br.com.bdChange;

public class DbChangeModel {

    private String cabecalho = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
    private String inicioScripts = "<havillan>";
    private String inicioScipt = "<script a_name=\"";

    private String fimScipt = "\"/>";
    private String descricaoScript = "\" version=\"99.99.99\" z_description=\"";

    private String fimScripts = "</havillan>";

    public String getCabecalho() {
        return cabecalho;
    }

    public String getInicioScripts() {
        return inicioScripts;
    }

    public String getScript(String nomeScript, String descicaoOpcionalScript) {
        String linhaScript;
        if (descicaoOpcionalScript == null || descicaoOpcionalScript.isEmpty()) {
            descicaoOpcionalScript = "Sem descição";
        }
        linhaScript = inicioScipt + nomeScript + descricaoScript + descicaoOpcionalScript + fimScipt;
        return linhaScript;
    }

    public String getfimScripts() {
        return fimScripts;
    }
}
