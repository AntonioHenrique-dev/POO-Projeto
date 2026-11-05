package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Repositorio<T> {

    private final Class<T> tipo;

    public Repositorio(Class<T> tipo) {
        this.tipo = tipo;
    }

    public List<T> listar() {
        return consultar("SELECT e FROM " + tipo.getSimpleName() + " e ORDER BY e.id", Map.of());
    }

    public List<T> consultar(String jpql, Map<String, Object> parametros) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            var query = em.createQuery(jpql, tipo);
            parametros.forEach(query::setParameter);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long contar(String jpql, Map<String, Object> parametros) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            var query = em.createQuery(jpql, Long.class);
            parametros.forEach(query::setParameter);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public T buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(tipo, id);
        } finally {
            em.close();
        }
    }

    public T salvar(T entidade) {
        return executar(em -> {
            em.persist(entidade);
            return entidade;
        });
    }

    public T atualizar(T entidade) {
        return executar(em -> em.merge(entidade));
    }

    public void excluir(Integer id) {
        try {
            executar(em -> {
                T entidade = em.find(tipo, id);
                if (entidade != null) {
                    em.remove(entidade);
                }
                return null;
            });
        } catch (PersistenceException e) {
            throw new IllegalStateException("Nao foi possivel excluir: o registro esta vinculado a outros cadastros.");
        }
    }

    private <R> R executar(Function<EntityManager, R> operacao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            R resultado = operacao.apply(em);
            em.getTransaction().commit();
            return resultado;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
