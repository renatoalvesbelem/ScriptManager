package br.com.git;

import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.*;

import static br.com.properties.Properties.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitSoftplan {
    private String usuario, senha;
    private File diretorioScript = new File(PASTA_SCRIPT_TMP);

    public GitSoftplan() {
    }

    public void clonandoRepositorio(String usuario, String senha) {

        try {
            CloneCommand cloneCommand = Git.cloneRepository();
            cloneCommand.setURI(URL_GIT_HTTPS);
            cloneCommand.setDirectory(diretorioScript);
            cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(usuario, senha));

            if (diretorioScript.exists()) {
                Git.open(diretorioScript).pull();
            } else {
                cloneCommand.call();
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listaBranchesRemotos() throws IOException, GitAPIException {
        try (Repository repository = Git.open(new File(PASTA_SCRIPT_TMP)).getRepository()) {
            System.out.println("Listing local branches:");
            try (Git git = new Git(repository)) {
                List<Ref> call = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
                for (Ref ref : call) {
                    System.out.println(ref.getName());
                }
            }
        }
    }

}

