package app.cs.controller.publicationplanning.dimension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.cs.boundary.delivery.Interactor;
import app.cs.impl.model.MultiDimensionalObject;
import app.cs.model.request.MoveDimensionRequest;

@Controller
public class MoveDimensionController {

	private MoveDimensionRequest request;

	private Interactor moveDimension;

	@Autowired
	public MoveDimensionController(Interactor moveDimension,
			MoveDimensionRequest request) {
		this.moveDimension = moveDimension;
		this.request = request;
	}

	@RequestMapping(value = "/dimension/move/{oldPath}/{newPath}")
	public void move(@PathVariable String oldPath,
			@PathVariable String newPath,
			@RequestBody MultiDimensionalObject objectInMove) {
		request.setNewPath(newPath);
		request.setOldPath(oldPath);
		request.setObjectInMove(objectInMove);
		moveDimension.execute(request);

	}

}