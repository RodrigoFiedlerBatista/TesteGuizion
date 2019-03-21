package model.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Endereco;

public class EnderecoDAO {
    
    public void addEndereco(Endereco endereco) {
        String sql = "insert into endereco(cep, uf, bairro, logradouro, complemento, ibge, gia, localidade) values (? ,?, ?, ?, ?, ?, ?, ?);";
        ConnectionFactory con = new ConnectionFactory();
        try {
            PreparedStatement stmt = con.getConnection().prepareStatement(sql);
            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getUf());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getLogradouro());
            stmt.setString(5, endereco.getComplemento());
            stmt.setString(6, endereco.getIbge());
            stmt.setString(7, endereco.getGia());
            stmt.setString(8, endereco.getLocalidade());
            stmt.execute();
            con.getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public ObservableList<Endereco> selectEndereco() {
        ObservableList<Endereco> enderecos = FXCollections.observableArrayList();
        String sql = "select * from endereco order by id_endereco asc;";
        ConnectionFactory con = new ConnectionFactory();
        try {
            PreparedStatement stmt = con.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Endereco endereco = new Endereco();
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCep(rs.getString("cep"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setGia(rs.getString("gia"));
                endereco.setIbge(rs.getString("ibge"));
                endereco.setId_endereco(rs.getInt("id_endereco"));
                endereco.setLocalidade(rs.getString("localidade"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setUf(rs.getString("uf"));
                enderecos.add(endereco);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return enderecos;
    }
    
    
}
