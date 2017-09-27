package model;

import java.sql.*;

public class FabricaConexao {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String LINK = "jdbc:mysql://localhost/agendapoo";
	private static final String USER = "root";
	private static final String PASS = "";
	
	public static Connection getConnection() {
		try {			
			Class.forName(DRIVER);
			return DriverManager.getConnection(LINK, USER, PASS);
		} catch (SQLException  e) {
			System.out.println(e.getMessage());		    
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());			
		}
		return null;		
	}
	//teste:
	public static void main(String[] args) {	
		/*	
		Connection con = new FabricaConexao().getConnection();
		if(con != null){System.out.println("hehe");}else{
			System.out.println("haha");
		}
		*/
		Usuario t = new Usuario();
		t.setNome("teste2222");
		t.setSenha("12345");
		UsuarioDAO x = new UsuarioDAO();
		if(x.inserir(t)){
			System.out.println("hehe");
		}else{
			System.out.println("haha");
		}
		
	}
}
