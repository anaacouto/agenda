package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ContatoDAO extends GenericoDAO{	
	public ContatoDAO() {
		super();
	}
	public boolean inserir(Contato contato){
		String sql = "INSERT INTO contato(nome, telefone, usuario_id) VALUES (?, ?, ?);";
		return this.inserir(sql, contato.getNome(), contato.getTelefone(), contato.getIdUsuario());
	}
	public boolean remover(int id){
		String sql = "DELETE FROM contato WHERE id = ?;";
		return this.remover(sql, id);
	}
	public boolean alterar(Contato novo){
		String sql = "UPDATE contato SET nome = ?,telefone = ? WHERE id = ?;";
		return this.alterar(sql, novo.getNome(), novo.getTelefone(), novo.getId());
	}
	public ArrayList<Contato> getContatos(int idUsuario){		
		String sql = "SELECT * FROM contato WHERE usuario_id = ? ORDER BY nome ASC;";
		try{
			ArrayList<Contato> lista = new ArrayList<Contato>();
			PreparedStatement stm = getConnection().prepareStatement(sql);					
			stm.setInt(1, idUsuario);	
			ResultSet r  = stm.executeQuery();
			while(r.next()){
				Contato c = new Contato();
				
				c.setNome(r.getString("nome"));
				c.setTelefone(r.getString("telefone"));
				c.setId(r.getInt("id"));
				c.setIdUsuario(r.getInt("usuario_id"));
				
				lista.add(c);
			}
			r.close();
			stm.close();
			getConnection().close();
			return lista;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean checarNomeValido(String nome, int idUsuario){		
		String sql = "SELECT * FROM contato WHERE usuario_id = ? AND nome LIKE ? ORDER BY nome ASC;";
		try{
			PreparedStatement stm = getConnection().prepareStatement(sql);					
			stm.setInt(1, idUsuario);
			stm.setString(2, nome+"%");
			ResultSet r  = stm.executeQuery();
			while(r.next()){
				return false;
			}
			r.close();
			stm.close();
			getConnection().close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	public ArrayList<Contato> getContatosBusca(Usuario usuarioLogado, String busca){		
		String sql = "SELECT * FROM contato WHERE nome  LIKE ? AND usuario_id = ? ORDER BY nome ASC";
		try{
			ArrayList<Contato> lista = new ArrayList<Contato>();
			PreparedStatement stm = getConnection().prepareStatement(sql);					
			stm.setString(1, "%"+busca+"%");	
			stm.setInt(2, usuarioLogado.getId());
			ResultSet r  = stm.executeQuery();
			while(r.next()){
				Contato c = new Contato();
				
				c.setNome(r.getString("nome"));
				c.setTelefone(r.getString("telefone"));
				c.setId(r.getInt("id"));
				c.setIdUsuario(r.getInt("usuario_id"));
				
				lista.add(c);
			}
			r.close();
			stm.close();
			getConnection().close();
			return lista;
		}catch(Exception e){
			System.out.println("sql: "+e.getMessage());
		}
		return null;
	}
	
}


