/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.controle;

/**
 *
 * @author Jayne
 */
public class Validacao {

    public Validacao() {

    }

    public boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // remove caracteres não numéricos

        if (cpf.length() != 11) { // o CPF deve ter 11 dígitos
            return false;
        }

        // verifica se todos os dígitos são iguais
        boolean todosIguais = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            return false;
        }

        // calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int resto = 11 - (soma % 11);
        int digito1 = (resto == 10 || resto == 11) ? 0 : resto;

        // calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        resto = 11 - (soma % 11);
        int digito2 = (resto == 10 || resto == 11) ? 0 : resto;

        // verifica se os dígitos calculados são iguais aos dígitos informados
        return (digito1 == cpf.charAt(9) - '0' && digito2 == cpf.charAt(10) - '0');
    }

    public boolean validarCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // Verifica se o CNPJ possui 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            int digito = Integer.parseInt(cnpj.substring(i, i + 1));
            soma += digito * peso;
            peso = peso == 9 ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int dv1 = resto < 2 ? 0 : 11 - resto;

        // Calcula o segundo dígito verificador
        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            int digito = Integer.parseInt(cnpj.substring(i, i + 1));
            soma += digito * peso;
            peso = peso == 9 ? 2 : peso + 1;
        }
        resto = soma % 11;
        int dv2 = resto < 2 ? 0 : 11 - resto;

        // Verifica se os dígitos verificadores calculados são iguais aos do CNPJ
        return dv1 == Integer.parseInt(cnpj.substring(12, 13)) && dv2 == Integer.parseInt(cnpj.substring(13));
    }

    public String formatarCpf(String cpf) {
        // remove quaisquer caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // adiciona "." e "-" para formatar o CPF
        cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);

        return cpf;
    }

}
