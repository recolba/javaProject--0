package project.dao;

import java.sql.*;
import java.util.*;
import project.conexao.ConexaoException;
import project.conexao.ConexaoJavaDb;
import project.entidade.Campeonato;

public class CampeonatoDao {

    private final static String sqlCreateTable = "CREATE TABLE campeonato "
            + "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
            + "nome VARCHAR(100) NOT NULL,"
            + "PRIMARY KEY(id))";
    private final String sqlC = "INSERT INTO campeonato (nome) VALUES ('?')";
    private final String sqlR = "SELECT * FROM campeonato";
    private final String sqlU = "UPDATE campeonato SET nome=? WHERE id=?";
    private final String sqlD = "DELETE FROM campeonato WHERE id=?";
    private final String sqlRById = "SELECT * FROM campeonato WHERE id=?";
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;
    public CampeonatoDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
		try {
			Connection con = conexao.getConnection();
            try {
                Statement stm = con.createStatement();
                stm.execute(sqlCreateTable);
                System.out.println("Tabela criada com sucesso!");
            } catch( SQLException ex ) {
                System.out.println("Tabela já existe!");
            }           
			stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);
			stmRById = con.prepareStatement(sqlRById);
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao preparar statement: " + ex.getMessage());
		}
	}
    
	public long create(Campeonato c) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, c.getNome());
			int r = stmC.executeUpdate();
			ResultSet rs = stmC.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao criar registro: " + ex.getMessage());
		}
		return id;
	}
	public List<Campeonato> read() throws DaoException {
		List<Campeonato> campeonatos = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");			
				Campeonato c = new Campeonato(id, nome);
				campeonatos.add(c);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return campeonatos;
	}
	public void update(Campeonato c) throws DaoException {
		try {
			stmU.setString(1, c.getNome());			
			stmU.setLong(2, c.getId());
			int r = stmU.executeUpdate();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
		}
	}
	public void delete(long id) throws DaoException {
		try {
			stmD.setLong(1, id);
			int r = stmD.executeUpdate();
		} catch(SQLException ex) {
			throw new DaoException("Falha ao apagar registro: " + ex.getMessage());
		}
	}
	public void close() throws DaoException {
		try {
			stmC.close();
			stmR.close();
			stmU.close();
			stmD.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
		}
	}	 

	public Campeonato readById(long id) throws DaoException {
		Campeonato c = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {				
				String nome = rs.getString("nome");
				c = new Campeonato(id,nome);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return c;
	}

}