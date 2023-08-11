/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DEV
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Classe que representa um cliente
class Cliente {
    private int id;
    private String nome;
    private String dataVencimento;
    private boolean alertado;

    public Cliente(int id, String nome, String dataVencimento) {
        this.id = id;
        this.nome = nome;
        this.dataVencimento = dataVencimento;
        this.alertado = false;
    }

    // Getters e Setters
 public String getDataVencimento() {
        return dataVencimento;
    }

    public void setdataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
     public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean isAlertado() {
        return alertado;
    }

    public void setAlertado(boolean alertado) {
        this.alertado = alertado;
    }
}

// Tarefa agendada para verificar clientes com vencimentos em atraso e exibir alertas
class AlertaTask extends TimerTask {
    private List<Cliente> clientes;

    public AlertaTask(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public void run() {
        System.out.println("Verificando clientes com vencimentos em atraso...");

        for (Cliente cliente : clientes) {
                // Lógica para verificar se o cliente está com vencimento em atraso
                // Neste exemplo, usamos um critério simples: a data de vencimento é "atrasada" se for anterior à data atual
                // Na prática, você precisará implementar a lógica de verificação de datas

                // Aqui, estamos apenas simulando a exibição do alerta
                if (cliente.getDataVencimento().compareTo("2023-07-26") < 0) {
                    System.out.println("ALERTA: Cliente " + cliente.getNome() + " está com vencimento em atraso!");
                    cliente.setAlertado(true);
                }
            
        }
    }
}

public class ExemploAlertasEmAtraso {
    public static void main(String[] args) {
        // Simulando a obtenção de clientes do banco de dados
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "Cliente 0", "2023-07-15"));
        clientes.add(new Cliente(1, "Cliente 1", "2023-07-25"));
        clientes.add(new Cliente(2, "Cliente 2", "2023-07-26"));
        clientes.add(new Cliente(3, "Cliente 3", "2023-07-27"));

        // Simulando a execução da tarefa a cada 30 minutos
        Timer timer = new Timer();
        long delay = 0;
        long interval = 1 * 60 * 1000; // 30 minutos
        timer.schedule(new AlertaTask(clientes), delay, interval);
    }
}
