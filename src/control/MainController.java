package control;

import br.com.parg.viacep.ViaCEP;
import br.com.parg.viacep.ViaCEPException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Endereco;
import model.jdbc.EnderecoDAO;

public class MainController {

    @FXML
    private TextArea textInformacoes;

    @FXML
    private TextField textCep;
    
    private Endereco endereco;

    @FXML
    void Pesquisar(ActionEvent event) {
        if (verificaCEP()) {
            String cep = textCep.getText();
            ViaCEP viaCep = new ViaCEP();
            endereco = new Endereco();
            textInformacoes.setWrapText(true);
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            ObservableList<Endereco> enderecos = enderecoDAO.selectEndereco();
            for (Endereco endereco1 : enderecos) {
                if (endereco1.getCep().equals(textCep.getText())) {
                    System.out.println("Achou no cache");
                    endereco = endereco1;
                    textInformacoes.setText(endereco.toString());
                    return;
                }
            }
            try {
                System.out.println("Pesquisou na internet");
                viaCep.buscar(cep);
                endereco.setBairro(viaCep.getBairro());
                endereco.setCep(viaCep.getCep());
                endereco.setComplemento(viaCep.getComplemento());
                endereco.setGia(viaCep.getGia());
                endereco.setIbge(viaCep.getIbge());
                endereco.setLocalidade(viaCep.getLocalidade());
                endereco.setLogradouro(viaCep.getLogradouro());
                endereco.setUf(viaCep.getUf());
                enderecoDAO.addEndereco(endereco);
                textInformacoes.setText(endereco.toString());
            } catch (ViaCEPException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(endereco);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("CEP");
            alerta.setHeaderText("Erro");
            alerta.setContentText("CEP inválido.");
            alerta.show();
        }
    }
    
    public boolean verificaCEP() {
        char[] cep = textCep.getText().toCharArray();
        if (cep.length != 9) {
            System.out.println("Tamanho diferente");
            return false;
        }
        if(cep[5] != '-') {
            System.out.println("Nao tem hifen");
            return false;
        }
        char[] auxiliar = cep;
        auxiliar[5] = '0';
        for (int i = 0; i < auxiliar.length; i++) {
            if (!Character.isDigit(auxiliar[i])) {
                System.out.println("Não é numero" + String.valueOf(auxiliar));
                return false;
            }
        }
        return true;
        
    }
    
    
    
}
