package br.edu.catolica.ucb.poo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JPAUtil {

    private static final String UNIDADE_PERSISTENCIA = "condominio-sqlite";
    private static final Logger LOGGER_HIBERNATE = Logger.getLogger("org.hibernate");

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            LOGGER_HIBERNATE.setLevel(Level.SEVERE);
            factory = Persistence.createEntityManagerFactory(UNIDADE_PERSISTENCIA);
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static synchronized void fechar() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
