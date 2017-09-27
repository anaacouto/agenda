package controller;

import java.sql.Connection;
import java.util.ArrayList;
import model.Contato;
import model.ContatoDAO;
import model.FabricaConexao;
import model.Usuario;

public class ContatoControle {
	public boolean testarConexao(){
		Connection con =  FabricaConexao.getConnection();
		if(con != null) return true;
		return false;
	}
	public boolean inserirContato(String nome, String telefone, Usuario usuario){
		ContatoDAO dao = new ContatoDAO();
		Contato novo = new Contato();
		novo.setNome(nome);
		novo.setTelefone(telefone);
		novo.setIdUsuario(usuario.getId());
		
		if(dao.inserir(novo))return true;
		return false;		
	}
	public ArrayList<Contato> getContatos(Usuario usuarioLogado){
		ContatoDAO dao = new ContatoDAO();
		return dao.getContatos(usuarioLogado.getId());
	}
	public boolean alterar(String nome, String telefone, Contato antigo){
		Contato novo = new Contato();
		novo.setNome(nome);
		novo.setTelefone(telefone);
		novo.setId(antigo.getId());
		novo.setIdUsuario(antigo.getIdUsuario());
		
		ContatoDAO dao = new ContatoDAO();
		return dao.alterar(novo);		
	}

	public boolean remover(Contato contato){		
		ContatoDAO dao = new ContatoDAO();
		return dao.remover(contato.getId());		
	}
	public boolean checarNomeValido(String nome, Usuario usuarioLogado){
		ContatoDAO dao = new ContatoDAO();
		return dao.checarNomeValido(nome, usuarioLogado.getId());
	}
	public ArrayList<Contato> getContatosBusca(Usuario usuarioLogado, String busca){
		ContatoDAO dao = new ContatoDAO();
		return dao.getContatosBusca(usuarioLogado, busca);
	}
}
