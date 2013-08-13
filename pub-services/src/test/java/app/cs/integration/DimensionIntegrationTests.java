package app.cs.integration;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.cs.cache.DimensionGroupCache;
import com.cs.data.core.nosql.InMemoryNoSqlRepository;
import com.cs.data.core.nosql.mongodb.MongoRepository;
import app.cs.model.ContentObject;
import app.cs.repository.DimensionRepository;
import app.cs.service.Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class DimensionIntegrationTests {

	@Autowired
	private MongoRepository noSqlRepository;

	List<ContentObject> models = new ArrayList<ContentObject>();

	private DimensionGroupCache cache;
	private DimensionRepository dimensionRepository;

	private Service dimensionService;

	@Autowired
	private InMemoryNoSqlRepository inMemoryNosqlRepository;

	@Before
	public void setUp() {

		ContentObject mp01 = new ContentObject("mp01", "MasterPublication",
				"mp01", "mp01", "-1");
		ContentObject mp012 = new ContentObject("mp012", "MasterPublication",
				"mp012", "mp012", "-1");
		ContentObject cp01 = new ContentObject("cp01", "Campaign", "cp01",
				"cp01", "mp01");
		ContentObject cp02 = new ContentObject("cp02", "Campaign", "cp02",
				"cp02", "-1");

		ContentObject mp02 = new ContentObject("mp02", "MasterPublication",
				"mp02", "mp02", "cp02");
		ContentObject pg02 = new ContentObject("pg02", "PublicationGroup",
				"pg02", "pg02", "cp02,mp02");
		ContentObject p02 = new ContentObject("p02", "Publication", "p02",
				"p02", "cp02,mp02,pg02");
		ContentObject cp03 = new ContentObject("cp03", "Campaign", "cp03",
				"cp03", "-1");
		ContentObject mp03 = new ContentObject("mp03", "MasterPublication",
				"mp03", "mp03", "cp03");
		ContentObject pg03 = new ContentObject("pg03", "PublicationGroup",
				"pg03", "pg03", "cp03,mp03");
		ContentObject p03 = new ContentObject("p03", "Publication", "p03",
				"p03", "cp03,mp03,pg03");
		ContentObject cp04 = new ContentObject("cp04", "Campaign", "cp04",
				"cp04", "-1");
		ContentObject mp04 = new ContentObject("mp04", "MasterPublication",
				"mp04", "mp04", "cp04");

		models.add(mp01);
		models.add(mp012);
		models.add(cp01);

		models.add(cp02);

		models.add(mp02);
		models.add(pg02);
		models.add(p02);
		models.add(cp03);
		models.add(mp03);
		models.add(pg03);
		models.add(p03);
		models.add(cp04);
		models.add(mp04);

	}

	@Test
	public void itShouldCreateMultipleDimensionGroupsForGivenModels() {

		cache = new DimensionGroupCache(inMemoryNosqlRepository);
		for (ContentObject dimension : models) {
			dimensionRepository = new DimensionRepository(null, cache,
					noSqlRepository);
			String test = dimensionRepository.createDimension(dimension);
			assertThat(test).isNotNull();
			assertThat(test).isEqualTo(dimension.getId());

		}

	}
}
