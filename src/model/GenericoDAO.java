package model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class GenericoDAO{
	private Connection con = null;
	
	protected GenericoDAO (){
		this.con = FabricaConexao.getConnection();
	}	
	protected Connection getConnection(){	
        return con;
    }
	protected boolean inserir(String sql, Object...dados){
		try{
			PreparedStatement stm = getConnection().prepareStatement(sql);					
	        for (int i = 0; i < dados.length; i++) {
	            stm.setObject(i+1, dados[i]);
	        }			
			stm.execute();
			stm.close();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	protected boolean remover(String sql, Object...dados){
		try{
			PreparedStatement stm = getConnection().prepareStatement(sql);				
			for (int i = 0; i < dados.length; i++) {
				stm.setObject(i+1, dados[i]);
			}						
			stm.execute();
			stm.close();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	protected boolean alterar(String sql, Object...dados){
		try{
			PreparedStatement stm = getConnection().prepareStatement(sql);					
	        
			for (int i = 0; i < dados.length; i++) {
	            stm.setObject(i+1, dados[i]);
	        }
	        			
			stm.execute();
			stm.close();
			con.close();
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
}
