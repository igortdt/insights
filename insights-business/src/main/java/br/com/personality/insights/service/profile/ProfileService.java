package br.com.personality.insights.service.profile;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import br.com.personality.insights.entity.ProfileEntity;
import br.com.personality.insights.exception.AppException;
import br.com.personality.insights.repository.profile.Profiles;

@Dependent
public class ProfileService implements Serializable {

	private static final long serialVersionUID = 8809850232210016856L;

	@Inject
	private Profiles profiles;

	public ProfileEntity salvar(ProfileEntity profile) throws AppException, Exception {

		return profiles.atualizar(profile);
	}

	public List<ProfileEntity> getAll() throws AppException {
		return profiles.getAll();
	}

	public void excluir(ProfileEntity profile) throws AppException {
		if (profile == null || profile.getId() == null) {
			throw new AppException("falha.obter.dados.formulario");
		}
		try {
			profiles.excluir(profile);
		} catch (ConstraintViolationException e) {
			throw new AppException(e);
		}
	}

}
