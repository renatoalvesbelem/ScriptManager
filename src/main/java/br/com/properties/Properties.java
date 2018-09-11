package br.com.properties;

public interface Properties {

    String URL_GIT_HTTPS = "https://git-unj.softplan.com.br/saj-tribunais/tj-dbscripts.git";
    String URL_GIT_SSH = "git@git-unj.softplan.com.br:saj-tribunais/tj-dbscripts.git";
    String PASTA_SCRIPT_TMP = "scripts";

    String PASTA_ORACLE = "oracle";
    String PASTA_SQLSERVER = "sqlserver";
    String PASTA_DB2 = "db2";

    String[] PASTAS_BANCOS = {PASTA_DB2, PASTA_ORACLE, PASTA_SQLSERVER};

    String NET = "NET";
    String PG = "PG";
    String SG = "SG";
    String IND = "IND";
    String SAJ = "SAJ";

    String PASTA_GIT = PASTA_SCRIPT_TMP+"/scripts/dbscript/";

}
