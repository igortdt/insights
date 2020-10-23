package br.com.personality.insights.repository.profile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import br.com.personality.insights.entity.ProfileEntity;
import br.com.personality.insights.exception.AppException;

@Dependent
public class Profiles implements Serializable {

	private static final long serialVersionUID = 146181397197415578L;

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public ProfileEntity atualizar(ProfileEntity profile) throws AppException, Exception {

		return manager.merge(profile);
	}

	@Transactional
	public void excluir(ProfileEntity profile) throws ConstraintViolationException, AppException {
		profile = manager.find(ProfileEntity.class, profile.getId());
		if (profile == null || profile.getId() == null) {
			throw new AppException("falha.obter.dados.formulario");
		}
		manager.remove(profile);
	}

	public List<ProfileEntity> getAll() {

		HashMap<String, Object> params = new HashMap<String, Object>();

		String hql = "select p from ProfileEntity p";

		TypedQuery<ProfileEntity> query = manager.createQuery(hql, ProfileEntity.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key).getClass().cast(params.get(key)));
		}

		List<ProfileEntity> lista = query.getResultList();

		return lista;

	}

}
