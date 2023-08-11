public class Emprestimo {

    public static double calcularValorEmprestado(double valorPagoTotal, double jurosIncluidos) {
        // Convertendo a porcentagem dos juros para decimal
        double jurosDecimal = jurosIncluidos / 100.0;
        
        // Cálculo do valor emprestado antes dos juros
        double valorEmprestado = valorPagoTotal / (1 + jurosDecimal);
        
        return valorEmprestado;
    }

    public static void main(String[] args) {
        // Exemplo de uso do método
        double valorPagoTotal = 600.0;
        double jurosIncluidos = 20.0;
        
        double valorEmprestado = calcularValorEmprestado(valorPagoTotal, jurosIncluidos);
        System.out.println("Valor original emprestado: " + valorEmprestado);
    }
}
