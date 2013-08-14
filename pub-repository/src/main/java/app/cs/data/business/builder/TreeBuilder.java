package app.cs.data.business.builder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.cs.data.business.api.builder.ITreeBuilder;
import app.cs.data.business.api.dimension.IDimensionRepository;
import app.cs.data.business.api.dimension.IInMemoryDimensionGroup;
import app.cs.data.business.api.dimension.IMultiDimensionalObject;
import app.cs.data.business.dimension.MultiDimensionalObject;
import app.cs.utils.ArrayUtils;



/**
 * The Class TreeBuilder.
 */
@Component
public class TreeBuilder implements ITreeBuilder {

	/** The cache. */
	private IInMemoryDimensionGroup cache;
	
	/** The repository. */
	private IDimensionRepository repository;
	
	/** The utils. */
	private ArrayUtils utils;
	
	/** The delimeter. */
	private final String DELIMETER = "-";

	/**
	 * Instantiates a new tree builder.
	 *
	 * @param cache the cache
	 * @param repository the repository
	 */
	@Autowired
	public TreeBuilder(IInMemoryDimensionGroup cache, IDimensionRepository repository) {
		this.cache = cache;
		this.repository = repository;
	}

	/* (non-Javadoc)
	 * @see com.cs.data.business.builder.ITreeBuilder#buildTree(java.lang.String)
	 */
	@Override
	public List<MultiDimensionalObject> buildTree(String structure) {
		String[] orderedTypes = getTypes(structure);
		List<MultiDimensionalObject> rootNodes = getAllSeparatedTrees(orderedTypes[0]);
		for (IMultiDimensionalObject dimension : rootNodes) {

			buildTreeForRootNode(dimension, orderedTypes, null);
		}

		return rootNodes;
	}

	/**
	 * Gets the types.
	 *
	 * @param structure the structure
	 * @return the types
	 */
	public String[] getTypes(String structure) {
		// TODO Auto-generated method stub
		return structure.split(DELIMETER);
	}

	/**
	 * Gets the all separated trees.
	 *
	 * @param type the type
	 * @return the all separated trees
	 */
	public List<MultiDimensionalObject> getAllSeparatedTrees(String type) {
		return repository.getDimensionsOfType(type);

	}

	/**
	 * Builds the tree for the given structure provided.
	 *
	 * @param root the root
	 * @param orderTypes the order types
	 * @param groupIdsRequiredForCurrentIteration the group ids required for current iteration
	 */
	public void buildTreeForRootNode(IMultiDimensionalObject root,
			String[] orderTypes,
			List<String> groupIdsRequiredForCurrentIteration) {
		List<String> groupIds = null;
		IMultiDimensionalObject currentRoot = root;
		if (groupIdsRequiredForCurrentIteration == null) {
			groupIds = currentRoot.getGroupId();
		} else {
			groupIds = intersectGroupIds(currentRoot.getGroupId(),
					groupIdsRequiredForCurrentIteration);

		}
		String[] typesOfDimensions = skipFirstOrderType(orderTypes);
		if (typesOfDimensions.length <= 0)
			return;
		List<MultiDimensionalObject> childrenOfCurrentLevel = getAllChildrenOfCurrentRoot(
				groupIds, typesOfDimensions[0]);

		currentRoot.setChildren(childrenOfCurrentLevel);
		for (IMultiDimensionalObject child : childrenOfCurrentLevel) {

			buildTreeForRootNode(child, typesOfDimensions, groupIds);

		}

	}

	/**
	 * Gets the all children of current root.
	 *
	 * @param groupIds the group ids
	 * @param type the type
	 * @return the all children of current root
	 */
	public List<MultiDimensionalObject> getAllChildrenOfCurrentRoot(
			List<String> groupIds, String type) {
		return repository.getDimensionsBy(type, groupIds);
	}

	/**
	 * Skip first order type.
	 *
	 * @param orderTypes the order types
	 * @return the string[]
	 */
	private String[] skipFirstOrderType(String[] orderTypes) {
		utils = new ArrayUtils();
		orderTypes = utils.skip(orderTypes, 1);
		return orderTypes;
	}

	/**
	 * Intersect group ids.
	 *
	 * @param groupIds the group ids
	 * @param groupIdsRequiredForCurrentIteration the group ids required for current iteration
	 * @return the list
	 */
	private List<String> intersectGroupIds(List<String> groupIds,
			List<String> groupIdsRequiredForCurrentIteration) {
		utils = new ArrayUtils();

		return utils
				.intersection(groupIds, groupIdsRequiredForCurrentIteration);

	}

}
