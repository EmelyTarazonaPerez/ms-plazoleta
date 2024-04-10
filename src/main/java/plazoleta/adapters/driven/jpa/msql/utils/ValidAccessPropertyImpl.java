package plazoleta.adapters.driven.jpa.msql.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AllArgsConstructor
public class ValidAccessPropertyImpl implements IValidAccessProperty {
    @Override
    public boolean properyHavePermis(int id) {
        return false;
    }
   /*
    @PersistenceContext(unitName = "otherEntityManagerFactory")
    private EntityManager entityManagerPersona; // EntityManager para la base de datos de la tabla persona
    public boolean properyHavePermis(int id) {
        // Ejecutar la consulta SQL nativa para verificar si el propietario del restaurante tiene el rol específico
        String sql = "SELECT COUNT(*) FROM persona p " +
                "INNER JOIN rol r ON p.id_rol = r.id_rol " +
                "INNER JOIN restaurant r ON p.id_persona = r.id_persona " +
                "WHERE r.id = ? AND r.id_rol = 2";

        Query query = entityManagerPersona.createNativeQuery(sql);
        query.setParameter(1, id);

        // Obtener el resultado de la consulta
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue() > 0;
    }

    */
}
