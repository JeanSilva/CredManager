/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DEV
 */

import java.io.File;
import java.net.URL;

public class CaminhoPastaSrc {
    public static void main(String[] args) {
         // Obter o caminho da pasta "Documentos" do sistema
        String documentosPath = System.getProperty("user.home") + File.separator + "Documents";
        
        // Criar uma nova pasta dentro da pasta "Documentos"
        String novaPastaNome = "MinhaNovaPasta";
        String novaPastaPath = documentosPath + File.separator + novaPastaNome;
        
        File novaPasta = new File(novaPastaPath);
        
        if (novaPasta.mkdir()) {
            System.out.println("Pasta criada com sucesso em: " + novaPastaPath);
        } else {
            System.out.println("Falha ao criar a pasta.");
        }
    }
}
