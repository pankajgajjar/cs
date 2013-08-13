package app.cs.cache;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import app.cs.cache.DimensionGroupCache;
import com.cs.data.core.nosql.redis.RedisRepository;
import app.cs.model.ContentObject;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DimensionGroupCacheTests {

	private ContentObject dimensionModel;
	private DimensionGroupCache groupCache;

	@Mock
	private RedisRepository redisRepository;

	@Before
	public void setUp() {

		groupCache = new DimensionGroupCache(redisRepository);
		dimensionModel = new ContentObject("test01", "campaign", "co01", "cp01","-1");
	}

	@Test
	public void itShouldGetDimensionGroupIdForGivenDimension() {

		// given

		String expectedId = "group01";
		String path = "testPath";
		// when

		when(redisRepository.get(path)).thenReturn(expectedId);
		String actualGroupId = groupCache.getDimensionGroupIdFor(path);
		// then
		verify(redisRepository).get(path);
		assertThat(actualGroupId).isEqualTo(expectedId);
	}

	@Test
	public void itShouldUpdateDimensionGroupIdForGivenDimension() {
		// given
		String key = "key";
		String value = "value";
		String groupId = "abc@123";
		// when
		groupCache.updateCache(dimensionModel, groupId);

		// then
		verify(redisRepository).delete(dimensionModel.getPath());
		verify(redisRepository)
				.set(dimensionModel.getPath().concat(
						"," + dimensionModel.getName()), groupId);
	}

	@Test
	public void itShouldSetDimensionGroupIdForGivenDimension() {
		// given
		String key = "key";
		String value = "value";
		String exptectedResult = "test";
		String groupId = "abc@123";
		// when
		groupCache.addNewGroup(dimensionModel, groupId);

		// then
		verify(redisRepository).set(dimensionModel.getName(), groupId);

	}

	@Test
	public void itShouldReturnFalseIfGroupIdIsAbsentInCache() {
		// given
		String path = "testKey";

		// when
		when(redisRepository.get(path)).thenReturn(null);
		boolean flag = groupCache.ifGroupIdExistsFor(path);

		// then
		verify(redisRepository).get(path);
		assertThat(flag).isEqualTo(false);
	}
}
