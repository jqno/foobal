package nl.jqno.foobal.io

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import nl.jqno.equalsverifier.EqualsVerifier
import java.net.URL

@RunWith(classOf[JUnitRunner])
class UrlTest extends FlatSpec with ShouldMatchers {
  behavior of "A Url"
  
  it should "have a correct implementation of equals and hashCode" in {
    EqualsVerifier.forClass(classOf[Url])
        .withPrefabValues(classOf[URL], new URL("http://google.com"), new URL("http://yahoo.com"))
        .verify
  }
}