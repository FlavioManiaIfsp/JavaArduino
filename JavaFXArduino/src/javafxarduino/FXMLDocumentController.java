package javafxarduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Mania
 */
public class FXMLDocumentController implements Initializable {    
    
    @FXML
    private Button btnConectar;

    @FXML
    private ComboBox cmbPortas;

    @FXML
    private Label label;    
    
    private SerialPort porta;
    
    private BufferedReader input;
    
    private int statusLed = 0;
    
    @FXML
    private Button btnLigaDesliga;
    @FXML
    private Circle objImagem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        objImagem.setFill(Color.rgb(255,255, 255));
        carregarPortas();
        btnLigaDesliga.setDisable(true);
    }    
    
    
    private void carregarPortas(){
        SerialPort[] portaNomes = SerialPort.getCommPorts();
        for (SerialPort portaNome : portaNomes) {
            cmbPortas.getItems().add(portaNome.getSystemPortName());
            
        }
    }
    
    @FXML
    private void conectar(ActionEvent event) {
        if(btnConectar.getText().equals("Conectar")){
            porta = SerialPort.getCommPort(cmbPortas.getSelectionModel().
                                                    getSelectedItem().toString());            
            if(porta.openPort()){
                btnConectar.setText("Desconectar");
                cmbPortas.setDisable(true);
                btnLigaDesliga.setDisable(false);
            }
            
        }else{
            porta.closePort();
            cmbPortas.setDisable(false);
            btnLigaDesliga.setDisable(true);
            btnConectar.setText("Conectar");
        }
    }  
    
    @FXML
    private void ligarLed(ActionEvent event) throws IOException {                
        PrintWriter saida = new PrintWriter(porta.getOutputStream());                       
        if(statusLed == 0){
            saida.print("1");         
            statusLed = 1;
        }else{
            saida.print("0");
            statusLed = 0;            
        }
        saida.flush();
        if(porta.isOpen()){
            porta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 300, 0);
            BufferedReader in = new BufferedReader(new InputStreamReader(porta.getInputStream()));
            try{
                String msg = in.readLine();
                System.out.println(msg);
                if(msg.equals("Desligado")){
                    objImagem.setFill(Color.rgb(235, 87, 87));
                }else{
                    objImagem.setFill(Color.rgb(203, 180, 212));                    
                }                                
            }catch(IOException e){
                e.printStackTrace();
            }            
        }    
    }    
}
