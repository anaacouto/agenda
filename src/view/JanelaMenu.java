package view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ContatoControle;
import model.Contato;
import model.Usuario;

public class JanelaMenu extends JFrame {
	//VARIAVEIS
	private Contato contatoAltAntigo;
	private Usuario usuarioLogado;
	private int linhaSelecionada;
	private boolean tabelaVazia;
	private ArrayList<Contato> listaContatos;
	private ContatoControle cc;
	//Objetos da Janela
	private JPanel painelPrincipal, painelVazio, painelInserir, painelAlterar, 
				   painelContatos, painelMenu, painelCards;		
	private JLabel lblAgenda, lblNovoContato, lblAltContato, lblInsNome, 
				   lblInsFone, lblInsConsole, lblBusca, lblUsuario, 
				   lblAltConsole, lblConsole, lblAltNome, lblAltFone;	
	private JTextField txtFBusca, txtFNome, txtFFone, txtFAltNome,txtFAltFone;
	private JButton btnRemContato, btnInsContato, btnNovoContato, btnLogout, btnBusca, 
				    btnAltContato, btnSalvarAltContato, btnHome;
	private CardLayout card_Operations;	
	private GroupLayout groupLayout;
	private JScrollPane painelRolagem;
	private JTable tabela;

	/*Construtor*/
	public JanelaMenu(Usuario usuarioLogado) {	
		this.usuarioLogado = usuarioLogado;
		cc = new ContatoControle();				
		//inicia todos os objetos da janela
		iniciarElementos();
		
		setTitle("Agenda Telefônica");
		lblUsuario.setText("Usuário: "+usuarioLogado.getNome());
		//CONEXAO			
		if(cc.testarConexao()){
			lblConsole.setText("");
		}else{				
			//se não conectar: 
			//desativar todos os elementos da janela
			//e muda a mensagem no console				
			lblConsole.setText("Banco indisponivel!");					
			btnNovoContato.setEnabled(false);
			btnBusca.setEnabled(false);				
			txtFBusca.setEnabled(false);
		}		
		//Define o estado inicial de alguns elementos:	
		card_Operations.show(painelCards, "lista_contatos");//mostra a lista de contatos			
		//desativa os botoes de altera e remover usuarios
		//ate que um contato na lista seja selecionado
		btnAltContato.setEnabled(false);	
		btnRemContato.setEnabled(false);			
		linhaSelecionada = -1; // nenhuma linha selecionada		
		tabela = new JTable();//inicia a tabela para os contatos pegados		
		//coloca os contatos na tabela e mostra na janela:
		loadAllTabelaContatos();
		
		//acoes dos botoes
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mostra o painelContatos
				card_Operations.show(painelCards, "lista_contatos");
				//carrega os contatos na tabela:
				loadAllTabelaContatos();
				//nenhuma linha selecionad - padrão: 
				linhaSelecionada = -1;
				//se a tabela não possui contatos:
				if(!tabelaVazia){
					//desativa os botoes remover e alterar contato
					btnRemContato.setEnabled(false);	
					btnAltContato.setEnabled(false);
					//ativa o botao de novo contato
					btnNovoContato.setEnabled(true);
				}	
				txtFBusca.setText("");
			}
		});	
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//se txtFBusca nao estiver vazio:
				if(!txtFBusca.getText().equals("")){
					//pega a lista com os resultados da busca
					//instancia uma tabela com essa listaa
					listaContatos = cc.getContatosBusca(usuarioLogado, txtFBusca.getText());					
					instanciarTabela(listaContatos);
					//desativa os botoes de remocao e alteracao de contatos
					//ateh uma linha ser selecionada:
					btnRemContato.setEnabled(false);
					btnAltContato.setEnabled(false);	
					//mostra o painelContatos
					card_Operations.show(painelCards, "lista_contatos");
				}
			}
		});				
		btnNovoContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//define os campos de texto vazios
				txtFNome.setText("");
				txtFFone.setText("");
				lblInsConsole.setText("");
				//mostra o painelInserir
				card_Operations.show(painelCards, "novo_contato");					
				//desativa os botoes de novo, alterar e remover
				btnAltContato.setEnabled(false);
				btnRemContato.setEnabled(false);
				btnNovoContato.setEnabled(false);
			}
		});			
		btnInsContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				//se algum dos campos for vazio:
				if(txtFNome.getText().equals("") || txtFFone.getText().equals("")){
					lblInsConsole.setText("Preencha os campos!");//mensagem pro usuario
					//se o nome do contato jah existir na agenda:
				}else if(!(cc.checarNomeValido(txtFNome.getText(), usuarioLogado))){			
					lblInsConsole.setText("Este nome já exite na agenda!");							
				}else{	
					//inserção:
					if(cc.inserirContato(txtFNome.getText(),  txtFFone.getText(), usuarioLogado)){
						lblInsConsole.setText("Contato salvo!");
						txtFNome.setText("");
						txtFFone.setText("");
					}else{
						lblInsConsole.setText("Erro ao salvar!");
					}
				}								
			}
		});	
		btnAltContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				//mostra o painelAlterar
				card_Operations.show(painelCards, "alterar_contato");					
				lblAltConsole.setText("");//define o console sem texto
				//carrega os dados do contato selecionado nos campos de texto:			
				txtFAltNome.setText(listaContatos.get(linhaSelecionada).getNome());
				txtFAltFone.setText(listaContatos.get(linhaSelecionada).getTelefone());
				//salvo o contato a ser alterado:
				contatoAltAntigo = listaContatos.get(linhaSelecionada);								
				//desativa os botes de altera e remover contatos
				//ateh que uma linha seja selecionada:
				btnNovoContato.setEnabled(false);
				btnRemContato.setEnabled(false);
				btnAltContato.setEnabled(false);
			}
		});	
		btnSalvarAltContato.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				//verifica se os campos  estao vazios
				if(txtFAltNome.getText().equals("") || txtFAltFone.getText().equals("")){
					lblAltConsole.setText("Preencha os campos!");
				//verifica se o apenas nome foi alterado e se é valido:
				}else if(contatoAltAntigo.getNome().equals(txtFAltNome.getText()) &&
						contatoAltAntigo.getTelefone().equals(txtFAltFone.getText())&&
						!(cc.checarNomeValido(txtFAltNome.getText(), usuarioLogado))
						){					
					lblAltConsole.setText("Este nome já exite na agenda!");										
				}else{		
					//update:
					if(cc.alterar(txtFAltNome.getText(), txtFAltFone.getText(), contatoAltAntigo)){
						lblAltConsole.setText("Alteração realizada!");
					}else{
						lblAltConsole.setText("Erro na alteração!");
					}	
				}							
			}
		});			
		btnRemContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//salva o contato antes a ser excluido:
				Contato contatoSelecionado = listaContatos.get(linhaSelecionada);				
				//pop-up de confirmação:
				//opcoes da janela:
				Object[] opcao = {"Sim", "Não"};
				//janela em si - retorna o index da opcao selecionada:
				int resposta = JOptionPane.showOptionDialog(null,
								"Remover " + "\"" + contatoSelecionado.getNome() + "\" ?",//mensagem
								"Remover Contato",//titulo
								JOptionPane.YES_NO_OPTION,//tipo opcoes
								JOptionPane.QUESTION_MESSAGE,//tipo janela
								null,     //nao usar um icone
								opcao,  //carrega as opcoes criadas
								opcao[1]); //define uma opcao padrao
				//para resposta sim:
				if(resposta == JOptionPane.YES_OPTION){
					//verifica se foi removido:
					if(cc.remover(contatoSelecionado)){
						//cria uma msg informando o sucesso:
						JOptionPane.showMessageDialog(null,
								"\"" + contatoSelecionado.getNome() + "\"" + " removido!",
							    "Contato removido",
							    JOptionPane.PLAIN_MESSAGE);
					}else{
						//cria uma msg informando o fracasso:
						JOptionPane.showMessageDialog(null,
							    "Não foi possivel remover " + 
						        "\"" + contatoSelecionado.getNome() + "\"",
							    "Erro",
							    JOptionPane.ERROR_MESSAGE);						
					}
				}
				//atualiza a tabela:
				loadAllTabelaContatos();
				//desativa os botoes de alteracao e remocao de contatos:
				btnRemContato.setEnabled(false);	
				btnAltContato.setEnabled(false);
			}
		});		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//abre a janela de login e fecha a janela da agenda:
				new JanelaLogin().setVisible(true);
				dispose();
			}
		});	
		
		//'LISTENERS' da tabela de contato
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				//ativado quando alguma celula da tabela eh selecionada
				if(!tabelaVazia){ 	
					//se a tabela nao estiver vazia
					//pega o index da linha selecionada
					//ativa os botoes de alterar e remover contato
					linhaSelecionada = tabela.getSelectedRow();
					btnRemContato.setEnabled(true);	
					btnAltContato.setEnabled(true);
				}
			}
	    });	
	}//fim construtor;
	//carrega a tabela com os contatos:
	public void loadAllTabelaContatos(){
		listaContatos = cc.getContatos(usuarioLogado);
		instanciarTabela(listaContatos);
	}	
	//instacia as colunas e linhas da tabela,com os contatos informados: 
	public void instanciarTabela( ArrayList<Contato> lista){	
		linhaSelecionada = -1;//nenhuma linha selecionada
		//define o vetor com o nome das colunhas e a matriz para linhas
		String[] colunas = new String[] {"Nome", "Telefone"};	
		Object[][] linhas;//colunas e linhas	
		//se a lista for vazia:
		if(lista.isEmpty() || lista == null){
			//cria uma tabela informando a falta de registros
			linhas = new Object[1][2];//1 linha e 2 colunas
			//dados na linha:
			linhas[0][0] = "sem registros";
			linhas[0][1] = "-";
			tabelaVazia = true;//sinaliza a tabela vazia
		}else{
			//cria a tabela com os dados da lista passada
			//quantidade de linhas = quant de contatos na lista,
			//2 colunas (nome, telefone)
			linhas = new Object[lista.size()][2];
			//percorre a lista informada
			//atribui os dados da lista para uma linha na matriz:
			for(int i = 0; i < lista.size(); i++){
				linhas[i][0] = lista.get(i).getNome();
				linhas[i][1] = lista.get(i).getTelefone();
			}
			tabelaVazia = false;//sinaliza a tabela nao vazia
		}	
		//define as celulas da tabela com nao editaveis:
		tabela.setModel(new DefaultTableModel(linhas, colunas){
			@Override
		    public boolean isCellEditable(int row, int column){
		      return false;
		    }
		});      
		//define atributos da tabela:
		tabela.setBorder(null);       
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);		
		tabela.setFillsViewportHeight(true);			
		tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);		
		painelRolagem.setViewportView(tabela);//coloca a tabela no painelRolagem
		
		//define o alinhamento das celulas (central):
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
   		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
   		for(int x=0;x<tabela.getColumnCount();x++){
   			tabela.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );          
   		}  	
	}	
	//inicia os objetos da janela com seus atributos:
	//Define os atributos de todos os objetos da janela:
	public void iniciarElementos(){
		//Define a operação de encerramento,
		//o tamanho e o titulo da janela
		//e define não redimensionavel
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 480);
		
		//Define os atributos dos paineis
		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		
		painelMenu = new JPanel();
		painelMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		painelCards = new JPanel();			
		painelPrincipal.add(painelCards);
		
		painelContatos = new JPanel();
		painelContatos.setBorder(new LineBorder(new Color(0, 0, 0)));		
				
		painelVazio = new JPanel();
		painelVazio.setBounds(10, 365, 373, 66);
		painelContatos.add(painelVazio);
			
		painelInserir = new JPanel();
		painelInserir.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelInserir.setForeground(Color.LIGHT_GRAY);			
	
		painelAlterar = new JPanel();
		painelAlterar.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelAlterar.setForeground(Color.LIGHT_GRAY);	
		
		
		//Define os layouts usados
		groupLayout = new GroupLayout(painelPrincipal);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(painelMenu, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelCards, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(painelMenu, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
				.addComponent(painelCards, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);		
		painelPrincipal.setLayout(groupLayout);
		
		card_Operations = new CardLayout();				
		painelCards.setLayout(card_Operations);
		
		painelContatos.setLayout(null);	
		painelVazio.setLayout(null);
		painelInserir.setLayout(null);
		painelMenu.setLayout(null);
		painelAlterar.setLayout(null);

		painelRolagem = new JScrollPane();
		painelRolagem.setBounds(10, 60, 373, 300);
		painelContatos.add(painelRolagem);	
		
		//adiciona os paineis lo painelCards
		painelCards.add(painelContatos, "lista_contatos");
		painelCards.add(painelInserir, "novo_contato");
		painelCards.add(painelAlterar, "alterar_contato");		
		
		//Define os atributos dos labels
		lblAltNome = new JLabel("Nome:");
		lblAltNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAltNome.setBounds(25, 60, 79, 14);
		painelAlterar.add(lblAltNome);
		
		lblAltFone = new JLabel("Telefone:");
		lblAltFone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAltFone.setBounds(25, 91, 79, 14);
		painelAlterar.add(lblAltFone);
		
		lblConsole = new JLabel("Console");
		lblConsole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsole.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsole.setBounds(88, 11, 201, 50);
		painelVazio.add(lblConsole);
		
		lblBusca = new JLabel("Pesquisar contato:");
		lblBusca.setBounds(12, 164, 135, 23);
		painelMenu.add(lblBusca);
		
		lblUsuario = new JLabel("Usu\u00E1rio: ");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuario.setBounds(10, 11, 135, 14);
		painelMenu.add(lblUsuario);		
		
		lblNovoContato = new JLabel("Novo contato");
		lblNovoContato.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovoContato.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNovoContato.setBounds(116, 11, 150, 47);
		painelInserir.add(lblNovoContato);
				
		lblAltContato = new JLabel("Alterar Contato");
		lblAltContato.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltContato.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAltContato.setBounds(87, 10, 215, 47);
		painelAlterar.add(lblAltContato);
		
		lblInsConsole = new JLabel("Console");
		lblInsConsole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInsConsole.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsConsole.setVerticalAlignment(SwingConstants.TOP);
		lblInsConsole.setBounds(25, 191, 348, 229);
		painelInserir.add(lblInsConsole);
		
		lblAltConsole = new JLabel("Console");
		lblAltConsole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAltConsole.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltConsole.setVerticalAlignment(SwingConstants.TOP);
		lblAltConsole.setBounds(25, 191, 348, 229);
		painelAlterar.add(lblAltConsole);
				
		lblInsNome = new JLabel("Nome:");
		lblInsNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInsNome.setBounds(25, 60, 79, 14);
		painelInserir.add(lblInsNome);
		
		lblInsFone = new JLabel("Telefone:");
		lblInsFone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInsFone.setBounds(25, 91, 79, 14);
		painelInserir.add(lblInsFone);
				
		lblAgenda = new JLabel("-Lista de Contatos-");
		lblAgenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgenda.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAgenda.setBounds(10, 11, 373, 47);
		painelContatos.add(lblAgenda);
		
		//Define os atributos dos campos de texto
		txtFNome = new JTextField();
		txtFNome.setText("");
		txtFNome.setBounds(85, 60, 274, 20);
		painelInserir.add(txtFNome);
		txtFNome.setColumns(10);
		
		txtFFone = new JTextField();
		txtFFone.setBounds(85, 91, 274, 20);
		painelInserir.add(txtFFone);
		txtFFone.setColumns(10);
		
		txtFBusca = new JTextField();
		txtFBusca.setToolTipText("Pesquisar contato");
		txtFBusca.setBounds(10, 187, 135, 23);
		painelMenu.add(txtFBusca);
		txtFBusca.setColumns(10);		

		txtFAltNome = new JTextField();	
		txtFAltNome.setBounds(85, 60, 274, 20);
		painelAlterar.add(txtFAltNome);
		txtFAltNome.setColumns(10);
		
		txtFAltFone = new JTextField();
		txtFAltFone.setBounds(85, 91, 274, 20);
		painelAlterar.add(txtFAltFone);
		txtFAltFone.setColumns(10);
		
		//Define os atributos dos botoes
		btnInsContato = new JButton("Salvar");		
		btnInsContato.setBounds(224, 122, 135, 23);
		painelInserir.add(btnInsContato);
		
		btnSalvarAltContato = new JButton("Salvar");		
		btnSalvarAltContato.setBounds(224, 122, 135, 23);
		painelAlterar.add(btnSalvarAltContato);
		
		btnBusca = new JButton("Pesquisar");
		btnBusca.setToolTipText("");
		btnBusca.setPreferredSize(new Dimension(135, 23));
		btnBusca.setBounds(10, 215, 135, 23);
		painelMenu.add(btnBusca);
		
		btnNovoContato = new JButton("Novo contato");		
		btnNovoContato.setToolTipText("Adicionar um novo contato");
		btnNovoContato.setBounds(10, 80, 135, 23);
		painelMenu.add(btnNovoContato);
		
		btnHome = new JButton("Contatos");		
		btnHome.setToolTipText("Lista de contatos do usu\u00E1rio");
		btnHome.setBounds(10, 50, 135, 23);
		btnHome.setIcon(new ImageIcon(JanelaMenu.class.getResource("/com/sun/java/swing/plaf/windows/icons/HomeFolder.gif")));
		painelMenu.add(btnHome);
		
		btnLogout = new JButton("SAIR");		
		btnLogout.setToolTipText("Sair da agenda");
		btnLogout.setBounds(10, 397, 135, 23);
		painelMenu.add(btnLogout);
		
		btnAltContato = new JButton("Alterar Contato");
		btnAltContato.setToolTipText("Alterar o contato selecionado");
		btnAltContato.setBounds(10, 110, 135, 23);
		painelMenu.add(btnAltContato);
			
		
		btnRemContato = new JButton("Remover Contato");
		btnRemContato.setToolTipText("Remover o contato selecionado");
		btnRemContato.setBounds(10, 140, 135, 23);
		painelMenu.add(btnRemContato);		
		
	}

	/*Executa a janela*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario teste = new Usuario();
					teste.setNome("teste");
					teste.setSenha("12345");
					teste.setId(9);
					JanelaMenu frame = new JanelaMenu(teste);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
