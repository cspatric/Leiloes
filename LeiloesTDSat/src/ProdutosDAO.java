/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        try {
            conectaDAO conexao = new conectaDAO();
            conexao.conectar();
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql, new String[]{"ID"});

            consulta.setString(1, produto.getNome());
            consulta.setInt(2, produto.getValor());
            consulta.setString(3, produto.getStatus());

            int status = consulta.executeUpdate();

            if (status == 1) {
                ResultSet generatedKeys = consulta.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
                    System.out.println("Produto cadastrado com sucesso. ID gerado: " + idGerado);
                } else {
                    System.out.println("Erro ao recuperar o ID gerado.");
                }
            } else {
                System.out.println("Erro ao cadastrar o produto.");
            }

            conexao.desconectar();

        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar dados: " + ex.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<ProdutosDTO>();

        try {
            conectaDAO conexao = new conectaDAO();
            conexao.conectar();

            String sql = "select * from produtos ";
            PreparedStatement consulta = conexao.getConexao().prepareStatement(sql);
            ResultSet resposta = consulta.executeQuery();

            while (resposta.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resposta.getInt("id"));
                p.setNome(resposta.getString("nome"));
                p.setValor(resposta.getInt("valor"));
                p.setStatus(resposta.getString("status"));
                produtos.add(p);
            }

            conexao.desconectar();

        } catch (SQLException ex) {
            System.out.println("ERRO ao tentar listar todos: " + ex.getMessage());

        }
        return produtos;

      
    }

     public void venderProduto(int id) {
        String sql = "update produtos set status=? where id=?";

        try {
            conectaDAO conexao = new conectaDAO();
            conexao.conectar();
            PreparedStatement prep = conexao.getConexao().prepareStatement(sql);

            //PreparedStatement prep = this.conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso");

        } catch (SQLException ex) {
            System.out.println("Nao foi possivel vender o produto " + ex.getMessage());
        }
    }

    
    
    
    
}
