package app.cs.actions.publicationplanning.dimension;import static org.fest.assertions.Assertions.assertThat;import static org.mockito.Mockito.verify;import static org.mockito.Mockito.when;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.mockito.Mock;import org.mockito.runners.MockitoJUnitRunner;import app.cs.actions.publicationplanning.perspective.TreeBuilder;import app.cs.impl.chapter.InMemoryViewStructure;import app.cs.impl.delegate.factory.IDomainFactory;import app.cs.impl.dimension.DimensionRepository;import app.cs.impl.model.MultiDimensionalObject;import app.cs.interfaces.dimension.IMultiDimensionalObject;import app.cs.model.request.CreateDimensionRequest;import app.cs.model.request.RequestModel;import app.cs.model.response.ResponseModel;import app.cs.model.response.StringResponse;@RunWith(MockitoJUnitRunner.class)public class CreateDimensionUnitTest {	private CreateDimension createDimension;	@Mock	private IMultiDimensionalObject dimensionModel;	@Mock	private TreeBuilder treeBuilder;	@Mock	private DimensionRepository dimensionRepository;	@Mock	private IDomainFactory factory;	@Mock	private InMemoryViewStructure cache;	@Before	public void setUp() {		createDimension = new CreateDimension(dimensionRepository);	}	@Test	public void itShouldCreateADimension() {		// given		String expectedDimensionId = "dimension01";		String name = "test";		String path = "A,B";		String type = "spread";		boolean isFolder = true;		// when		MultiDimensionalObject test = new MultiDimensionalObject();		when(dimensionRepository.getDomain("MultiDimensionalObject"))				.thenReturn(test);		when(dimensionRepository.createDimension(test)).thenReturn(name);		RequestModel model = new CreateDimensionRequest(type, name, path,				isFolder);		StringResponse response = (StringResponse) createDimension				.execute(model);		// then		verify(dimensionRepository).createDimension(test);		assertThat(response.getResponseString()).isEqualTo(name);	}}