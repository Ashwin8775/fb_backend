package com.iocl.fb.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Component;

@Component
public class ProcedureCaller {

	@PersistenceContext
	private EntityManager entityManager;

	public <T> List<T> callStoredProcedure(String procedureName, Map<String, Object> inputParams, Class<T> resultType) {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(procedureName, resultType);

		// Register input parameters dynamically
		for (Map.Entry<String, Object> entry : inputParams.entrySet()) {
			storedProcedure.registerStoredProcedureParameter(entry.getKey(), entry.getValue().getClass(),
					ParameterMode.IN);
			storedProcedure.setParameter(entry.getKey(), entry.getValue());
		}

		// Register the output parameter for the cursor
		storedProcedure.registerStoredProcedureParameter("INSP_CURSOR", void.class, ParameterMode.REF_CURSOR);

		// Execute the stored procedure
		storedProcedure.execute();

		// Retrieve the result from the cursor
		return storedProcedure.getResultList();

	}

}
