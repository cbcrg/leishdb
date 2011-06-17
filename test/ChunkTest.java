import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;


public class ChunkTest extends FunctionalTest {

	@Test
	public void testChunck() { 
		Response response = GET("/chunk?echo=Hola");
		assertTrue( response.chunked );
		assertEquals( "Hola", response.out.toString() );
	}
	
	
}
