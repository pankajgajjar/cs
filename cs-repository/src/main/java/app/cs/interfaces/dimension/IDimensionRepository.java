package app.cs.interfaces.dimension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.cs.data.api.core.GenericDomain;

import app.cs.impl.model.MultiDimensionalObject;

public interface IDimensionRepository {

	/**
	 * Creates the dimension.
	 * 
	 * @param dimension
	 *            the dimension
	 * @return the string
	 */
	public abstract String createDimension(MultiDimensionalObject dimension);

	/**
	 * Gets the all dimensions.
	 * 
	 * @return the all dimensions
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws URISyntaxException
	 *             the uRI syntax exception
	 */
	public abstract String getAllDimensions() throws IOException,
			URISyntaxException;

	/**
	 * Gets the dimensions.
	 * 
	 * @return the dimensions
	 */
	public abstract List<MultiDimensionalObject> getDimensions();

	/**
	 * Gets the dimensions of type.
	 * 
	 * @param type
	 *            the type
	 * @return the dimensions of type
	 */
	public abstract List<MultiDimensionalObject> getDimensionsOfType(String type);

	/**
	 * Gets the dimensions by type.
	 * 
	 * @param type2
	 *            the type2
	 * @param groupIds
	 *            the group ids
	 * @return the dimensions by
	 */
	public abstract List<MultiDimensionalObject> getDimensionsBy(String type2,
			List<String> groupIds);

	GenericDomain getDomain(String type);

}