package app.cs.impl.chapter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import app.cs.impl.chapter.InMemoryViewStructure;
import app.cs.interfaces.chapter.IInMemoryViewStructure;

import com.cs.data.core.nosql.redis.RedisRepository;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewStructureCacheUnitTests {

	private IInMemoryViewStructure cache;

	@Mock
	private RedisRepository repository;

	@Before
	public void setUp() {
		cache = new InMemoryViewStructure(repository);
	}

	@Test
	public void itShouldGetCurrentViewStructureFromCache() {
		// when
		String key = "view";

		String value = "value";
		when(repository.get(key)).thenReturn(value);
		String currentViewStructure = cache.getCurrentViewStructure();

		// then

		verify(repository).get(key);
		assertThat(currentViewStructure).isEqualTo(value);
	}

	@Test
	public void itShouldSetCurrentViewStructureFromCache() {
		// when
		String key = "view";

		String value = "value";
		cache.setCurrentViewStructure(key, value);

		// then

		verify(repository).set(key, value);
	}

}