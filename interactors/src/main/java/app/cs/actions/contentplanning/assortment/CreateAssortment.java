package app.cs.actions.contentplanning.assortment;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.cs.boundary.delivery.Interactor;
import app.cs.impl.assortment.AssortmentRepository;
import app.cs.impl.model.Assortment;
import app.cs.model.request.CreateAssortmentRequest;
import app.cs.model.request.RequestModel;
import app.cs.model.response.EmptyResponse;
import app.cs.model.response.ResponseModel;

@Component
public class CreateAssortment implements Interactor{

	private AssortmentRepository assortmentRepository;

	@Autowired
	public CreateAssortment(AssortmentRepository assortmentRepository) {
		this.assortmentRepository = assortmentRepository;

	}

	public ResponseModel execute(RequestModel request) {

		CreateAssortmentRequest createAssortmentRequest = (CreateAssortmentRequest) request;

		Assortment assortment = createAssortmentRequest.getAssortment();
		String path = createAssortmentRequest.getNewPath();
		assortment.setID(UUID.randomUUID().toString());
		assortmentRepository.save(assortment, path);
		return new EmptyResponse();

	}

}
