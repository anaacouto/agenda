package controller;

import java.sql.Connection;

import model.FabricaConexao;
import model.Usuario;
import model.UsuarioDAO;

public class UsuarioControle {
	public boolean testarConexao(){
		Connection con =  FabricaConexao.getConnection();
		if(con != null) return true;
		return false;
	}
	public Usuario acharUsuario(String nome, String senha){
		Usuario info = new Usuario();
		info.setNome(nome);
		info.setSenha(senha);
		
		UsuarioDAO dao = new UsuarioDAO();
		return dao.acharUsuario(info);
	}
	public boolean checarNome(String nome){
		UsuarioDAO dao = new UsuarioDAO();
		return dao.checarNome(nome);
	}
	public boolean inserirUsuario(String nome, String senha){
		UsuarioDAO dao = new UsuarioDAO();
		Usuario novo = new Usuario();
		novo.setNome(nome);
		novo.setSenha(senha);
		
		if(dao.inserir(novo))return true;
		return false;		
	}	
}
