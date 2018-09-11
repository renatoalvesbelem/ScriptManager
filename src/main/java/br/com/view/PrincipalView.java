package br.com.view;

import br.com.git.GitSoftplan;
import br.com.script.LeitorScriptControl;
import br.com.script.DiretorioControl;

import javax.swing.*;
import javax.swing.JTextArea;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTextField txtCaminhoScripts, txtUsuario;
    private JPasswordField txtSenha;
    private JLabel lbUsuario, lbSenha;
    private JCheckBox ckbTipoClone;
    private JButton btSelecionarArquivo, btGerarArquivos;
    private static JTextArea areLog;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new PrincipalView().init();

    }

    private void init() {
        jFrame = configurarFrame();
        jPanel = configurarJpanel();
        jFrame.add(jPanel);

        btSelecionarArquivo = configurarBotao("Selecionar", 30, 110, 1, 1);
        selecionarBotaoSelecionarArquivo(btSelecionarArquivo);
        adicionarElementoNoPainel(btSelecionarArquivo);

        txtCaminhoScripts = configurarCampoTexto(30, 500, 115, 1);
        adicionarElementoNoPainel(txtCaminhoScripts);

        lbUsuario = configurarLabel("Usuário", 30, 60, 45, 40);
        adicionarElementoNoPainel(lbUsuario);
        txtUsuario = configurarCampoTexto(30, 400, 115, 40);
        adicionarElementoNoPainel(txtUsuario);

        lbSenha = configurarLabel("Senha", 30, 60, 45, 80);
        adicionarElementoNoPainel(lbSenha);
        txtSenha = configurarCampoPassword(30, 400, 115, 80);
        adicionarElementoNoPainel(txtSenha);

        areLog = configurarJtextArea();
        adicionarElementoNoPainel(configurarJScrollPane(areLog));

        btGerarArquivos = configurarBotao("Gerar Arquivos", 30, 150, 1, 500);
        adicionarElementoNoPainel(btGerarArquivos);
        selecionarBotaoGerarArquivo(btGerarArquivos);

        jFrame.setVisible(true);
    }

    private JFrame configurarFrame() {
        jFrame = new JFrame();
        jFrame.setSize(new Dimension(800, 600));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return jFrame;
    }

    private JPanel configurarJpanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setSize(new Dimension(800, 600));
        return jPanel;
    }

    private JButton configurarBotao(String nomeBotao, int altura, int largura, int posicaoX, int posicaoY) {
        JButton jButton = new JButton(nomeBotao);
        jButton.setSize(largura, altura);
        jButton.setLocation(posicaoX, posicaoY);
        return jButton;
    }

    private void adicionarElementoNoPainel(JComponent component) {
        jPanel.add(component);
    }

    private JTextField configurarCampoTexto(int altura, int largura, int posicaoX, int posicaoY) {
        JTextField jTextField = new JTextField();
        jTextField.setSize(largura, altura);
        jTextField.setLocation(posicaoX, posicaoY);
        return jTextField;
    }

    private JLabel configurarLabel(String texto, int altura, int largura, int posicaoX, int posicaoY) {
        JLabel jLabel = new JLabel(texto);
        jLabel.setSize(largura, altura);
        jLabel.setLocation(posicaoX, posicaoY);
        return jLabel;
    }

    private JPasswordField configurarCampoPassword(int altura, int largura, int posicaoX, int posicaoY) {
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setSize(largura, altura);
        jPasswordField.setLocation(posicaoX, posicaoY);
        return jPasswordField;

    }

    private void selecionarBotaoSelecionarArquivo(final JButton jButton) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(txtCaminhoScripts.getText());
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.showOpenDialog(null);
                txtCaminhoScripts.setText(fileChooser.getSelectedFile().toString());
            }
        });

    }

    private void selecionarBotaoGerarArquivo(final JButton jButton) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GitSoftplan gitSoftplan = new GitSoftplan();
                if (!txtCaminhoScripts.getText().isEmpty()) {
                    LeitorScriptControl leitorScriptControl = new LeitorScriptControl(txtCaminhoScripts.getText());
                    if (validarDadosObrigatorios()) {
                        gitSoftplan.clonandoRepositorio(txtUsuario.getText(), new String(txtSenha.getPassword()));
                        new DiretorioControl(leitorScriptControl.ler(), txtCaminhoScripts.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário e senha devem ser informador");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Arquivo ou pasta do script deve ser informado");
                }
            }

        });
    }

    private boolean validarDadosObrigatorios() {
        return !txtUsuario.getText().isEmpty() & txtSenha.getPassword().length > 0;
    }

    private JTextArea configurarJtextArea() {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        return jTextArea;
    }


    private JScrollPane configurarJScrollPane(JTextArea jTextArea) {
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setLocation(50, 150);
        jScrollPane.setSize(600, 200);
        return jScrollPane;
    }

    public static void adicionarLinhaAreaLog(String linha) {
        areLog.append(linha + "\n\n");
    }

}
