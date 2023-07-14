/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.controle;

import br.rm.modelo.Rm_Cliente;
import br.rm.modelo.Rm_Colaborador;
import br.rm.modelo.Rm_Emprestimo;
import br.rm.modelo.Rm_Parcela;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author manloja
 */
public class Repository {

    public static Repository repository;
    private final EntityManager entidade;

    public Repository() {
        entidade = Persistence.createEntityManagerFactory("RMPU").createEntityManager();
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public void cadastrarCliente(Rm_Cliente cliente) {
        try {
            entidade.getTransaction().begin();
            entidade.merge(cliente);
            entidade.getTransaction().commit();
        } catch (RuntimeException ex) {
            entidade.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
    }

    public void cadastrarColaborador(Rm_Colaborador colaborador) {
        try {
            entidade.getTransaction().begin();
            entidade.merge(colaborador);
            entidade.getTransaction().commit();
        } catch (RuntimeException ex) {
            entidade.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
    }

    public void cadastrarEmprestimo(Rm_Emprestimo emprestimo) {
        try {
            if (!entidade.getTransaction().isActive()) {
                entidade.getTransaction().begin();
            }
            entidade.persist(emprestimo);
            entidade.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (entidade.getTransaction().isActive()) {
                entidade.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        }
    }
    
     public void atualizarEmprestimo(Rm_Emprestimo emprestimo) {
        try {
            if (!entidade.getTransaction().isActive()) {
                entidade.getTransaction().begin();
            }
            entidade.merge(emprestimo);
            entidade.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (entidade.getTransaction().isActive()) {
                entidade.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        }
    }

    public void atualizarParcela(List<Rm_Parcela> parcelas) {
        try {

            for (Rm_Parcela p : parcelas) {
                entidade.getTransaction().begin();
                entidade.merge(p);
                entidade.getTransaction().commit();
            }

        } catch (RuntimeException ex) {
            entidade.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
    }

    /**
     * public void criarVenda(Venda venda) { try {
     * entidade.getTransaction().begin(); entidade.persist(venda);
     * entidade.getTransaction().commit();
     *
     * } catch (RuntimeException ex) { entidade.getTransaction().rollback(); //
     * desfaz a transação throw new RuntimeException("Erro ao criar venda !",
     * ex); } }
     *
     * @param usuario
     * @return
     */
    public Rm_Colaborador buscarColaboradorPorUsuario(String usuario) {
        try {
            TypedQuery<Rm_Colaborador> query = entidade.createNamedQuery("colaborador.buscarUsuario", Rm_Colaborador.class);
            query.setParameter("usuario", usuario);

            return query.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public Rm_Cliente buscarClientePorCpf(String cpf) {
        try {
            TypedQuery<Rm_Cliente> query = entidade.createNamedQuery("cliente.buscarCpf", Rm_Cliente.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Rm_Colaborador buscarColaboradorPorCpf(String cpf) {
        try {
            TypedQuery<Rm_Colaborador> query = entidade.createNamedQuery("colaborador.buscarCpf", Rm_Colaborador.class);
            query.setParameter("cpf", cpf);

            return query.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
//
//
//    public void salvarUsuario(Usuario usuario) {
//        try {
//            entidade.getTransaction().begin();
//            entidade.persist(usuario);
//            entidade.getTransaction().commit();
//        } catch (RuntimeException ex) {
//            entidade.getTransaction().rollback();
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public void salvarNovaMesa(Mesa novaMesa) {
//        try {
//            entidade.getTransaction().begin();
//            entidade.persist(novaMesa);
//            entidade.getTransaction().commit();
//        } catch (RuntimeException ex) {
//            entidade.getTransaction().rollback();
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public void salvarReserva(Reserva reserva) {
//        try {
//            entidade.getTransaction().begin();
//            entidade.merge(reserva);
//            entidade.getTransaction().commit();
//        } catch (RuntimeException ex) {
//            entidade.getTransaction().rollback();
//            throw new RuntimeException(ex);
//        }
//    }
//
//   
//
//    public void excluirMesa(Long mesaExcluida) {
//        try {
//            entidade.getTransaction().begin();
//            entidade.remove(mesaExcluida);
//            entidade.getTransaction().commit();
//        } catch (RuntimeException ex) {
//            entidade.getTransaction().rollback(); // desfaz a transação
//            throw new RuntimeException(ex);
//        }
//    }
//
/*
    public Comanda criarComanda(Comanda comanda) {
        try {
            entidade.getTransaction().begin();
            comanda = entidade.merge(comanda);
            entidade.getTransaction().commit();
        } catch (RuntimeException ex) {
            entidade.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return comanda;
    }
     */
//

//
    /*
    public void excluirComada(Comanda comanda) {
        try {
            entidade.getTransaction().begin();
            entidade.remove(comanda);
            entidade.getTransaction().commit();

        } catch (RuntimeException ex) {
            entidade.getTransaction().rollback(); // desfaz a transação
            throw new RuntimeException("Erro ao excluir comanda !", ex);
        }

    }

    public List<Comanda> buscarComandas(Date data) {
        TypedQuery<Comanda> query = entidade.createNamedQuery("comanda.buscarTodasDoDia", Comanda.class);
        query.setParameter("criacao", data);

        return query.getResultList();
    }

    public List<Produto> buscarTodosProduto() {
        TypedQuery<Produto> query = entidade.createNamedQuery("produto.buscarTodosProduto", Produto.class);
        return query.getResultList();
    }
     */
    public List<Rm_Cliente> buscarClienteNome(String nome) {
        TypedQuery<Rm_Cliente> query = entidade.createNamedQuery("cliente.buscarNome", Rm_Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        List<Rm_Cliente> lista = query.getResultList();
        return lista;
    }

    public List<Rm_Colaborador> buscarColaboradorNome(String nome) {
        TypedQuery<Rm_Colaborador> query = entidade.createNamedQuery("colaborador.buscarNome", Rm_Colaborador.class);
        query.setParameter("nome", "%" + nome + "%");
        List<Rm_Colaborador> lista = query.getResultList();
        return lista;
    }

    /*
    public List<Produto> buscarDescricao(String descricao) {
        TypedQuery<Produto> query = entidade.createNamedQuery("produto.buscarDescricao", Produto.class);
        query.setParameter("descricao", "%" + descricao + "%");
        List<Produto> lista = query.getResultList();
        return lista;
    }

    public Produto buscarProdutoCodigoBarras(String codigoBarras) {
        try {
            TypedQuery<Produto> query = entidade.createNamedQuery("produto.buscarPorCodigoBarras", Produto.class);
            query.setParameter("codigoBarras", codigoBarras);

            return query.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<ProdutoVenda> buscarRelatorioVenda(Date dataInicio, Date dataFim) {
        try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodos", ProdutoVenda.class);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<ProdutoVenda> buscarVendas() {
        TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscar", ProdutoVenda.class);
        return query.getResultList();
    }

    public List<ProdutoVenda> buscarTodasVendasCartao() {
        TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodasCartao", ProdutoVenda.class);
        return query.getResultList();
    }

    public List<ProdutoVenda> buscarTodasVendasDinheiro() {
        TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodasDinheiro", ProdutoVenda.class);
        return query.getResultList();
    }

    //
    public List<ProdutoVenda> buscarVendaDinheiroPorData(Date dataInicio, Date dataFim) {
        try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarVendaDinheiroPorData", ProdutoVenda.class);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<ProdutoVenda> buscarVendaCartaoPorData(Date dataInicio, Date dataFim) {
        try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarVendaCartaoPorData", ProdutoVenda.class);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
//buscarVendaCartaoPorData
//    public Mesa buscarMesa(int numMesa) {
//        try {
//            TypedQuery query = entidade.createNamedQuery("mesa.buscar", Mesa.class).setParameter("numMesa", numMesa);
//            Mesa mesa = (Mesa) query.getSingleResult();
//            return mesa;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<Mesa> listarTodasMesas() {
//        TypedQuery query = entidade.createNamedQuery("mesa.listar", Mesa.class);
//        return query.getResultList();
//    }
//
//    public List<Reserva> buscarReserva(String campoBusca) {
//        TypedQuery<Reserva> query = entidade.createNamedQuery("reserva.buscar", Reserva.class);
//        query.setParameter("campoBusca", "%" + campoBusca + "%");
//        List<Reserva> lista = query.getResultList();
//        return lista;
//    }
//
//    public Reserva buscarReservaID(Long id) {
//        try {
//            TypedQuery<Reserva> query = entidade.createNamedQuery("reserva.BuscarID", Reserva.class);
//            query.setParameter("ID", id);
//
//            return query.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<Reserva> listarTodasReservas() {
//        TypedQuery query = entidade.createNamedQuery("reserva.listar", Reserva.class);
//        return query.getResultList();
//    }
//
//    public List<Item> listarItem() {
//        TypedQuery<Item> query = entidade.createNamedQuery("item.listar", Item.class);
//        return query.getResultList();
//    }
//
//    public List<Item> listarItemPorComanda(Long idComanda) {
//        TypedQuery<Item> query = entidade.createNamedQuery("item.listarPorComanda", Item.class).setParameter("idComanda", idComanda);;
//
//        return query.getResultList();
//    }
//
//    public List<Float> buscarValorItem(String item) {
//        TypedQuery query = entidade.createNamedQuery("item.buscarValotItem", Item.class).setParameter("item", item);
//        List<Float> listaObtida = query.getResultList();
//        return listaObtida;
//    }
//
//    public List<Comanda> listarComandaCliente(int numMesa) {
//        TypedQuery<Comanda> query = entidade.createNamedQuery("comanda.listarComandaCliente", Comanda.class).setParameter("numMesa", numMesa);
//        List<Comanda> c = query.getResultList();
//        return c;
//    }
//
//    public List<Comanda> buscaTodasComandas() {
//        TypedQuery<Comanda> query = entidade.createNamedQuery("comanda.buscarTodas", Comanda.class);
//        List<Comanda> c = query.getResultList();
//        return c;
//    }

    public void close() {
        entidade.close();
    }

    public void cadastrarSaida(Saida saida) {
        try {
            entidade.getTransaction().begin();
            entidade.persist(saida);
            entidade.getTransaction().commit();
        } catch (RuntimeException ex) {
            entidade.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
    }

    public List<Saida> buscarSaida(Date dataInicio, Date dataFim) {
        try {
            TypedQuery<Saida> query = entidade.createNamedQuery("saida.buscarSaida", Saida.class);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<Saida> buscarTodasSaidas() {
        TypedQuery<Saida> query = entidade.createNamedQuery("saida.buscarTodas", Saida.class);
        return query.getResultList();
    }

    public List<Comanda> buscarComandasAberta() {
        TypedQuery<Comanda> query = entidade.createNamedQuery("comanda.buscarComandaAberta", Comanda.class);

        return query.getResultList();
    }

    public List<ProdutoVenda> buscaTodasVendaPorProduto(Produto produto) {

        try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodasVendasPorProduto", ProdutoVenda.class);
            query.setParameter("produtoID", produto.getId());

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<ProdutoVenda> buscaTodasVendasPorProdutoEData(Date dataInicio, Date dataFim, Produto produto) {
        try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodasVendasPorProdutoData", ProdutoVenda.class);
            query.setParameter("produtoID", produto.getId());
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public List<ProdutoVenda> buscaTodasVendasPorProdutoDataFormaPagamento(Date dataInicio, Date dataFim, Produto produto, String formaPagamento) {
    try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodasVendasPorProdutoDataFormaPagamento", ProdutoVenda.class);
            query.setParameter("produtoID", produto.getId());
             query.setParameter("formaPagamento", formaPagamento);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
           
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    
    }
    public List<ProdutoVenda> buscaTodasVendasPorProdutoEFormaPagamento(Produto produto, String formaPagamento) {
    try {
            TypedQuery<ProdutoVenda> query = entidade.createNamedQuery("produtoVenda.buscarTodasVendasPorProdutoFormaPagamento", ProdutoVenda.class);
            query.setParameter("produtoID", produto.getId());
             query.setParameter("formaPagamento", formaPagamento);
            
           
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    
    }
     */
    public List<Rm_Emprestimo> buscarEmprestimoPorCliente(Rm_Cliente cliente) {
        TypedQuery<Rm_Emprestimo> query = entidade.createNamedQuery("emprestimo.BuscarPorCliente", Rm_Emprestimo.class);
        query.setParameter("clienteId", cliente.getId());
        List<Rm_Emprestimo> lista = query.getResultList();
        return lista;

    }

    public List<Rm_Emprestimo> BuscarEmprestimoPorColaborador(Rm_Colaborador colaborador) {
        try {
            TypedQuery<Rm_Emprestimo> query = entidade.createNamedQuery("emprestimo.BuscarPorColaborador", Rm_Emprestimo.class);
            query.setParameter("colaboradorId", colaborador.getId());
            List<Rm_Emprestimo> lista = query.getResultList();
            if (lista.isEmpty()) {
                return null;
            }
            return lista;

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    public List<Rm_Parcela> buscarParcelasEmprestimo(Rm_Emprestimo emprestimo) {
        TypedQuery<Rm_Parcela> query = entidade.createNamedQuery("parcela.BuscarPorEmprestimo", Rm_Parcela.class);
        query.setParameter("emprestimoId", emprestimo.getId());
        List<Rm_Parcela> lista = query.getResultList();
        return lista;

    }
    public List<Rm_Parcela> BuscarTodasPorEmprestimo(Rm_Emprestimo emprestimo) {
        TypedQuery<Rm_Parcela> query = entidade.createNamedQuery("parcela.BuscarTodasPorEmprestimo", Rm_Parcela.class);
        query.setParameter("emprestimoId", emprestimo.getId());
        List<Rm_Parcela> lista = query.getResultList();
        return lista;

    }

    
    
    public List<Rm_Parcela> buscarParcelasAtrazada() {
        TypedQuery<Rm_Parcela> query = entidade.createNamedQuery("parcela.BuscarAtrazadas", Rm_Parcela.class);
        List<Rm_Parcela> lista = query.getResultList();
        return lista;

    }

    public List<Rm_Emprestimo> BuscarEmprestimoPorDataEColaborador(Rm_Colaborador colaborador, Date dateInicial, Date dateFinal) {
        try {
            TypedQuery<Rm_Emprestimo> query = entidade.createNamedQuery("emprestimo.BuscarPorColaboradorEPeriodo", Rm_Emprestimo.class);
            query.setParameter("colaboradorId", colaborador.getId());
            query.setParameter("dataInicial", dateInicial);
            query.setParameter("dataFinal", dateFinal);
            List<Rm_Emprestimo> lista = query.getResultList();
            if (lista.isEmpty()) {
                return null;
            }
            return lista;
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
