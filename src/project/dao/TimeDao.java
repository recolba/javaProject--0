package project.dao;

import java.sql.*;
import java.util.*;
import project.conexao.ConexaoException;
import project.conexao.ConexaoJavaDb;
import project.entidade.Time;

public class TimeDao {
    private final static String sqlCreateTable = "CREATE TABLE time " 
        + "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
        + "nome VARCHAR(100) NOT NULL,"
        + "anf INT NOT NULL,"
        + "cidade VARCHAR(100) NOT NULL,"
        + "estado VARCHAR(100) NOT NULL,"    
        + "PRIMARY KEY (id))";
	private final String sqlC = "INSERT INTO time (nome, anf, cidade, estado) VALUES (?,?,?,?)";
	private final String sqlR = "SELECT * FROM time";
	private final String sqlU = "UPDATE time SET nome=?, anf=?, cidade=?, estado=? WHERE id=?";
	private final String sqlD = "DELETE FROM time WHERE id=?";	
	private final String sqlRById = "SELECT * FROM time WHERE id=?";
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;
	private PreparedStatement stmRById;
	public TimeDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
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
    
	public long create(Time t) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, t.getNome());
			stmC.setInt(2, t.getAnf());
                        stmC.setString(3, t.getCidade());
                        stmC.setString(4, t.getEstado());
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
        
        public List<Time> read() throws DaoException {
		List<Time> times = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
                                int anf = rs.getInt("anf");
                                String cidade = rs.getString("cidade");
                                String estado = rs.getString("estado");
				Time t = new Time(id, nome, anf, cidade, estado);
				times.add(t);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return times;
	}
	public void update(Time t) throws DaoException {
		try {
			stmU.setString(1, t.getNome());
                        stmU.setInt(2, t.getAnf());
                        stmU.setString(3, t.getCidade());
                        stmU.setString(4, t.getEstado());
			stmU.setLong(5, t.getId());
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

	public Time readById(long id) throws DaoException {
		Time t = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {				
				String nome = rs.getString("nome");
                                int anf = rs.getInt("anf");
                                String cidade = rs.getString("cidade");
                                String estado = rs.getString("estado");
				t = new Time(id,nome, anf, cidade, estado);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return t;
	}

}
