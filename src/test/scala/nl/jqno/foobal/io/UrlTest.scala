package nl.jqno.foobal.io

import java.net.URL

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class UrlTest extends FlatSpec with Matchers {
  behavior of "A Url"
  
  it should "have a correct implementation of equals and hashCode" in {
    EqualsVerifier.forClass(classOf[Url])
        .withPrefabValues(classOf[URL], new URL("http://google.com"), new URL("http://yahoo.com"))
        .verify
  }
}