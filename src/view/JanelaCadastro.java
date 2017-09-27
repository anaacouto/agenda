package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import controller.UsuarioControle;


public class JanelaCadastro extends JFrame {
	private UsuarioControle  uc;
	//OBJETOS DA JANELA
	private JPanel painelPrimario, painelSec, painelTerc;
	private JTextField txtFNomeUsuario;
	private JPasswordField txtFSenha, txtFSenhaConf;
	private JButton btnRegistrar, btnCancelar; 	
	private GroupLayout groupLayoutPanels;
	private JLabel lblInfo, lblUsuario, lblSenha, lblSenhaConf, lblConsole;

	/*Construtor*/
	public JanelaCadastro() {		
		instanciar();
		uc = new UsuarioControle();
		//CONEXAO
		if(uc.testarConexao()){
			//se conectar
			lblConsole.setText("");
		}else{
			//se não conectar: desativar todos os elementos da janela
			//e muda a mensagem no console
			lblInfo.setEnabled(false);
			lblUsuario.setEnabled(false);
			lblSenha.setEnabled(false);
			lblSenhaConf.setEnabled(false);			
			btnRegistrar.setEnabled(false);			
			lblConsole.setText("Banco de dados indisponivel!");
		}
		// 'LISTENERs' dos campos de texto
		//quando algo eh digitado ou apagado esses metodos sao chamados
		txtFSenha.addKeyListener(new KeyAdapter() {
			@Override
		    public void  keyReleased(KeyEvent event) {				
				checarCampos();			
		    }
		});
		txtFSenhaConf.addKeyListener(new KeyAdapter() {
			@Override
		    public void  keyReleased(KeyEvent event) {
				checarCampos();
		    }
		});
		txtFNomeUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void  keyReleased(KeyEvent e) {
				checarCampos();
			}
		});	
		
		//Acoes dos botoes
		//Registrar:
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				String nome  = txtFNomeUsuario.getText();
				String senha = txtFSenha.getText();			
				if(uc.inserirUsuario(nome, senha)){
					lblConsole.setText("Cadastro realizado com sucesso!");
				}else{
					lblConsole.setText("Erro ao cadastrar!");
				}				
			}
		});
		//Cancelar:
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Instancia uma JanelaLogin 
				new JanelaLogin().setVisible(true);
				dispose();//dispensa a janela atual		
			}
		});	
		
	}
	private void instanciar(){
		//Define a operação de encerramento,
		//o tamanho e o titulo da janela
		//e define não redimensionavel
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 260, 300);		
		this.setResizable(false);
		this.setTitle("Cadastro");
		
		//Define os atributos dos paineis
		painelPrimario = new JPanel();
		painelPrimario.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrimario);		
		
		painelSec = new JPanel();
		painelTerc = new JPanel();
		
		//Cria um layout do tipo group e define seus atributos
		//Sera aplicado no painelPrimario
		//para agrupar o painelSecundario e o painelTerciario
		groupLayoutPanels = new GroupLayout(painelPrimario);
		groupLayoutPanels.setHorizontalGroup(
				groupLayoutPanels.createParallelGroup(Alignment.LEADING)
				.addComponent(painelSec, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
				.addComponent(painelTerc, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
		);		
		groupLayoutPanels.setVerticalGroup(
				groupLayoutPanels.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayoutPanels.createSequentialGroup()
					.addComponent(painelSec, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(painelTerc, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		//Define os atributos dos labels
		lblInfo = new JLabel("Preencha:");
		lblInfo.setBounds(42, 5, 150, 14);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		painelSec.add(lblInfo);
		
		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(67, 30, 100, 14);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelSec.add(lblUsuario);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(67, 74, 100, 14);
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelSec.add(lblSenha);
		
		lblSenhaConf = new JLabel("Repita a senha:");
		lblSenhaConf.setBounds(67, 118, 100, 14);
		lblSenhaConf.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenhaConf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelSec.add(lblSenhaConf);
		
		lblConsole = new JLabel("Console");
		lblConsole.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsole.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConsole.setBounds(0, 32, 234, 45);
		painelTerc.add(lblConsole);		
		
		//Define os atributos dos campos de texto e senha
		txtFNomeUsuario = new JTextField();		
		txtFNomeUsuario.setBounds(17, 49, 200, 20);
		txtFNomeUsuario.setText("");
		txtFNomeUsuario.setColumns(10);
		painelSec.add(txtFNomeUsuario);
		
		txtFSenha= new JPasswordField();		
		txtFSenha.setBounds(17, 93, 200, 20);
		txtFSenha.setColumns(10);
		painelSec.add(txtFSenha);
		
		txtFSenhaConf = new JPasswordField();
		txtFSenhaConf.setBounds(17, 137, 200, 20);
		txtFSenhaConf.setMinimumSize(new Dimension(50, 20));
		txtFSenhaConf.setColumns(10);
		painelSec.add(txtFSenhaConf);
		
		//Define os atributos dos botoes
		btnRegistrar = new JButton("Registrar");		
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRegistrar.setBounds(122, 5, 100, 23);
		painelTerc.add(btnRegistrar);
		
		btnCancelar = new JButton("Voltar");		
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(11, 5, 100, 23);
		painelTerc.add(btnCancelar);
		
		//Define o layout dos paineis
		painelPrimario.setLayout(groupLayoutPanels);
		painelSec.setLayout(null);
		painelTerc.setLayout(null);
		
		btnRegistrar.setEnabled(false);		
	}
	/*Checa os campos de texto por:
	 * Campos vazios;
	 * Usuario ja existente na base de dados
	 * Senhas incompativeis
	 */
	private void checarCampos(){
		//se os campos estiverem vazios:
		if(txtFNomeUsuario.getText().equals("") ||  
				txtFSenha.getText().trim().equals("")  ||  
				txtFSenhaConf.getText().trim().equals("")
		  ){
			btnRegistrar.setEnabled(false);
			lblConsole.setText("");
		//se o nome jah estiver cadastrado:
		}else if(uc.checarNome(txtFNomeUsuario.getText())){
			btnRegistrar.setEnabled(false);
			lblConsole.setText("Usuário já cadastrado!");
		//se as senhas não forem iguas:
		}else if( !(txtFSenha.getText().equals( txtFSenhaConf.getText() ))){
			btnRegistrar.setEnabled(false);
			lblConsole.setText("Senhas incompatíveis!");
		}else{		
			lblConsole.setText("");
			btnRegistrar.setEnabled(true);
		}
	}
	
	/*Executa a classe*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastro frame = new JanelaCadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
}
