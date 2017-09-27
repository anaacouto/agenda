package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO extends GenericoDAO{
	public UsuarioDAO() {
		super();
	}
	public boolean inserir(Usuario usuario){
		String sql = "INSERT INTO usuario (nome, senha) VALUES (?,?);";
		return this.inserir(sql, usuario.getNome(), usuario.getSenha());
	}
	public Usuario acharUsuario(Usuario usuario){		
		String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?;";
		try{
			Usuario retorno = null;
			PreparedStatement stm = getConnection().prepareStatement(sql);					
	        
	        stm.setString(1, usuario.getNome());
	        stm.setString(2, usuario.getSenha());	
			ResultSet r  = stm.executeQuery();
			
			while(r.next()){
			System.out.println("null");
				retorno = new Usuario();
				retorno.setNome( r.getString("nome"));
				retorno.setSenha( r.getString("senha"));
				retorno.setId( r.getInt("id"));
			}
			r.close();
			stm.close();
			getConnection().close();
			return retorno;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean checarNome(String nome){
		String sql = "SELECT * FROM usuario WHERE nome = ?;";
		try{
			PreparedStatement stm = getConnection().prepareStatement(sql);					
			stm.setString(1, nome);	
			ResultSet r  = stm.executeQuery();
			while(r.next()){
				return true;
			}
			r.close();
			stm.close();
			getConnection().close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
}
