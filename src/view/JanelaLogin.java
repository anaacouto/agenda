package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.UsuarioControle;
import model.Usuario;


public class JanelaLogin extends JFrame {
	//OBJETOS DA JANELA
	private UsuarioControle  uc;
	private JPanel painelPrincipal, painelSecundario;
	private JTextField txtFNomeUsuario;
	private JPasswordField pswFSenhaUsuario;
	private JLabel lblUsuario, lblSenha, lblConsole;
	private JButton btnSignIn, btnLogin;
	
	/* Constutor da janela*/
	public JanelaLogin() {
		uc = new UsuarioControle();
		instanciar();	
		//CONEXAO 
		if(uc.testarConexao()){
			//se conectado
			//lblConsole.setText("");
		}else{
			//se não conectar: 
			//desativar todos os elementos da janela
			//e muda a mensagem no lblconsole
			lblUsuario.setEnabled(false);
			lblSenha.setEnabled(false);			
			txtFNomeUsuario.setEnabled(false);
			pswFSenhaUsuario.setEnabled(false);			
			btnSignIn.setEnabled(false);
			btnLogin.setEnabled(false);			
			lblConsole.setText("Banco de dados indisponivel!");
		}	
		//Acoes dos botoes
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Instancia uma nova JanelaCadastro:
				new JanelaCadastro().setVisible(true);
				dispose();//destroi a janela atual
			}
		});		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {					
				String nome = txtFNomeUsuario.getText();
				String senha = pswFSenhaUsuario.getText();
				//se os campos forem vazios:
				if(nome.equals("") || senha.equals("")){
					lblConsole.setText("Informe os dados!");
				//se o nome não existir no BD:
				}else if(!uc.checarNome(nome)){
					lblConsole.setText("Usuário não existe!");
				}else{
					Usuario usuarioLogado = uc.acharUsuario(nome, senha);
					//autenticação dos dados:
					if(usuarioLogado == null){
						lblConsole.setText("Dados Incorretos!");
					}else{
						//cria o menu:
						new JanelaMenu(usuarioLogado).setVisible(true);
						//detroi a janela atual:
						dispose();
					}						
				}				
			}
		});						
	}	
	private void instanciar(){
		//Define a operação de encerramento,
		//o tamanho e o titulo da janela
		//e define não redimensionavel
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 249, 300);	
		this.setResizable(false);
		this.setTitle("Login");
		
		//Define os atributos do painelPrincipal
		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		//Define os atributos do painelSecundario
		painelSecundario = new JPanel();
		painelSecundario.setBounds(0, 11, 245, 148);
		painelPrincipal.add(painelSecundario);
		painelSecundario.setLayout(null);
		
		//Define os atributos inciais de todos os labels
		lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(52, 16, 140, 14);
		painelSecundario.add(lblUsuario);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setBounds(52, 82, 140, 14);
		painelSecundario.add(lblSenha);
		
		lblConsole = new JLabel("<html><p>N\u00E3o possui uma conta?</p><p>&nbsp;&nbsp;Clique em Registrar</p></html>");
		lblConsole.setForeground(Color.BLACK);
		lblConsole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsole.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsole.setVerticalAlignment(SwingConstants.CENTER);
		lblConsole.setBounds(0, 199, 245, 51);
		painelPrincipal.add(lblConsole);
		
		//Define os atributos dos botoes
		btnLogin = new JButton("Entrar");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(128, 165, 100, 23);
		painelPrincipal.add(btnLogin);
		
		btnSignIn = new JButton("Registrar");		
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSignIn.setBounds(14, 165, 100, 23);
		painelPrincipal.add(btnSignIn);
		
		//Define os atributos dos campos de texto e senha
		txtFNomeUsuario = new JTextField();
		txtFNomeUsuario.setBounds(52, 40, 140, 20);
		painelSecundario.add(txtFNomeUsuario);
		txtFNomeUsuario.setColumns(10);
		
		pswFSenhaUsuario = new JPasswordField();
		pswFSenhaUsuario.setBounds(52, 106, 140, 20);
		painelSecundario.add(pswFSenhaUsuario);
	}
	/*Executa a classe*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaLogin frame = new JanelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
