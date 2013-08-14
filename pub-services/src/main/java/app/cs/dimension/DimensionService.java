package app.cs.dimension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.cs.data.business.api.builder.ITreeBuilder;
import app.cs.data.business.api.chapter.IInMemoryViewStructure;
import app.cs.data.business.api.dimension.IDimensionRepository;
import app.cs.data.business.api.dimension.IMultiDimensionalObject;
import app.cs.data.business.api.model.IDomainFactory;
import app.cs.service.Service;


/**
 * The Class DimensionService.
 * TODO remove out all annotation from class 
 */
@Component
public class DimensionService implements Service {

	/** The tree builder. */
	private ITreeBuilder treeBuilder;

	/** The dimension repository. */
	private IDimensionRepository dimensionRepository;

	/** The Domain factory. */
	private IDomainFactory factory;

	/** The ViewStructure cache. */
	private IInMemoryViewStructure cache;

	/** The contentobject. */
	private final String CONTENTOBJECT = "ContentObject";

	/** The Constant KEY. */
	private static final String KEY = "view";

	/**
	 * Instantiates a new dimension service.
	 * 
	 * @param dimensionRepository
	 *            the dimension repository
	 * @param treeBuilder
	 *            the tree builder
	 */
	@Autowired
	public DimensionService(IDimensionRepository dimensionRepository,
			ITreeBuilder treeBuilder, IDomainFactory factory,
			IInMemoryViewStructure cache) {

		this.dimensionRepository = dimensionRepository;
		this.treeBuilder = treeBuilder;
		this.factory = factory;
		this.cache = cache;

	}

	/*
	 * Get all dimension.
	 * 
	 * @see com.cs.service.IService#getAll()
	 */
	@Override
	public String getAll() throws IOException, URISyntaxException {
		// TODO Auto-generated method stub

		return dimensionRepository.getAllDimensions();
	}

	/*
	 * Creates new dimension.
	 * 
	 * @see com.cs.service.IService#create(com.cs.model.DimensionModel)
	 */

	/*
	 * Get all chapters for given structure.
	 * 
	 * @see com.cs.service.IService#getAllBy(java.lang.String)
	 */
	@Override
	public List<app.cs.data.business.dimension.MultiDimensionalObject> getAllBy(String structure) {
		// TODO Auto-generated method stub
		setCurrentViewStructure(structure);
		return treeBuilder.buildTree(structure);

	}

	/*
	 * Deletes the dimension.
	 * 
	 * @see com.cs.service.IService#delete(com.cs.model.ContentObject,
	 * java.lang.String)
	 */
	@Override
	public void delete(IMultiDimensionalObject chapter, String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public String create(String type, String name, String path, String isFolder) {
		app.cs.data.business.dimension.MultiDimensionalObject dimension = (app.cs.data.business.dimension.MultiDimensionalObject) factory
				.getDomainObject(CONTENTOBJECT);

		setDimensionAttributes(dimension, type, name, path, isFolder);
		return dimensionRepository.createDimension(dimension);
	}

	/**
	 * Sets the dimension attributes.
	 * 
	 * @param dimension
	 *            the dimension
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param path
	 *            the path
	 * @param isFolder
	 *            the is folder
	 */

	protected void setDimensionAttributes(IMultiDimensionalObject dimension,
			String type, String name, String path, String isFolder) {
		dimension.setId(name);
		dimension.setTitle(name);
		dimension.setIsFolder(isFolder);
		dimension.setPath(path);
		dimension.setName(name);
		dimension.setType(type);

	}

	/**
	 * Sets the current view structure.
	 * 
	 * @param currentViewStructure
	 *            the new current view structure
	 */
	public void setCurrentViewStructure(String currentViewStructure) {
		cache.setCurrentViewStructure(KEY, currentViewStructure);

	}

	@Override
	public void move(String type, String name, String path, String isFolder,
			String newpath) {
		// TODO Auto-generated method stub

	}

}